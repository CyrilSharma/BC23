package carrier_micro_unit_count;
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
    
    static final int MAX_WELL_STORED = 10;
    WellInfo[] wellCache = new WellInfo[MAX_WELL_STORED];
    int numWells = 0;
    ResourceType[] resources = {ResourceType.ADAMANTIUM, ResourceType.ELIXIR, ResourceType.MANA};
    int numHQ = 0;
    int numEnemyHQCache = 0;
    int numEnemyHQ = 0;
    int lastReported = -10;
    //constants
    // this should never be used in a for loop.
    // static final int SHARED_ARRAY_SIZE = 64;
    static final int MAX_WELLS_FOR_TYPE = 4;

    //message types
    static final int ADAMANTIUM_WELL = 0;
    static final int ELIXIR_WELL = 1;
    static final int MANA_WELL = 2;
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
    }

    public void last() throws GameActionException {
        symmetryChecker.updateSymmetry();
        /*
        System.out.println("Symmetry is...: " + symmetryChecker.getSymmetry());
        System.out.println("" + symmetryChecker.hSym + 
            " "  + symmetryChecker.vSym + 
            " " + symmetryChecker.rSym);
         */
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
        if (rc.getType() == RobotType.HEADQUARTERS) {
            if (rc.getRoundNum()%Constants.ATTACK_REFRESH > ATTACK_TARGETS_WIDTH) return;
            rc.writeSharedArray(ATTACK_TARGETS + rc.getRoundNum()%Constants.ATTACK_REFRESH, 0);
        }

        // purge memory after x turns to prevent bad updates.
        broadcastTargetMemory[rc.getRoundNum()%3] = null;
    }

    // the idea here is the value will be weights.
    // the weight will be used to sample from a random distribution to determine
    // the desired resource whilst mantaining a raio.
    public void setResourceNeed(ResourceType r, int val) throws GameActionException {
        rc.writeSharedArray(RESOURCE_NEED + r.ordinal(), val);
    }

    public ResourceType getResourceNeed() throws GameActionException {
        int sum = 0;
        int prev=0;
        int cur;
        int[] bounds = new int[3];
        for(int i = 0; i < 3; i++){
            cur = rc.readSharedArray(i);
            sum += cur;
            bounds[i] = prev + cur;
            prev = bounds[i];
        }
        if (sum == 0) return resources[rng.nextInt(3)];
        int val = rng.nextInt(sum);
        for (int i = 0; i < 3; i++) {
            if (val < bounds[i]) return resources[i];
        }
        return resources[rng.nextInt(3)];
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

    // always mines at a 50-50 ratio; may not want that.
    public ResourceType readResourceNeed() throws GameActionException {
        int mn = 10000000;
        ResourceType r = null;
        for (int i = 0; i < 3; i++) if(i != 1){
            int val = rc.readSharedArray(RESOURCE_COUNT + i);
            if(val < mn){
                mn = val;
                r = resources[i];
            }
        }
        if(rc.getRoundNum() <= 50) return ResourceType.MANA;
        return r;
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

    public MapLocation findBestWell() throws GameActionException{
        MapLocation bestWell = null;
        int dist = 1000000;
        //TODO: later on, go to elixir too (we don't seem to need it now)
        for (int i = KEYLOCATIONS; i < KEYLOCATIONS + KEYLOCATIONS_WIDTH; i++) {
            if (rc.readSharedArray(i) == 0) continue;
            int val = rc.readSharedArray(i);
            int typ = val & (0b111);
            if (typ == 3) continue;
            if(resources[typ] == readResourceNeed()){
                MapLocation w = new MapLocation((val >> 3) & (0b111111), (val >> 9) & (0b111111));
                if(rc.getLocation().distanceSquaredTo(w) < dist){
                    dist = rc.getLocation().distanceSquaredTo(w);
                    bestWell = w;
                }
            }
        }
        return bestWell;
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
                    if(wellCache[i].getResourceType() == ResourceType.ADAMANTIUM){
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
        MapLocation best = null;
        int bestD = 100000;
        for (MapLocation m: HQs) {
            if (m == null) continue;
            int d = rc.getLocation().distanceSquaredTo(m);
            if (d < bestD) {
                bestD = d;
                best = m;
            }
        }
        return best;
    }

    public void broadcastAttackTargets() throws GameActionException {
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
        if (!write) {
            broadcastTargetMemory[rc.getRoundNum()%3] = r;
            return false;
        } else {
            rc.writeSharedArray(empty_index, message);
            return true;
        }
    }

    public void clearTargets() throws GameActionException {
        for (int i = ATTACK_TARGETS; i < ATTACK_TARGETS + ATTACK_TARGETS_WIDTH; i++) {
            int message = rc.readSharedArray(i);
            if (message == 0) continue;
            int x = (message>>4) & (0b111111);
            int y = (message>>10) & (0b111111);
            MapLocation m = new MapLocation(x, y);
            // TODO: may be bugged because of clouds
            if (rc.canSenseLocation(m)) {
                RobotInfo r = rc.senseRobotAtLocation(m);
                if (r == null) {
                    if (rc.canWriteSharedArray(i, 0)) rc.writeSharedArray(i, 0);
                    else ; // add to memory?
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
            if (at.d + 4 < d) return false;
            if (d + 4 < at.d) return true;
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
    // does not currently account for clouds bc i wrote this offline
    // and couldn't intuit the api,
    class SymmetryChecker {
        class Data {
            Direction current;
            int tileType;
            int isHQ;
            Data(Direction current, int tileType, int status) {
                this.current = current;
                this.tileType = tileType;
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

        int getSymmetry() {
            if (hSym && !vSym && !rSym) return 0;
            if (!hSym && vSym && !rSym) return 1;
            if (!hSym && !vSym && rSym) return 2;
            return -1;
        }

        void updateSymmetry() throws GameActionException {
            // all default to 1, they have to be eliminated.
            if (!isReady()) return;
            hSym = rc.readSharedArray(H_SYM) == 0;
            vSym = rc.readSharedArray(V_SYM) == 0;
            rSym = rc.readSharedArray(R_SYM) == 0;
            MapInfo[] area = rc.senseNearbyMapInfos(-1);
            for (MapInfo mi: area) {
                if (Clock.getBytecodesLeft() < 500) return;
                if (getSymmetry() != -1) return;
                MapLocation m = mi.getMapLocation();
                int status;
                // check needed because of clouds.
                if (rc.canSenseRobotAtLocation(m)) {
                    RobotInfo r = rc.senseRobotAtLocation(m);
                    status = (r == null) ? 0 : 
                        (r.type == RobotType.HEADQUARTERS) ? 1 : 0;
                } else {
                    status = -1;
                }
                
                int tileType = 0;
                if (rc.senseIsland(m) != -1) tileType = 1;
                else if (mi.hasCloud()) tileType = 2;

                tiles[m.x][m.y] = new Data(mi.getCurrentDirection(), tileType, status);

                if (hSym) {
                    MapLocation s = getHSym(m);
                    Data sym = tiles[s.x][s.y];
                    if (sym == null) continue;
                    if (mi.getCurrentDirection().getDeltaX() != sym.current.getDeltaX() ||
                        mi.getCurrentDirection().getDeltaY() != -1*sym.current.getDeltaY())
                        hSym = false;
                    if ((sym.isHQ != -1 && status != -1) && 
                        sym.isHQ != tiles[m.x][m.y].isHQ) hSym = false;
                    if (sym.tileType != tileType) hSym = false;

                }

                if (vSym) {
                    MapLocation s = getVSym(m);
                    Data sym = tiles[s.x][s.y];
                    if (sym == null) continue;
                    if (mi.getCurrentDirection().getDeltaY() != sym.current.getDeltaY() ||
                        mi.getCurrentDirection().getDeltaX() != -1*sym.current.getDeltaX())
                        vSym = false;
                    
                    if ((sym.isHQ != -1 && status != -1) && 
                        sym.isHQ != tiles[m.x][m.y].isHQ) vSym = false;
                    if (sym.tileType != tileType) vSym = false;
                }

                if (rSym) {
                    MapLocation s = getRSym(m);
                    Data sym = tiles[s.x][s.y];
                    if (sym == null) continue;
                    if (mi.getCurrentDirection() != sym.current.opposite()) 
                        rSym = false;

                    if ((sym.isHQ != -1 && status != -1) && 
                        sym.isHQ != tiles[m.x][m.y].isHQ) rSym = false;
                    if (sym.tileType != tileType) rSym = false;
                }
                if (rc.canWriteSharedArray(H_SYM, 0)) {
                    if (!hSym || updates[0]) rc.writeSharedArray(H_SYM, 1);
                    if (!vSym || updates[1]) rc.writeSharedArray(V_SYM, 1);
                    if (!rSym || updates[2]) rc.writeSharedArray(R_SYM, 1);
                } else {
                    updates[0] = !hSym;
                    updates[1] = !vSym;
                    updates[2] = !rSym;
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