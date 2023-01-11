package carrier_micro;
import battlecode.common.*;

public class Communications {
    RobotController rc;
    MapLocation[] HQs = new MapLocation[5];
    int numHQ = 0;
    //constants
    static final int SHARED_ARRAY_SIZE = 64;
    static final int MAX_WELLS_FOR_TYPE = 10;
    //message types
    static final int HQ_LOCATION = 1;
    static final int MANA_WELL = 2;
    static final int ADAMANTIUM_WELL = 3;
    static final int ELIXIR_WELL = 4;

    public Communications(RobotController rc) {
        this.rc = rc;
    }
    public boolean writeTypeLoc(int type, MapLocation loc) throws GameActionException{
        int xLoc = loc.x, yLoc = loc.y;
        for(int i = 0; i < SHARED_ARRAY_SIZE; i++) if(rc.readSharedArray(i) == 0){
            int msg = type + (1 << 3) * (rc.getLocation().x) + (1 << 9) * (rc.getLocation().y);
            if(rc.canWriteSharedArray(i, msg)){
                //System.out.println("Sent location.");
                rc.writeSharedArray(i, msg);
                return true;
            }
            else{
                //System.out.println("Can't send location.");
            }
            break;
        }
        return false;
    }

    public MapLocation findBestWell() throws GameActionException{
        MapLocation bestWell = null;
        int dist = 1000000;
        //TODO: later on, go to elixir too (we don't seem to need it now)
        for(int i = 0; i < SHARED_ARRAY_SIZE; i++) if(rc.readSharedArray(i) != 0){
            int val = rc.readSharedArray(i);
            int typ = val & (0b111);
            if(typ == MANA_WELL || typ == ADAMANTIUM_WELL){
                MapLocation w = new MapLocation((val >> 3) & (0b111111), (val >> 9) & (0b111111));
                if(rc.getLocation().distanceSquaredTo(w) < dist){
                    dist = rc.getLocation().distanceSquaredTo(w);
                    bestWell = w;
                }
            }
        }
        if(bestWell != null) {
            System.out.println("Read about well on: " + bestWell);
        }
        return bestWell;
    }

    public void reportWells() throws GameActionException{
        WellInfo[] wells = rc.senseNearbyWells();
        if(wells.length == 0) return;
        int ind = 0;
        int[] freeSlots = new int[wells.length];
        int cntMana = 0, cntAda = 0, cntElixir = 0;
        for(int i = 0; i < SHARED_ARRAY_SIZE; i++){
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
                   for(int i = 0; i < SHARED_ARRAY_SIZE; i++){
                       if(rc.readSharedArray(i) == msg){
                           not_marked = 0;
                           break;
                       }
                   }
                   if(not_marked > 0) {
                       System.out.println("Telling about mana well on: " + w.getMapLocation());
                       rc.writeSharedArray(freeSlots[addInd++], msg);
                       cntMana++;
                   }
               }
           }
           if(w.getResourceType().equals(ResourceType.ADAMANTIUM)) {
                if (cntAda < MAX_WELLS_FOR_TYPE) {
                    int msg = ADAMANTIUM_WELL + (1 << 3) * (w.getMapLocation().x) + (1 << 9) * (w.getMapLocation().y);
                    int not_marked = 1;
                    for(int i = 0; i < SHARED_ARRAY_SIZE; i++){
                        if(rc.readSharedArray(i) == msg){
                            not_marked = 0;
                            break;
                        }
                    }
                    if(not_marked > 0){
                        System.out.println("Telling about ada well on: " + w.getMapLocation());
                        rc.writeSharedArray(freeSlots[addInd++], msg);
                        cntAda++;
                    }
                }
           }
           if(w.getResourceType().equals(ResourceType.ELIXIR)) {
                if (cntElixir < MAX_WELLS_FOR_TYPE) {
                    int msg = ELIXIR_WELL + (1 << 3) * (w.getMapLocation().x) + (1 << 9) * (w.getMapLocation().y);
                    int not_marked = 1;
                    for(int i = 0; i < SHARED_ARRAY_SIZE; i++){
                        if(rc.readSharedArray(i) == msg){
                            not_marked = 0;
                            break;
                        }
                    }
                    if(not_marked > 0) {
                        System.out.println("Telling about elixir well on: " + w.getMapLocation());
                        rc.writeSharedArray(freeSlots[addInd++], msg);
                        cntElixir++;
                    }
                }
            }
       }
    }

    public void report() throws GameActionException{
        reportWells();
        //TODO: add reporting for other stuff - enemy HQs, enemy units, islands, etc.
    }

    public void findOurHQs() throws GameActionException{
        for(int i = 0; i < SHARED_ARRAY_SIZE; i++) if((rc.readSharedArray(i) & (0b111)) == HQ_LOCATION){
            int val = rc.readSharedArray(i);
            MapLocation hq = new MapLocation((val >> 3) & (0b111111), (val >> 9) & (0b111111));
            HQs[numHQ++] = hq;
        }
    }
}