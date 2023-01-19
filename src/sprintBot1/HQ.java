package sprintBot1;
import battlecode.common.*;

public class HQ extends Robot {
    int MAX_MINERS = 20;
    int cntCarriers = 0, cntLaunchers = 0, cntAmplifiers = 0;
    int anchorRound = 200;
    boolean makingAnchor = false;
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
        rc.setIndicatorString("Built: Nothing");
        if(rc.getRoundNum() == 1) communications.writeTypeLoc(Communications.HQ_LOCATION, rc.getLocation());
        if(rc.getRoundNum() == 2) communications.findOurHQs();
        if (rc.getRoundNum() % Constants.REPORT_FREQ == 0) {
            // we're trying to go Comms-less, so for now just use these.
            cntCarriers = communications.getUnitCount(RobotType.CARRIER);
            cntLaunchers = communications.getUnitCount(RobotType.LAUNCHER);
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
        //rc.setIndicatorString("Symmetry is " + communications.symmetryChecker.getSymmetry());
    }

    void build() throws GameActionException {
        Build b = getBuildType();
        if (b == Build.NONE) return;
        
        if (b == Build.ANCHOR) {
            if (rc.canBuildAnchor(Anchor.STANDARD)) {
                rc.buildAnchor(Anchor.STANDARD);
                communications.updateAnchor(0);
                makingAnchor = false;
            }
            return;
        }

        RobotType needed = buildToRobotType(b);
        buildIfCan(needed);
        for (RobotType r: RobotType.values()) {
            // if (r == RobotType.CARRIER) continue;
            // use in late game, but for now just don't make any.
            if (r == RobotType.AMPLIFIER) continue;
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
                rc.setIndicatorString("Built: " + r);
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
        if (rc.getRoundNum() <= 3)
            return Build.CARRIER;

        // We probably are somewhat secure, build an amplifier.
        if (rc.getRoundNum() >= 200 && cntAmplifiers <= 2 && 
            RobotType.AMPLIFIER.buildCostMana <= rc.getResourceAmount(ResourceType.MANA)) {
            return Build.AMPLIFIER;
        } else if (rc.getRoundNum() >= 200 && cntAmplifiers <= 2)
            return Build.NONE;
        
        // Game is probably over, build anchors.
        int anchorFreq = 50;
        if (rc.getRoundNum() > anchorRound && rc.canBuildAnchor(Anchor.STANDARD) &&
            ((communications.shouldBuildAnchor() && (rc.getRoundNum()%anchorFreq == 0)) || makingAnchor)) {
            communications.updateAnchor(1);
            makingAnchor = true;
            return Build.ANCHOR;
        } else if (rc.getRoundNum() > anchorRound && 
            ((communications.shouldBuildAnchor() && (rc.getRoundNum()%anchorFreq == 0)) || makingAnchor)) {
            communications.updateAnchor(1);
            makingAnchor = true;
            return Build.NONE;
        }

        // alternate between which things you add, unless ratios go out of wack.
        int mod = rc.getRoundNum() % 2;
        if (mod==0) return Build.CARRIER;
        return Build.LAUNCHER;
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
