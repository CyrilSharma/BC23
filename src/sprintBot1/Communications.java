package sprintBot1;
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
    ResourceType resourceNeeded = null;
    
    static final int MAX_WELL_STORED = 10;
    WellInfo[] wellCache = new WellInfo[MAX_WELL_STORED];
    int numWells = 0;
    ResourceType[] resources = {ResourceType.ADAMANTIUM, ResourceType.MANA, ResourceType.ELIXIR};
    int numHQ = 0;
    int numEnemyHQCache = 0;
    int numEnemyHQ = 0;
    int lastReported = -10;
    int index;
    //constants
    // this should never be used in a for loop.
    // static final int SHARED_ARRAY_SIZE = 64;
    static final int MAX_WELLS_FOR_TYPE = 4;

    //message types
    static final int ADAMANTIUM_WELL = 0;
    static final int MANA_WELL = 1;
    static final int ELIXIR_WELL = 2;
    static final int HQ_LOCATION = 3;

    //stuff like our HQs and resource locations
    static final int KEYLOCATIONS = 0;
    static final int KEYLOCATIONS_WIDTH = 15;

    static final int ATTACK_TARGETS = KEYLOCATIONS + KEYLOCATIONS_WIDTH;
    static final int ATTACK_TARGETS_WIDTH = 10;

    //for maintaining unit counts
    static final int UNIT_COUNTS = ATTACK_TARGETS + ATTACK_TARGETS_WIDTH;
    static final int UNIT_COUNTS_WIDTH = 8;

    static final int BUILD_COUNT = UNIT_COUNTS + UNIT_COUNTS_WIDTH;
    static final int BUILD_COUNT_WIDTH = 8;

    static final int RESOURCE_COUNT = BUILD_COUNT + BUILD_COUNT_WIDTH;
    static final int RESOURCE_COUNT_WIDTH = 3;

    static final int RESOURCE_NEED = RESOURCE_COUNT + RESOURCE_COUNT_WIDTH;
    static final int RESOURCE_NEED_WIDTH = 3;

    static final int ENEMY_HQ = RESOURCE_NEED + RESOURCE_NEED_WIDTH;
    static final int ENEMY_HQ_WIDTH = 4;

    static final int H_SYM = ENEMY_HQ + ENEMY_HQ_WIDTH;
    static final int V_SYM = H_SYM + 1;
    static final int R_SYM = V_SYM + 1;

    static final int ANCHOR = R_SYM + 1;
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
        clearTargets();
        broadcastAttackTargets();
        setMineRatio();
    }

    public boolean getGreedy() throws GameActionException {
        // scales between 0 and 1/2
        double total = 4 * numHQ;
        int mn = Math.min(rc.getMapHeight(), rc.getMapWidth());
        int numExplore = Math.min((int) Math.round((((double) mn) / 100.0) * total), 4);
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
        //System.out.println("COUNT: "+count);
        double total = 4 * numHQ;
        int mn = Math.min(rc.getMapHeight(), rc.getMapWidth());
        int nAdamantium = Math.min((int) Math.round((((double) mn / 60.0) * total)), (int)(0.75 * total));
        //System.out.println("nAdamantium: "+nAdamantium);
        //System.out.println(nAdamantium);
        if (count < nAdamantium) return ResourceType.ADAMANTIUM;
        else return ResourceType.MANA;
    }

    public void updateAnchor(int a) throws GameActionException {
        rc.writeSharedArray(ANCHOR, a);
    }

    public boolean shouldBuildAnchor() throws GameActionException {
        return rc.readSharedArray(ANCHOR) == 0;
    }

    public void setMineRatio() throws GameActionException {
        // Add more complex logic!! prob should account for mapsize and roundnum.
        if (rc.getType() == RobotType.HEADQUARTERS) {
            if (rc.getRoundNum() <= 150) {
                setResourceNeed(ResourceType.MANA, 4);
                setResourceNeed(ResourceType.ADAMANTIUM, 2);
            } else if (rc.getRoundNum() <= 250){
                setResourceNeed(ResourceType.MANA, 4);
                setResourceNeed(ResourceType.ADAMANTIUM, 2);
            } else {
                setResourceNeed(ResourceType.MANA, 9);
                setResourceNeed(ResourceType.ADAMANTIUM, 3);
            }
        }
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
            if (rc.getRoundNum()%Constants.ATTACK_REFRESH >= ATTACK_TARGETS_WIDTH) return;
            rc.writeSharedArray(ATTACK_TARGETS + (rc.getRoundNum()%Constants.ATTACK_REFRESH), 0);
        }
    }

    // the idea here is the value will be weights.
    // the weight will be used to sample from a random distribution to determine
    // the desired resource whilst mantaining a raio.
    public void setResourceNeed(ResourceType r, int val) throws GameActionException {
        int type = 10000000;
        if (r == ResourceType.ADAMANTIUM) type = 0;
        if (r == ResourceType.MANA) type = 1;
        if (r == ResourceType.ELIXIR) type = 2;
        rc.writeSharedArray(RESOURCE_NEED + type, val);
    }

    public ResourceType getResourceNeed() throws GameActionException {
        int sum = 0;
        int prev=0;
        int cur;
        int[] bounds = new int[3];
        for(int i = 0; i < 3; i++){
            cur = rc.readSharedArray(RESOURCE_NEED + i);
            sum += cur;
            bounds[i] = prev + cur;
            prev = bounds[i];
        }
        ResourceType[] res = {ResourceType.ADAMANTIUM, ResourceType.MANA, ResourceType.ELIXIR};
        if (sum == 0) return res[rng.nextInt(3)];
        int val = rng.nextInt(sum);

        for (int i = 0; i < 3; i++) {
            if (val < bounds[i]) {
                /* if (rc.getRoundNum()<20) {
                    System.out.println("ADAMANTIUM: "+rc.readSharedArray(RESOURCE_NEED)+" MANA: "+rc.readSharedArray(RESOURCE_NEED+1));
                    System.out.println("BOUND[0]: "+bounds[0]+" BOUND[1]: "+bounds[1]);
                    System.out.println("SUM: "+sum);
                    System.out.println("VAL: "+val);
                    System.out.println(res[i]);
                } */
                return res[i];
            }
        }
        return res[rng.nextInt(3)];
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
            if (broadcastTargetMemory[i] != null)
                broadcastAttackTarget(broadcastTargetMemory[i]);
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

    public boolean writeTypeLoc(int type, MapLocation loc) throws GameActionException{
        int xLoc = loc.x, yLoc = loc.y;
        // 10 so we reserve other space. we can play w this.
        for (int i = KEYLOCATIONS; i < KEYLOCATIONS + KEYLOCATIONS_WIDTH; i++) {
            if (rc.readSharedArray(i) != 0) continue;
            int msg = type + (1 << 3) * (xLoc) + (1 << 9) * yLoc;
            if (rc.canWriteSharedArray(i, msg)){
                rc.writeSharedArray(i, msg);
                return true;
            }
            break;
        }
        return false;
    }

    public void getWell(ResourceType r) throws GameActionException {
        int ind = 0;
        MapLocation[] locs = new MapLocation[15];
        for (int i = KEYLOCATIONS; i < KEYLOCATIONS + KEYLOCATIONS_WIDTH; i++) {
            if (rc.readSharedArray(i) == 0) continue;
            int val = rc.readSharedArray(i);
            int typ = val & (0b111);
            if (typ == 3) continue;
            if(resources[typ] == r){
                MapLocation m = new MapLocation((val >> 3) & (0b111111), (val >> 9) & (0b111111));
            }
        }
    }

    // return all wells
    public MapLocation[] readWells(ResourceType r) throws GameActionException{
        MapLocation[] locs = new MapLocation[15];
        int ind = 0;
        for (int i = KEYLOCATIONS; i < KEYLOCATIONS + KEYLOCATIONS_WIDTH; i++) {
            if (rc.readSharedArray(i) == 0) continue;
            int val = rc.readSharedArray(i);
            int typ = val & (0b111);
            if (typ == 3) continue;
            if(resources[typ] == r){
                MapLocation m = new MapLocation((val >> 3) & (0b111111), (val >> 9) & (0b111111));
                locs[ind] = new MapLocation((val >> 3) & (0b111111), (val >> 9) & (0b111111));
                ind++;
            }
        }
        return locs;
    }

    public void reportWellCache() throws GameActionException{
        for(int i = 0; i < numWells; i++){
            boolean marked = false;
            for(int j = 0; j < KEYLOCATIONS_WIDTH; j++){
                int val = rc.readSharedArray(j);
                if(val == 0) continue;
                MapLocation ex = new MapLocation((val >> 3) & (0b111111), (val >> 9) & (0b111111));
                if(wellCache[i].getMapLocation().equals(ex)){
                    marked = true;
                    break;
                }
            }
            if(!marked){
                for(int j = 0; j < KEYLOCATIONS_WIDTH; j++) {
                    int val = rc.readSharedArray(j);
                    if(val != 0) continue;
                    int msg = MANA_WELL + (1 << 3) * (wellCache[i].getMapLocation().x) + (1 << 9) * (wellCache[i].getMapLocation().y);
                    if (wellCache[i].getResourceType() == ResourceType.ADAMANTIUM){
                        msg = ADAMANTIUM_WELL + (1 << 3) * (wellCache[i].getMapLocation().x) + (1 << 9) * (wellCache[i].getMapLocation().y);
                    }
                    rc.writeSharedArray(j, msg);
                    break;
                }
            }
        }
        numWells = 0;
    }

    public void reportWells() throws GameActionException{
        WellInfo[] wells = rc.senseNearbyWells();
        if(wells.length == 0) return;
        int ind = 0;
        int[] freeSlots = new int[wells.length];
        int cntMana = 0, cntAda = 0, cntElixir = 0;
        for (int i = KEYLOCATIONS; i < KEYLOCATIONS + KEYLOCATIONS_WIDTH; i++) {
            if(rc.readSharedArray(i) != 0){
                int val = rc.readSharedArray(i);
                int typ = val & (0b111);
                if(typ == MANA_WELL) cntMana++;
                if(typ == ADAMANTIUM_WELL) cntAda++;
                if(typ == ELIXIR_WELL) cntElixir++;
            }
            else if(ind < wells.length){
                freeSlots[ind++] = i;
            }
        }
        if(!rc.canWriteSharedArray(0, 0)){
            //remember the wells
            for(WellInfo w : wells){
                boolean marked = false;
                for(int i = 0; i < numWells; i++){
                    if(w.getMapLocation().equals(wellCache[i].getMapLocation())){
                        marked = true;
                        break;
                    }
                }
                //now check in messages
                for(int i = 0; i < KEYLOCATIONS_WIDTH; i++){
                    int val = rc.readSharedArray(i);
                    if(val == 0) continue;
                    MapLocation ex = new MapLocation((val >> 3) & (0b111111), (val >> 9) & (0b111111));
                    if(w.getMapLocation().equals(ex)){
                        marked = true;
                        break;
                    }
                }
                if(!marked && numWells < MAX_WELL_STORED){
                    wellCache[numWells++] = w;
                }
            }
            return;
        }
        //System.out.println("wells: " + numWells);
        reportWellCache();
        int addInd = 0;
        for (WellInfo w : wells){
            if (w.getResourceType().equals(ResourceType.MANA)) {
               if (cntMana < MAX_WELLS_FOR_TYPE) {
                   int msg = MANA_WELL + (1 << 3) * (w.getMapLocation().x) + (1 << 9) * (w.getMapLocation().y);
                   int not_marked = 1;
                   for(int i = 0; i < KEYLOCATIONS_WIDTH; i++){
                       if(rc.readSharedArray(i) == msg){
                           not_marked = 0;
                           break;
                       }
                   }
                   if(not_marked > 0) {
                       //System.out.println("Telling about mana well on: " + w.getMapLocation());
                       rc.writeSharedArray(freeSlots[addInd++], msg);
                       cntMana++;
                   }
               }
           }
           if(w.getResourceType().equals(ResourceType.ADAMANTIUM)) {
                if (cntAda < MAX_WELLS_FOR_TYPE) {
                    int msg = ADAMANTIUM_WELL + (1 << 3) * (w.getMapLocation().x) + (1 << 9) * (w.getMapLocation().y);
                    int not_marked = 1;
                    for(int i = 0; i < KEYLOCATIONS_WIDTH; i++){
                        if(rc.readSharedArray(i) == msg){
                            not_marked = 0;
                            break;
                        }
                    }
                    if(not_marked > 0){
                        //System.out.println("Telling about ada well on: " + w.getMapLocation());
                        rc.writeSharedArray(freeSlots[addInd++], msg);
                        cntAda++;
                    }
                }
           }
           if (w.getResourceType().equals(ResourceType.ELIXIR)) {
                if (cntElixir < MAX_WELLS_FOR_TYPE) {
                    int msg = ELIXIR_WELL + (1 << 3) * (w.getMapLocation().x) + (1 << 9) * (w.getMapLocation().y);
                    int not_marked = 1;
                    for(int i = 0; i < KEYLOCATIONS_WIDTH; i++){
                        if(rc.readSharedArray(i) == msg){
                            not_marked = 0;
                            break;
                        }
                    }
                    if(not_marked > 0) {
                        //System.out.println("Telling about elixir well on: " + w.getMapLocation());
                        rc.writeSharedArray(freeSlots[addInd++], msg);
                        cntElixir++;
                    }
                }
            }
       }
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

    public void reportEnemyHQs() throws GameActionException{
        RobotInfo[] rb = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());
        for(RobotInfo r : rb) if(r.getType() == RobotType.HEADQUARTERS){
            if(!rc.canWriteSharedArray(0, 0)){
                boolean alr = false;
                for(int i = 0; i < numEnemyHQCache; i++){
                    if(r.getLocation().equals(EnemyHQsCache[i])){
                        alr = true;
                    }
                }
                if(!alr){
                    EnemyHQsCache[numEnemyHQCache++] = r.getLocation();
                }
                continue;
            }
            int msg = 1 + (1 << 1) * r.getLocation().x + (1 << 7) * r.getLocation().y;
            boolean marked = false;
            for(int i = ENEMY_HQ; i < ENEMY_HQ + ENEMY_HQ_WIDTH; i++){
                int val = rc.readSharedArray(i);
                if(val == msg){
                    marked = true;
                    break;
                }
            }
            if(!marked){
                for(int i = ENEMY_HQ; i < ENEMY_HQ + ENEMY_HQ_WIDTH; i++){
                    int val = rc.readSharedArray(i);
                    if(val == 0){
                        rc.writeSharedArray(i, msg);
                        break;
                    }
                }
            }
        }
        if(rc.canWriteSharedArray(0, 0)) reportEnemyHQCache();
    }

    public void reportEnemyHQCache() throws GameActionException{
        for(int i = 0; i < numEnemyHQCache; i++){
            int msg = 1 + (1 << 1) * EnemyHQsCache[i].x + (1 << 7) * EnemyHQsCache[i].y;
            boolean marked = false;
            for(int j = ENEMY_HQ; j < ENEMY_HQ + ENEMY_HQ_WIDTH; j++){
                int val = rc.readSharedArray(j);
                if(val == msg){
                    marked = true;
                    break;
                }
            }
            if(!marked){
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

    public void checkEnemyHQs() throws GameActionException{
        for(int i = ENEMY_HQ; i < ENEMY_HQ + ENEMY_HQ_WIDTH; i++){
            int val = rc.readSharedArray(i);
            if(val == 0) continue;
            MapLocation cr = new MapLocation((val >> 1) & (0b111111), (val >> 7) & (0b111111));
            boolean marked = false;
            for(int j = 0; j < numEnemyHQ; j++){
                if(cr.equals(EnemyHQs[j])){
                    marked = true;
                    break;
                }
            }
            if(!marked) EnemyHQs[numEnemyHQ++] = cr;
            /*
            for(int j = 0; j < numEnemyHQ; j++){
                System.out.println(EnemyHQs[j]);
            }
            System.out.println("-------");
             */
        }
    }

    public void report() throws GameActionException{
        //maybe dont report something on amplifiers bcz they are close (or at) bytecode limit
        if(rc.getType() != RobotType.AMPLIFIER) reportWells();
        reportCount();
        reportEnemyHQs();
        //TODO: add reporting for other stuff - enemy HQs, enemy units, islands, etc.
    }

    public void findOurHQs() throws GameActionException{
        for (int i = 0; i < KEYLOCATIONS_WIDTH; i++) {
            if ((rc.readSharedArray(i) & (0b111)) != HQ_LOCATION) continue;
            int val = rc.readSharedArray(i);
            MapLocation hq = new MapLocation((val >> 3) & (0b111111), (val >> 9) & (0b111111));
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
        int count = 0;
        for (int i = ATTACK_TARGETS; i < ATTACK_TARGETS + ATTACK_TARGETS_WIDTH; i++) {
            int message = rc.readSharedArray(i);
            if (message == 0) continue;
            AttackTarget cur = new AttackTarget(rc.readSharedArray(i));
            if (cur.isBetterThan(best)) best = cur;
            count++;
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

    public boolean isEnemyTerritory(MapLocation m, MapLocation[] HQS, int numHQQ) throws GameActionException {
        if (symmetryChecker.getSymmetry() == -1) return false;
        int minDistEnemy = 100000;
        int minDistAlly = 100000;
        for(int i = 0; i < numHQQ; i++){
            MapLocation h = HQS[i];
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
        return getClosestEnemyHQTo(rc.getLocation());
    }

    public MapLocation getClosestEnemyHQTo(MapLocation pos) throws GameActionException {
        if (symmetryChecker.getSymmetry() == -1) return null;
        int minDistEnemy = 100000;
        MapLocation best = null;
        for(int i = 0; i < numHQ; i++){
            MapLocation h = HQs[i];
            MapLocation s = null;
            switch (symmetryChecker.getSymmetry()) {
                case 0: s = symmetryChecker.getHSym(h); break;
                case 1: s = symmetryChecker.getVSym(h); break;
                case 2: s = symmetryChecker.getRSym(h); break;
                default:
            }
            int d = pos.distanceSquaredTo(s);
            if (d < minDistEnemy) {
                minDistEnemy = d;
                best = s;
            }
        }
        return best;
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
        boolean hSym, vSym, rSym;

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
            hSym = (rc.readSharedArray(H_SYM) == 0);
            vSym = (rc.readSharedArray(V_SYM) == 0);
            rSym = (rc.readSharedArray(R_SYM) == 0);
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

        void updateSymmetry() throws GameActionException {
            // all default to 1, they have to be eliminated.
            if (!isReady()) return;
            pushUpdates();
            MapInfo[] area = rc.senseNearbyMapInfos(-1);
            for (int i = 0; i < numHQ; i++) {
                tiles[HQs[i].x][HQs[i].y] = new Data(Direction.CENTER, 0, 0, 1);
            }
            for (MapInfo mi: area) {
                if (Clock.getBytecodesLeft() < 1500) return;
                if (getSymmetry() != -1) return;
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
                    Data sym = tiles[s.x][s.y];
                    if (sym == null) continue;
                    if (mi.getCurrentDirection().getDeltaX() != sym.current.getDeltaX() ||
                        mi.getCurrentDirection().getDeltaY() != -1*sym.current.getDeltaY())
                        hSym = false;
                    if ((sym.isHQ != -1 && status != -1) && 
                        (sym.isHQ + tiles[m.x][m.y].isHQ) != 3) hSym = false;
                    if (sym.tileType != tileType) hSym = false;
                    if ((sym.wellType != -1 && wellType != -1) && 
                        sym.wellType != tiles[m.x][m.y].wellType) hSym = false;
                }

                if (vSym) {
                    MapLocation s = getVSym(m);
                    Data sym = tiles[s.x][s.y];
                    if (sym == null) continue;
                    if (mi.getCurrentDirection().getDeltaY() != sym.current.getDeltaY() ||
                        mi.getCurrentDirection().getDeltaX() != -1*sym.current.getDeltaX())
                        vSym = false;
                    
                    if ((sym.isHQ != -1 && status != -1) && 
                        (sym.isHQ + tiles[m.x][m.y].isHQ) != 3) vSym = false;
                    if (sym.tileType != tileType) vSym = false;
                    if ((sym.wellType != -1 && wellType != -1) && 
                        sym.wellType != tiles[m.x][m.y].wellType) vSym = false;
                }

                if (rSym) {
                    MapLocation s = getRSym(m);
                    Data sym = tiles[s.x][s.y];
                    if (sym == null) continue;
                    if (mi.getCurrentDirection() != sym.current.opposite()) 
                        rSym = false;

                    if ((sym.isHQ != -1 && status != -1) && 
                        (sym.isHQ + tiles[m.x][m.y].isHQ) != 3) rSym = false;
                    if (sym.tileType != tileType) rSym = false;
                    if ((sym.wellType != -1 && wellType != -1) && 
                        sym.wellType != tiles[m.x][m.y].wellType) rSym = false;
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