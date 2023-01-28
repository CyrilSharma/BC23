package sprintBot2;
import java.util.Map;
import java.util.Random;

import battlecode.common.*;

// TODO: count the numbers of each unit for use in HQ!!
public class Communications {
    RobotController rc;
    Util util;
    Random rng = new Random();
    SymmetryChecker symmetryChecker;
    MapLocation[] HQs = new MapLocation[5];
    MapLocation[] EnemyHQsCache = new MapLocation[5];
    MapLocation[] EnemyHQs = new MapLocation[5];
    MapLocation[] EnemyHQEstimates = null;
    ResourceType resourceNeeded = null;
    
    static final int MAX_WELL_STORED = 10;
    WellInfo[] wellCache = new WellInfo[MAX_WELL_STORED];
    ResourceType[] resources = {ResourceType.ADAMANTIUM, ResourceType.MANA, ResourceType.ELIXIR};

    boolean pushedHQs = false;
    int numWells = 0;
    int numHQ = 0;
    int numEnemyHQCache = 0;
    int numEnemyHQ = 0;
    int lastReported = -10;
    int hqIndex = -1;

    static final Direction[] directions = {
        Direction.NORTH,
        Direction.NORTHEAST,
        Direction.EAST,
        Direction.SOUTHEAST,
        Direction.SOUTH,
        Direction.SOUTHWEST,
        Direction.WEST,
        Direction.NORTHWEST,
        Direction.CENTER
    };

    //constants
    static final int HQ_LOCATIONS = 0;
    static final int HQ_LOCATIONS_WIDTH = 4;

    static final int ADAMANTIUM_LOCATIONS = HQ_LOCATIONS + HQ_LOCATIONS_WIDTH;
    static final int ADAMANTIUM_LOCATIONS_WIDTH = 5;

    static final int MANA_LOCATIONS = ADAMANTIUM_LOCATIONS + ADAMANTIUM_LOCATIONS_WIDTH;
    static final int MANA_LOCATIONS_WIDTH = 5;

    static final int ELIXIR_LOCATIONS = MANA_LOCATIONS + MANA_LOCATIONS_WIDTH;
    static final int ELIXIR_LOCATIONS_WIDTH = 2;

    static final int ATTACK_TARGETS = ELIXIR_LOCATIONS + ELIXIR_LOCATIONS_WIDTH;
    static final int ATTACK_TARGETS_WIDTH = 10;

    static final int UNIT_COUNTS = ATTACK_TARGETS + ATTACK_TARGETS_WIDTH;
    static final int UNIT_COUNTS_WIDTH = 8;

    static final int BUILD_COUNT = UNIT_COUNTS + UNIT_COUNTS_WIDTH;
    static final int BUILD_COUNT_WIDTH = 8;

    static final int RESOURCE_COUNT = BUILD_COUNT + BUILD_COUNT_WIDTH;
    static final int RESOURCE_COUNT_WIDTH = 3;

    static final int ENEMY_HQ = RESOURCE_COUNT + RESOURCE_COUNT_WIDTH;
    static final int ENEMY_HQ_WIDTH = 4;

    static final int H_SYM = ENEMY_HQ + ENEMY_HQ_WIDTH;
    static final int V_SYM = H_SYM + 1;
    static final int R_SYM = V_SYM + 1;
    static final int SEES_HQ = R_SYM + 1;

    static final int ANCHOR = SEES_HQ + 1;
    static final int GREEDYCOUNT = ANCHOR+1;

    static final RobotType[] UNITS = {
            RobotType.CARRIER,
            RobotType.LAUNCHER,
            RobotType.AMPLIFIER,
            RobotType.BOOSTER,
            RobotType.DESTABILIZER
    };

    public Communications(RobotController rc) {
        this.rc = rc;
        this.util = new Util(rc);
        symmetryChecker = new SymmetryChecker(rc);
        rng.setSeed((long) rc.getID());
    }

    // all classes call this at the beginning.
    public void initial() throws GameActionException {
        refresh();
        sendMemory();
        report();
        checkEnemyHQs();
        getEnemyHQs();
        clearTargets();
        broadcastAttackTargets();
        estimateEnemyHQs();
        displayEstimates();
        displayAvailability();
    }

    public void displayAvailability() throws GameActionException {
        if (hqIndex != 0) return;
        String s = "";
        for (int i = ADAMANTIUM_LOCATIONS; i < ELIXIR_LOCATIONS + ELIXIR_LOCATIONS_WIDTH; i++) {
            if (rc.readSharedArray(i) == 0) continue;
            int val = rc.readSharedArray(i);
            int x = (val) & (0b111111);
            int y = (val >> 6) & (0b111111);
            int availability = (val >> 12) & (0b1111);
            s += String.format("[%d %d]: %d | ", x, y, availability);
        }
        rc.setIndicatorString(s);
        //System.out.println(s);
    }

    public void updateAvailability(MapLocation loc, int availability) throws GameActionException {
        if (!rc.canWriteSharedArray(0, 0)) return;
        for (int i = ADAMANTIUM_LOCATIONS; i < ELIXIR_LOCATIONS + ELIXIR_LOCATIONS_WIDTH; i++) {
            if (rc.readSharedArray(i) == 0) continue;
            int val = rc.readSharedArray(i);
            int x = (val) & (0b111111);
            int y = (val >> 6) & (0b111111);
            if (loc.x != x || loc.y != y) continue;
            // I CHANGED THE MESSAGE FORMAT, YOU HAVE BEEN WARNED.
            int message = x + (y << 6) + (availability << 12);
            rc.writeSharedArray(i, message);
            break;
        }
    }

    public void displayEstimates() throws GameActionException {
        if (EnemyHQEstimates == null) return;
        for (MapLocation m: EnemyHQEstimates) {
            if (m == null) continue;
            rc.setIndicatorDot(m, 0, 0, 0);
        }
    }

    public boolean getGreedy() throws GameActionException {
        // scales between 0 and 1/2
        double total = 4 * numHQ;
        int mn = Math.min(rc.getMapHeight(), rc.getMapWidth());
        int numExplore = Math.min((int) Math.round((((double) mn) / 160.0) * total), 4);
        int count = rc.readSharedArray(GREEDYCOUNT);
        if (rc.canWriteSharedArray(0, 0))
            rc.writeSharedArray(GREEDYCOUNT, count+1);
        // the way this is set up, primarily adamantium dudes will be greedy.
        if (count < numExplore) {
            return false;
        }
        return true;
    }

    public ResourceType getResourceInitial() throws GameActionException {
        int count = rc.readSharedArray(GREEDYCOUNT);
        double total = 4 * numHQ;
        int mn = Math.min(rc.getMapHeight(), rc.getMapWidth());
        int nAdamantium = Math.min((int) Math.round((((double) mn / 60.0) * total)), (int)(0.75 * total));
        if (count < nAdamantium) return ResourceType.ADAMANTIUM;
        else return ResourceType.MANA;
    }

    public void updateAnchor(int a) throws GameActionException {
        rc.writeSharedArray(ANCHOR, a);
    }

    public boolean shouldBuildAnchor() throws GameActionException {
        return rc.readSharedArray(ANCHOR) == 0;
    }

    public void last() throws GameActionException {
        symmetryChecker.updateSymmetry();
        /* System.out.println("Symmetry is...: " + symmetryChecker.getSymmetry());
        System.out.println("" + symmetryChecker.hSym + 
            " "  + symmetryChecker.vSym + 
            " " + symmetryChecker.rSym); */
    }

    void updateBuild(RobotType r) throws GameActionException {
        int index = BUILD_COUNT + r.ordinal();
        int count = rc.readSharedArray(index);
        rc.writeSharedArray(index, count+1);
    }

    int readBuild(RobotType r) throws GameActionException {
        int index = BUILD_COUNT + r.ordinal();
        return rc.readSharedArray(index);
    }

    public void refresh() throws GameActionException {
        // purge memory after x turns to prevent bad updates.
        if (rc.getType() == RobotType.HEADQUARTERS) {
            broadcastTargetMemory[rc.getRoundNum()%3] = null;
            if ((rc.getRoundNum()%Constants.ATTACK_REFRESH) >= ATTACK_TARGETS_WIDTH) return;
            rc.writeSharedArray(ATTACK_TARGETS + (rc.getRoundNum()%Constants.ATTACK_REFRESH), 0);
        }
    }

    public ResourceType getResourceNeed() throws GameActionException {
        MapLocation en = getClosestEnemyHQ();
        int xLoc = 0, yLoc = 0;
        for(int i = 0; i < numHQ; i++){
            xLoc += HQs[i].x;
            yLoc += HQs[i].y;
        }
        xLoc /= numHQ;
        yLoc /= numHQ;
        int d = Math.abs(xLoc - en.x) + Math.abs(yLoc - en.y);
        double dangerDistRatio = ((double)d / (double)(rc.getMapHeight() + rc.getMapHeight()));
        double val = rng.nextDouble();

        // The larger c is, the more likely we are to mine mana.
        if (rc.getRoundNum() <= 25) {
            if (val < dangerDistRatio) {
                return ResourceType.ADAMANTIUM;
            } else {
                return ResourceType.MANA;
            }
        } else {
            if (val < 0.75) return ResourceType.MANA;
            else return ResourceType.ADAMANTIUM;
        }
    }

    public void resetResourceCounts() throws GameActionException{
        for(int i = 0; i < 3; i++){
            rc.writeSharedArray(RESOURCE_COUNT + i, 0);
        }
    }
    public void updateResources() throws GameActionException{
        for(int i = 0; i < 3; i++){
            int val = rc.readSharedArray(RESOURCE_COUNT + i);
            rc.writeSharedArray(RESOURCE_COUNT + i, val + rc.getResourceAmount(resources[i]));
        }
    }

    public void divideResources(ResourceType r, int f) throws GameActionException{
        for(int i = 0; i < 3; i++){
            if(resources[i].equals(r)){
                int val = rc.readSharedArray(RESOURCE_COUNT + i);
                rc.writeSharedArray(RESOURCE_COUNT + i, val / f);
            }
        }
    }
    public int getAdamantiumReq() throws GameActionException{
        return rc.readSharedArray(RESOURCE_COUNT);
    }

    public int getManaReq() throws GameActionException{
        return rc.readSharedArray(RESOURCE_COUNT + 2);
    }

    public ResourceType readResourceNeed() throws GameActionException {
        return getResourceNeed();
    }

    public void sendMemory() throws GameActionException {
        // if u can write one u can write all... right?
        if (!rc.canWriteSharedArray(0, 0)) return;
        // do this for all memories...
        for (int i = 0; i < 3; i++) {
            if (broadcastTargetMemory[i] != null) {
                broadcastAttackTarget(broadcastTargetMemory[i]);
                broadcastTargetMemory[i] = null;
            }
        }
    }

    public void resetCounts() throws GameActionException{
        for(int i = UNIT_COUNTS; i < UNIT_COUNTS + UNIT_COUNTS_WIDTH; i++){
            rc.writeSharedArray(i, 0);
        }
    }

    public int getUnitCount(RobotType r) throws GameActionException{
        return rc.readSharedArray(UNIT_COUNTS + r.ordinal());
    }

    public void postHQ() throws GameActionException{
        assert(rc.getType() == RobotType.HEADQUARTERS);
        MapLocation cur = rc.getLocation();
        for (int i = HQ_LOCATIONS; i < HQ_LOCATIONS + HQ_LOCATIONS_WIDTH; i++) {
            if (rc.readSharedArray(i) != 0) continue;
            int msg = cur.x + (1 << 6) * cur.y;
            rc.writeSharedArray(i, msg);
            return;
        }
    }

    // return all wells
    public MapLocation[] readWells(ResourceType r) throws GameActionException {
        boolean updated = false;
        int ind = 0;
        int[] start_stop = getStartStop(r);
        MapLocation[] locs = new MapLocation[5];
        for (int i = start_stop[0]; i < start_stop[1]; i++) {
            if (rc.readSharedArray(i) == 0) continue;
            int val = rc.readSharedArray(i);
            locs[ind++] = new MapLocation(val & (0b111111), (val >> 6) & (0b111111));
            updated = true;
        }
        if (!updated) return null;
        return locs;
    }

    public ResourceType getTypeFromIndex(int i) throws GameActionException {
        if (i < MANA_LOCATIONS) return ResourceType.ADAMANTIUM;
        else if (i < ELIXIR_LOCATIONS) return ResourceType.MANA;
        else return ResourceType.ELIXIR;
    }

    public int[] getStartStop(ResourceType r) {
        int start = -1;
        int stop = -1;
        switch (r) {
            case ADAMANTIUM: 
                start = ADAMANTIUM_LOCATIONS;
                stop = ADAMANTIUM_LOCATIONS + ADAMANTIUM_LOCATIONS_WIDTH;
                break;
            case MANA: 
                start = MANA_LOCATIONS;
                stop = MANA_LOCATIONS + MANA_LOCATIONS_WIDTH;
                break;
            case ELIXIR:
                start = ELIXIR_LOCATIONS;
                stop = ELIXIR_LOCATIONS + ELIXIR_LOCATIONS_WIDTH;
                break;
            default:
        }
        return new int[]{start, stop};
    }

    public void reportWellCache() throws GameActionException {
        if (!rc.canWriteSharedArray(0, 0)) return;
        for (int i = 0; i < numWells; i++){
            boolean marked = false;
            int[] start_stop = getStartStop(wellCache[i].getResourceType());
            for (int j = start_stop[0]; j < start_stop[1]; j++) {
                int val = rc.readSharedArray(j);
                if (val == 0) continue;
                MapLocation ex = new MapLocation(val & (0b111111), (val >> 6) & (0b111111));
                if(wellCache[i].getMapLocation().equals(ex)){
                    marked = true;
                    break;
                }
            }
            if (marked) {
                wellCache[i] = null;
                continue;
            }

            for (int j = start_stop[0]; j < start_stop[1]; j++) {
                int val = rc.readSharedArray(j);
                if (val != 0) continue;
                int msg = wellCache[i].getMapLocation().x + (1 << 6) * (wellCache[i].getMapLocation().y) + (1 << 12) * 9;
                rc.writeSharedArray(j, msg);
                break;
            }
        }
        numWells = 0;
    }

    public void updateWellCache(WellInfo[] wells) throws GameActionException {
        for (WellInfo w : wells) {
            boolean marked = false;
            for (int i = 0; i < numWells; i++) {
                if(w.getMapLocation().equals(wellCache[i].getMapLocation())) {
                    marked = true;
                    break;
                }
            }
            //now check in messages
            int[] start_stop = getStartStop(w.getResourceType());
            for(int i = start_stop[0]; i < start_stop[1]; i++) {
                int val = rc.readSharedArray(i);
                if (val == 0) continue;
                MapLocation ex = new MapLocation(val & (0b111111), (val >> 6) & (0b111111));
                if (w.getMapLocation().equals(ex)) {
                    marked = true;
                    break;
                }
            }
            if(!marked && numWells < MAX_WELL_STORED) {
                wellCache[numWells++] = w;
            }
        }
    }

    public void reportWells() throws GameActionException {
        WellInfo[] wells = rc.senseNearbyWells();
        if (wells.length == 0) return;
        if (!rc.canWriteSharedArray(0, 0)) {
            updateWellCache(wells);
            return;
        }

        for (WellInfo w : wells) {
            boolean marked = false;
            int msg = w.getMapLocation().x + (1 << 6) * (w.getMapLocation().y) + (1 << 12) * 9;
            int[] start_stop = getStartStop(w.getResourceType());
            for (int i = start_stop[0]; i < start_stop[1]; i++) {
                int val = rc.readSharedArray(i);
                MapLocation m = new MapLocation(val & (0b111111), (val >> 6) & (0b111111));
                if (w.getMapLocation().equals(m)) {
                    marked = true;
                    break;
                }
            }
            if (marked) continue;
            for (int i = start_stop[0]; i < start_stop[1]; i++) {
                int val = rc.readSharedArray(i);
                if(val == 0){
                    rc.writeSharedArray(i, msg);
                    break;
                }
            }
        }
    }

    public boolean isHQDead(MapLocation m) throws GameActionException {
        for (int i = ENEMY_HQ; i < ENEMY_HQ + ENEMY_HQ_WIDTH; i++){
            int val = rc.readSharedArray(i);
            MapLocation cur = new MapLocation((val >> 1) & (0b111111), (val >> 7) & (0b111111));
            if (cur.equals(m)) {
                int ans = ((val >> 13) & (0b1));
                return ans == 1;
            }
        }
        return false;
    }

    public void markHQDead(MapLocation m) throws GameActionException {
        for (int i = ENEMY_HQ; i < ENEMY_HQ + ENEMY_HQ_WIDTH; i++) {
            int val = rc.readSharedArray(i);
            if (val == 0) continue;
            MapLocation cur = new MapLocation((val >> 1) & (0b111111), (val >> 7) & (0b111111));
            if (cur.equals(m)) {
                int message = 1 + (1 << 1) * m.x + (1 << 7) * m.y + (1 << 13) * 1;
                if (rc.canWriteSharedArray(0, 0))
                    rc.writeSharedArray(i, message);
                break;
            }
        }
    }

    public void reportEnemyHQs() throws GameActionException {
        if (symmetryChecker.getSymmetry() != -1) return;
        RobotInfo[] rb = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());
        for(RobotInfo r : rb) {
            if (r.getType() != RobotType.HEADQUARTERS) continue;
            if (!rc.canWriteSharedArray(0, 0)){
                updateEnemyHQCache(r);
                continue;
            }
            int msg = 1 + (1 << 1) * r.getLocation().x + (1 << 7) * r.getLocation().y;
            boolean marked = false;
            for (int i = ENEMY_HQ; i < ENEMY_HQ + ENEMY_HQ_WIDTH; i++){
                int val = rc.readSharedArray(i);
                MapLocation m = new MapLocation((val >> 1) & (0b111111), (val >> 7) & (0b111111));
                if (r.location.equals(m)) {
                    marked = true;
                    break;
                }
            }
            if (marked) continue;
            for(int i = ENEMY_HQ; i < ENEMY_HQ + ENEMY_HQ_WIDTH; i++){
                int val = rc.readSharedArray(i);
                if(val == 0){
                    rc.writeSharedArray(i, msg);
                    break;
                }
            }
        }
        reportEnemyHQCache();
    }

    public void updateEnemyHQCache(RobotInfo r) throws GameActionException {
        for(int i = 0; i < numEnemyHQCache; i++){
            if (r.getLocation().equals(EnemyHQsCache[i])){
                return;
            }
        }
        EnemyHQsCache[numEnemyHQCache++] = r.getLocation();
    }

    public void reportEnemyHQCache() throws GameActionException {
        if (symmetryChecker.getSymmetry() != -1) return;
        if (!rc.canWriteSharedArray(0, 0)) return;
        for (int i = 0; i < numEnemyHQCache; i++){
            int msg = 1 + (1 << 1) * EnemyHQsCache[i].x + (1 << 7) * EnemyHQsCache[i].y;
            boolean marked = false;
            for(int j = ENEMY_HQ; j < ENEMY_HQ + ENEMY_HQ_WIDTH; j++){
                int val = rc.readSharedArray(j);
                if(val == msg){
                    marked = true;
                    break;
                }
            }
            if (!marked){
                for(int j = ENEMY_HQ; j < ENEMY_HQ + ENEMY_HQ_WIDTH; j++){
                    int val = rc.readSharedArray(j);
                    if(val == 0){
                        rc.writeSharedArray(j, msg);
                        break;
                    }
                }
            }
        }
        numEnemyHQCache = 0;
    }

    public void checkEnemyHQs() throws GameActionException {
        if (symmetryChecker.getSymmetry() == -1 || pushedHQs) return;
        int count = 0;
        for (int i = ENEMY_HQ; i < ENEMY_HQ + ENEMY_HQ_WIDTH; i++) {
            int val = rc.readSharedArray(i);
            if (val != 0) count++;
        }
        if (count == numHQ) return;
        int ind = 0;
        for (MapLocation m: HQs) {
            if (m == null) continue;
            MapLocation s = null;
            switch (symmetryChecker.getSymmetry()) {
                case 0: s = symmetryChecker.getHSym(m); break;
                case 1: s = symmetryChecker.getVSym(m); break;
                case 2: s = symmetryChecker.getRSym(m); break;
                default:
            }
            int message = 1 + (1 << 1) * s.x + (1 << 7) * s.y;
            if (rc.canWriteSharedArray(0, 0))
                rc.writeSharedArray(ENEMY_HQ + ind, message);
        }
        pushedHQs = true;
        numEnemyHQ = numHQ;
    }

    public MapLocation[] getEnemyHQs() throws GameActionException {
        if (symmetryChecker.getSymmetry() == -1) return null;
        MapLocation[] locs = new MapLocation[4];
        int ind = 0;
        for (MapLocation m: HQs) {
            if (m == null) continue;
            MapLocation s = null;
            switch (symmetryChecker.getSymmetry()) {
                case 0: s = symmetryChecker.getHSym(m); break;
                case 1: s = symmetryChecker.getVSym(m); break;
                case 2: s = symmetryChecker.getRSym(m); break;
                default:
            }
            locs[ind++] = s;
        }
        return locs;
    }

    public void report() throws GameActionException{
        //maybe dont report something on amplifiers bcz they are close (or at) bytecode limit
        reportWellCache();
        reportWells();
        reportCount();
        reportEnemyHQs();
    }

    public void reportCount() throws GameActionException{
        if(rc.getType() == RobotType.HEADQUARTERS) return;
        if(!rc.canWriteSharedArray(0, 0)) return;
        int val = rc.readSharedArray(UNIT_COUNTS + rc.getType().ordinal());
        if(lastReported < rc.getRoundNum() - (rc.getRoundNum() % Constants.REPORT_FREQ)) {
            rc.writeSharedArray(UNIT_COUNTS + rc.getType().ordinal(), val + 1);
            lastReported = rc.getRoundNum();
        }
    }

    public void findOurHQs() throws GameActionException{
        for (int i = HQ_LOCATIONS; i < HQ_LOCATIONS + HQ_LOCATIONS_WIDTH; i++) {
            int val = rc.readSharedArray(i);
            if (val == 0) continue;
            MapLocation hq = new MapLocation(val & (0b111111), (val >> 6) & (0b111111));
            if (hq.equals(rc.getLocation())) hqIndex = numHQ;
            HQs[numHQ++] = hq;
        }
    }

    public MapLocation findClosestHQ() throws GameActionException {
        return findClosestHQto(rc.getLocation());
    }

    public MapLocation findClosestHQto(MapLocation g) throws GameActionException {
        MapLocation best = null;
        int bestD = 100000;
        for (MapLocation m: HQs) {
            if (m == null) continue;
            int d = g.distanceSquaredTo(m);
            if (d < bestD) {
                bestD = d;
                best = m;
            }
        }
        return best;
    }

    public void broadcastAttackTargets() throws GameActionException {
        if (!rc.canWriteSharedArray(0, 0)) return;
        RobotInfo r = util.getBestAttackTarget();
        if (r != null && r.type != RobotType.HEADQUARTERS) 
            broadcastAttackTarget(r);
    }

    // Note that because of the reset method memory only lasts 10 turns.
    RobotInfo[] broadcastTargetMemory = new RobotInfo[3];
    public boolean broadcastAttackTarget(RobotInfo r) throws GameActionException {
        int low_health = r.health <= 8 ? 1 : 0;
        int priority;
        switch (r.type) {
            case BOOSTER: priority=4; break;
            case AMPLIFIER: priority=3; break;
            case HEADQUARTERS: priority=1; break;
            case CARRIER: priority=2; break;
            case LAUNCHER: priority=6; break;
            case DESTABILIZER: priority=5; break;
            default: priority=7;
        }
        int message = low_health + (1<<1) * priority + (1<<4) * r.location.x + (1<<10) * r.location.y;

        // if message already there, don't post it again.
        // otherwise write to an empty_index.
        boolean write = false;
        int empty_index = -1;
        for (int i = ATTACK_TARGETS; i < ATTACK_TARGETS + ATTACK_TARGETS_WIDTH; i++) {
            int cur = rc.readSharedArray(i);
            if (cur != 0) {
                int x = (cur>>4) & 0b111111;
                int y = (cur>>10) & 0b111111;
                MapLocation m = new MapLocation(x, y);
                // already there.
                if (m.equals(r.location)) return true;
            } else if (rc.canWriteSharedArray(i, message)) {
                empty_index = i;
                write = true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (broadcastTargetMemory[i] != null &&
                broadcastTargetMemory[i].equals(r)) 
                return true;
        }
        if (empty_index == -1) return false;
        if (!write) {
            broadcastTargetMemory[rc.getRoundNum()%3] = r;
            return false;
        } else {
            rc.writeSharedArray(empty_index, message);
            return true;
        }
    }

    public void clearTargets() throws GameActionException {
        if (!rc.canWriteSharedArray(0, 0)) return;
        for (int i = ATTACK_TARGETS; i < ATTACK_TARGETS + ATTACK_TARGETS_WIDTH; i++) {
            int message = rc.readSharedArray(i);
            if (message == 0) continue;
            int x = (message>>4) & (0b111111);
            int y = (message>>10) & (0b111111);
            MapLocation m = new MapLocation(x, y);
            if (rc.canSenseLocation(m)) {
                RobotInfo r = rc.senseRobotAtLocation(m);
                if (r == null) {
                    rc.writeSharedArray(i, 0);
                } 
            }
        }
    }

    public MapLocation findBestAttackTarget() throws GameActionException {
        AttackTarget best = null;
        for (int i = ATTACK_TARGETS; i < ATTACK_TARGETS + ATTACK_TARGETS_WIDTH; i++) {
            int message = rc.readSharedArray(i);
            if (message == 0) continue;
            AttackTarget cur = new AttackTarget(rc.readSharedArray(i));
            if (cur.isBetterThan(best)) best = cur;
        }
        //rc.setIndicatorString("Number of targets: " + count);
        if (best == null) return null;
        else return best.m;
    }

    public MapLocation getBestRendevous() throws GameActionException {
        // This could be made better if it accounted for the actual positions of HQs,
        // and the number of HQs, i.e make central points to consecutive pairs of HQs
        return symmetryChecker.getRSym(findClosestHQ());
    }

    public boolean isEnemyTerritory(MapLocation m) throws GameActionException {
        if (symmetryChecker.getSymmetry() == -1) return false;
        int minDistEnemy = 100000;
        int minDistAlly = 100000;
        for(int i = 0; i < numHQ; i++){
            MapLocation h = HQs[i];
            int d1 = h.distanceSquaredTo(m);
            MapLocation s = null;
            switch (symmetryChecker.getSymmetry()) {
                case 0: s = symmetryChecker.getHSym(h); break;
                case 1: s = symmetryChecker.getVSym(h); break;
                case 2: s = symmetryChecker.getRSym(h); break;
                default:
            }
            int d2 = s.distanceSquaredTo(m);
            if (d1 < minDistAlly) minDistAlly = d1;
            if (d2 < minDistEnemy) minDistEnemy = d2;
        }
        return minDistEnemy < minDistAlly;
    }

    public MapLocation getClosestEnemyHQ() throws GameActionException {
        return getClosestEnemyHQ(null);
    }

    public MapLocation getClosestEnemyHQ(MapLocation[] dead) throws GameActionException {
        return getClosestEnemyHQTo(rc.getLocation(), dead);
    }

    public MapLocation getClosestEnemyHQTo(MapLocation pos, MapLocation[] dead) throws GameActionException {
        MapLocation[] locs = null;
        if (symmetryChecker.getSymmetry() != -1) locs = getEnemyHQs();
        else if (EnemyHQEstimates != null) locs = EnemyHQEstimates;
        else locs = new MapLocation[]{estimateEnemyTerritory()};
        int minDistEnemy = 100000;
        MapLocation best = null;
        for(int i = 0; i < locs.length; i++){
            MapLocation h = locs[i];
            if (h == null) continue;
            if (dead != null) {
                boolean bad = false;
                for (MapLocation d: dead) {
                    if (d == null) continue;
                    if (d.distanceSquaredTo(h) <= 25) {
                        bad = true;
                        break;
                    }
                }
                if (bad) continue;
            }
            // if (isHQDead(h) && !countDead) continue;
            int d = pos.distanceSquaredTo(h);
            if (d < minDistEnemy) {
                minDistEnemy = d;
                best = h;
            }
        }
        return best;
    }

    public MapLocation estimateEnemyTerritory() throws GameActionException{
        int xPos = 0, yPos = 0;
        int tot = 0;
        for (int i = 0; i < numHQ; i++) {
            if (HQs[i] == null) continue;
            if (rc.readSharedArray(H_SYM) != 1){
               MapLocation cr = symmetryChecker.getHSym(HQs[i]);
               xPos += cr.x;
               yPos += cr.y;
               tot++;
            }
            if (rc.readSharedArray(V_SYM) != 1){
               MapLocation cr = symmetryChecker.getVSym(HQs[i]);
               xPos += cr.x;
               yPos += cr.y;
               tot++;
            }
            if (rc.readSharedArray(R_SYM) != 1){
               MapLocation cr = symmetryChecker.getRSym(HQs[i]);
               xPos += cr.x;
               yPos += cr.y;
               tot++;
            }
            MapLocation g = getFar(HQs[i]);
            xPos += g.x;
            yPos += g.y;
            tot++;
       }
       if(tot == 0) return null;
       return new MapLocation(xPos / tot, yPos / tot);
    }


    public void estimateEnemyHQs() throws GameActionException {
        boolean hSym = rc.readSharedArray(H_SYM) != 1;
        boolean vSym = rc.readSharedArray(V_SYM) != 1;
        boolean rSym = rc.readSharedArray(R_SYM) != 1;
        // at least one degree must be eliminate for this to be useful.
        if (EnemyHQEstimates != null) return;
        // we already know the symmetry.
        if (symmetryChecker.getSymmetry() != -1) return;
        if (hSym && vSym && rSym) return;
        int ind = 0;
        MapLocation[] locs = new MapLocation[numHQ * 2];
        for (int i = 0; i < numHQ; i++) {
            if (HQs[i] == null) continue;
            if (hSym) {
                MapLocation cr = symmetryChecker.getHSym(HQs[i]);
                locs[ind++] = cr;
            }
            if (vSym) {
                MapLocation cr = symmetryChecker.getVSym(HQs[i]);
                locs[ind++] = cr;
            }
            if (rSym) {
                MapLocation cr = symmetryChecker.getRSym(HQs[i]);
                locs[ind++] = cr;
            }
        }
        int ind2 = 0;
        int[] marked = new int[numHQ * 2];
        int[] count = {1, 1, 1, 1, 1};
        MapLocation[] estimates = new MapLocation[numHQ];
        for (int i = 0; i < numHQ * 2; i++) {
            if (marked[i] != 0) continue;
            if (locs[i] == null) continue;
            int bestD = 1000000;
            int bestJ = -1;
            for (int j = 0; j < numHQ * 2; j++) {
                if (marked[j] != 0 || j == i) continue;
                if (locs[j] == null) continue;
                int d = locs[j].distanceSquaredTo(locs[i]);
                if (d > 225) continue;
                if (d <= bestD) {
                    bestD = d;
                    bestJ = j;
                }
            }
            if (bestJ != -1) {
                marked[bestJ] = 1;
                marked[i] = 1;
                count[ind2]++;
                estimates[ind2++] = new MapLocation(locs[i].x + locs[bestJ].x,
                    locs[i].y + locs[bestJ].y);
            } else {
                marked[i] = -1;
            }
        }
        for (int i = 0; i < numHQ * 2; i++) {
            if (marked[i] != -1) continue;
            int bestD = 1000000;
            int bestJ = -1;
            for (int j = 0; j < ind2; j++) {
                int d = estimates[j].distanceSquaredTo(locs[i]);
                if (d > 225) continue;
                if (d <= bestD) {
                    bestD = d;
                    bestJ = j;
                }
            }
            if (bestJ == -1) return;
            count[bestJ]++;
            estimates[bestJ] = new MapLocation(estimates[bestJ].x + locs[i].x, 
                estimates[bestJ].y + locs[i].y);
        }
        int nonNull = 0;
        for (int i = 0; i < numHQ; i++) {
            if (estimates[i] == null) continue;
            estimates[i] = new MapLocation(estimates[i].x / count[i],
                estimates[i].y / count[i]);
            nonNull++;
        }
        if (nonNull == 0) return;
        EnemyHQEstimates = estimates;
    }

    public MapLocation getFar(MapLocation m) throws GameActionException{
        int d = -1;
        MapLocation bst = null;
        MapLocation a = new MapLocation(m.x, rc.getMapHeight() - 1 - m.y);
        if(a.distanceSquaredTo(m) > d){
            bst = a;
            d = a.distanceSquaredTo(m);
        }
        MapLocation b = new MapLocation(rc.getMapWidth() - 1 - m.x, rc.getMapHeight() - 1 - m.y);
        if(b.distanceSquaredTo(m) > d){
            bst = b;
            d = b.distanceSquaredTo(m);
        }
        MapLocation c = new MapLocation(rc.getMapWidth() - 1 - m.x, m.y);
        if(c.distanceSquaredTo(m) > d){
            bst = c;
            d = c.distanceSquaredTo(m);
        }
        return bst;
    }

    // Find best well using only comms [i think that's ok].
    public MapLocation findBestWell(ResourceType want) throws GameActionException {
        WellTarget best = null;
        for (int i = ADAMANTIUM_LOCATIONS; i < ELIXIR_LOCATIONS + ELIXIR_LOCATIONS_WIDTH; i++) {
            if (rc.readSharedArray(i) == 0) continue;
            int val = rc.readSharedArray(i);
            int x = val & (0b111111);
            int y = (val >> 6) & (0b111111);
            int available = (val >> 12) & (0b1111);
            // can do a better estimate.
            if (rc.canSenseLocation(new MapLocation(x, y))) continue;
            WellTarget cur = new WellTarget(new MapLocation(x, y),
                getTypeFromIndex(i), want, available);
            if (cur.isBetterThan(best)) best = cur;
        }

        for (WellInfo w: rc.senseNearbyWells()) {
            int available = 0;
            for (Direction dir: directions) {
                MapLocation m = w.getMapLocation().add(dir);
                if (rc.canSenseLocation(m) && rc.sensePassability(m) && 
                    !rc.isLocationOccupied(m)) {
                    available++;
                }
            }
            WellTarget cur = new WellTarget(w.getMapLocation(), w.getResourceType(), want, available);
            if (cur.isBetterThan(best)) best = cur;
        }
        if (best == null) return null;
        if (best.dist > 15) return null;
        //if(!best.bestResource()) return null;
        // decrement it. now we dynamically specify availability!!
        updateAvailability(best.loc, Math.max(best.avail - 1, 0));
        return best.loc;
    }

    class WellTarget {
        MapLocation loc;
        double dist;
        ResourceType r;
        ResourceType want;
        int avail;
        WellTarget(MapLocation m, ResourceType res, ResourceType want, int avail) 
            throws GameActionException {
            loc = m;
            dist = Util.absDistance(loc, rc.getLocation());
            this.avail = avail;
            this.r = res;
            this.want = want;
        }

        boolean bestResource() throws GameActionException {
            return r == want;
        }

        boolean crowded() throws GameActionException {
            return avail <= 2;
        }

        boolean isBetterThan(WellTarget wt) throws GameActionException {
            if (wt == null) return true;
            if (!wt.crowded() && crowded()) return false;
            if (wt.crowded() && !crowded()) return true;
            if (wt.bestResource() && !bestResource()) return false;
            if (!wt.bestResource() && bestResource()) return true;
            if (wt.dist + 8 < dist) return false;
            if (wt.dist > dist + 8) return true;
            return dist <= wt.dist;
        }
    }

    class AttackTarget {
        boolean low_health;
        int priority;
        double d;
        MapLocation m;
        AttackTarget(int message) {
            low_health = (message&1) == 1;
            priority = (message>>1) & (0b111);
            int x = (message>>4) & (0b111111);
            int y = (message>>10) & (0b111111);
            m = new MapLocation(x, y);
            d = Util.absDistance(rc.getLocation(), m);
        }

        boolean isBetterThan(AttackTarget at) {
            if (at == null) return true;
            if (at.d + 8 < d) return false;
            if (d + 8 < at.d) return true;
            if (at.priority > priority) return false;
            if (at.priority < priority) return true;
            if (at.low_health && !low_health) return false;
            if (!at.low_health && low_health) return true;
            return d <= at.d;
        }
    }
    // Essentially ported from Xsquare's symmetry checker.
    // Use getSymmetry to figure out if symmetry is indeterminate,
    // or which specific symmetry it is.
    class SymmetryChecker {
        class Data {
            Direction current;
            int tileType;
            int wellType;
            int isHQ;
            Data(Direction current, int tileType, int wellType, int status) {
                this.current = current;
                this.tileType = tileType;
                this.wellType = wellType;
                this.isHQ = status;
            }
        }

        int W, H;
        RobotController rc;
        Data[][] tiles;
        boolean ready = false;
        int curW = 0;
        boolean[] updates = {false, false, false};
        boolean hSym = true;
        boolean vSym = true;
        boolean rSym = true;
        boolean checkedHQs = false;

        SymmetryChecker(RobotController rc) {
            this.rc = rc;
            W = rc.getMapWidth();
            H = rc.getMapHeight();
            tiles = new Data[W][];
        }

        boolean isReady() {
            if (ready) return true;
            while (curW < W) {
                if (Clock.getBytecodesLeft() < 300) return false;
                tiles[curW] = new Data[H];
                curW++;
            }
            ready = true;
            return true;
        }

        int getSymmetry() throws GameActionException {
            hSym = (rc.readSharedArray(H_SYM) == 0) & !updates[0];
            vSym = (rc.readSharedArray(V_SYM) == 0) & !updates[1];
            rSym = (rc.readSharedArray(R_SYM) == 0) & !updates[2];
            if (hSym && !vSym && !rSym) return 0;
            if (!hSym && vSym && !rSym) return 1;
            if (!hSym && !vSym && rSym) return 2;
            return -1;
        }

        void pushUpdates() throws GameActionException {
            if (rc.canWriteSharedArray(H_SYM, 0)) {
                if (updates[0]) rc.writeSharedArray(H_SYM, 1);
                if (updates[1]) rc.writeSharedArray(V_SYM, 1);
                if (updates[2]) rc.writeSharedArray(R_SYM, 1);
            }
        }

        void updateHQSymmetry() throws GameActionException {
            // Mark whether or not you can see an enemy HQ.
            if (getSymmetry() != -1) return;
            for (int i = 0; i < 4; i++) {
                if (Clock.getBytecodesLeft() < 500) break;
                MapLocation m = HQs[i];
                if (m == null) continue;
                if (hSym) {
                    MapLocation s = getHSym(m);
                    if (rc.canSenseLocation(s)) {
                        RobotInfo e = rc.senseRobotAtLocation(s);
                        if (e == null || e.team != rc.getTeam().opponent() || e.type != RobotType.HEADQUARTERS)
                            hSym = false;
                    }
                }
                if (vSym) {
                    MapLocation s = getVSym(m);
                    if (rc.canSenseLocation(s)) {
                        RobotInfo e = rc.senseRobotAtLocation(s);
                        if (e == null || e.team != rc.getTeam().opponent() || e.type != RobotType.HEADQUARTERS)
                            vSym = false;
                    }
                }
                if (rSym) {
                    MapLocation s = getRSym(m);
                    if (rc.canSenseLocation(s)) {
                        RobotInfo e = rc.senseRobotAtLocation(s);
                        if (e == null || e.team != rc.getTeam().opponent() || e.type != RobotType.HEADQUARTERS)
                            rSym = false;
                    }
                }
            }

            if (rc.canWriteSharedArray(H_SYM, 0)) {
                if (!hSym) rc.writeSharedArray(H_SYM, 1);
                if (!vSym) rc.writeSharedArray(V_SYM, 1);
                if (!rSym) rc.writeSharedArray(R_SYM, 1);
            } else {
                if (!hSym) updates[0] = true;
                if (!vSym) updates[1] = true;
                if (!rSym) updates[2] = true;
            }
        }

        void updateWellSymmetry() throws GameActionException {
            if (getSymmetry() != -1) return;
            for (int i = ADAMANTIUM_LOCATIONS; i < ELIXIR_LOCATIONS + ELIXIR_LOCATIONS_WIDTH; i++) {
                if (Clock.getBytecodesLeft() < 500) break;
                int val = rc.readSharedArray(i);
                if (val == 0) continue;
                MapLocation m = new MapLocation(val & (0b111111), (val >> 6) & (0b111111));

                // get resource type.
                ResourceType r = getTypeFromIndex(i);

                if (hSym) {
                    MapLocation s = getHSym(m);
                    if (rc.canSenseLocation(s)) {
                        WellInfo w = rc.senseWell(s);
                        if (w == null || w.getResourceType() != r)
                            hSym = false;
                    }
                }
                if (vSym) {
                    MapLocation s = getVSym(m);
                    if (rc.canSenseLocation(s)) {
                        WellInfo w = rc.senseWell(s);
                        if (w == null || w.getResourceType() != r)
                            vSym = false;
                    }
                }
                if (rSym) {
                    MapLocation s = getRSym(m);
                    if (rc.canSenseLocation(s)) {
                        WellInfo w = rc.senseWell(s);
                        if (w == null || w.getResourceType() != r)
                            rSym = false;
                    }
                }

                if (rc.canWriteSharedArray(H_SYM, 0)) {
                    if (!hSym) rc.writeSharedArray(H_SYM, 1);
                    if (!vSym) rc.writeSharedArray(V_SYM, 1);
                    if (!rSym) rc.writeSharedArray(R_SYM, 1);
                } else {
                    if (!hSym) updates[0] = true;
                    if (!vSym) updates[1] = true;
                    if (!rSym) updates[2] = true;
                }
            }
        }

        void updateSymmetry() throws GameActionException {
            pushUpdates();
            updateHQSymmetry();
            updateWellSymmetry();
            if (!isReady()) return;
            MapInfo[] area = rc.senseNearbyMapInfos(-1);
            int iters = 0;
            for (MapInfo mi: area) {
                if (Clock.getBytecodesLeft() < 750) break;
                if (getSymmetry() != -1) break;
                MapLocation m = mi.getMapLocation();
                int status = -1;
                if (rc.canSenseRobotAtLocation(m)) {
                    RobotInfo r = rc.senseRobotAtLocation(m);
                    if (r == null) status = 0;
                    else if (r.type == RobotType.HEADQUARTERS) 
                        status = r.team == rc.getTeam() ? 1 : 2;
                }
                int wellType = -1;
                if (rc.canSenseLocation(m)) {
                    WellInfo w = rc.senseWell(m);
                    if (w != null) {
                        boolean hasA = w.getResource(ResourceType.ADAMANTIUM) > 0;
                        boolean hasM = w.getResource(ResourceType.MANA) > 0;
                        if (hasA && !hasM) wellType = 1;
                        if (!hasA && hasM) wellType = 2;
                    } else {
                        wellType = 0;
                    }
                }
                
                int tileType = 0;
                if (rc.senseIsland(m) != -1) tileType = 1;
                else if (mi.hasCloud()) tileType = 2;

                tiles[m.x][m.y] = new Data(mi.getCurrentDirection(), tileType, wellType, status);

                if (hSym) {
                    MapLocation s = getHSym(m);
                    if (!s.equals(m)) {
                        Data sym = tiles[s.x][s.y];
                        if (sym != null) {
                            if (mi.getCurrentDirection().getDeltaX() != sym.current.getDeltaX() ||
                                mi.getCurrentDirection().getDeltaY() != -1*sym.current.getDeltaY())
                                hSym = false;
                            if ((sym.isHQ != -1 && status != -1) && 
                                (sym.isHQ + tiles[m.x][m.y].isHQ) != 3) hSym = false;
                            if (sym.tileType != tileType) hSym = false;
                            if ((sym.wellType != -1 && wellType != -1) && 
                                sym.wellType != tiles[m.x][m.y].wellType) hSym = false;
                        }
                    }
                }
                if (vSym) {
                    MapLocation s = getVSym(m);
                    if (!s.equals(m)) {
                        Data sym = tiles[s.x][s.y];
                        if (sym != null) {
                            if (mi.getCurrentDirection().getDeltaY() != sym.current.getDeltaY() ||
                                mi.getCurrentDirection().getDeltaX() != -1*sym.current.getDeltaX())
                                vSym = false;
                            
                            if ((sym.isHQ != -1 && status != -1) && 
                                (sym.isHQ + tiles[m.x][m.y].isHQ) != 3) vSym = false;
                            if (sym.tileType != tileType) vSym = false;
                            if ((sym.wellType != -1 && wellType != -1) && 
                                sym.wellType != tiles[m.x][m.y].wellType) vSym = false;
                        }
                    }
                }
                if (rSym) {
                    MapLocation s = getRSym(m);
                    if (!s.equals(m)) {
                        Data sym = tiles[s.x][s.y];
                        if (sym != null) {
                            if (mi.getCurrentDirection() != sym.current.opposite()) 
                                rSym = false;

                            if ((sym.isHQ != -1 && status != -1) && 
                                (sym.isHQ + tiles[m.x][m.y].isHQ) != 3) rSym = false;
                            if (sym.tileType != tileType) rSym = false;
                            if ((sym.wellType != -1 && wellType != -1) && 
                                sym.wellType != tiles[m.x][m.y].wellType) rSym = false;
                        }
                    }
                }
                if (rc.canWriteSharedArray(H_SYM, 0)) {
                    if (!hSym) rc.writeSharedArray(H_SYM, 1);
                    if (!vSym) rc.writeSharedArray(V_SYM, 1);
                    if (!rSym) rc.writeSharedArray(R_SYM, 1);
                } else {
                    if (!hSym) updates[0] = true;
                    if (!vSym) updates[1] = true;
                    if (!rSym) updates[2] = true;
                }
                iters++;
            }
        }

        MapLocation getHSym(MapLocation a) {
            return new MapLocation(a.x, H - a.y - 1);
        }

        MapLocation getVSym(MapLocation a) {
            return new MapLocation(W - a.x - 1, a.y);
        }

        MapLocation getRSym(MapLocation a) {
            return new MapLocation(W - a.x - 1, H - a.y - 1);
        }
    }
}