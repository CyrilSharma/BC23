package jan28;
import battlecode.common.*;

import java.util.Map;

public class GreedyPath {
    RobotController rc;
    boolean ready = false;
    boolean shoudAvoidClouds = false;
    int sz = 0;
    int[][] lastLoc;
    boolean shouldBug = false;
    int curIter = 0;
    int goalRound = 0;
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

    void getReady() throws GameActionException {
        if (ready) return;
        int start = Clock.getBytecodesLeft();
        while (curIter < rc.getMapWidth()) {
            if (start - Clock.getBytecodesLeft() > 1000) return;
            lastLoc[curIter] = new int[rc.getMapHeight()];
            curIter++;
        }
        ready = true;
        return;
    }

    MapLocation previous = null;
    boolean shouldMove;
    public Direction move(MapLocation loc) throws GameActionException {
        return move(loc, false);
    }

    public Direction move(MapLocation loc, boolean avoidClouds) throws GameActionException {
        if (!rc.isMovementReady()) return null;
        if (rc.getType() == RobotType.LAUNCHER &&
            rc.getRoundNum()%2 == 0) return null;
        if (rc.getLocation().equals(loc)) return Direction.CENTER;
        if (previous == null || loc.distanceSquaredTo(previous) > 100) {
            shoudAvoidClouds = avoidClouds;
            previous = loc;
            resetGreedy(loc);
            resetBug(loc);
            shouldBug = false;
        }
        getReady();
        if (!shouldBug) return fuzzy(loc);
        else return bug(loc);
    }
    
    public static MapLocation bugTarget = null;
    public static int bestSoFar = 0;
    // which direction to start your checks in the next bugnav move
    public static int startDir = -1;
    // whether to search clockwise (as opposed to anticlockwise) in bugnav
    public static boolean clockwise = false;
    // number of turns in which startDir has been missing in a row
    public static int startDirMissingInARow = 0;
    public Direction bug(MapLocation loc) throws GameActionException {
        //rc.setIndicatorString("BUG: " + loc);
        // Exit condition: got closer to the destination then when I started.
        int dist = hybridDistance(rc.getLocation(), bugTarget);
        //rc.setIndicatorString("D: "+dist+" BEST: "+bestSoFar);
        if (dist < bestSoFar || hasCycle()) {
            shouldBug = false;
            resetGreedy(loc);
            return fuzzy(loc);
        }
        markLoc();
        int dir = startDir;
        for (int i = 0; i < 8; i++) {
            if (dir == 8) dir = 0;
            MapLocation next = rc.adjacentLocation(directions[dir]);
            if (!rc.canSenseLocation(next)) break;
            MapInfo mi = rc.senseMapInfo(next);
            // if this is my final move...
            int cld = rc.getType().movementCooldown;
            if(rc.getType() == RobotType.CARRIER) cld = (int)Math.floor(5.0 + (double)rc.getWeight()/8.0);
            if (rc.getMovementCooldownTurns() + cld >= 10)
                next = next.add(mi.getCurrentDirection());
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
                    // reset startDir to point towards bugTarget
                    if (++startDirMissingInARow == 3) {
                        startDir = rc.getLocation().directionTo(bugTarget).ordinal();
                        startDirMissingInARow = 0;
                    }
                }
                // Rare occasion when startDir gets set to Direction.CENTER
                if (startDir == 8) {
                    startDir = 0;
                }
                // Safeguard 3: If startDir points off the map, reset startDir towards bugTarget
                if (!rc.onTheMap(rc.adjacentLocation(directions[startDir]))) {
                    startDir = rc.getLocation().directionTo(bugTarget).ordinal();
                }
                
                return directions[dir];
            }

            if (clockwise) dir = (dir + 1) % 8;
            else dir = (dir + 7) % 8;
        }
        return null;
    }

    public static MapLocation greedyTarget = null;
    public Direction fuzzy(MapLocation goal) throws GameActionException {
        // Exit condition: encountered a cycle.
        if (hasCycle()) {
            // rc.setIndicatorString("I WAS HERE: "+lastLoc[rc.getLocation().x][rc.getLocation().y]);
            shouldBug = true;
            resetBug(goal);
            return bug(goal);
        }
        markLoc();
         //rc.setIndicatorString("FUZZY: " + goal);
        int mn = 10000000;
        Direction bst = Direction.CENTER;
        int curDirStart = (int) (Math.random() * directions.length);
        for (int i = 0; i < 8; i++) {
            Direction dir = directions[(curDirStart + i) % 8];
            MapLocation nxt = rc.getLocation().add(dir);
            if (!rc.onTheMap(nxt)) continue;
            MapInfo mi = rc.senseMapInfo(nxt);
            int cld = rc.getType().movementCooldown;
            if(rc.getType() == RobotType.CARRIER) cld = (int)Math.floor(5.0 + (double)rc.getWeight()/8.0);
            if (rc.getMovementCooldownTurns() + cld >= 10)
                nxt = nxt.add(mi.getCurrentDirection());
            if (rc.canMove(dir) && !(shoudAvoidClouds && mi.hasCloud())) {
                if (goal.distanceSquaredTo(nxt) < mn) {
                    bst = dir;
                    mn = goal.distanceSquaredTo(nxt);
                }
            }
        }
        if(!bst.equals(Direction.CENTER) && rc.canMove(bst) 
            && rc.getLocation().distanceSquaredTo(goal) >= 
            rc.getLocation().add(bst).distanceSquaredTo(goal)) {
            rc.move(bst);
            return bst;
        }
        return null;
    }

    void resetGreedy(MapLocation loc) {
        greedyTarget = loc;
        goalRound = rc.getRoundNum();
    }

    void resetBug(MapLocation loc) throws GameActionException {
        bugTarget = loc;
        bestSoFar = hybridDistance(rc.getLocation(), bugTarget);
        startDir = rc.getLocation().directionTo(loc).ordinal();
        clockwise = true; // all units should orbit in same dir -> Math.random() < 0.5;
        startDirMissingInARow = 0;
        goalRound = rc.getRoundNum();
    }

    boolean hasCycle() {
        return (ready && lastLoc[rc.getLocation().x][rc.getLocation().y] > 0 && 
        (rc.getRoundNum() - lastLoc[rc.getLocation().x][rc.getLocation().y]) < 10 &&
        lastLoc[rc.getLocation().x][rc.getLocation().y] >= goalRound);
    }

    void markLoc() {
        if (!ready) return;
        lastLoc[rc.getLocation().x][rc.getLocation().y] = rc.getRoundNum() - 1;
    }

    // hybrid between manhattan distance (dx + dy) and max distance max(dx, dy)
    public static int hybridDistance(MapLocation a, MapLocation b) {
        int dy = Math.abs(a.y - b.y);
        int dx = Math.abs(a.x - b.x);
        return dy + dx;
    }

    public boolean tryMove(Direction dir) throws GameActionException{
        MapInfo mi = rc.senseMapInfo(rc.getLocation().add(dir));
        if(rc.canMove(dir) && !dir.equals(Direction.CENTER) &&
            !(shoudAvoidClouds && mi.hasCloud())){
            rc.move(dir);
            return true;
        }
        return false;
    }

    public boolean flee() throws GameActionException {
        int dist = 0;
        Direction bst = Direction.CENTER;
        RobotInfo[] r = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        if (r == null) return false;
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
