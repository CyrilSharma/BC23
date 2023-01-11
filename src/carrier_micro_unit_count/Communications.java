package carrier_micro_unit_count;
import battlecode.common.*;
// TODO: count the numbers of each unit for use in HQ!!
public class Communications {
    RobotController rc;
    Util util;
    MapLocation[] HQs = new MapLocation[5];
    ResourceType[] resources = {ResourceType.ADAMANTIUM, ResourceType.ELIXIR, ResourceType.MANA};
    int numHQ = 0;
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

    static final int RESOURCE_NEED = BUILD_COUNT + BUILD_COUNT_WIDTH;
    static final int RESOURCE_NEED_WIDTH = 3;

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
    }

    // all classes call this at the beginning.
    public void initial() throws GameActionException {
        refresh();
        sendMemory();
        report();
        clearTargets();
        broadcastAttackTargets();
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
        if (rc.getType() == RobotType.HEADQUARTERS)
            rc.writeSharedArray(ATTACK_TARGETS + rc.getRoundNum()%3, 0);

        // purge memory after x turns to prevent bad updates.
        broadcastTargetMemory[rc.getRoundNum()%3] = null;
    }

    // does not need this many channels but whatever.
    public void setResourceNeed(ResourceType r) throws GameActionException {
        for (int i = 0; i < 3; i++) {
            int val = (i==r.ordinal()) ? 1 : 0;
            rc.writeSharedArray(RESOURCE_NEED + i, val);
        }
    }

    public ResourceType readResourceNeed() throws GameActionException {
        for (int i = 0; i < 3; i++) {
            int val = rc.readSharedArray(RESOURCE_NEED + i);
            if (val == 1) return resources[i];
        }
        return null;
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
        //TODO: add them to our memory if we can't write yet
        if(!rc.canWriteSharedArray(0, 0)) return;
        int addInd = 0;
        for (WellInfo w : wells) {
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

    public void report() throws GameActionException{
        reportWells();
        reportCount();
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
            }
            if (rc.canWriteSharedArray(i, message)) {
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
        for (int i = ATTACK_TARGETS; i < ATTACK_TARGETS + ATTACK_TARGETS_WIDTH; i++) {
            int message = rc.readSharedArray(i);
            if (message == 0) continue;
            AttackTarget cur = new AttackTarget(rc.readSharedArray(i));
            if (cur.isBetterThan(best)) best = cur;
        }
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
            int y = (message>>4) & (0b111111);
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
}