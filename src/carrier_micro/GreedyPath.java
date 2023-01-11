package carrier_micro;
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
        try {
            target = t;
            MoveTarget best = new MoveTarget(directions[0]);
            for (Direction d: directions) {
                MoveTarget cur = new MoveTarget(d);
                if (cur.isBetterThan(best))
                    best = cur;
            }
            if (rc.canMove(best.dir))
                rc.move(best.dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MoveTarget {
        Direction dir;
        boolean canMove;
        int d;
        int current_dir;
        MoveTarget(Direction dir) throws GameActionException {
            this.dir = dir;
            if (rc.canMove(dir)) {
                MapLocation nloc = rc.getLocation().add(dir);
                MapInfo mi = rc.senseMapInfo(nloc);
                nloc = nloc.add(mi.getCurrentDirection());
                d = nloc.distanceSquaredTo(target);
                canMove = true;
            } else {
                canMove = false;
            }
        }

        boolean isBetterThan(MoveTarget mt) {
            if (mt.canMove && !canMove) return false;
            if (!mt.canMove && canMove) return true;
            return d <= mt.d;
        }
    }
}
