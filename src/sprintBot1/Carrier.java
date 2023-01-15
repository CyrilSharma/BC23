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
    private boolean shouldDeliver = false;
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
        //rc.setIndicatorString("need: " + communications.readResourceNeed());
        attack();
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
                fleeTurns = 3;
                return State.FLEE;
            }
        }

        // fleeing but current seeing no enemies, deposit ur stuff.
        if (shouldDeliver) return State.DELIVERING;
        if (fleeTurns > 0) {
            fleeTurns--;
            if (adamantium + mana + elixir > 10) {
                shouldDeliver = true;
            }
            else return State.FLEE;
        }

        //System.out.println("good resource: " + communications.readResourceNeed());
        if (hasAnchor) return State.DELIVER_ANCHOR;
        if (wellTarget == null && 
            adamantium == 0 &&
            mana == 0 && 
            elixir == 0) {
            return State.SEARCHING;
        }
        
        if (wellTarget != null && 
            rc.getLocation().distanceSquaredTo(wellTarget) > 2) {
            return State.SEEKING;
        }

        if ((adamantium + mana + elixir) < 39 && 
            wellTarget != null) {
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
        if(wellTarget != null){
            seek();
            return;
        }
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
                int d = w.getMapLocation().distanceSquaredTo(communications.findClosestHQto(w.getMapLocation()));
                if(rc.getLocation().distanceSquaredTo(w.getMapLocation()) + d < dist){
                   WellTarget uwu = new WellTarget(w);
                   if(!uwu.crowded()){
                       bst = uwu.loc;
                       dist = rc.getLocation().distanceSquaredTo(uwu.loc) + d;
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
        WellTarget(WellInfo w) throws GameActionException{
            loc = w.getMapLocation();
            distance = loc.distanceSquaredTo(rc.getLocation()) + loc.distanceSquaredTo(communications.findClosestHQto(loc));
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
            communications.resourceNeeded = null;
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
                if (rc.canTransferResource(depositLoc, r, rc.getResourceAmount(r))) {
                    rc.transferResource(depositLoc, r, rc.getResourceAmount(r));
                    shouldDeliver = false;
                }
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

    void attack() throws GameActionException {
        RobotInfo best = util.getBestAttackTarget();
        if (best == null) return;
        if (!Util.isAttacker(best.type)) return;
        if (rc.canAttack(best.location)) {
            int total = adamantium + mana + elixir;
            if (total > 5 && total < 20) {
                rc.attack(best.location);
                shouldDeliver = false;
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
