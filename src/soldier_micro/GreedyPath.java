package soldier_micro;
import battlecode.common.*;

public class GreedyPath {
    RobotController rc;
    MapLocation target;
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
    public GreedyPath(RobotController rc) {
        this.rc = rc;
    }

    public void move(MapLocation t) {
        target = t;
        MoveTarget best = new MoveTarget(directions[0]);
        for (Direction d: directions) {
            MoveTarget cur = new MoveTarget(d);
            if (cur.isBetterThan(best))
                best = cur;
        }
        try {
            if (rc.canMove(best.dir))
                rc.move(best.dir);
        } catch (GameActionException e) {
            System.out.println(e.getMessage());
        }
    }

    class MoveTarget {
        Direction dir;
        boolean canMove;
        int d;
        int current_dir;
        MoveTarget(Direction dir) {
            this.dir = dir;
            canMove = rc.canMove(dir);
            MapLocation nloc = rc.getLocation().add(dir);
            d = nloc.distanceSquaredTo(target);
            // When I can detect current I will add that as well.
            
            //MapInfo mi = rc.senseMapInfo(nloc);
            // rc.sense
            //MapInfo mi = rc.senseMapInfo(nloc);
        }

        boolean isBetterThan(MoveTarget mt) {
            if (mt.canMove && !canMove) return false;
            if (!mt.canMove && canMove) return true;
            return d <= mt.d;
        }
    }
}
