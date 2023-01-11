package carrier_micro_unit_count;
import battlecode.common.*;

public class HQ extends Robot {
    int MAX_MINERS = 20;
    int cntCarriers = 0, cntLaunchers = 0, cntAmplifiers = 0;
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
        if(rc.getRoundNum() == 2) communications.findOurHQs();
        if(rc.getRoundNum() % 5 == 0 && rc.getRoundNum() >= 300){
            cntCarriers = communications.getUnitCount(RobotType.CARRIER);
            cntLaunchers = communications.getUnitCount(RobotType.LAUNCHER);
            cntAmplifiers = communications.getUnitCount(RobotType.AMPLIFIER);
        }
        if(rc.getRoundNum() > 0 && rc.getRoundNum() % 5 == 0){
            if(communications.HQs[communications.numHQ - 1].equals(rc.getLocation())) communications.resetCounts();
        }
        communications.initial();
        build();
    }

    void addCnt(RobotType r) throws GameActionException{
        //add unit we make to the count
        if(r == RobotType.CARRIER) cntCarriers++;
        if(r == RobotType.LAUNCHER) cntLaunchers++;
        if(r == RobotType.AMPLIFIER) cntAmplifiers++;
    }

    void build() throws GameActionException {
        Build b = getBuildType();
        //rc.setIndicatorString("carry: " + cntCarriers + ", " + "launch: " + cntLaunchers + ", " + "amplify: " + cntAmplifiers);

        if (b == Build.NONE) return;
        RobotType r = buildToRobotType(b);
        if (b == Build.ANCHOR &&
                rc.canBuildAnchor(Anchor.STANDARD)) {
            rc.buildAnchor(Anchor.STANDARD);
            return;
        }
        for(int i = 0; i < 2; i++) {
            if (rc.getResourceAmount(ResourceType.ADAMANTIUM) >=
                    r.buildCostAdamantium &&
                    rc.getResourceAmount(ResourceType.MANA) >=
                            r.buildCostMana &&
                    rc.getResourceAmount(ResourceType.ELIXIR) >=
                            r.buildCostElixir) {
                MapLocation loc = getBuildLocation(r);
                rc.setIndicatorString("Trying to build a " + b.toString() + " at " + loc);
                if (rc.canBuildRobot(r, loc)) {
                    rc.buildRobot(r, loc);
                    addCnt(r);
                }
            }
            if(r == RobotType.CARRIER) r = RobotType.LAUNCHER;
            else if(r == RobotType.LAUNCHER) r = RobotType.CARRIER;
        }
    }

    MapLocation getBuildLocation(RobotType t) throws GameActionException {
        MapLocation[] locs = rc.getAllLocationsWithinRadiusSquared(rc.getLocation(), RobotType.HEADQUARTERS.actionRadiusSquared);
        BuildTarget best = null;
        RobotInfo[] rb = rc.senseNearbyRobots(rc.getType().actionRadiusSquared);
        for (MapLocation loc: locs) if(rc.sensePassability(loc)){
            int ok = 1;
            for(RobotInfo r : rb) if(r.getLocation() == loc ) ok = 0;
            if(ok == 0) continue;
            BuildTarget cur = new BuildTarget(loc);
            best = cur;
            break;
        }
        return best.mloc;
    }

    Build getBuildType() throws GameActionException{
        //use unit counts to get ratios, unless we are in danger
        RobotInfo[] enemies = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());
        int defend = 0;
        for(RobotInfo r : enemies){
            if(r.getType() != RobotType.HEADQUARTERS){
                defend = 1;
                break;
            }
        }
        if(defend > 0){
            if(rc.getResourceAmount(ResourceType.MANA) >= RobotType.LAUNCHER.buildCostMana){
                return Build.LAUNCHER;
            }
        }
        if(rc.getRoundNum() <= 200) {
            if (cntCarriers < 8) return Build.CARRIER;
            if (cntCarriers < 10){
                if (cntLaunchers < 3 * cntCarriers) return Build.LAUNCHER;
                else return Build.CARRIER;
            }
            if (cntCarriers < 20) {
                if (cntLaunchers < 2 * cntCarriers) return Build.LAUNCHER;
                else return Build.CARRIER;
            }
        }
        if(cntAmplifiers * 3 < cntLaunchers && cntAmplifiers < 20) return Build.AMPLIFIER;
        if(cntCarriers < 20) return Build.CARRIER;
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
