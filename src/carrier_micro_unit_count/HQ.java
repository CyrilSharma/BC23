package carrier_micro_unit_count;
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
        if(rc.getRoundNum() == 1) communications.writeTypeLoc(Communications.HQ_LOCATION, rc.getLocation());
        if(rc.getRoundNum() == 2) communications.findOurHQs();
        if(rc.getRoundNum() >= 300) {
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

        ResourceType best = null;
        int smallest = 100000;
        for (ResourceType r: resources) {
            if (smallest > rc.getResourceAmount(r)) {
                smallest = rc.getResourceAmount(r);
                best = r;
            }
        }
        assert (best != null);
        communications.setResourceNeed(best);
        build();
    }

    void build() throws GameActionException {
        Build b = getBuildType();
        rc.setIndicatorString("carry: " + cntCarriers + ", " + "launch: " + cntLaunchers + ", " + "amplify: " + cntAmplifiers);

        if (b == Build.NONE) return;
        RobotType r = buildToRobotType(b);
        if (b == Build.ANCHOR) {
            if (rc.canBuildAnchor(Anchor.STANDARD)) {
                rc.buildAnchor(Anchor.STANDARD);
                System.out.println("Built an anchor");
            }
            return;
        }

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
        // Carrier spam is not helpful, use excesses to upgrade things.

        // if (r == RobotType.CARRIER) r = RobotType.LAUNCHER;
        // else if(r == RobotType.LAUNCHER) r = RobotType.CARRIER;
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
        boolean defend = false;
        for(RobotInfo r : enemies){
            if(r.getType() != RobotType.HEADQUARTERS){
                defend = true;
                break;
            }
        }
        if (defend && rc.getResourceAmount(ResourceType.MANA) >= RobotType.LAUNCHER.buildCostMana) {
            return Build.LAUNCHER;
        }

        // spam carriers initially.
        if (rc.getRoundNum() <= 50 && cntCarriers < 8) 
            return Build.CARRIER;

        
        // Emergency low.
        if (cntCarriers < 4) return Build.CARRIER;
        if (cntLaunchers < 4) return Build.LAUNCHER;
        if (cntAmplifiers < 2) return Build.AMPLIFIER;

        // alternate between which things you add, unless ratios go out of wack.
        int mod = rc.getRoundNum() % 4;
        if (mod==0 && (cntCarriers < 20 || cntCarriers < 4 * cntLaunchers)) return Build.CARRIER;
        if ((mod==1 && cntLaunchers < 3 * cntCarriers)) return Build.LAUNCHER;
        if ((mod==2 && cntAmplifiers * 3 < cntLaunchers)) return Build.AMPLIFIER;
        if (rc.getResourceAmount(ResourceType.MANA) >= 100 &&
            rc.getResourceAmount(ResourceType.ADAMANTIUM) >= 100)
            return Build.ANCHOR;
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
