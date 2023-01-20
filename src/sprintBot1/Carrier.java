package sprintBot1;
import battlecode.common.*;

public class Carrier extends Robot {
    int adamantium, mana, elixir;
    MapLocation wellTarget;
    MapLocation islandTarget;
    MapLocation depositLoc = null;
    ResourceType chosen;
    ResourceType resourceNeeded;
    // will eventually be replaced with comms.
    int fleeTurns = 0;
    MapLocation home;
    boolean hasAnchor = false;
    boolean allowCommedWells;
    boolean mineEfficently = false;
    MapLocation[][] islandCache = new MapLocation[100][];
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
        resourceNeeded = communications.readResourceNeed();
    }

    void run() throws GameActionException {
        grab_anchor();
        allowCommedWells = true;//rc.getRoundNum() >= 25;
        //mineEfficently = rc.getRoundNum() >= 75;
        //rc.disintegrate();
        initialize();
        State state = determineState();
        rc.setIndicatorString(state.toString());
        communications.initial();
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
        //System.out.println(Clock.getBytecodesLeft());
        communications.last();
    }

    void initialize() {
        adamantium = rc.getResourceAmount(ResourceType.ADAMANTIUM);
        mana = rc.getResourceAmount(ResourceType.MANA);
        elixir = rc.getResourceAmount(ResourceType.ELIXIR);
    }

    // No Seeking state until we have comms.
    State determineState() throws GameActionException {
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
            wellTarget = null;
            resourceNeeded = communications.readResourceNeed();
            return State.DELIVERING;
        }
        return State.SEARCHING;
    }

    void flee() throws GameActionException {
        doMine();
        findTarget();
        greedyPath.flee();
        greedyPath.flee();
        doMine();
    }

    void doMine() throws GameActionException {
        for (WellInfo w: rc.senseNearbyWells()) {
            while (rc.canCollectResource(w.getMapLocation(), 1)) {
                if (rc.canCollectResource(w.getMapLocation(), 7-(adamantium+mana+elixir))) {
                    rc.collectResource(w.getMapLocation(), 7-(adamantium+mana+elixir));
                } else if (rc.canCollectResource(w.getMapLocation(), -1)) {
                    rc.collectResource(w.getMapLocation(), -1);
                }
            }
        }
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
        // recompute if crowded.
        if (wellTarget.distanceSquaredTo(rc.getLocation()) <= 9) {
            if (rc.canSenseLocation(wellTarget)) {
                WellInfo w = rc.senseWell(wellTarget);
                int count = 0;
                RobotInfo[] bots = rc.senseNearbyRobots(w.getMapLocation(), 2, rc.getTeam());
                for (RobotInfo r: bots) {
                    if (r.type == RobotType.CARRIER) count++;
                }
                if (count > 7) findTarget();
            }
        }
        greedyPath.move(wellTarget);
        greedyPath.move(wellTarget);
    }

    void findTarget() throws GameActionException {
        int iters = 0;
        ResourceType r = resourceNeeded;
        RobotInfo[] friends = rc.senseNearbyRobots(-1, rc.getTeam());
        WellInfo[] wells = rc.senseNearbyWells();
        WellTarget[] wellTargets = new WellTarget[wells.length + 15];
        while (iters < 3) {
            if (Clock.getBytecodesLeft() < 6000) break;

            chosen = r;
            int ind = 0;
            WellTarget best = null;
            for (WellInfo w : wells){
                if (Clock.getBytecodesLeft() < 6000) break;
                if (w.getResourceType() != r) continue;
                // don't die.
                wellTargets[ind] = new WellTarget(w.getMapLocation(), w.getResourceType());
                ind++;
            }

            for (RobotInfo f: friends) {
                if (Clock.getBytecodesLeft() < 6000) break;
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
                    if (Clock.getBytecodesLeft() < 6000) break;
                    if (m == null) continue;
                    WellTarget cur = new WellTarget(m, r);
                    if (cur.isBetterThan(best)) best = cur;
                }
                if (best != null && !best.crowded() && best.dist < 12) {
                    wellTarget = best.loc;
                    return;
                }
            }
            // bash through all resources.
            r = ResourceType.values()[(r.ordinal() + 2)%3];
            if (!mineEfficently) break;
        }
    }

    // TODO: add some evasive maneuvers
    void harvest() throws GameActionException {
        while (rc.isActionReady()) {
            if (rc.canCollectResource(wellTarget, 39-(adamantium + mana + elixir))) {
                rc.collectResource(wellTarget, 39-(adamantium + mana + elixir));
                if (!rc.isActionReady()) {
                    wellTarget = null;
                }
            } else if (rc.canCollectResource(wellTarget, -1)) {
                rc.collectResource(wellTarget, -1);
            }
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
            //System.out.println(Clock.getBytecodesLeft());
            islandTarget = findIslandTarget();
            //System.out.println(Clock.getBytecodesLeft());
            if (islandTarget == null) {
                //moveTowardsSoldiers();
                exploration.move(communications.HQs, communications.numHQ);
            }
        } else {
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

    void moveTowardsSoldiers() throws GameActionException {
        if (!rc.isMovementReady()) return;
        int[] dists = new int[9];
        for (Direction d: directions) {
            dists[d.ordinal()] = 0;
        }
        MapLocation loc = rc.getLocation();
        for (RobotInfo r: rc.senseNearbyRobots(-1, rc.getTeam())) {
            if (r.type != RobotType.LAUNCHER) continue;
            if (Clock.getBytecodesLeft() < 7000) break;
            for (Direction d: directions) {
                if (!rc.canMove(d)) continue;
                MapLocation m = loc.add(d);
                if (rc.canSenseLocation(m)) {
                    MapInfo mi = rc.senseMapInfo(m);
                    m.add(mi.getCurrentDirection());
                }
                dists[d.ordinal()] += r.location.distanceSquaredTo(m);
            }
        }
        int bestD = dists[0];
        Direction best = null;
        for (Direction d: directions) {
            if (dists[d.ordinal()] < bestD) {
                best = d;
                bestD = dists[d.ordinal()];
            }
        }
        //System.out.println(Clock.getBytecodesLeft());
        if (best == null) {
            // too expensive.
            exploration.move(communications.HQs, communications.numHQ);
            //System.out.println("end: " + Clock.getBytecodesLeft());
        } else if (rc.canMove(best)) 
            rc.move(best);
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

    // it's not a massive advantage to place it near soldiers [maybe near carrier??!]
    /* MapLocation findIslandTarget() throws GameActionException {
        RobotInfo[] friends = rc.senseNearbyRobots(-1, rc.getTeam());
        if (friends.length <= 3) return null;
        int[] islands = rc.senseNearbyIslands();
        if (islands.length == 0) return null;
        IslandTarget best = null;
        for (int idx: islands) {
            if (Clock.getBytecodesLeft() < 9000) break;
            if (rc.senseAnchor(idx) != null) continue;

            // caching bc this is insanely expensive.
            MapLocation[] spots;
            if (islandCache[idx] == null) {
                spots = rc.senseNearbyIslandLocations(idx);
                islandCache[idx] = spots;
            } else {
                spots = islandCache[idx];
            }

            IslandTarget[] targets = new IslandTarget[spots.length];
            for (int i = 0; i < spots.length; i++) {
                targets[i] = new IslandTarget(spots[i]);
            }
            for (RobotInfo f: friends) {
                if (Clock.getBytecodesLeft() < 8000) break;
                if (targets.length == 0) break;
                if (f.type != RobotType.LAUNCHER) continue;
                for (IslandTarget t: targets) 
                    t.updateSoldier(f);
            }
            for (int i = 0; i < spots.length; i++) {
                if (targets[i].isBetterThan(best)) best = targets[i];
            }
        }
        if (best == null) return null;
        if (best.soldiersNear < 5 && rc.getRoundNum() <= 750) return null;
        return best.loc;
    } */

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
            if (crowded()) return;
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

    class IslandTarget {
        MapLocation loc;
        int soldiersNear;
        double dist;
        IslandTarget(MapLocation m) throws GameActionException{
            loc = m;
            dist = Util.absDistance(loc, rc.getLocation());
            soldiersNear = 0;
        }

        void updateSoldier(RobotInfo r) {
            if (r.location.distanceSquaredTo(loc) <= 4) {
                soldiersNear++;
            }
        }

        boolean isBetterThan(IslandTarget it) throws GameActionException {
            if (it == null) return true;
            if (it.soldiersNear > soldiersNear) return false;
            if (it.soldiersNear < soldiersNear) return true;
            return dist <= it.dist;
        }
    }
}
