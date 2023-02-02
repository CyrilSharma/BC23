package finalBot;

import battlecode.common.*;

public class HQ extends Robot {
    int MAX_MINERS = 20;
    int cntCarriers = 0, cntLaunchers = 0, cntAmplifiers = 0;
    int anchorRound = 100;
    int surroundTurn = -10;
    int enemiesSurrounding;
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
        communications.initial();
        if(rc.getRoundNum() == 1) communications.postHQ();
        if(rc.getRoundNum() == 2) communications.findOurHQs();
        if (rc.getRoundNum() % Constants.REPORT_FREQ == 0) {
            cntCarriers = communications.readBuild(RobotType.CARRIER);
            cntLaunchers = communications.readBuild(RobotType.LAUNCHER);
            cntAmplifiers = communications.getUnitCount(RobotType.AMPLIFIER);
        }
        if(rc.getRoundNum() > 0 && (rc.getRoundNum() % Constants.REPORT_FREQ) == 0) {
            if (communications.hqIndex == communications.numHQ - 1)
                communications.resetCounts();
        }
        build();
        communications.last();
    }

    void build() throws GameActionException {
        if (rc.getRoundNum() == 1) buildConvoy();
        if (rc.getRoundNum() <= 2) placeExploratoryCarriers();

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

        // splurge otherwise.
        RobotType needed = buildToRobotType(b);
        buildRobot(needed);
        for (RobotType r: RobotType.values()) {
            // if (r == RobotType.CARRIER) continue;
            // use in late game, but for now just don't make any.
            if (r == RobotType.AMPLIFIER) continue;
            boolean shouldContinue = true;
            while (canBuild(r) && shouldContinue) {
                shouldContinue = buildRobot(r);
            }
        }
    }

    boolean canBuild(RobotType r) throws GameActionException {
        return (rc.getActionCooldownTurns() < 10 &&
        rc.getResourceAmount(ResourceType.ADAMANTIUM) >= r.buildCostAdamantium &&
        rc.getResourceAmount(ResourceType.MANA) >= r.buildCostMana &&
        rc.getResourceAmount(ResourceType.ELIXIR) >= r.buildCostElixir);
    }

    boolean isSurrounded() throws GameActionException {
        int allies = 0;
        int enemies = 0;
        for (RobotInfo r: rc.senseNearbyRobots(-1)) {
            if (r.type != RobotType.LAUNCHER) continue;
            if (r.team == rc.getTeam()) allies++;
            else enemies++;
        }
        enemiesSurrounding = enemies;
        return (enemies - allies >= 3);
    }

    boolean buildRobot(RobotType r) throws GameActionException{
        MapLocation loc = getBuildLocation(r);
        if (rc.canBuildRobot(r, loc)) {
            rc.buildRobot(r, loc);
            communications.updateBuild(r);
            return true;
        }
        return false;
    }

    void buildConvoy() throws GameActionException {
        MapLocation[] locs = rc.getAllLocationsWithinRadiusSquared(rc.getLocation(), RobotType.HEADQUARTERS.actionRadiusSquared);
        MapLocation best = null;
        int bestD = 1000000;
        for (MapLocation loc: locs) {
            if (Clock.getBytecodesLeft() < 1000) break;
            if (!rc.canBuildRobot(RobotType.LAUNCHER, loc)) continue;
            MapLocation mloc = loc;
            if (rc.canSenseLocation(loc)) {
                MapInfo mi = rc.senseMapInfo(mloc);
                mloc = mloc.add(mi.getCurrentDirection());
            }
            int d = mloc.distanceSquaredTo(new MapLocation(rc.getMapWidth()/2, rc.getMapHeight()/2));
            if (d < bestD) {
                bestD = d;
                best = loc;
            }
        }
        if (rc.canBuildRobot(RobotType.LAUNCHER, best))
            rc.buildRobot(RobotType.LAUNCHER, best);
        for (MapLocation m: rc.getAllLocationsWithinRadiusSquared(best, 1)) {
            if (m.distanceSquaredTo(rc.getLocation()) >= RobotType.HEADQUARTERS.actionRadiusSquared) continue;
            if (rc.canBuildRobot(RobotType.LAUNCHER, m))
                rc.buildRobot(RobotType.LAUNCHER, m);
        }
        for (Direction dir: directions) {
            if (best.add(dir).distanceSquaredTo(rc.getLocation()) >= RobotType.HEADQUARTERS.actionRadiusSquared) continue;
            if (rc.canBuildRobot(RobotType.LAUNCHER, best.add(dir)))
                rc.buildRobot(RobotType.LAUNCHER, best.add(dir));
        }
    }

    int placementInd=0;
    MapLocation[] prevPlacements = new MapLocation[4];
    void placeExploratoryCarriers() throws GameActionException {
        MapLocation[] locs = rc.getAllLocationsWithinRadiusSquared(rc.getLocation(), RobotType.HEADQUARTERS.actionRadiusSquared);
        if (placementInd == 0) {
            MapLocation best = null;
            int bestD = -1;
            for (MapLocation loc: locs) {
                if (Clock.getBytecodesLeft() < 2000) break;
                if (!rc.canBuildRobot(RobotType.CARRIER, loc)) continue;
                MapLocation mloc = loc;
                if (rc.canSenseLocation(loc)) {
                    MapInfo mi = rc.senseMapInfo(mloc);
                    mloc = mloc.add(mi.getCurrentDirection());
                }
                int d = mloc.distanceSquaredTo(new MapLocation(rc.getMapWidth()/2, rc.getMapHeight()/2));
                if (d > bestD) {
                    bestD = d;
                    best = loc;
                }
            }
            if (rc.canBuildRobot(RobotType.CARRIER, best))
                rc.buildRobot(RobotType.CARRIER, best);
            prevPlacements[placementInd++] = best;
        }
        
        int minDist;
        MapLocation cur;
        for (int i = 0; rc.getResourceAmount(ResourceType.ADAMANTIUM) >= RobotType.CARRIER.buildCostAdamantium && i < 5; i++) {
            if (!canBuild(RobotType.CARRIER)) break;
            cur = locs[rng.nextInt(locs.length)];
            minDist = 10000;
            for (MapLocation m: prevPlacements) {
                if (m == null) continue;
                if (cur.distanceSquaredTo(m) <= minDist) 
                    minDist = cur.distanceSquaredTo(m);
            }
            if (minDist >= 9 && minDist <= 25 && rc.canBuildRobot(RobotType.CARRIER, cur)) {
                rc.buildRobot(RobotType.CARRIER, cur);
                prevPlacements[placementInd++] = cur;
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

    Build getBuildType() throws GameActionException {
        boolean surrounded = isSurrounded();
        boolean canOutnumber = (rc.getResourceAmount(ResourceType.MANA) / RobotType.LAUNCHER.buildCostMana)
            > enemiesSurrounding;
        if (surrounded) surroundTurn = rc.getRoundNum();
        if ((surrounded || (rc.getRoundNum() - surroundTurn) < 10) &&
            !canOutnumber) {
            communications.reportSurrounded(true);
            return Build.NONE;
        }
        communications.reportSurrounded(false);

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
        if (rc.getRoundNum() >= 200 && cntAmplifiers <= 1 && 
            RobotType.AMPLIFIER.buildCostMana <= rc.getResourceAmount(ResourceType.MANA)) {
            return Build.AMPLIFIER;
        }
        
        // Game is probably over, build anchors.
        int anchorFreq = 100;
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
        // rc.setIndicatorString("is it? " + communications.isEverythingSaturated());
        if (mod==0 && !communications.isEverythingSaturated()) return Build.CARRIER;
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
