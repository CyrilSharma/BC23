package sprintBot1;
import battlecode.common.*;

// TODO some weird errors with Home being confused with nearest HQ.

public class Carrier extends Robot {
    int adamantium, mana, elixir;
    MapLocation wellTarget;
    MapLocation islandTarget;
    MapLocation depositLoc = null;
    // will eventually be replaced with comms.
    int fleeTurns = 0;
    MapLocation home;
    boolean hasAnchor = false;
    enum State {
        SEARCHING,
        SEEKING,
        HARVESTING,
        DELIVERING,
        DELIVER_ANCHOR,
        FLEE
    }
    public Carrier(RobotController rc) throws GameActionException {
        super(rc);
        communications.findOurHQs();
        findHome();
    }

    void run() throws GameActionException {
        initialize();
        State state = determineState();
        rc.setIndicatorString(state.toString());
        
        communications.initial();
        handleDeath();
        switch (state) {
            case SEARCHING: search(); break;
            case SEEKING: seek(); break;
            case HARVESTING: harvest(); break;
            case DELIVERING: deliver(); break;
            case DELIVER_ANCHOR: deliver_anchor(); break;
            case FLEE: flee(); break;
            default:
        }
    }

    void initialize() {
        adamantium = rc.getResourceAmount(ResourceType.ADAMANTIUM);
        mana = rc.getResourceAmount(ResourceType.MANA);
        elixir = rc.getResourceAmount(ResourceType.ELIXIR);
    }

    // No Seeking state until we have comms.
    State determineState() throws GameActionException {
        grab_anchor();
        for (RobotInfo r: rc.senseNearbyRobots(-1, rc.getTeam().opponent())) {
            if (Util.isAttacker(r.type)) {
                fleeTurns = 3x;
                return State.FLEE;
            }
        }
        if (fleeTurns > 0) {
            fleeTurns--;
            return State.FLEE;
        }
        //System.out.println("good resource: " + communications.readResourceNeed());
        if (hasAnchor) return State.DELIVER_ANCHOR;
        if (wellTarget == null && 
            adamantium == 0 &&
            mana == 0 && 
            elixir == 0)
            return State.SEARCHING;
        
        if (wellTarget != null && 
            rc.getLocation().distanceSquaredTo(wellTarget) > 2)
            return State.SEEKING;

        if (adamantium + mana + elixir < 39 && wellTarget != null) {
            return State.HARVESTING;
        }
        if (adamantium + mana + elixir > 0) {
            return State.DELIVERING;
        }
        return State.SEARCHING;
    }

    void flee() throws GameActionException {
        findTarget();
        greedyPath.flee();
    }

    void search() throws GameActionException{
        findTarget();
        exploration.move();
        exploration.move();
    }

    void seek() throws GameActionException {
        greedyPath.move(wellTarget);
        greedyPath.move(wellTarget);
        checkBetterTarget();
    }
    //need to redo this part

    void findTarget() throws GameActionException{
        //basically the idea is, we flip a coin
        //and based on that, check for certain resources
        WellInfo[] wells = rc.senseNearbyWells();
        if (wells.length > 0) {
            WellTarget best = null;
            for(WellInfo w : wells){
                WellTarget cur = new WellTarget(w);
                if (cur.isBetterThan(best)) best = cur;
            }
            // if its crowded and not a good resource use comms.
            if (!best.crowded() && best.r == communications.readResourceNeed())
                wellTarget = best.loc;
            else
                wellTarget = communications.findBestWell();
        }
        else wellTarget = communications.findBestWell(); //null if nothing found
    }

    void checkBetterTarget() throws GameActionException{
        WellInfo[] wells = rc.senseNearbyWells();
        //int ada = communications.getAdamantiumReq(), mana = communications.getManaReq();
        if (wells.length > 0) {
            MapLocation bst = null;
            int dist = rc.getLocation().distanceSquaredTo(wellTarget);
            for(WellInfo w : wells){
                if(w.getResourceType() != communications.readResourceNeed()) continue;
                if(rc.getLocation().distanceSquaredTo(w.getMapLocation()) < dist){
                   WellTarget uwu = new WellTarget(w);
                   if(!uwu.crowded()){
                       bst = uwu.loc;
                       dist = rc.getLocation().distanceSquaredTo(uwu.loc);
                   }
                }
            }
            if (bst != null) wellTarget = bst;
        }
    }

    class WellTarget {
        MapLocation loc;
        int harvestersNear;
        int distance;
        ResourceType r;
        WellTarget(WellInfo w) {
            loc = w.getMapLocation();
            distance = loc.distanceSquaredTo(rc.getLocation());
            harvestersNear = 0;
            RobotInfo[] robots = rc.senseNearbyRobots();
            for (RobotInfo r: robots) {
                if (r.type != RobotType.CARRIER) continue;
                if (r.location.distanceSquaredTo(loc) <= 4) {
                    harvestersNear++;
                }
            }
            r = w.getResourceType();
        }

        boolean crowded() {
            return harvestersNear>7;
        }

        boolean bestResource() throws GameActionException {
            return r == communications.readResourceNeed();
        }

        boolean isBetterThan(WellTarget wt) throws GameActionException {
            if (wt == null) return true;
            if (wt.bestResource() && !bestResource()) return false;
            if (!wt.bestResource() && bestResource()) return true;
            if (!wt.crowded() && crowded()) return false;
            if (wt.crowded() && !crowded()) return true;
            return distance <= wt.distance; 
        }
    }

    // TODO: add some evasive maneuvers
    void harvest() throws GameActionException {
        WellInfo wi = rc.senseWell(wellTarget);
        ResourceType rt = wi.getResourceType();
        if (rc.canCollectResource(wellTarget, 39-(adamantium + mana + elixir))) {
            rc.collectResource(wellTarget, 39-(adamantium + mana + elixir));
            wellTarget = null;
        } else if (rc.canCollectResource(wellTarget, -1)) {
            rc.collectResource(wellTarget, -1);
        }
    }

    void deliver() throws GameActionException {
        int dist = 1000000;
        for(int i = 0; i < communications.numHQ; i++){
            if(rc.getLocation().distanceSquaredTo(communications.HQs[i]) < dist){
                depositLoc = communications.HQs[i];
                dist = rc.getLocation().distanceSquaredTo(communications.HQs[i]);
            }
        }
        if (depositLoc.distanceSquaredTo(rc.getLocation()) > 1) {
            greedyPath.move(depositLoc);
        } else {
            ResourceType[] resources = {ResourceType.ADAMANTIUM, ResourceType.ELIXIR, ResourceType.MANA};
            for (ResourceType r: resources) {
                if (rc.getResourceAmount(r) == 0) continue;
                if (rc.canTransferResource(depositLoc, r, rc.getResourceAmount(r)))
                    rc.transferResource(depositLoc, r, rc.getResourceAmount(r));
            }
        }
    }

    void grab_anchor() throws GameActionException {
        if (depositLoc == null) return;
        if (rc.canTakeAnchor(depositLoc, Anchor.STANDARD)) {
            rc.takeAnchor(depositLoc, Anchor.STANDARD);
            hasAnchor = true;
        }
    }

    void deliver_anchor() throws GameActionException {
        if (islandTarget == null) {
            islandTarget = findIslandTarget();
            if (islandTarget == null) 
                exploration.move();
        }
        if (islandTarget != null) {
            if (rc.getLocation().distanceSquaredTo(islandTarget) > 0) {
                greedyPath.move(islandTarget);
            } else {
                if (rc.canPlaceAnchor()) {
                    rc.placeAnchor();
                    islandTarget = null;
                    hasAnchor = false;
                }
            }
        }
    }

    MapLocation findIslandTarget() throws GameActionException {
        int[] islands = rc.senseNearbyIslands();
        MapLocation closestTarget = null;
        int d = 100000;
        for (int idx: islands) {
            if (rc.senseAnchor(idx) != null) continue;
            MapLocation[] spots = rc.senseNearbyIslandLocations(idx);
            for (MapLocation spot: spots) {
                if (rc.getLocation().distanceSquaredTo(spot) < d) {
                    closestTarget = spot;
                }
            }
        }
        return closestTarget;
    }

    void findHome() throws GameActionException {
        RobotInfo[] robots = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam());
        for (RobotInfo r: robots) {
            if (r.type == RobotType.HEADQUARTERS) {
                home = r.location;
                break;
            }
        }
    }

    void handleDeath() throws GameActionException {
        double dps_targetting = 0.0;
        RobotInfo[] enemies = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        AttackTarget best = null;
        for (RobotInfo e : enemies) {
            if (!Util.isAttacker(e.type)) continue;
            MapLocation m = e.location;
            double mult = rc.senseCooldownMultiplier(m);
            int d = rc.getLocation().distanceSquaredTo(m);
            if (d <= e.type.visionRadiusSquared)
                dps_targetting += e.type.damage * (1.0 / mult);
            
            AttackTarget cur = new AttackTarget(e);
            if (cur.isBetterThan(best)) best = cur;
        }
        if (dps_targetting >= 0.8 * (double) rc.getHealth()) {
            if (rc.canAttack(best.loc) && 
                (adamantium + mana + elixir) > 5) {
                rc.attack(best.loc);
            }
        }
    }

    class AttackTarget {
        MapLocation loc;
        boolean inRange;
        int priority;
        int health;

        AttackTarget(RobotInfo r) {
            loc = r.location;
            switch (r.getType()) {
                case BOOSTER: priority=4; break;
                case AMPLIFIER: priority=3; break;
                case HEADQUARTERS: priority=1; break;
                case CARRIER: priority=2; break;
                case LAUNCHER: priority=6; break;
                case DESTABILIZER: priority=5; break;
            }
            health = r.health;
            inRange = (rc.getLocation().distanceSquaredTo(loc) < rc.getType().actionRadiusSquared);
        }

        boolean isBetterThan(AttackTarget at) {
            if (at == null) return true;
            if (at.inRange && !inRange) return false;
            if (!at.inRange && inRange) return true;
            if (at.priority > priority) return false;
            if (at.priority < priority) return true;
            return health <= at.health;
        }
    }
}
