package optimizedBot;
import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.Team;

public class NeighborTracker {
    RobotController rc;
    static final int FE_MASK_WIDTH = 11;
    static final int FE_MASK_HEIGHT = 5;
    long friend_mask[] = { 0, 0 };
    long enemy_mask[] = { 0, 0 };
    public boolean hasLaunchersNear = false;
    public boolean hasCarriersNear = false;

    public NeighborTracker(RobotController rc) {
        this.rc = rc;
    }

    void updateNeighbors() throws GameActionException {
        int initial = Clock.getBytecodesLeft();

        hasLaunchersNear = false;
        hasCarriersNear = false;
        int fe_mask_width = FE_MASK_WIDTH;
        int fe_mask_height = FE_MASK_HEIGHT;
        Team myTeam = rc.getTeam();

        // Location of the bottom left corner.
        MapLocation myloc = rc.getLocation();
        int blx = myloc.x - (fe_mask_width / 2);
        int bly = myloc.y - (fe_mask_height);

        int cc = 0;
        long tfriend_mask0 = 0;
        long tfriend_mask1 = 0;
        long tenemy_mask0 = 0;
        long tenemy_mask1 = 0;
        RobotInfo[] friends = rc.senseNearbyRobots(-1, myTeam);
        for (int j = friends.length; j-- > 0; ) {
            RobotInfo r = friends[j];
            switch (r.type) {
                case LAUNCHER:
                    switch (r.location.y - bly) {
                        case (0):
                            switch (r.location.x - blx) {
                                case (0): tfriend_mask0 |= 1L; continue;
                                case (1): tfriend_mask0 |= 2L; continue;
                                case (2): tfriend_mask0 |= 4L; continue;
                                case (3): tfriend_mask0 |= 8L; continue;
                                case (4): tfriend_mask0 |= 16L; continue;
                                case (5): tfriend_mask0 |= 32L; continue;
                                case (6): tfriend_mask0 |= 64L; continue;
                                case (7): tfriend_mask0 |= 128L; continue;
                                case (8): tfriend_mask0 |= 256L; continue;
                                case (9): tfriend_mask0 |= 512L; continue;
                                case (10): tfriend_mask0 |= 1024L; continue;
                                default: continue;
                            }
                        case (1):
                            switch (r.location.x - blx) {
                                case (0): tfriend_mask0 |= 2048L; continue;
                                case (1): tfriend_mask0 |= 4096L; continue;
                                case (2): tfriend_mask0 |= 8192L; continue;
                                case (3): tfriend_mask0 |= 16384L; continue;
                                case (4): tfriend_mask0 |= 32768L; continue;
                                case (5): tfriend_mask0 |= 65536L; continue;
                                case (6): tfriend_mask0 |= 131072L; continue;
                                case (7): tfriend_mask0 |= 262144L; continue;
                                case (8): tfriend_mask0 |= 524288L; continue;
                                case (9): tfriend_mask0 |= 1048576L; continue;
                                case (10): tfriend_mask0 |= 2097152L; continue;
                                default: continue;
                            }
                        case (2):
                            switch (r.location.x - blx) {
                                case (0): tfriend_mask0 |= 4194304L; continue;
                                case (1): tfriend_mask0 |= 8388608L; continue;
                                case (2): tfriend_mask0 |= 16777216L; continue;
                                case (3): tfriend_mask0 |= 33554432L; continue;
                                case (4): tfriend_mask0 |= 67108864L; continue;
                                case (5): tfriend_mask0 |= 134217728L; continue;
                                case (6): tfriend_mask0 |= 268435456L; continue;
                                case (7): tfriend_mask0 |= 536870912L; continue;
                                case (8): tfriend_mask0 |= 1073741824L; continue;
                                case (9): tfriend_mask0 |= 2147483648L; continue;
                                case (10): tfriend_mask0 |= 4294967296L; continue;
                                default: continue;
                            }
                        case (3):
                            switch (r.location.x - blx) {
                                case (0): tfriend_mask0 |= 8589934592L; continue;
                                case (1): tfriend_mask0 |= 17179869184L; continue;
                                case (2): tfriend_mask0 |= 34359738368L; continue;
                                case (3): tfriend_mask0 |= 68719476736L; continue;
                                case (4): tfriend_mask0 |= 137438953472L; continue;
                                case (5): tfriend_mask0 |= 274877906944L; continue;
                                case (6): tfriend_mask0 |= 549755813888L; continue;
                                case (7): tfriend_mask0 |= 1099511627776L; continue;
                                case (8): tfriend_mask0 |= 2199023255552L; continue;
                                case (9): tfriend_mask0 |= 4398046511104L; continue;
                                case (10): tfriend_mask0 |= 8796093022208L; continue;
                                default: continue;
                            }
                        case (4):
                            switch (r.location.x - blx) {
                                case (0): tfriend_mask0 |= 17592186044416L; continue;
                                case (1): tfriend_mask0 |= 35184372088832L; continue;
                                case (2): tfriend_mask0 |= 70368744177664L; continue;
                                case (3): tfriend_mask0 |= 140737488355328L; continue;
                                case (4): tfriend_mask0 |= 281474976710656L; continue;
                                case (5): tfriend_mask0 |= 562949953421312L; continue;
                                case (6): tfriend_mask0 |= 1125899906842624L; continue;
                                case (7): tfriend_mask0 |= 2251799813685248L; continue;
                                case (8): tfriend_mask0 |= 4503599627370496L; continue;
                                case (9): tfriend_mask0 |= 9007199254740992L; continue;
                                case (10): tfriend_mask0 |= 18014398509481984L; continue;
                                default: continue;
                            }
                        case (5):
                            switch (r.location.x - blx) {
                                case (0): tfriend_mask1 |= 1L; continue;
                                case (1): tfriend_mask1 |= 2L; continue;
                                case (2): tfriend_mask1 |= 4L; continue;
                                case (3): tfriend_mask1 |= 8L; continue;
                                case (4): tfriend_mask1 |= 16L; continue;
                                case (5): tfriend_mask1 |= 32L; continue;
                                case (6): tfriend_mask1 |= 64L; continue;
                                case (7): tfriend_mask1 |= 128L; continue;
                                case (8): tfriend_mask1 |= 256L; continue;
                                case (9): tfriend_mask1 |= 512L; continue;
                                case (10): tfriend_mask1 |= 1024L; continue;
                                default: continue;
                            }
                        case (6):
                            switch (r.location.x - blx) {
                                case (0): tfriend_mask1 |= 2048L; continue;
                                case (1): tfriend_mask1 |= 4096L; continue;
                                case (2): tfriend_mask1 |= 8192L; continue;
                                case (3): tfriend_mask1 |= 16384L; continue;
                                case (4): tfriend_mask1 |= 32768L; continue;
                                case (5): tfriend_mask1 |= 65536L; continue;
                                case (6): tfriend_mask1 |= 131072L; continue;
                                case (7): tfriend_mask1 |= 262144L; continue;
                                case (8): tfriend_mask1 |= 524288L; continue;
                                case (9): tfriend_mask1 |= 1048576L; continue;
                                case (10): tfriend_mask1 |= 2097152L; continue;
                                default: continue;
                            }
                        case (7):
                            switch (r.location.x - blx) {
                                case (0): tfriend_mask1 |= 4194304L; continue;
                                case (1): tfriend_mask1 |= 8388608L; continue;
                                case (2): tfriend_mask1 |= 16777216L; continue;
                                case (3): tfriend_mask1 |= 33554432L; continue;
                                case (4): tfriend_mask1 |= 67108864L; continue;
                                case (5): tfriend_mask1 |= 134217728L; continue;
                                case (6): tfriend_mask1 |= 268435456L; continue;
                                case (7): tfriend_mask1 |= 536870912L; continue;
                                case (8): tfriend_mask1 |= 1073741824L; continue;
                                case (9): tfriend_mask1 |= 2147483648L; continue;
                                case (10): tfriend_mask1 |= 4294967296L; continue;
                                default: continue;
                            }
                        case (8):
                            switch (r.location.x - blx) {
                                case (0): tfriend_mask1 |= 8589934592L; continue;
                                case (1): tfriend_mask1 |= 17179869184L; continue;
                                case (2): tfriend_mask1 |= 34359738368L; continue;
                                case (3): tfriend_mask1 |= 68719476736L; continue;
                                case (4): tfriend_mask1 |= 137438953472L; continue;
                                case (5): tfriend_mask1 |= 274877906944L; continue;
                                case (6): tfriend_mask1 |= 549755813888L; continue;
                                case (7): tfriend_mask1 |= 1099511627776L; continue;
                                case (8): tfriend_mask1 |= 2199023255552L; continue;
                                case (9): tfriend_mask1 |= 4398046511104L; continue;
                                case (10): tfriend_mask1 |= 8796093022208L; continue;
                                default: continue;
                            }
                        case (9):
                            switch (r.location.x - blx) {
                                case (0): tfriend_mask1 |= 17592186044416L; continue;
                                case (1): tfriend_mask1 |= 35184372088832L; continue;
                                case (2): tfriend_mask1 |= 70368744177664L; continue;
                                case (3): tfriend_mask1 |= 140737488355328L; continue;
                                case (4): tfriend_mask1 |= 281474976710656L; continue;
                                case (5): tfriend_mask1 |= 562949953421312L; continue;
                                case (6): tfriend_mask1 |= 1125899906842624L; continue;
                                case (7): tfriend_mask1 |= 2251799813685248L; continue;
                                case (8): tfriend_mask1 |= 4503599627370496L; continue;
                                case (9): tfriend_mask1 |= 9007199254740992L; continue;
                                case (10): tfriend_mask1 |= 18014398509481984L; continue;
                                default: continue;
                            }
                    default: 
                }
                default: continue;
            }
        }
        

        RobotInfo[] enemies = rc.senseNearbyRobots(-1, myTeam.opponent());
        for (int j = enemies.length; j-- > 0; ) {
            RobotInfo r = enemies[j];
            switch (r.type) {
                case LAUNCHER:
                    switch (r.location.y - bly) {
                    case (0):
                        switch (r.location.x - blx) {
                            case (0): tenemy_mask0 |= 1L; continue;
                            case (1): tenemy_mask0 |= 2L; continue;
                            case (2): tenemy_mask0 |= 4L; continue;
                            case (3): tenemy_mask0 |= 8L; continue;
                            case (4): tenemy_mask0 |= 16L; continue;
                            case (5): tenemy_mask0 |= 32L; continue;
                            case (6): tenemy_mask0 |= 64L; continue;
                            case (7): tenemy_mask0 |= 128L; continue;
                            case (8): tenemy_mask0 |= 256L; continue;
                            case (9): tenemy_mask0 |= 512L; continue;
                            case (10): tenemy_mask0 |= 1024L; continue;
                            default: continue;
                        }
                    case (1):
                        switch (r.location.x - blx) {
                            case (0): tenemy_mask0 |= 2048L; continue;
                            case (1): tenemy_mask0 |= 4096L; continue;
                            case (2): tenemy_mask0 |= 8192L; continue;
                            case (3): tenemy_mask0 |= 16384L; continue;
                            case (4): tenemy_mask0 |= 32768L; continue;
                            case (5): tenemy_mask0 |= 65536L; continue;
                            case (6): tenemy_mask0 |= 131072L; continue;
                            case (7): tenemy_mask0 |= 262144L; continue;
                            case (8): tenemy_mask0 |= 524288L; continue;
                            case (9): tenemy_mask0 |= 1048576L; continue;
                            case (10): tenemy_mask0 |= 2097152L; continue;
                            default: continue;
                        }
                    case (2):
                        switch (r.location.x - blx) {
                            case (0): tenemy_mask0 |= 4194304L; continue;
                            case (1): tenemy_mask0 |= 8388608L; continue;
                            case (2): tenemy_mask0 |= 16777216L; continue;
                            case (3): tenemy_mask0 |= 33554432L; continue;
                            case (4): tenemy_mask0 |= 67108864L; continue;
                            case (5): tenemy_mask0 |= 134217728L; continue;
                            case (6): tenemy_mask0 |= 268435456L; continue;
                            case (7): tenemy_mask0 |= 536870912L; continue;
                            case (8): tenemy_mask0 |= 1073741824L; continue;
                            case (9): tenemy_mask0 |= 2147483648L; continue;
                            case (10): tenemy_mask0 |= 4294967296L; continue;
                            default: continue;
                        }
                    case (3):
                        switch (r.location.x - blx) {
                            case (0): tenemy_mask0 |= 8589934592L; continue;
                            case (1): tenemy_mask0 |= 17179869184L; continue;
                            case (2): tenemy_mask0 |= 34359738368L; continue;
                            case (3): tenemy_mask0 |= 68719476736L; continue;
                            case (4): tenemy_mask0 |= 137438953472L; continue;
                            case (5): tenemy_mask0 |= 274877906944L; continue;
                            case (6): tenemy_mask0 |= 549755813888L; continue;
                            case (7): tenemy_mask0 |= 1099511627776L; continue;
                            case (8): tenemy_mask0 |= 2199023255552L; continue;
                            case (9): tenemy_mask0 |= 4398046511104L; continue;
                            case (10): tenemy_mask0 |= 8796093022208L; continue;
                            default: continue;
                        }
                    case (4):
                        switch (r.location.x - blx) {
                            case (0): tenemy_mask0 |= 17592186044416L; continue;
                            case (1): tenemy_mask0 |= 35184372088832L; continue;
                            case (2): tenemy_mask0 |= 70368744177664L; continue;
                            case (3): tenemy_mask0 |= 140737488355328L; continue;
                            case (4): tenemy_mask0 |= 281474976710656L; continue;
                            case (5): tenemy_mask0 |= 562949953421312L; continue;
                            case (6): tenemy_mask0 |= 1125899906842624L; continue;
                            case (7): tenemy_mask0 |= 2251799813685248L; continue;
                            case (8): tenemy_mask0 |= 4503599627370496L; continue;
                            case (9): tenemy_mask0 |= 9007199254740992L; continue;
                            case (10): tenemy_mask0 |= 18014398509481984L; continue;
                            default: continue;
                        }
                    case (5):
                        switch (r.location.x - blx) {
                            case (0): tenemy_mask1 |= 1L; continue;
                            case (1): tenemy_mask1 |= 2L; continue;
                            case (2): tenemy_mask1 |= 4L; continue;
                            case (3): tenemy_mask1 |= 8L; continue;
                            case (4): tenemy_mask1 |= 16L; continue;
                            case (5): tenemy_mask1 |= 32L; continue;
                            case (6): tenemy_mask1 |= 64L; continue;
                            case (7): tenemy_mask1 |= 128L; continue;
                            case (8): tenemy_mask1 |= 256L; continue;
                            case (9): tenemy_mask1 |= 512L; continue;
                            case (10): tenemy_mask1 |= 1024L; continue;
                            default: continue;
                        }
                    case (6):
                        switch (r.location.x - blx) {
                            case (0): tenemy_mask1 |= 2048L; continue;
                            case (1): tenemy_mask1 |= 4096L; continue;
                            case (2): tenemy_mask1 |= 8192L; continue;
                            case (3): tenemy_mask1 |= 16384L; continue;
                            case (4): tenemy_mask1 |= 32768L; continue;
                            case (5): tenemy_mask1 |= 65536L; continue;
                            case (6): tenemy_mask1 |= 131072L; continue;
                            case (7): tenemy_mask1 |= 262144L; continue;
                            case (8): tenemy_mask1 |= 524288L; continue;
                            case (9): tenemy_mask1 |= 1048576L; continue;
                            case (10): tenemy_mask1 |= 2097152L; continue;
                            default: continue;
                        }
                    case (7):
                        switch (r.location.x - blx) {
                            case (0): tenemy_mask1 |= 4194304L; continue;
                            case (1): tenemy_mask1 |= 8388608L; continue;
                            case (2): tenemy_mask1 |= 16777216L; continue;
                            case (3): tenemy_mask1 |= 33554432L; continue;
                            case (4): tenemy_mask1 |= 67108864L; continue;
                            case (5): tenemy_mask1 |= 134217728L; continue;
                            case (6): tenemy_mask1 |= 268435456L; continue;
                            case (7): tenemy_mask1 |= 536870912L; continue;
                            case (8): tenemy_mask1 |= 1073741824L; continue;
                            case (9): tenemy_mask1 |= 2147483648L; continue;
                            case (10): tenemy_mask1 |= 4294967296L; continue;
                            default: continue;
                        }
                    case (8):
                        switch (r.location.x - blx) {
                            case (0): tenemy_mask1 |= 8589934592L; continue;
                            case (1): tenemy_mask1 |= 17179869184L; continue;
                            case (2): tenemy_mask1 |= 34359738368L; continue;
                            case (3): tenemy_mask1 |= 68719476736L; continue;
                            case (4): tenemy_mask1 |= 137438953472L; continue;
                            case (5): tenemy_mask1 |= 274877906944L; continue;
                            case (6): tenemy_mask1 |= 549755813888L; continue;
                            case (7): tenemy_mask1 |= 1099511627776L; continue;
                            case (8): tenemy_mask1 |= 2199023255552L; continue;
                            case (9): tenemy_mask1 |= 4398046511104L; continue;
                            case (10): tenemy_mask1 |= 8796093022208L; continue;
                            default: continue;
                        }
                    case (9):
                        switch (r.location.x - blx) {
                            case (0): tenemy_mask1 |= 17592186044416L; continue;
                            case (1): tenemy_mask1 |= 35184372088832L; continue;
                            case (2): tenemy_mask1 |= 70368744177664L; continue;
                            case (3): tenemy_mask1 |= 140737488355328L; continue;
                            case (4): tenemy_mask1 |= 281474976710656L; continue;
                            case (5): tenemy_mask1 |= 562949953421312L; continue;
                            case (6): tenemy_mask1 |= 1125899906842624L; continue;
                            case (7): tenemy_mask1 |= 2251799813685248L; continue;
                            case (8): tenemy_mask1 |= 4503599627370496L; continue;
                            case (9): tenemy_mask1 |= 9007199254740992L; continue;
                            case (10): tenemy_mask1 |= 18014398509481984L; continue;
                            default: continue;
                        }
                    default: 
                }
                case CARRIER: cc++;
                default: continue;
            }
        }

        if ((tenemy_mask0 | tenemy_mask1) > 0) hasLaunchersNear = true;
        if (cc > 0) hasCarriersNear = true;
        friend_mask[0] = tfriend_mask0;
        friend_mask[1] = tfriend_mask1;
        enemy_mask[0]  = tenemy_mask0;
        enemy_mask[1]  = tenemy_mask1;

        int end = Clock.getBytecodesLeft();
        System.out.println("Tracker Used: " + (initial - end));
    }

    /*
     * Return the direction which minimizes the distance to attacked soldiers.
     * Ignores walls and cooldown for now.
     * We also ignore wraparound issues, but this may be ok, since we don't account for walls.
     * I.e the wraparound squares will never be closer then the squares that made them.
     * 
     * Also this isn't particularly optimized. The most obvious improvement - 
     * Change all statics to locals to avoid the extra loads.
     * Change the arrays to seperate variables so we don't have to load the reference every time.
     */

    Direction advance() throws GameActionException {
        long mask0 = friend_mask[0];
        long mask1 = friend_mask[1];
        int fe_mask_width = FE_MASK_WIDTH;
        int fe_mask_height = FE_MASK_HEIGHT;
        int shift = (fe_mask_height - 1) * (fe_mask_width);
        for (int i = 5; i-- > 0;) {
            mask0 |= (mask0 << 1);
            mask1 |= (mask1 << 1);

            mask0 |= (mask0 >> 1);
            mask1 |= (mask1 >> 1);

            mask1 |= (mask1 << fe_mask_width);
            mask1 |= (mask0 >> shift);
            mask0 |= (mask0 << fe_mask_width);

            mask0 |= (mask0 >> fe_mask_width);
            mask0 |= (mask1 << shift);
            mask1 |= (mask1 >> fe_mask_width);

            switch ((int)(mask1 & 0b0000111000000001110000)) {
                case (0b0000000000000000000000): 
                    break;
                case (0b0000000000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    break;
                case (0b0000000000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    break;
                case (0b0000000000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    break;
                case (0b0000000000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    break;
                case (0b0000000000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    break;
                case (0b0000000000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    break;
                case (0b0000000000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    break;
                case (0b0000001000000000000000): 
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000001000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    break;
                case (0b0000010000000000000000): 
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000010000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000000000000): 
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000011000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    break;
                case (0b0000100000000000000000): 
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000100000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000000000000): 
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000101000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000000000000): 
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000110000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000000000000): 
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000000010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000000100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000000110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000001000000): 
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000001010000): 
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000001100000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                case (0b0000111000000001110000): 
                    if (rc.canMove(Direction.CENTER)) return Direction.CENTER;
                    if (rc.canMove(Direction.WEST)) return Direction.WEST;
                    if (rc.canMove(Direction.EAST)) return Direction.EAST;
                    if (rc.canMove(Direction.NORTHWEST)) return Direction.NORTHWEST;
                    if (rc.canMove(Direction.NORTH)) return Direction.NORTH;
                    if (rc.canMove(Direction.NORTHEAST)) return Direction.NORTHEAST;
                    break;
                default: 
            }
            switch ((int)(mask0 >> (shift) & 0b00001110000)) {
                case (0b00000000000):
                    break;
                case (0b00000010000):
                    if (rc.canMove(Direction.SOUTHWEST)) return Direction.SOUTHWEST;
                    break;
                case (0b00000100000):
                    if (rc.canMove(Direction.SOUTH)) return Direction.SOUTH;
                    break;
                case (0b00000110000):
                    if (rc.canMove(Direction.SOUTHWEST)) return Direction.SOUTHWEST;
                    if (rc.canMove(Direction.SOUTH)) return Direction.SOUTH;
                    break;
                case (0b00001000000):
                    if (rc.canMove(Direction.SOUTHEAST)) return Direction.SOUTHEAST;
                    break;
                case (0b00001010000):
                    if (rc.canMove(Direction.SOUTHWEST)) return Direction.SOUTHWEST;
                    if (rc.canMove(Direction.SOUTHEAST)) return Direction.SOUTHEAST;
                    break;
                case (0b00001100000):
                    if (rc.canMove(Direction.SOUTH)) return Direction.SOUTH;
                    if (rc.canMove(Direction.SOUTHEAST)) return Direction.SOUTHEAST;
                    break;
                case (0b00001110000):
                    if (rc.canMove(Direction.SOUTHWEST)) return Direction.SOUTHWEST;
                    if (rc.canMove(Direction.SOUTH)) return Direction.SOUTH;
                    if (rc.canMove(Direction.SOUTHEAST)) return Direction.SOUTHEAST;
                    break;
                default: 
            }
        }
        return null;
    }

    void displayMap() throws GameActionException {
        long[] mask = friend_mask;
        MapLocation myloc = rc.getLocation();
        for (int i = 0; i < FE_MASK_HEIGHT; i++) {
            for (int j = 0; j < FE_MASK_WIDTH; j++) {
                if (Clock.getBytecodesLeft() < 1000) break;
                int on = (int) ((mask[0] >> (i * FE_MASK_WIDTH + j)) & 1);
                int x = j + (myloc.x - FE_MASK_WIDTH / 2);
                int y = i + (myloc.y - FE_MASK_HEIGHT);
                MapLocation spot = new MapLocation(x, y);
                rc.setIndicatorDot(spot, on * 255, 0, 0);
            }
        }
        for (int i = 0; i < FE_MASK_HEIGHT; i++) {
            for (int j = 0; j < FE_MASK_WIDTH; j++) {
                if (Clock.getBytecodesLeft() < 1000) break;
                int on = (int) ((mask[1] >> (i * FE_MASK_WIDTH + j)) & 1);
                int x = j + (myloc.x - FE_MASK_WIDTH / 2);
                int y = i + (myloc.y);
                MapLocation spot = new MapLocation(x, y);
                rc.setIndicatorDot(spot, on * 255, 0, 0);
            }
        }
        rc.setIndicatorString("Map: " + Long.toBinaryString(mask[0]));
    }
}
