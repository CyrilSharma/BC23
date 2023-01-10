package soldier_micro;
import battlecode.common.*;

public class Carrier extends Robot {
    int adamantium, mana, elixir;
    MapLocation wellTarget;
    // will eventually be replaced with comms.
    MapLocation home;
    enum State {
        SEARCHING,
        SEEKING,
        HARVESTING,
        DELIVERING
    }
    public Carrier(RobotController rc) throws GameActionException {
        super(rc);
        for (Direction d: directions) {
            MapLocation nloc = rc.getLocation().add(d);
            if (rc.canSenseLocation(rc.getLocation().add(d))) {
                RobotInfo r = rc.senseRobotAtLocation(nloc);
                if (r.type == RobotType.HEADQUARTERS)
                    home = nloc;
            }
        }
    }
    void run() throws GameActionException {
        State state = determineState();
        switch (state) {
            case SEARCHING: search(); break;
            case HARVESTING: harvest(); break;
            case DELIVERING: deliver(); break;
            default:
        }
    }

    void initialize() {
        adamantium = rc.getResourceAmount(ResourceType.ADAMANTIUM);
        mana = rc.getResourceAmount(ResourceType.MANA);
        elixir = rc.getResourceAmount(ResourceType.ELIXIR);
    }

    // No Seeking state until we have comms.
    State determineState() {
        if (adamantium == 0 &&
            mana == 0 &&
            elixir == 0 ||
            wellTarget == null)
            return State.SEARCHING;
        
        if (rc.getLocation().distanceSquaredTo(wellTarget) <= rc.getType().actionRadiusSquared &&
            adamantium + mana + elixir <= 39) {
            return State.HARVESTING;
        }
        return State.DELIVERING;
    }



    void search() {
        exploration.move();
        findTarget();
    }

    void findTarget() {
        WellInfo[] wells = rc.senseNearbyWells();
        wellTarget = wells[0].getMapLocation();
    }

    // TODO: add some evasive maneuvers
    void harvest() throws GameActionException {
        WellInfo wi = rc.senseWell(wellTarget);
        ResourceType rt = wi.getResourceType();
        if (rc.canCollectResource(wellTarget, 39-(adamantium + mana + elixir)))
            rc.collectResource(wellTarget, 39-(adamantium + mana + elixir));
        else if (rc.canCollectResource(wellTarget, 4)) {
            rc.collectResource(wellTarget, 4);
        }
    }

    void deliver() throws GameActionException {
        if (home.distanceSquaredTo(rc.getLocation()) > rc.getType().actionRadiusSquared) {
            greedyPath.move(home);
        } else {
            ResourceType[] resources = {ResourceType.ADAMANTIUM, ResourceType.ELIXIR, ResourceType.MANA};
            for (ResourceType r: resources) {
                if (rc.canTransferResource(home, r, 4));
                    rc.transferResource(home, r, 4);
            }
        }
    }
}
