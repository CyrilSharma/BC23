package sprintBot1;
import battlecode.common.*;

import java.util.Map;

public class GreedyPath {
    RobotController rc;
    MapLocation target;
    boolean ready = false;
    int sz = 0;
    int[][] lastLoc;
    int fuzzyCnt = 0;
    int curIter = 0;
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
        lastLoc = new int[rc.getMapWidth()][];
    }

    boolean isReady() throws GameActionException {
        if (ready) return true;
        int start = Clock.getBytecodesLeft();
        while (curIter < rc.getMapWidth()) {
            if (start - Clock.getBytecodesLeft() > 1000) return false;
            lastLoc[curIter] = new int[rc.getMapHeight()];
            curIter++;
        }
        ready = true;
        return true;
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
        if (!rc.isMovementReady()) return;
        if (rc.getType() == RobotType.LAUNCHER &&
            rc.getRoundNum()%3 == 1) return;
        if (!loc.equals(destination)) {
            destination = loc;
            bestSoFar = 99999;
            startDir = -1;
            clockwise = Math.random() < 0.5;
            startDirMissingInARow = 0;
        }
        isReady();
        if(rc.getLocation().equals(loc)) return;
        if (ready) lastLoc[rc.getLocation().x][rc.getLocation().y] = rc.getRoundNum();
        RobotInfo[] r = rc.senseNearbyRobots(rc.getType().visionRadiusSquared);
        if(r.length > 25){
            fuzzyCnt = 10;
        }
        if (ready && lastLoc[rc.getLocation().x][rc.getLocation().y] > 0 && 
            rc.getRoundNum() - lastLoc[rc.getLocation().x][rc.getLocation().y] < 10){
            fuzzyCnt = 10;
        }
        if(fuzzyCnt > 0){
            fuzzy(loc);
            fuzzyCnt--;
        }
        int dist = hybridDistance(rc.getLocation(), destination);
        if (dist < bestSoFar) {
            bestSoFar = dist;
            startDir = rc.getLocation().directionTo(destination).ordinal();
            int firstDist = -1; // Distance if you move in the current clockwise/anticlockwise direction
            int lastDist = -1; // Distance if you move in the opposite direction
            int dir = startDir;
            for (int i = 0; i < 8; i++) {
                if(dir == 8) dir = 0;
                MapLocation next = rc.adjacentLocation(directions[dir]);
                if (rc.onTheMap(next) && rc.canMove(directions[dir])){
                    for (int iter = 0; iter < 1; iter++) {
                        if (!rc.canSenseLocation(next)) break;
                        MapInfo mi = rc.senseMapInfo(next);
                        next = next.add(mi.getCurrentDirection());
                    }
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
            if(dir == 8) dir = 0;
            MapLocation next = rc.adjacentLocation(directions[dir]);
            for (int iter = 0; iter < 1; iter++) {
                if (!rc.canSenseLocation(next)) break;
                MapInfo mi = rc.senseMapInfo(next);
                next = next.add(mi.getCurrentDirection());
            }
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

    public void fuzzy(MapLocation goal) throws GameActionException{
        if (ready) lastLoc[rc.getLocation().x][rc.getLocation().y] = rc.getRoundNum();
        Direction bst = Direction.CENTER;
        int mn = 10000000;
        int curDirStart = (int) (Math.random() * directions.length);
        for (int i = 0; i < 8; i++) {
            Direction dir = directions[(curDirStart + i) % 8];
            MapLocation nxt = rc.getLocation().add(dir);
            int f = 1;
            if (ready && lastLoc[rc.getLocation().x][rc.getLocation().y] > 0 && 
                rc.getRoundNum() - lastLoc[rc.getLocation().x][rc.getLocation().y] < 10){
                f = 0;
            }
            if (rc.canMove(dir) && f > 0) {
                if (goal.distanceSquaredTo(nxt) < mn) {
                    bst = dir;
                    mn = goal.distanceSquaredTo(nxt);
                }
            }
        }
        if(!bst.equals(Direction.CENTER) && rc.canMove(bst) && rc.getLocation().distanceSquaredTo(goal) >= rc.getLocation().add(bst).distanceSquaredTo(goal)) rc.move(bst);
    }

    // hybrid between manhattan distance (dx + dy) and max distance max(dx, dy)
    public static int hybridDistance(MapLocation a, MapLocation b) {
        int dy = Math.abs(a.y - b.y);
        int dx = Math.abs(a.x - b.x);
        return dy + dx + Math.max(dy, dx);
    }

    public boolean tryMove(Direction dir) throws GameActionException{
        if(rc.canMove(dir) && !dir.equals(Direction.CENTER)){
            rc.move(dir);
            return true;
        }
        return false;
    }

    public boolean flee() throws GameActionException {
        int dist = 0;
        Direction bst = Direction.CENTER;
        RobotInfo[] r = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        for(Direction dir : directions) {
            if (!rc.canMove(dir)) continue;
            MapLocation esc = rc.getLocation().add(dir);
            int curDist = 0;
            for (RobotInfo rob : r){
                curDist += esc.distanceSquaredTo(rob.location) * (Util.isAttacker(rob.type) ? 3 : 1);
            }
            if (curDist > dist){
                bst = dir;
                dist = curDist;
            }
        }

        if (!bst.equals(Direction.CENTER) && rc.canMove(bst)){
            rc.move(bst);
            return true;
        }
        return false;
    }

    public void launcherFlee() throws GameActionException {
        int dist = -1000000;
        Direction bst = Direction.CENTER;
        RobotInfo[] r = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        for(Direction dir : directions) {
            if (!rc.canMove(dir)) continue;
            MapLocation esc = rc.getLocation().add(dir);
            int curDist = 0;
            for (RobotInfo rob : r){
                if(rob.getType() == RobotType.HEADQUARTERS) continue;
                curDist += esc.distanceSquaredTo(rob.location) * (Util.isAttacker(rob.type) ? 3 : -2);
            }
            if (curDist > dist){
                bst = dir;
                dist = curDist;
            }
        }

        if (!bst.equals(Direction.CENTER) && rc.canMove(bst)){
            rc.move(bst);
        }
    }
}
