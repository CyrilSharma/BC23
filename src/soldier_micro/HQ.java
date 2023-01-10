package soldier_micro;
import battlecode.common.*;

public class HQ extends Robot {
    public HQ(RobotController rc) {
        super(rc);
    }
    void run() throws GameActionException {
        build();
    }

    void build() throws GameActionException {
        RobotType r = getBuildType();
        if (r == null) return;
        rc.setIndicatorString("Trying to build a " + r.toString());
        if (rc.getResourceAmount(ResourceType.ADAMANTIUM) >=
            r.buildCostAdamantium &&
            rc.getResourceAmount(ResourceType.MANA) >=
            r.buildCostMana &&
            rc.getResourceAmount(ResourceType.ELIXIR) >=
            r.buildCostElixir) {
            MapLocation loc = getBuildLocation(r);
            if (rc.canBuildRobot(r, loc)) {
                rc.buildRobot(r, loc);
            }
        }
    }

    MapLocation getBuildLocation(RobotType t) throws GameActionException {
        MapLocation[] locs = rc.getAllLocationsWithinRadiusSquared(rc.getLocation(), RobotType.HEADQUARTERS.actionRadiusSquared);
        BuildTarget best = new BuildTarget(locs[0]);
        for (MapLocation loc: locs) {
            BuildTarget cur = new BuildTarget(loc);
            if (cur.isBetterThan(best)) best = cur;
        }
        return best.mloc;
    }

    RobotType getBuildType() {
        if (rc.getRoundNum()/50 % 2 == 0) return RobotType.CARRIER;
        return RobotType.LAUNCHER;
    }

    class BuildTarget {
        MapLocation mloc;
        boolean placeable;
        // when we have comms make it spawn towards goals and stuff.
        BuildTarget(MapLocation mloc) {
            this.mloc = mloc;
            try {
                placeable = rc.sensePassability(mloc);
            } catch (GameActionException e) {
                System.out.println(e.getMessage());
            }
        }

        boolean isBetterThan(BuildTarget bt) {
            if (bt.placeable && !placeable) return false;
            if (!bt.placeable && placeable) return true;
            return true;
        }
    }
}
