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
    MapLocation prevLocation;
    static final int FE_MASK_WIDTH = 11;
    static final int FE_MASK_HEIGHT = 5;
    static final int HEALTH_LEN = 31;
    long friend_mask[] = { 0, 0 };
    long att_friend_mask[] = { 0, 0 };
    long enemy_mask[] = { 0, 0 };
    long fupdates[][] = { {0, 0}, {0, 0}, {0, 0} };
    long eupdates[][] = { {0, 0}, {0, 0}, {0, 0} };
    long friend_healths[] = {
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0
    };
    public boolean hasLaunchersNear = false;
    public boolean hasCarriersNear = false;

    public NeighborTracker(RobotController rc) {
        this.rc = rc;
        this.prevLocation = null;
    }

    void updateNeighbors() throws GameActionException {
        int initial = Clock.getBytecodesLeft();
        int fe_mask_width = FE_MASK_WIDTH;
        int fe_mask_height = FE_MASK_HEIGHT;
        int health_len = HEALTH_LEN;

        hasLaunchersNear = false;
        hasCarriersNear = false;
        Team myTeam = rc.getTeam();
        int new_healths[] = new int[health_len];

        // Location of the bottom left corner.
        MapLocation myloc = rc.getLocation();
        int blx = myloc.x - (fe_mask_width / 2);
        int bly = myloc.y - (fe_mask_height);

        // Correct the masks according to our movement.
        // This is only really useful if we want to mantain
        // Items that are outside of our vision range.
        // This is cheaper then it looks.
        if (prevLocation != null) {
            int dx = myloc.x - prevLocation.x;
            int dy = myloc.y - prevLocation.y;
            if (dx < 0) {
                int neg = -dx;
                friend_mask[0] <<= neg;
                friend_mask[1] <<= neg;
                enemy_mask[0] <<= neg;
                enemy_mask[1] <<= neg;
            } else if (dx > 0) {
                friend_mask[0] >>= dx;
                friend_mask[1] >>= dx;
                enemy_mask[0] >>= dx;
                enemy_mask[1] >>= dx;
            }
            int shift = (fe_mask_height - 1) * (fe_mask_width);
            while (dy < 0) {
                int neg = (-dy * fe_mask_width);
                friend_mask[1] <<= neg;
                friend_mask[1] |= (friend_mask[0] >> (shift));
                friend_mask[0] <<= neg;
                enemy_mask[1] <<= neg;
                enemy_mask[1] |= (enemy_mask[0] >> (shift));
                enemy_mask[0] <<= neg;
                dy++;
            }
            while (dy > 0) {
                int mul = (dy * fe_mask_width);
                friend_mask[0] >>= mul;
                friend_mask[0] |= (friend_mask[1] & 0b11111111111) << shift;
                friend_mask[1] >>= mul;
                enemy_mask[0] >>= mul;
                enemy_mask[0] |= (enemy_mask[1] & 0b11111111111) << shift;
                enemy_mask[1] >>= mul;
                dy--;
            }
        }

        long att_friend_mask0 = 0;
        long att_friend_mask1 = 0;
        long tfriend_mask0 = 0;
        long tfriend_mask1 = 0;
        long tenemy_mask0 = 0;
        long tenemy_mask1 = 0;
        RobotInfo[] friends = rc.senseNearbyRobots(-1, myTeam);
        for (int j = friends.length; j-- > 0; ) {
            RobotInfo r = friends[j];
            switch (r.type) {
                case LAUNCHER: break;
                default: continue;
            }

            int idx = r.ID % HEALTH_LEN;
            long phealth = friend_healths[idx];
            new_healths[idx] = r.health;
            outer: switch (r.location.y - bly) {
                case (0):
                    switch (r.location.x - blx) {
                        case (0):
                            tfriend_mask0 |= 1L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 1L;
                            }
                            break outer;
                        case (1):
                            tfriend_mask0 |= 2L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 2L;
                            }
                            break outer;
                        case (2):
                            tfriend_mask0 |= 4L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 4L;
                            }
                            break outer;
                        case (3):
                            tfriend_mask0 |= 8L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 8L;
                            }
                            break outer;
                        case (4):
                            tfriend_mask0 |= 16L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 16L;
                            }
                            break outer;
                        case (5):
                            tfriend_mask0 |= 32L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 32L;
                            }
                            break outer;
                        case (6):
                            tfriend_mask0 |= 64L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 64L;
                            }
                            break outer;
                        case (7):
                            tfriend_mask0 |= 128L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 128L;
                            }
                            break outer;
                        case (8):
                            tfriend_mask0 |= 256L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 256L;
                            }
                            break outer;
                        case (9):
                            tfriend_mask0 |= 512L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 512L;
                            }
                            break outer;
                        case (10):
                            tfriend_mask0 |= 1024L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 1024L;
                            }
                            break outer;
                        default: break outer;
                    }
                case (1):
                    switch (r.location.x - blx) {
                        case (0):
                            tfriend_mask0 |= 2048L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 2048L;
                            }
                            break outer;
                        case (1):
                            tfriend_mask0 |= 4096L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 4096L;
                            }
                            break outer;
                        case (2):
                            tfriend_mask0 |= 8192L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 8192L;
                            }
                            break outer;
                        case (3):
                            tfriend_mask0 |= 16384L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 16384L;
                            }
                            break outer;
                        case (4):
                            tfriend_mask0 |= 32768L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 32768L;
                            }
                            break outer;
                        case (5):
                            tfriend_mask0 |= 65536L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 65536L;
                            }
                            break outer;
                        case (6):
                            tfriend_mask0 |= 131072L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 131072L;
                            }
                            break outer;
                        case (7):
                            tfriend_mask0 |= 262144L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 262144L;
                            }
                            break outer;
                        case (8):
                            tfriend_mask0 |= 524288L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 524288L;
                            }
                            break outer;
                        case (9):
                            tfriend_mask0 |= 1048576L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 1048576L;
                            }
                            break outer;
                        case (10):
                            tfriend_mask0 |= 2097152L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 2097152L;
                            }
                            break outer;
                        default: break outer;
                    }
                case (2):
                    switch (r.location.x - blx) {
                        case (0):
                            tfriend_mask0 |= 4194304L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 4194304L;
                            }
                            break outer;
                        case (1):
                            tfriend_mask0 |= 8388608L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 8388608L;
                            }
                            break outer;
                        case (2):
                            tfriend_mask0 |= 16777216L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 16777216L;
                            }
                            break outer;
                        case (3):
                            tfriend_mask0 |= 33554432L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 33554432L;
                            }
                            break outer;
                        case (4):
                            tfriend_mask0 |= 67108864L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 67108864L;
                            }
                            break outer;
                        case (5):
                            tfriend_mask0 |= 134217728L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 134217728L;
                            }
                            break outer;
                        case (6):
                            tfriend_mask0 |= 268435456L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 268435456L;
                            }
                            break outer;
                        case (7):
                            tfriend_mask0 |= 536870912L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 536870912L;
                            }
                            break outer;
                        case (8):
                            tfriend_mask0 |= 1073741824L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 1073741824L;
                            }
                            break outer;
                        case (9):
                            tfriend_mask0 |= 2147483648L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 2147483648L;
                            }
                            break outer;
                        case (10):
                            tfriend_mask0 |= 4294967296L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 4294967296L;
                            }
                            break outer;
                        default: break outer;
                    }
                case (3):
                    switch (r.location.x - blx) {
                        case (0):
                            tfriend_mask0 |= 8589934592L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 8589934592L;
                            }
                            break outer;
                        case (1):
                            tfriend_mask0 |= 17179869184L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 17179869184L;
                            }
                            break outer;
                        case (2):
                            tfriend_mask0 |= 34359738368L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 34359738368L;
                            }
                            break outer;
                        case (3):
                            tfriend_mask0 |= 68719476736L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 68719476736L;
                            }
                            break outer;
                        case (4):
                            tfriend_mask0 |= 137438953472L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 137438953472L;
                            }
                            break outer;
                        case (5):
                            tfriend_mask0 |= 274877906944L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 274877906944L;
                            }
                            break outer;
                        case (6):
                            tfriend_mask0 |= 549755813888L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 549755813888L;
                            }
                            break outer;
                        case (7):
                            tfriend_mask0 |= 1099511627776L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 1099511627776L;
                            }
                            break outer;
                        case (8):
                            tfriend_mask0 |= 2199023255552L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 2199023255552L;
                            }
                            break outer;
                        case (9):
                            tfriend_mask0 |= 4398046511104L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 4398046511104L;
                            }
                            break outer;
                        case (10):
                            tfriend_mask0 |= 8796093022208L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 8796093022208L;
                            }
                            break outer;
                        default: break outer;
                    }
                case (4):
                    switch (r.location.x - blx) {
                        case (0):
                            tfriend_mask0 |= 17592186044416L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 17592186044416L;
                            }
                            break outer;
                        case (1):
                            tfriend_mask0 |= 35184372088832L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 35184372088832L;
                            }
                            break outer;
                        case (2):
                            tfriend_mask0 |= 70368744177664L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 70368744177664L;
                            }
                            break outer;
                        case (3):
                            tfriend_mask0 |= 140737488355328L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 140737488355328L;
                            }
                            break outer;
                        case (4):
                            tfriend_mask0 |= 281474976710656L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 281474976710656L;
                            }
                            break outer;
                        case (5):
                            tfriend_mask0 |= 562949953421312L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 562949953421312L;
                            }
                            break outer;
                        case (6):
                            tfriend_mask0 |= 1125899906842624L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 1125899906842624L;
                            }
                            break outer;
                        case (7):
                            tfriend_mask0 |= 2251799813685248L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 2251799813685248L;
                            }
                            break outer;
                        case (8):
                            tfriend_mask0 |= 4503599627370496L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 4503599627370496L;
                            }
                            break outer;
                        case (9):
                            tfriend_mask0 |= 9007199254740992L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 9007199254740992L;
                            }
                            break outer;
                        case (10):
                            tfriend_mask0 |= 18014398509481984L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask0 |= 18014398509481984L;
                            }
                            break outer;
                        default: break outer;
                    }
                case (5):
                    switch (r.location.x - blx) {
                        case (0):
                            tfriend_mask1 |= 1L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 1L;
                            }
                            break outer;
                        case (1):
                            tfriend_mask1 |= 2L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 2L;
                            }
                            break outer;
                        case (2):
                            tfriend_mask1 |= 4L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 4L;
                            }
                            break outer;
                        case (3):
                            tfriend_mask1 |= 8L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 8L;
                            }
                            break outer;
                        case (4):
                            tfriend_mask1 |= 16L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 16L;
                            }
                            break outer;
                        case (5):
                            tfriend_mask1 |= 32L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 32L;
                            }
                            break outer;
                        case (6):
                            tfriend_mask1 |= 64L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 64L;
                            }
                            break outer;
                        case (7):
                            tfriend_mask1 |= 128L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 128L;
                            }
                            break outer;
                        case (8):
                            tfriend_mask1 |= 256L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 256L;
                            }
                            break outer;
                        case (9):
                            tfriend_mask1 |= 512L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 512L;
                            }
                            break outer;
                        case (10):
                            tfriend_mask1 |= 1024L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 1024L;
                            }
                            break outer;
                        default: break outer;
                    }
                case (6):
                    switch (r.location.x - blx) {
                        case (0):
                            tfriend_mask1 |= 2048L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 2048L;
                            }
                            break outer;
                        case (1):
                            tfriend_mask1 |= 4096L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 4096L;
                            }
                            break outer;
                        case (2):
                            tfriend_mask1 |= 8192L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 8192L;
                            }
                            break outer;
                        case (3):
                            tfriend_mask1 |= 16384L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 16384L;
                            }
                            break outer;
                        case (4):
                            tfriend_mask1 |= 32768L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 32768L;
                            }
                            break outer;
                        case (5):
                            tfriend_mask1 |= 65536L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 65536L;
                            }
                            break outer;
                        case (6):
                            tfriend_mask1 |= 131072L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 131072L;
                            }
                            break outer;
                        case (7):
                            tfriend_mask1 |= 262144L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 262144L;
                            }
                            break outer;
                        case (8):
                            tfriend_mask1 |= 524288L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 524288L;
                            }
                            break outer;
                        case (9):
                            tfriend_mask1 |= 1048576L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 1048576L;
                            }
                            break outer;
                        case (10):
                            tfriend_mask1 |= 2097152L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 2097152L;
                            }
                            break outer;
                        default: break outer;
                    }
                case (7):
                    switch (r.location.x - blx) {
                        case (0):
                            tfriend_mask1 |= 4194304L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 4194304L;
                            }
                            break outer;
                        case (1):
                            tfriend_mask1 |= 8388608L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 8388608L;
                            }
                            break outer;
                        case (2):
                            tfriend_mask1 |= 16777216L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 16777216L;
                            }
                            break outer;
                        case (3):
                            tfriend_mask1 |= 33554432L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 33554432L;
                            }
                            break outer;
                        case (4):
                            tfriend_mask1 |= 67108864L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 67108864L;
                            }
                            break outer;
                        case (5):
                            tfriend_mask1 |= 134217728L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 134217728L;
                            }
                            break outer;
                        case (6):
                            tfriend_mask1 |= 268435456L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 268435456L;
                            }
                            break outer;
                        case (7):
                            tfriend_mask1 |= 536870912L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 536870912L;
                            }
                            break outer;
                        case (8):
                            tfriend_mask1 |= 1073741824L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 1073741824L;
                            }
                            break outer;
                        case (9):
                            tfriend_mask1 |= 2147483648L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 2147483648L;
                            }
                            break outer;
                        case (10):
                            tfriend_mask1 |= 4294967296L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 4294967296L;
                            }
                            break outer;
                        default: break outer;
                    }
                case (8):
                    switch (r.location.x - blx) {
                        case (0):
                            tfriend_mask1 |= 8589934592L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 8589934592L;
                            }
                            break outer;
                        case (1):
                            tfriend_mask1 |= 17179869184L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 17179869184L;
                            }
                            break outer;
                        case (2):
                            tfriend_mask1 |= 34359738368L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 34359738368L;
                            }
                            break outer;
                        case (3):
                            tfriend_mask1 |= 68719476736L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 68719476736L;
                            }
                            break outer;
                        case (4):
                            tfriend_mask1 |= 137438953472L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 137438953472L;
                            }
                            break outer;
                        case (5):
                            tfriend_mask1 |= 274877906944L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 274877906944L;
                            }
                            break outer;
                        case (6):
                            tfriend_mask1 |= 549755813888L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 549755813888L;
                            }
                            break outer;
                        case (7):
                            tfriend_mask1 |= 1099511627776L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 1099511627776L;
                            }
                            break outer;
                        case (8):
                            tfriend_mask1 |= 2199023255552L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 2199023255552L;
                            }
                            break outer;
                        case (9):
                            tfriend_mask1 |= 4398046511104L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 4398046511104L;
                            }
                            break outer;
                        case (10):
                            tfriend_mask1 |= 8796093022208L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 8796093022208L;
                            }
                            break outer;
                        default: break outer;
                    }
                case (9):
                    switch (r.location.x - blx) {
                        case (0):
                            tfriend_mask1 |= 17592186044416L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 17592186044416L;
                            }
                            break outer;
                        case (1):
                            tfriend_mask1 |= 35184372088832L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 35184372088832L;
                            }
                            break outer;
                        case (2):
                            tfriend_mask1 |= 70368744177664L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 70368744177664L;
                            }
                            break outer;
                        case (3):
                            tfriend_mask1 |= 140737488355328L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 140737488355328L;
                            }
                            break outer;
                        case (4):
                            tfriend_mask1 |= 281474976710656L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 281474976710656L;
                            }
                            break outer;
                        case (5):
                            tfriend_mask1 |= 562949953421312L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 562949953421312L;
                            }
                            break outer;
                        case (6):
                            tfriend_mask1 |= 1125899906842624L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 1125899906842624L;
                            }
                            break outer;
                        case (7):
                            tfriend_mask1 |= 2251799813685248L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 2251799813685248L;
                            }
                            break outer;
                        case (8):
                            tfriend_mask1 |= 4503599627370496L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 4503599627370496L;
                            }
                            break outer;
                        case (9):
                            tfriend_mask1 |= 9007199254740992L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 9007199254740992L;
                            }
                            break outer;
                        case (10):
                            tfriend_mask1 |= 18014398509481984L;
                            if (r.health + 10 < phealth) {
                                att_friend_mask1 |= 18014398509481984L;
                            }
                            break outer;
                        default: break outer;
                    }
                default: 
            }
        }
        

        RobotInfo[] enemies = rc.senseNearbyRobots(-1, myTeam.opponent());
        for (int j = enemies.length; j-- > 0; ) {
            RobotInfo r = enemies[j];
            switch (r.type) {
                case LAUNCHER: break;
                case CARRIER: hasCarriersNear = true;
                default: continue;
            }
            outer: switch (r.location.y - bly) {
                case (0):
                    switch (r.location.x - blx) {
                        case (0): tenemy_mask0 |= 1L; break outer;
                        case (1): tenemy_mask0 |= 2L; break outer;
                        case (2): tenemy_mask0 |= 4L; break outer;
                        case (3): tenemy_mask0 |= 8L; break outer;
                        case (4): tenemy_mask0 |= 16L; break outer;
                        case (5): tenemy_mask0 |= 32L; break outer;
                        case (6): tenemy_mask0 |= 64L; break outer;
                        case (7): tenemy_mask0 |= 128L; break outer;
                        case (8): tenemy_mask0 |= 256L; break outer;
                        case (9): tenemy_mask0 |= 512L; break outer;
                        case (10): tenemy_mask0 |= 1024L; break outer;
                        default: break outer;
                    }
                case (1):
                    switch (r.location.x - blx) {
                        case (0): tenemy_mask0 |= 2048L; break outer;
                        case (1): tenemy_mask0 |= 4096L; break outer;
                        case (2): tenemy_mask0 |= 8192L; break outer;
                        case (3): tenemy_mask0 |= 16384L; break outer;
                        case (4): tenemy_mask0 |= 32768L; break outer;
                        case (5): tenemy_mask0 |= 65536L; break outer;
                        case (6): tenemy_mask0 |= 131072L; break outer;
                        case (7): tenemy_mask0 |= 262144L; break outer;
                        case (8): tenemy_mask0 |= 524288L; break outer;
                        case (9): tenemy_mask0 |= 1048576L; break outer;
                        case (10): tenemy_mask0 |= 2097152L; break outer;
                        default: break outer;
                    }
                case (2):
                    switch (r.location.x - blx) {
                        case (0): tenemy_mask0 |= 4194304L; break outer;
                        case (1): tenemy_mask0 |= 8388608L; break outer;
                        case (2): tenemy_mask0 |= 16777216L; break outer;
                        case (3): tenemy_mask0 |= 33554432L; break outer;
                        case (4): tenemy_mask0 |= 67108864L; break outer;
                        case (5): tenemy_mask0 |= 134217728L; break outer;
                        case (6): tenemy_mask0 |= 268435456L; break outer;
                        case (7): tenemy_mask0 |= 536870912L; break outer;
                        case (8): tenemy_mask0 |= 1073741824L; break outer;
                        case (9): tenemy_mask0 |= 2147483648L; break outer;
                        case (10): tenemy_mask0 |= 4294967296L; break outer;
                        default: break outer;
                    }
                case (3):
                    switch (r.location.x - blx) {
                        case (0): tenemy_mask0 |= 8589934592L; break outer;
                        case (1): tenemy_mask0 |= 17179869184L; break outer;
                        case (2): tenemy_mask0 |= 34359738368L; break outer;
                        case (3): tenemy_mask0 |= 68719476736L; break outer;
                        case (4): tenemy_mask0 |= 137438953472L; break outer;
                        case (5): tenemy_mask0 |= 274877906944L; break outer;
                        case (6): tenemy_mask0 |= 549755813888L; break outer;
                        case (7): tenemy_mask0 |= 1099511627776L; break outer;
                        case (8): tenemy_mask0 |= 2199023255552L; break outer;
                        case (9): tenemy_mask0 |= 4398046511104L; break outer;
                        case (10): tenemy_mask0 |= 8796093022208L; break outer;
                        default: break outer;
                    }
                case (4):
                    switch (r.location.x - blx) {
                        case (0): tenemy_mask0 |= 17592186044416L; break outer;
                        case (1): tenemy_mask0 |= 35184372088832L; break outer;
                        case (2): tenemy_mask0 |= 70368744177664L; break outer;
                        case (3): tenemy_mask0 |= 140737488355328L; break outer;
                        case (4): tenemy_mask0 |= 281474976710656L; break outer;
                        case (5): tenemy_mask0 |= 562949953421312L; break outer;
                        case (6): tenemy_mask0 |= 1125899906842624L; break outer;
                        case (7): tenemy_mask0 |= 2251799813685248L; break outer;
                        case (8): tenemy_mask0 |= 4503599627370496L; break outer;
                        case (9): tenemy_mask0 |= 9007199254740992L; break outer;
                        case (10): tenemy_mask0 |= 18014398509481984L; break outer;
                        default: break outer;
                    }
                case (5):
                    switch (r.location.x - blx) {
                        case (0): tenemy_mask1 |= 1L; break outer;
                        case (1): tenemy_mask1 |= 2L; break outer;
                        case (2): tenemy_mask1 |= 4L; break outer;
                        case (3): tenemy_mask1 |= 8L; break outer;
                        case (4): tenemy_mask1 |= 16L; break outer;
                        case (5): tenemy_mask1 |= 32L; break outer;
                        case (6): tenemy_mask1 |= 64L; break outer;
                        case (7): tenemy_mask1 |= 128L; break outer;
                        case (8): tenemy_mask1 |= 256L; break outer;
                        case (9): tenemy_mask1 |= 512L; break outer;
                        case (10): tenemy_mask1 |= 1024L; break outer;
                        default: break outer;
                    }
                case (6):
                    switch (r.location.x - blx) {
                        case (0): tenemy_mask1 |= 2048L; break outer;
                        case (1): tenemy_mask1 |= 4096L; break outer;
                        case (2): tenemy_mask1 |= 8192L; break outer;
                        case (3): tenemy_mask1 |= 16384L; break outer;
                        case (4): tenemy_mask1 |= 32768L; break outer;
                        case (5): tenemy_mask1 |= 65536L; break outer;
                        case (6): tenemy_mask1 |= 131072L; break outer;
                        case (7): tenemy_mask1 |= 262144L; break outer;
                        case (8): tenemy_mask1 |= 524288L; break outer;
                        case (9): tenemy_mask1 |= 1048576L; break outer;
                        case (10): tenemy_mask1 |= 2097152L; break outer;
                        default: break outer;
                    }
                case (7):
                    switch (r.location.x - blx) {
                        case (0): tenemy_mask1 |= 4194304L; break outer;
                        case (1): tenemy_mask1 |= 8388608L; break outer;
                        case (2): tenemy_mask1 |= 16777216L; break outer;
                        case (3): tenemy_mask1 |= 33554432L; break outer;
                        case (4): tenemy_mask1 |= 67108864L; break outer;
                        case (5): tenemy_mask1 |= 134217728L; break outer;
                        case (6): tenemy_mask1 |= 268435456L; break outer;
                        case (7): tenemy_mask1 |= 536870912L; break outer;
                        case (8): tenemy_mask1 |= 1073741824L; break outer;
                        case (9): tenemy_mask1 |= 2147483648L; break outer;
                        case (10): tenemy_mask1 |= 4294967296L; break outer;
                        default: break outer;
                    }
                case (8):
                    switch (r.location.x - blx) {
                        case (0): tenemy_mask1 |= 8589934592L; break outer;
                        case (1): tenemy_mask1 |= 17179869184L; break outer;
                        case (2): tenemy_mask1 |= 34359738368L; break outer;
                        case (3): tenemy_mask1 |= 68719476736L; break outer;
                        case (4): tenemy_mask1 |= 137438953472L; break outer;
                        case (5): tenemy_mask1 |= 274877906944L; break outer;
                        case (6): tenemy_mask1 |= 549755813888L; break outer;
                        case (7): tenemy_mask1 |= 1099511627776L; break outer;
                        case (8): tenemy_mask1 |= 2199023255552L; break outer;
                        case (9): tenemy_mask1 |= 4398046511104L; break outer;
                        case (10): tenemy_mask1 |= 8796093022208L; break outer;
                        default: break outer;
                    }
                case (9):
                    switch (r.location.x - blx) {
                        case (0): tenemy_mask1 |= 17592186044416L; break outer;
                        case (1): tenemy_mask1 |= 35184372088832L; break outer;
                        case (2): tenemy_mask1 |= 70368744177664L; break outer;
                        case (3): tenemy_mask1 |= 140737488355328L; break outer;
                        case (4): tenemy_mask1 |= 281474976710656L; break outer;
                        case (5): tenemy_mask1 |= 562949953421312L; break outer;
                        case (6): tenemy_mask1 |= 1125899906842624L; break outer;
                        case (7): tenemy_mask1 |= 2251799813685248L; break outer;
                        case (8): tenemy_mask1 |= 4503599627370496L; break outer;
                        case (9): tenemy_mask1 |= 9007199254740992L; break outer;
                        case (10): tenemy_mask1 |= 18014398509481984L; break outer;
                        default: break outer;
                    }
                default: 
            }
        }

        if ((tenemy_mask0 | tenemy_mask1) > 0) {
            hasLaunchersNear = true;
        }

        //---- Cleanup! ---------
        for (int i = health_len; i-- > 0;) {
            friend_healths[i] = new_healths[i];
        }

        // I should probably be autogenerating these...
        // 00011111000
        // 00111111100
        // 01111111110
        // 01111111110    
        // 01111X11110
        // -> 0001111100000111111100011111111100111111111001111111110
        // Normal

        // 00000000000
        // 00000000000
        // 00000000000
        // 00001110000
        // 00001X10000
        // -> 0000000000000000000000000000000000000111000000001110000
        // Cloudy
        // Center 1 (the higher mask)

        // 01111111110
        // 01111111110
        // 00111111100
        // 00011111000
        // 00000000000
        // -> 0111111111001111111110001111111000001111100000000000000
        // Normal

        // 00001110000
        // 00000000000
        // 00000000000
        // 00000000000
        // 00000000000
        // -> 0000111000000000000000000000000000000000000000000000000
        // Cloudy
        // Center 0 (the lower mask)

        att_friend_mask[0] = att_friend_mask0;
        att_friend_mask[1] = att_friend_mask1;

        int mod = rc.getRoundNum() % 3;
        friend_mask[0] &= ~fupdates[mod][0];
        friend_mask[1] &= ~fupdates[mod][1];
        enemy_mask[0]  &= ~eupdates[mod][0];
        enemy_mask[1]  &= ~eupdates[mod][1];

        // Specifies what we can see.
        // Used to delete entries we no longer see.
        long center0 = 0;
        long center1 = 0;
        if (rc.senseCloud(myloc)) {
            center0 = 0b0000111000000000000000000000000000000000000000000000000L;
            center1 = 0b0000000000000000000000000000000000000111000000001110000L;
        } else {
            center0 = 0b0111111111001111111110001111111000001111100000000000000L;
            center1 = 0b0001111100000111111100011111111100111111111001111111110L;
        }

        friend_mask[0] &= ~center0;
        friend_mask[1] &= ~center1;
        enemy_mask[0]  &= ~center0;
        enemy_mask[1]  &= ~center1;

        friend_mask[0] |= tfriend_mask0;
        friend_mask[1] |= tfriend_mask1;
        enemy_mask[0]  |= tenemy_mask0;
        enemy_mask[1]  |= tenemy_mask1;

        fupdates[mod][0] = tfriend_mask0;
        fupdates[mod][1] = tfriend_mask1;
        eupdates[mod][0] = tenemy_mask0;
        eupdates[mod][1] = tenemy_mask1;
        prevLocation = rc.getLocation();

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
        long[] mask = { 0, 0 };
        if (att_friend_mask[0] == 0 && att_friend_mask[1] == 0) {
            mask[0] = friend_mask[0];
            mask[1] = friend_mask[1];
        } else {
            mask[0] = att_friend_mask[0];
            mask[1] = att_friend_mask[1];
        }
        int shift = (FE_MASK_HEIGHT - 1) * (FE_MASK_WIDTH);
        for (int i = 5; i-- > 0;) {
            mask[0] |= (mask[0] << 1);
            mask[1] |= (mask[1] << 1);

            mask[0] |= (mask[0] >> 1);
            mask[1] |= (mask[1] >> 1);

            mask[1] |= (mask[1] << FE_MASK_WIDTH);
            mask[1] |= (mask[0] >> shift);
            mask[0] |= (mask[0] << FE_MASK_WIDTH);

            mask[0] |= (mask[0] >> FE_MASK_WIDTH);
            mask[0] |= (mask[1] << shift);
            mask[1] |= (mask[1] >> FE_MASK_WIDTH);

            // some annoying switch statements.
            // System.out.println("");
            switch ((int)(mask[1] & 0b0000111000000001110000)) {
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
            switch ((int)(mask[0] >> (FE_MASK_WIDTH * (FE_MASK_HEIGHT - 1)) & 0b00001110000)) {
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
        // long[] mask = att_friend_mask;
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
