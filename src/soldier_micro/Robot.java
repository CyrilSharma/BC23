package soldier_micro;
import java.util.Random;
import battlecode.common.*;

public abstract class Robot {
    RobotController rc;
    final Random rng = new Random();
    GreedyPath greedyPath;
    Exploration exploration;
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

    public Robot(RobotController rc) {
        this.rc = rc;
        rng.setSeed((long) rc.getID());
        greedyPath = new GreedyPath(rc);
    }
    abstract void run() throws GameActionException;
}
