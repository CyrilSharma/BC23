package carrier_micro_unit_count;
import java.util.Random;
import battlecode.common.*;

public abstract class Robot {
    RobotController rc;
    final Random rng = new Random();
    GreedyPath greedyPath;
    Exploration exploration;
    Communications communications;
    Util util;
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

    public Robot(RobotController rc){
        this.rc = rc;
        rng.setSeed((long) rc.getID());
        greedyPath = new GreedyPath(rc);
        exploration = new Exploration(rc);
        communications = new Communications(rc);
        util = new Util(rc);
    }
    abstract void run() throws GameActionException;
}
