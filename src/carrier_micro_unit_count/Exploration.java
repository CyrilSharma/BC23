package carrier_micro_unit_count;
import java.util.Random;

import battlecode.common.*;

public class Exploration {
    RobotController rc;
    GreedyPath greedyPath;
    MapLocation target;
    MapLocation[] keypos;
    int height, width;
    final Random rng = new Random();
    static final Direction[] directions = {
        Direction.NORTH,
        Direction.NORTHEAST,
        Direction.EAST,
        Direction.SOUTHEAST,
        Direction.SOUTH,
        Direction.SOUTHWEST,
        Direction.WEST,
        Direction.NORTHWEST,
    };
    public Exploration(RobotController rc) {
        this.rc = rc;
        greedyPath = new GreedyPath(rc);
        height = rc.getMapHeight();
        width = rc.getMapWidth();
        rng.setSeed((long) rc.getID());
        keypos = new MapLocation[5];
        keypos[0] = new MapLocation(width, 0);
        keypos[1] = new MapLocation(0, height);
        keypos[2] = new MapLocation(width, height);
        keypos[3] = new MapLocation(0, 0);
        keypos[4] = new MapLocation(width/2, height/2);
    }

    public void move() throws GameActionException{
        if (target == null) target = generateTarget();
        if (rc.getLocation().distanceSquaredTo(target) <= 9) {
            target = generateTarget();
        }
        greedyPath.move(target);
    }

    public MapLocation generateTarget() {
        // we need to compute enemy territory somehow so we don't end up waltzing into enemy territory.
        if (rng.nextInt(2) == 0) {
            return keypos[rng.nextInt(5)];
        } else {
            MapLocation m = rc.getLocation();
            while (rc.getLocation().distanceSquaredTo(m) <= 80) {
                m = new MapLocation(rng.nextInt(width), rng.nextInt(height));
            }
            return m;
        }
    }
}
