package sprintBot1;
import battlecode.common.*;

public class Carrier extends Robot {
    int adamantium, mana, elixir;
    int fleeTurns = 0;
    int numGreedy = 0;
    int prevCrowdedTurn = 0;
    MapLocation wellTarget;
    MapLocation islandTarget;
    MapLocation depositLoc = null;
    MapLocation prevCrowdedWell;
    ResourceType chosen;
    ResourceType resourceNeeded;
    boolean hasAnchor = false;
    boolean allowCommedWells;
    boolean mineEfficently = false;
    boolean shouldDeliver = false;
    boolean initialGreedy = false;

    MapLocation[][] islandCache = new MapLocation[100][];
    enum State {
        SEARCHING,
        SEEKING,
        HARVESTING,
        DELIVERING,
        DELIVER_ANCHOR,
        FLEE,
        EXPLORE
    }

    public Carrier(RobotController rc) throws GameActionException {
        super(rc);
        communications.findOurHQs();
        initialGreedy = communications.getGreedy();
        if (rc.getRoundNum() <= 4) resourceNeeded = communications.getResourceInitial();
        else resourceNeeded = communications.getResourceNeed();
    }

    void run() throws GameActionException {
        //printWells();
        grab_anchor();
        allowCommedWells = true;//rc.getRoundNum() >= 25;
        // mineEfficently = rc.getRoundNum() >= 75;
        initialize();
        State state = determineState();
        rc.setIndicatorString(state.toString() + " "+resourceNeeded);
        communications.initial();
        attack();
        switch (state) {
            case SEARCHING: search(); break;
            case SEEKING: seek(); break;
            case HARVESTING: harvest(); break;
            case DELIVERING: deliver(); break;
            case DELIVER_ANCHOR: deliver_anchor(); break;
            case FLEE: flee(); break;
            case EXPLORE: explore(); break;
            default:
        }
        //System.out.println(Clock.getBytecodesLeft());
        communications.last();
    }

    void printWells() throws GameActionException {
        String s = "";
        ResourceType[] res = {ResourceType.ADAMANTIUM, ResourceType.MANA};
        for (int i = 0; i < 2; i++) {
            s += ""+res[i]+": ";
            MapLocation[] wells = communications.readWells(res[i]);
            for (MapLocation m: wells) {
                if (m == null) continue;
                s += m+" ";
            }
            s+="\n";
        }
        rc.setIndicatorString(s);
    }

    void initialize() {
        adamantium = rc.getResourceAmount(ResourceType.ADAMANTIUM);
        mana = rc.getResourceAmount(ResourceType.MANA);
        elixir = rc.getResourceAmount(ResourceType.ELIXIR);
    }

    // No Seeking state until we have comms.
    State determineState() throws GameActionException {
        for (RobotInfo r: rc.senseNearbyRobots(-1)) {
            if (r.team == rc.getTeam().opponent() && Util.isAttacker(r.type)) {
                fleeTurns = 3;
                return State.FLEE;
            }
        }

        if (fleeTurns > 0) {
            fleeTurns--;
            if (adamantium + mana + elixir > 10) {
                shouldDeliver = true;
            }
            return State.FLEE;
        }

        if (rc.getRoundNum() <= 10 && !initialGreedy) return State.EXPLORE;

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

    MapLocation prev = null;
    void explore() throws GameActionException {
        MapLocation[] locs = rc.getAllLocationsWithinRadiusSquared(rc.getLocation(), 8);
        int bestD = -1;
        MapLocation best = rc.getLocation();
        for (MapLocation m: locs) {
            if (m.distanceSquaredTo(rc.getLocation()) <= 2) continue;
            if (rc.canSenseLocation(m)) {
                if (!rc.sensePassability(m)) continue;
            }
            int distHQ = m.distanceSquaredTo(communications.findClosestHQ());
            if (distHQ > RobotType.HEADQUARTERS.visionRadiusSquared + 20) continue;
            int distPrev = 0;
            if (prev != null) {
                distPrev = m.distanceSquaredTo(prev);
            }
            if (distHQ + distPrev > bestD) {
                bestD = distHQ + distPrev;
                best = m;
            }
        }
        if (best != null) {
            prev = rc.getLocation();
            greedyPath.move(best);
            greedyPath.move(best);
        }
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
        if (wellTarget != null) {
            seek();
            return;
        }
        exploration.move(communications.HQs, communications.numHQ);
        exploration.move(communications.HQs, communications.numHQ);
    }

    void seek() throws GameActionException {
        rc.setIndicatorString("WellTarget: "+wellTarget);
        // initially, we don't know all the wells. re-evaluate target regularly.
        if (rc.getRoundNum() <= 15) findTarget();
        if (rc.getLocation().distanceSquaredTo(wellTarget) > 2) greedyPath.move(wellTarget);
        if (rc.getLocation().distanceSquaredTo(wellTarget) > 2) greedyPath.move(wellTarget);
        // recompute if crowded.
        if (rc.canSenseLocation(wellTarget)) {
            WellInfo w = rc.senseWell(wellTarget);
            int count = 0;
            RobotInfo[] bots = rc.senseNearbyRobots(w.getMapLocation(), 2, rc.getTeam());
            for (RobotInfo r: bots) {
                if (r.type == RobotType.CARRIER) 
                    count++;
            }

            int blocked = 0;
            for (Direction d: directions) {
                MapLocation a = wellTarget.add(d);
                if (rc.canSenseLocation(a)) {
                    if (!rc.sensePassability(a) && !rc.isLocationOccupied(a))
                        blocked++;
                }
            }

            if (count >= (9 - blocked)) {
                wellTarget = null;
                if (resourceNeeded == ResourceType.MANA) {
                    resourceNeeded = ResourceType.ADAMANTIUM;
                } else
                    resourceNeeded = ResourceType.MANA;
                prevCrowdedWell = wellTarget;
                prevCrowdedTurn = rc.getRoundNum();
                findTarget();
            }
        }
    }

    void harvest() throws GameActionException {
        // greedyPath.move(wellTarget);
        Direction wellDir = rc.getLocation().directionTo(wellTarget);
        if (rc.canMove(wellDir)) rc.move(wellDir);
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
                if (prevCrowdedWell != null && rc.getRoundNum() - 10 < prevCrowdedTurn && 
                    w.getMapLocation().equals(prevCrowdedWell)) continue;
                wellTargets[ind++] = new WellTarget(w.getMapLocation(), w.getResourceType());
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
                //rc.setIndicatorString(""+best.loc+" c: "+best.harvestersNear);
                wellTarget = best.loc;
                return;
            }

            if (allowCommedWells) {
                for (MapLocation m: communications.readWells(r)) {
                    if (Clock.getBytecodesLeft() < 6000) break;
                    if (m == null) continue;
                    if (m.distanceSquaredTo(rc.getLocation()) <= RobotType.CARRIER.visionRadiusSquared) continue;
                    if (prevCrowdedWell != null && rc.getRoundNum() - 10 < prevCrowdedTurn && 
                        m.equals(prevCrowdedWell)) continue;
                    WellTarget cur = new WellTarget(m, r);
                    if (cur.isBetterThan(best)) best = cur;
                }
                if (best != null && !best.crowded()) {
                    wellTarget = best.loc;
                    return;
                }
            }
            // bash through all resources.
            r = ResourceType.values()[(r.ordinal() + 2)%3];
            break;
            // if (!mineEfficently) break;
        }
    }

    class WellTarget {
        MapLocation loc;
        int harvestersNear;
        int blocked = 0;
        int distHQ;
        double dist;
        ResourceType r;
        WellTarget(MapLocation m, ResourceType res) throws GameActionException{
            loc = m;
            dist = Util.absDistance(loc, rc.getLocation());
            distHQ = loc.distanceSquaredTo(communications.findClosestHQto(loc));
            harvestersNear = 0;
            this.r = res;

            // Count number of blocked tiles.
            for (Direction d: directions) {
                MapLocation a = loc.add(d);
                if (rc.canSenseLocation(a)) {
                    if (!rc.sensePassability(a) && !rc.isLocationOccupied(a))
                        blocked++;
                }
            }
        }

        void updateCarrier(RobotInfo r) {
            if (crowded()) return;
            if (r.location.distanceSquaredTo(loc) <= 4) {
                harvestersNear++;
            }
        }

        boolean crowded() {
            return harvestersNear >= (9 - blocked);
        }

        boolean bestResource() throws GameActionException {
            return r == resourceNeeded;
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
