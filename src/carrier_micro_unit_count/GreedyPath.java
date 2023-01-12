package carrier_micro_unit_count;
import battlecode.common.*;

import java.util.Map;

public class GreedyPath {
    RobotController rc;
    MapLocation target;
    int sz = 0;
    MapLocation[] lastLoc = new MapLocation[11];
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

    public void move(MapLocation goal) throws GameActionException{
        if(sz < 11){
            lastLoc[sz] = rc.getLocation();
            sz++;
        }
        else{
            for(int i = 0; i < 10; i++) lastLoc[i] = lastLoc[i + 1];
            lastLoc[10] = rc.getLocation();
        }
        Direction bst = Direction.CENTER;
        int mn = 10000000;
        int curDirStart = (int) (Math.random() * directions.length);
        for (int i = 0; i < 8; i++) {
            Direction dir = directions[(curDirStart + i) % 8];
            MapLocation nxt = rc.getLocation().add(dir);
            int f = 1;
            for (int j = 0; j < sz; j++)
                if (lastLoc[j].equals(nxt)) {
                    f = 0;
                    break;
                }
            if (rc.canMove(dir) && f > 0) {
                if (goal.distanceSquaredTo(nxt) < mn) {
                    bst = dir;
                    mn = goal.distanceSquaredTo(nxt);
                }
            }
        }
        if(bst != Direction.CENTER && rc.canMove(bst)) rc.move(bst);
    }

    public void flee() throws GameActionException{
        Direction bst = Direction.CENTER;
        int dist = 0;
        RobotInfo[] r = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());
        for(Direction dir : directions) if(rc.canMove(dir)){
            MapLocation esc = rc.getLocation().add(dir);
            int curDist = 0;
            for(RobotInfo rob : r){
                curDist += esc.distanceSquaredTo(rob.location) * (Util.isAttacker(rob.type) ? 3 : 1);
            }
            if(curDist > dist){
                bst = dir;
                dist = curDist;
            }
        }
        if(bst != Direction.CENTER && rc.canMove(bst)){
            rc.move(bst);
            move(rc.getLocation().add(bst).add(bst));
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
