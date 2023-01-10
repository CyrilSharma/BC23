package soldier_micro;
import battlecode.common.*;
import battlecode.world.Inventory;

public class Carrier extends Robot {
    int adamantium, mana, elixir;
    MapLocation wellTarget;
    MapLocation islandTarget;
    // will eventually be replaced with comms.
    MapLocation home;
    boolean homeHasAnchor = false;
    boolean hasAnchor = false;
    enum State {
        SEARCHING,
        SEEKING,
        HARVESTING,
        DELIVERING,
        DELIVER_ANCHOR,
        WAIT_FOR_ANCHOR
    }
    public Carrier(RobotController rc) throws GameActionException {
        super(rc);
        findHome();
    }

    void run() throws GameActionException {
        initialize();
        State state = determineState();
        rc.setIndicatorString(state.toString());
        switch (state) {
            case SEARCHING: search(); break;
            case SEEKING: seek(); break;
            case HARVESTING: harvest(); break;
            case DELIVERING: deliver(); break;
            case DELIVER_ANCHOR: deliver_anchor(); break;
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
        if (hasAnchor) return State.DELIVER_ANCHOR;
        if (homeHasAnchor) return State.WAIT_FOR_ANCHOR;
        if (wellTarget == null && 
            adamantium == 0 &&
            mana == 0 && 
            elixir == 0)
            return State.SEARCHING;
        
        if (wellTarget != null && 
            rc.getLocation().distanceSquaredTo(wellTarget) > 1)
            return State.SEEKING;

        if (adamantium + mana + elixir < 39 && wellTarget != null) {
            return State.HARVESTING;
        }
        if (adamantium + mana + elixir > 0) {
            return State.DELIVERING;
        }
        return State.SEARCHING;
    }

    void search() {
        exploration.move();
        exploration.move();
        findTarget();
    }

    void seek() {
        greedyPath.move(wellTarget);
        greedyPath.move(wellTarget);
    }

    void findTarget() {
        WellInfo[] wells = rc.senseNearbyWells();
        if (wells.length > 0)
            wellTarget = wells[0].getMapLocation();
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
        if (home.distanceSquaredTo(rc.getLocation()) > 1) {
            greedyPath.move(home);
        } else {
            ResourceType[] resources = {ResourceType.ADAMANTIUM, ResourceType.ELIXIR, ResourceType.MANA};
            for (ResourceType r: resources) {
                if (rc.getResourceAmount(r) == 0) continue;
                if (rc.canTransferResource(home, r, rc.getResourceAmount(r)))
                    rc.transferResource(home, r, rc.getResourceAmount(r));
                RobotInfo hq = rc.senseRobotAtLocation(home);
                if (hq.getNumAnchors(Anchor.STANDARD) > 0) {
                    homeHasAnchor = true;
                }
            }
        }
    }

    void grab_anchor() throws GameActionException {
        if (rc.canTakeAnchor(home, Anchor.STANDARD)) {
            rc.takeAnchor(home, Anchor.STANDARD);
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
                System.out.println("hi!");
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
            MapLocation[] spots = rc.senseNearbyIslandLocations(idx);
            for (MapLocation spot: spots) {
                if (rc.getLocation().distanceSquaredTo(spot) < d)
                    closestTarget = spot;
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
}
