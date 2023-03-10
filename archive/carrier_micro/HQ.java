package carrier_micro;
import battlecode.common.*;

public class HQ extends Robot {
    int MAX_MINERS = 20;
    public HQ(RobotController rc) {
        super(rc);
    }
    enum Build {
        CARRIER,
        LAUNCHER,
        BOOSTER,
        AMPLIFIER,
        DESTABILIZER,
        ANCHOR, 
        NONE
    }
    void run() throws GameActionException {
        if(rc.getRoundNum() == 1) communications.writeTypeLoc(Communications.HQ_LOCATION, rc.getLocation());
        communications.report();
        build();
    }

    void build() throws GameActionException {
        Build b = getBuildType();
        rc.setIndicatorString("Trying to build a " + b.toString());

        if (b == Build.NONE) return;
        RobotType r = buildToRobotType(b);
        if (b != Build.ANCHOR &&
            rc.getResourceAmount(ResourceType.ADAMANTIUM) >=
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
        if (b == Build.ANCHOR &&
            rc.canBuildAnchor(Anchor.STANDARD)) {
            rc.buildAnchor(Anchor.STANDARD);
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

    Build getBuildType() {
        int mod = (rc.getRoundNum()/10) % 8;
        if (mod/2 == 0) return Build.CARRIER;
        else if (mod/2 == 1) return Build.LAUNCHER;
        else if (mod == 5) return Build.AMPLIFIER;
        else return Build.ANCHOR;
    }

    RobotType buildToRobotType(Build b) {
        switch (b) {
            case CARRIER: return RobotType.CARRIER;
            case LAUNCHER: return RobotType.LAUNCHER;
            case AMPLIFIER: return RobotType.AMPLIFIER;
            case BOOSTER: return RobotType.BOOSTER;
            case DESTABILIZER: return RobotType.DESTABILIZER;
            default: return null;
        }
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
