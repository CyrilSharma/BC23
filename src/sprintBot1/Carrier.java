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
    boolean allowCommedWells;
    boolean mineEfficently;
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
        allowCommedWells = true;//rc.getRoundNum() >= 25;
        mineEfficently = rc.getRoundNum() >= 75;
        //rc.disintegrate();
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
        communications.last();
    }

    void initialize() {
        adamantium = rc.getResourceAmount(ResourceType.ADAMANTIUM);
        mana = rc.getResourceAmount(ResourceType.MANA);
        elixir = rc.getResourceAmount(ResourceType.ELIXIR);
    }

    // No Seeking state until we have comms.
    State determineState() throws GameActionException {
        grab_anchor();
        int attackerHealth = 0;
        int defenderHealth = 0;
        for (RobotInfo r: rc.senseNearbyRobots(-1)) {
            if (r.team == rc.getTeam().opponent() && Util.isAttacker(r.type)) {
                attackerHealth += r.health;
            }
            if (r.team == rc.getTeam() && Util.isAttacker(r.type)) {
                defenderHealth += r.health;
            }
        }
        if (attackerHealth > 0) {
            fleeTurns = 3;
            return State.FLEE;
        }

        if (fleeTurns > 0) {
            fleeTurns--;
            if (adamantium + mana + elixir > 10) {
                shouldDeliver = true;
            }
            return State.FLEE;
        }
        if (shouldDeliver) return State.DELIVERING;

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
        exploration.move(communications.HQs, communications.numHQ);
        exploration.move(communications.HQs, communications.numHQ);
    }

    void seek() throws GameActionException {
        greedyPath.move(wellTarget);
        greedyPath.move(wellTarget);
        // recompute target iff crowded.
        if (wellTarget.distanceSquaredTo(rc.getLocation()) <= 9) {
            if (rc.canSenseLocation(wellTarget)) {
                WellInfo w = rc.senseWell(wellTarget);
                int count = 0;
                RobotInfo[] bots = rc.senseNearbyRobots(w.getMapLocation(), 2, rc.getTeam());
                for (RobotInfo r: bots) {
                    if (r.type == RobotType.CARRIER) count++;
                }
                if (count > 4) findTarget();
            }
        }

    }

    void findTarget() throws GameActionException {
        int iters = 0;
        ResourceType r = communications.readResourceNeed();
        RobotInfo[] friends = rc.senseNearbyRobots(-1, rc.getTeam());
        WellInfo[] wells = rc.senseNearbyWells();
        WellTarget[] wellTargets = new WellTarget[wells.length + 15];
        while (iters < 3) {
            int ind = 0;
            WellTarget best = null;
            for (WellInfo w : wells){
                if (Clock.getBytecodesLeft() < 5000) break;
                if (w.getResourceType() != r) continue;
                wellTargets[ind] = new WellTarget(w.getMapLocation(), w.getResourceType());
                ind++;
            }

            for (RobotInfo f: friends) {
                if (f.type != RobotType.CARRIER) continue;
                for (int i = 0; i < ind; i++)
                    wellTargets[i].updateCarrier(f);
            }

            for (int i = 0; i < ind; i++) {
                if (wellTargets[i].isBetterThan(best)) 
                    best = wellTargets[i];
            }

            if (best != null && !best.crowded()) {
                wellTarget = best.loc;
                return;
            }

            if (allowCommedWells) {
                for (MapLocation m: communications.readWells(r)) {
                    if (Clock.getBytecodesLeft() < 5000) break;
                    if (m == null) continue;
                    WellTarget cur = new WellTarget(m, r);
                    if (cur.isBetterThan(best)) best = cur;
                }
                if (best != null && !best.crowded()) {
                    wellTarget = best.loc;
                    return;
                }
            }
            // bash through all resources.
            r = ResourceType.values()[(r.ordinal() + 1)%3];
            if (!mineEfficently) break;
        }
    }

    class WellTarget {
        MapLocation loc;
        int harvestersNear;
        int distHQ;
        double dist;
        ResourceType r;
        WellTarget(MapLocation m, ResourceType res) throws GameActionException{
            loc = m;
            dist = Util.absDistance(loc, rc.getLocation());
            distHQ = loc.distanceSquaredTo(communications.findClosestHQto(loc));
            harvestersNear = 0;
            this.r = res;
        }

        void updateCarrier(RobotInfo r) {
            if (r.location.distanceSquaredTo(loc) <= 4) {
                harvestersNear++;
            }
        }

        boolean crowded() {
            return harvestersNear > 7;
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
            if (wt.dist + 12 < dist) return false;
            if (dist + 12 < wt.dist) return true;
            return distHQ <= wt.distHQ; 
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
                exploration.move(communications.HQs, communications.numHQ);
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
            if (Clock.getBytecodesLeft() < 1000) break;
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
            if (total > 7) {
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
