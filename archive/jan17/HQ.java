package jan17;
import battlecode.common.*;

public class HQ extends Robot {
    int MAX_MINERS = 20;
    int cntCarriers = 0, cntLaunchers = 0, cntAmplifiers = 0;
    ResourceType[] resources = {ResourceType.ADAMANTIUM, ResourceType.ELIXIR, ResourceType.MANA};
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
        //if(rc.getRoundNum() == 1000) rc.resign();
        rc.setIndicatorString("" + cntCarriers);
        if(rc.getRoundNum() == 1) communications.writeTypeLoc(Communications.HQ_LOCATION, rc.getLocation());
        if(rc.getRoundNum() == 2) communications.findOurHQs();
        if(rc.getRoundNum() >= 50) {
            if (rc.getRoundNum() % Constants.REPORT_FREQ == 0) {
                // once comms have been estabished switch to this.
                cntCarriers = communications.getUnitCount(RobotType.CARRIER);
                cntLaunchers = communications.getUnitCount(RobotType.LAUNCHER);
                cntAmplifiers = communications.getUnitCount(RobotType.AMPLIFIER);
            }
        } else {
            cntCarriers = communications.readBuild(RobotType.CARRIER);
            cntLaunchers = communications.readBuild(RobotType.LAUNCHER);
            cntAmplifiers = communications.readBuild(RobotType.AMPLIFIER);
        }
        if(rc.getRoundNum() > 0 && (rc.getRoundNum() % Constants.REPORT_FREQ) == 0){
            if(communications.HQs[communications.numHQ - 1].equals(rc.getLocation())) 
                communications.resetCounts();
        }
        if(rc.getRoundNum() > 2){
            communications.initial();
        }
        build();
        communications.last();
        rc.setIndicatorString("symmetry is " + communications.symmetryChecker.getSymmetry());
    }

    void build() throws GameActionException {
        Build b = getBuildType();
        if (b == Build.NONE) return;
        
        if (b == Build.ANCHOR) {
            if (rc.canBuildAnchor(Anchor.STANDARD)) {
                rc.buildAnchor(Anchor.STANDARD);
            }
            return;
        }

        RobotType needed = buildToRobotType(b);
        buildIfCan(needed);
        for (RobotType r: RobotType.values()) {
            // Replace with a needs array.
            // i.e set globally what troops we want.
            // only build troops we want.
            if (r == RobotType.CARRIER &&
                cntCarriers > 20) continue;
            if (r == RobotType.AMPLIFIER &&
                cntAmplifiers * 3 >= cntLaunchers) continue;
            buildIfCan(r);
        }
    }

    void buildIfCan(RobotType r) throws GameActionException{
        if (rc.getResourceAmount(ResourceType.ADAMANTIUM) >=
                r.buildCostAdamantium &&
            rc.getResourceAmount(ResourceType.MANA) >=
                    r.buildCostMana &&
            rc.getResourceAmount(ResourceType.ELIXIR) >=
                    r.buildCostElixir) {
            MapLocation loc = getBuildLocation(r);
            if (rc.canBuildRobot(r, loc)) {
                rc.buildRobot(r, loc);
                communications.updateBuild(r);
            }
        }
    }

    MapLocation getBuildLocation(RobotType t) throws GameActionException {
        MapLocation[] locs = rc.getAllLocationsWithinRadiusSquared(rc.getLocation(), RobotType.HEADQUARTERS.actionRadiusSquared);
        BuildTarget best = null;
        for (MapLocation loc: locs) {
            BuildTarget cur = new BuildTarget(loc);
            if (cur.isBetterThan(best)) best = cur;
        }
        return best.mloc;
    }

    Build getBuildType() throws GameActionException{
        //use unit counts to get ratios, unless we are in danger
        RobotInfo[] enemies = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());
        boolean defend = false;
        for(RobotInfo r : enemies){
            if(r.getType() != RobotType.HEADQUARTERS){
                defend = true;
                break;
            }
        }
        if (defend) return Build.LAUNCHER;

        // spam carriers initially.
        if (rc.getRoundNum() <= 50 && cntCarriers < (4 * Math.max(communications.numHQ, 1)))
            return Build.CARRIER;

        // Emergency low.
        if (cntCarriers < 4) return Build.CARRIER;
        if (cntLaunchers < 4) return Build.LAUNCHER;
        if (cntAmplifiers < 2) return Build.AMPLIFIER;
        if(rc.getRoundNum() >= 1800) return Build.ANCHOR;
        // alternate between which things you add, unless ratios go out of wack.
        int mod = rc.getRoundNum() % 4;
        if (mod==0 && (cntCarriers < 20 || cntCarriers * 3 < cntLaunchers)) return Build.CARRIER;
        if (mod==1 && cntLaunchers < 3 * cntCarriers) return Build.LAUNCHER;
        if (mod==2 && cntAmplifiers * 3 < cntLaunchers) return Build.AMPLIFIER;
        if (mod==3) return Build.ANCHOR;
        else return Build.LAUNCHER;
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
        int d;
        // when we have comms make it spawn towards goals and stuff.
        BuildTarget(MapLocation mloc) {
            this.mloc = mloc;
            try {
                placeable = rc.sensePassability(mloc) &&
                    !rc.isLocationOccupied(mloc);
                d = mloc.distanceSquaredTo(new MapLocation(rc.getMapHeight()/2, rc.getMapWidth()/2));
            } catch (GameActionException e) {
                System.out.println(e.getMessage());
            }
        }

        boolean isBetterThan(BuildTarget bt) {
            if (bt == null) return true;
            if (bt.placeable && !placeable) return false;
            if (!bt.placeable && placeable) return true;
            return d <= bt.d;
        }
    }
}
