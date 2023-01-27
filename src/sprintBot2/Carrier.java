package sprintBot2;
import battlecode.common.*;
import battlecode.world.Well;

public class Carrier extends Robot {
    int adamantium, mana, elixir;
    int fleeTurns = 0;
    int numGreedy = 0;
    int prevBadTurn = 0;
    int available = -1;
    int targetRound = -1;
    int born;
    MapLocation prevWellTarget;
    MapLocation wellTarget;
    MapLocation islandTarget;
    MapLocation depositLoc = null;
    MapLocation prevBadWell;
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
        resourceNeeded = communications.getResourceNeed();
        born = rc.getRoundNum();
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
        rc.setIndicatorString("WT: "+wellTarget+" S: "+state.toString());
        communications.initial();
        //printWells();
        updateSaturation();
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
        communications.last();
    }

    void updateSaturation() throws GameActionException {
        if (!rc.canWriteSharedArray(0, 0)) return;
        if (available != -1) {
            communications.updateAvailability(prevWellTarget, available);
            available = -1;
        }
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

    State determineState() throws GameActionException {
        for (RobotInfo r: rc.senseNearbyRobots(-1)) {
            if (r.team == rc.getTeam().opponent() && Util.isAttacker(r.type)) {
                fleeTurns = 3;
                prevBadWell = wellTarget;
                prevBadTurn = rc.getRoundNum();
                return State.FLEE;
            }
        }

        if (fleeTurns > 0) {
            fleeTurns--;
            if (adamantium + mana + elixir > 10) {
                shouldDeliver = true;
            }
            else resourceNeeded = communications.getResourceNeed();
            return State.FLEE;
        }

        if (hasAnchor) return State.DELIVER_ANCHOR;
        if (born%10 == 0 && rc.getRoundNum() - born <= 10) return State.EXPLORE;
        if (shouldDeliver) return State.DELIVERING;

        //System.out.println("good resource: " + communications.readResourceNeed());
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
        if (born%10 == 0) exploreSafe();
        else exploreUnsafe();
    }

    void exploreUnsafe() throws GameActionException {
        exploration.move(communications.HQs, communications.numHQ);
        exploration.move(communications.HQs, communications.numHQ);
    }

    void exploreSafe() throws GameActionException {
        MapLocation[] locs = rc.getAllLocationsWithinRadiusSquared(rc.getLocation(), 8);
        double bestD = -1;
        double d = Math.sqrt(RobotType.HEADQUARTERS.visionRadiusSquared) + 6;
        MapLocation best = rc.getLocation();
        for (MapLocation m: locs) {
            if (m.distanceSquaredTo(rc.getLocation()) <= 2) continue;
            if (rc.canSenseLocation(m)) {
                if (!rc.sensePassability(m)) continue;
            }
            double distHQ = Util.absDistance(m, communications.findClosestHQ());
            if (distHQ > d) continue;
            double distPrev = 0;
            if (prev != null) {
                distPrev = Util.absDistance(m, prev);
            }
            double carrierDist = 0;
            for (RobotInfo r : rc.senseNearbyRobots(-1, rc.getTeam())) {
                if (r.type != RobotType.CARRIER) continue;
                carrierDist += Util.absDistance(r.location, m);
            }
            if (distHQ + carrierDist + 2 * distPrev > bestD) {
                bestD = distHQ + carrierDist + 2 * distPrev;
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
        MapLocation m = communications.findClosestHQ();
        for (int i = 1; i <= 3; i++) {
            ResourceType r = ResourceType.values()[i];
            if (rc.canTransferResource(m, r, rc.getResourceAmount(r))) {
                rc.transferResource(m, r, rc.getResourceAmount(r));
                shouldDeliver = false;
                resourceNeeded = communications.getResourceNeed();
            }
        }
        if (m.distanceSquaredTo(rc.getLocation()) <= 9 && (mana + adamantium + elixir > 20)) {
            greedyPath.move(m);
            greedyPath.move(m);
        } else {
            // shouldn't flee towards where it just got attacked.
            greedyPath.flee();
            greedyPath.flee();
            // if (!greedyPath.flee()) greedyPath.move(m);
            // if (!greedyPath.flee()) greedyPath.move(m);
        }
        if (rc.canPlaceAnchor()) {
            rc.placeAnchor();
            islandTarget = null;
            hasAnchor = false;
        }
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
        explore();
        //exploration.move(communications.HQs, communications.numHQ);
        //exploration.move(communications.HQs, communications.numHQ);
    }

    void seek() throws GameActionException {
        // initially, we don't know all the wells. re-evaluate target regularly.
        // if (rc.getRoundNum()%3 == 0) findTarget();
        if (rc.getRoundNum() - targetRound > 10) findTarget();
        rc.setIndicatorString("target is " + wellTarget);
        boolean f = (rc.getLocation().distanceSquaredTo(wellTarget) <= 2 && rc.getLocation().add(rc.senseMapInfo(rc.getLocation()).getCurrentDirection()).distanceSquaredTo(wellTarget) > 2);
        if (rc.getLocation().distanceSquaredTo(wellTarget) > 2 || f) greedyPath.move(wellTarget);
        f = (rc.getLocation().distanceSquaredTo(wellTarget) <= 2 && rc.getLocation().add(rc.senseMapInfo(rc.getLocation()).getCurrentDirection()).distanceSquaredTo(wellTarget) > 2);
        if (rc.getLocation().distanceSquaredTo(wellTarget) > 2 || f) greedyPath.move(wellTarget);
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
                /* if (resourceNeeded == ResourceType.MANA) {
                    resourceNeeded = ResourceType.ADAMANTIUM;
                } else
                    resourceNeeded = ResourceType.MANA; */
                prevBadWell = wellTarget;
                prevBadTurn = rc.getRoundNum();
                findTarget();
            }
        }
    }

    void harvest() throws GameActionException {
        // Make space!
        for (Direction d: directions) {
            MapLocation nloc = rc.getLocation().add(d);
            if (nloc.distanceSquaredTo(wellTarget) < 2 && nloc.add(rc.senseMapInfo(nloc).getCurrentDirection()).distanceSquaredTo(wellTarget) < 2) {
                if (rc.canMove(d)) rc.move(d);
                break;
            }
        }
        while (rc.isActionReady()) {
            if (rc.canCollectResource(wellTarget, 39-(adamantium + mana + elixir))) {
                rc.collectResource(wellTarget, 39-(adamantium + mana + elixir));
                // NOTE THIS ESTIMATE NEEDS TO ACCOUNT FOR SPACE AVAILABLE.
                available = estimateCarriers25Turns(rc.senseWell(wellTarget));
                prevWellTarget = wellTarget;
                wellTarget = null;
            } else if (rc.canCollectResource(wellTarget, -1)) {
                rc.collectResource(wellTarget, -1);
            }
        }
    }

    void deliver() throws GameActionException {
        MapLocation m = communications.findClosestHQ();
        if (m.distanceSquaredTo(rc.getLocation()) > 2 || rc.getLocation().add(rc.senseMapInfo(rc.getLocation()).getCurrentDirection()).distanceSquaredTo(m) > 2) {
            greedyPath.move(m);
        } else {
            ResourceType[] resources = {ResourceType.ADAMANTIUM, ResourceType.ELIXIR, ResourceType.MANA};
            for (ResourceType r: resources) {
                if (rc.getResourceAmount(r) == 0) continue;
                if (rc.canTransferResource(m, r, rc.getResourceAmount(r))) {
                    rc.transferResource(m, r, rc.getResourceAmount(r));
                    shouldDeliver = false;
                    resourceNeeded = communications.getResourceNeed();
                }
            }
        }
    }

    void grab_anchor() throws GameActionException {
        MapLocation m = communications.findClosestHQ();
        if (m == null) return;
        if (rc.canTakeAnchor(m, Anchor.STANDARD)) {
            rc.takeAnchor(m, Anchor.STANDARD);
            hasAnchor = true;
        }
    }

    int estimateCarriers25Turns(WellInfo well) throws GameActionException {
        RobotInfo[] robots = rc.senseNearbyRobots(-1, rc.getTeam());
        ResourceType wellType = well.getResourceType();
        int available = 0;
        for (Direction dir: directions) {
            MapLocation m = well.getMapLocation().add(dir);
            if (rc.canSenseLocation(m) && rc.sensePassability(m) && 
                !rc.isLocationOccupied(m)) {
                available++;
            }
        }
        for (RobotInfo r: robots) {
            if (r.type != RobotType.CARRIER) continue;
            double mineRate = (10.0 / RobotType.CARRIER.actionCooldown);
            int resourceRemaining = (GameConstants.CARRIER_CAPACITY-1) - r.getResourceAmount(wellType);
            if (resourceRemaining / mineRate <= 5)
                available++;
        }   
        return available;
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
        wellTarget = communications.findBestWell(resourceNeeded);
        targetRound = rc.getRoundNum();
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
