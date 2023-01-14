package sprintBot1;
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

    public static MapLocation destination = null;
    public static int bestSoFar = 0;
    // which direction to start your checks in the next bugnav move
    public static int startDir = -1;
    // whether to search clockwise (as opposed to anticlockwise) in bugnav
    public static boolean clockwise = false;
    // number of turns in which startDir has been missing in a row
    public static int startDirMissingInARow = 0;
    public void move(MapLocation loc) throws GameActionException {
        if (!loc.equals(destination)) {
            destination = loc;
            bestSoFar = 99999;
            startDir = -1;
            clockwise = Math.random() < 0.5;
            startDirMissingInARow = 0;
        }

        int dist = hybridDistance(rc.getLocation(), destination);
        if (dist < bestSoFar) {
            bestSoFar = dist;
            startDir = rc.getLocation().directionTo(destination).ordinal();
            int firstDist = -1; // Distance if you move in the current clockwise/anticlockwise direction
            int lastDist = -1; // Distance if you move in the opposite direction
            int dir = startDir;
            for (int i = 0; i < 8; i++) {
                MapLocation next = rc.adjacentLocation(directions[dir]);
                if (rc.onTheMap(next) && rc.canMove(directions[dir])){
                    int nextDist = hybridDistance(next, destination);
                    if (firstDist == -1) {
                        firstDist = nextDist;
                    }
                    lastDist = nextDist;
                }
                if (clockwise) dir = (dir + 1) % 8;
                else dir = (dir + 7) % 8;
            }
            //System.out.println("clockwise = " + clockwise + ", firstDist = " + firstDist + ", lastDist = " + lastDist);
            if (lastDist < firstDist) {
                // Switch directions
                clockwise = !clockwise;
            }
        }

        int dir = startDir;
        for (int i = 0; i < 8; i++) {
            MapLocation next = rc.adjacentLocation(directions[dir]);
            // If you hit the edge of the map, reverse direction
            if (!rc.onTheMap(next)) {
                clockwise = !clockwise;
                dir = startDir;
            } else if (tryMove(directions[dir])) {
                // Safeguard 1: dir might equal startDir if this robot was blocked by another robot last turn
                // that has since moved.
                if (dir != startDir) {
                    if (clockwise) startDir = dir % 2 == 1 ? (dir + 5) % 8 : (dir + 6) % 8;
                    else startDir = dir % 2 == 1 ? (dir + 3) % 8 : (dir + 2) % 8;

                    startDirMissingInARow = 0;
                } else {
                    // Safeguard 2: If the obstacle that should be at startDir is missing 2/3 turns in a row
                    // reset startDir to point towards destination
                    if (++startDirMissingInARow == 3) {
                        startDir = rc.getLocation().directionTo(destination).ordinal();
                        startDirMissingInARow = 0;
                    }
                }
                // Rare occasion when startDir gets set to Direction.CENTER
                if (startDir == 8) {
                    startDir = 0;
                }
                // Safeguard 3: If startDir points off the map, reset startDir towards destination
                if (!rc.onTheMap(rc.adjacentLocation(directions[startDir]))) {
                    startDir = rc.getLocation().directionTo(destination).ordinal();
                }
                return;
            }

            if (clockwise) dir = (dir + 1) % 8;
            else dir = (dir + 7) % 8;
        }
    }
    // hybrid between manhattan distance (dx + dy) and max distance max(dx, dy)
    public static int hybridDistance(MapLocation a, MapLocation b) {
        int dy = Math.abs(a.y - b.y);
        int dx = Math.abs(a.x - b.x);
        return dy + dx + Math.max(dy, dx);
    }

/*
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
            if (!rc.canMove(dir)) continue;
            MapInfo mi = rc.senseMapInfo(nxt);
            nxt = nxt.add(mi.getCurrentDirection());
            int f = 1;
            for (int j = 0; j < sz; j++)
                if (lastLoc[j].equals(nxt)) {
                    f = 0;
                    break;
                }
            if (f > 0) {
                if (goal.distanceSquaredTo(nxt) < mn) {
                    bst = dir;
                    mn = goal.distanceSquaredTo(nxt);
                }
            }
        }
        if(bst != Direction.CENTER && rc.canMove(bst)) rc.move(bst);
    }

 */
    public boolean tryMove(Direction dir) throws GameActionException{
        if(rc.canMove(dir)){
            rc.move(dir);
            return true;
        }
        return false;
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
