package optimizedBot;
import battlecode.common.Clock;
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
        hasLaunchersNear = false;
        hasCarriersNear = false;
        Team myTeam = rc.getTeam();
        int new_healths[] = new int[HEALTH_LEN];
        MapLocation new_locs[] = new MapLocation[HEALTH_LEN];
        for (int i = HEALTH_LEN; i-- > 0;) {
            new_healths[i] = 0;
            new_locs[i] = null;
        }

        // Location of the bottom left corner.
        MapLocation myloc = rc.getLocation();
        int blx = myloc.x - (FE_MASK_WIDTH / 2);
        int bly = myloc.y - (FE_MASK_HEIGHT);

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
            int shift = (FE_MASK_HEIGHT - 1) * (FE_MASK_WIDTH);
            while (dy < 0) {
                int neg = (-dy * FE_MASK_WIDTH);
                friend_mask[1] <<= neg;
                friend_mask[1] |= (friend_mask[0] >> (shift));
                friend_mask[0] <<= neg;
                enemy_mask[1] <<= neg;
                enemy_mask[1] |= (enemy_mask[0] >> (shift));
                enemy_mask[0] <<= neg;
                dy++;
            }
            while (dy > 0) {
                int mul = (dy * FE_MASK_WIDTH);
                friend_mask[0] >>= mul;
                friend_mask[0] |= (friend_mask[1] & 0b11111111111) << shift;
                friend_mask[1] >>= mul;
                enemy_mask[0] >>= mul;
                enemy_mask[0] |= (enemy_mask[1] & 0b11111111111) << shift;
                enemy_mask[1] >>= mul;
                dy--;
            }
        }

        att_friend_mask[0] = 0;
        att_friend_mask[1] = 0;
        long tfriend_mask[] = { 0, 0 };
        long tenemy_mask[] = { 0, 0 };
        RobotInfo[] robots = rc.senseNearbyRobots(-1);
        for (int j = robots.length; j-- > 0; ) {
            RobotInfo r = robots[j];
            switch (r.type) {
                case LAUNCHER: break;
                case CARRIER:  
                    if (r.team != myTeam) {
                        hasCarriersNear = true;
                    }
                default: continue;
            }

            int dx = r.location.x - blx;
            int dy = r.location.y - bly;
            if (r.team == myTeam) {
                long phealth = friend_healths[r.ID % HEALTH_LEN];
                new_healths[(r.ID % HEALTH_LEN)] = r.health;
                if (dy < FE_MASK_HEIGHT) {
                    int shift = (dy * FE_MASK_WIDTH + dx);
                    tfriend_mask[0] |= (1L << shift);
                    if (r.health + 10 < phealth) {
                        att_friend_mask[0] |= (1L << shift);
                    }
                } else {
                    int shift = ((dy - FE_MASK_HEIGHT) * (FE_MASK_WIDTH) + dx);
                    tfriend_mask[1] |= (1L << shift);
                    if (r.health + 10 < phealth) {
                        att_friend_mask[1] |= (1L << shift);
                    }
                }
            } else {
                if (dy < FE_MASK_HEIGHT) {
                    int shift = (dy * FE_MASK_WIDTH + dx);
                    tenemy_mask[0] |= (1L << shift);
                } else {
                    int shift = ((dy - FE_MASK_HEIGHT) * (FE_MASK_WIDTH) + dx);
                    tenemy_mask[1] |= (1L << shift);
                }
                hasLaunchersNear = true;
            }
        }

        for (int i = HEALTH_LEN; i-- > 0;) {
            friend_healths[i] = new_healths[i];
        }

        int mod = rc.getRoundNum() % 3;

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

        friend_mask[0] |= tfriend_mask[0];
        friend_mask[1] |= tfriend_mask[1];
        enemy_mask[0]  |= tenemy_mask[0];
        enemy_mask[1]  |= tenemy_mask[1];

        fupdates[mod][0] = tfriend_mask[0];
        fupdates[mod][1] = tfriend_mask[1];
        eupdates[mod][0] = tenemy_mask[0];
        eupdates[mod][1] = tenemy_mask[1];
        prevLocation = rc.getLocation();
    }

    /*
     *  Minimizes the distance to attacked soldiers.
     */

    void advance() throws GameActionException {
        long[] mask = { 0, 0 };
        int shift = (FE_MASK_HEIGHT - 1) * (FE_MASK_WIDTH);

        mask[0] |= (mask[0] << 1);
        mask[1] |= (mask[1] << 1);

        mask[0] |= (mask[0] >> 1);
        mask[1] |= (mask[1] >> 1);

        mask[1] |= (mask[1] << FE_MASK_WIDTH);
        mask[1] |= (mask[0] >> (shift));
        mask[0] |= (mask[0] << FE_MASK_WIDTH);

        mask[0] |= (mask[0] >> FE_MASK_WIDTH);
        mask[0] |= (mask[1] << (shift));
        mask[1] |= (mask[1] >> FE_MASK_WIDTH);

        // some annoying switch statements.
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
