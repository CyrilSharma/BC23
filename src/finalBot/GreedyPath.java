package finalBot;
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
        if (previous == null || !loc.equals(previous)){
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
    public static int unitObstacle = 0;
    public static boolean skippedTurn = false;
    public Direction bug(MapLocation loc) throws GameActionException {
        //rc.setIndicatorString("BUG: " + loc);
        // Exit condition: got closer to the destination then when I started.
        int dist = hybridDistance(rc.getLocation(), bugTarget);
        //rc.setIndicatorString("D: "+dist+" BEST: "+bestSoFar+", unit: " + unitObstacle);
        if (dist < bestSoFar || hasCycle()) {
            shouldBug = false;
            resetGreedy(loc);
            return fuzzy(loc);
        }
        //try switching over

        markLoc();
        int dir = startDir;
        StringBuilder uwu = new StringBuilder();
        rc.setIndicatorString("start: " + startDir);
        for (int i = 0; i < 8; i++) {
            if (dir == 8) dir = 0;
            MapLocation next = rc.getLocation().add(directions[dir]);
            uwu.append("|"+next+"|"+directions[dir]+"|");
            int cld = rc.getType().movementCooldown;
            if (rc.canSenseLocation(next)) {
                MapInfo mi = rc.senseMapInfo(next);
                // if this is my final move...
                if (rc.getType() == RobotType.CARRIER) cld = (int) Math.floor(5.0 + (double) rc.getWeight() / 8.0);
                if (rc.getMovementCooldownTurns() + cld >= 10)
                    next = next.add(mi.getCurrentDirection());
            }
            if(dir == startDir && (rc.canSenseLocation(next) && rc.senseRobotAtLocation(next) != null && rc.senseRobotAtLocation(next).getType() != RobotType.HEADQUARTERS)){
                unitObstacle++;
            }
            if(unitObstacle == 3){
                startDir = rc.getLocation().directionTo(bugTarget).ordinal();
                clockwise = !clockwise;
                startDirMissingInARow = 0;
                unitObstacle = 0;
                dir = startDir;
                next = rc.adjacentLocation(directions[dir]);
                if(!rc.canSenseLocation(next)) break;
                MapInfo mi = rc.senseMapInfo(next);
                if(rc.getType() == RobotType.CARRIER) cld = (int)Math.floor(5.0 + (double)rc.getWeight()/8.0);
                if (rc.getMovementCooldownTurns() + cld >= 10)
                    next = next.add(mi.getCurrentDirection());
                if(dir == startDir && (rc.canSenseLocation(next) && rc.senseRobotAtLocation(next) != null && rc.senseRobotAtLocation(next).getType() != RobotType.HEADQUARTERS)) unitObstacle++;
            }
            if (dir == startDir && !skippedTurn){
                RobotInfo[] e = rc.senseNearbyRobots(rc.getType().visionRadiusSquared);
                if(e.length >= 25) {
                    boolean f = true;
                    for (RobotInfo r : e) {
                        if (r.location.equals(next)) {
                            f = false;
                            break;
                        }
                    }
                    if (!f) {
                        skippedTurn = true;
                        break;
                    }
                }
            }
            skippedTurn = false;
            // If you hit the edge of the map, reverse direction
            if (!rc.onTheMap(next)) {
                clockwise = !clockwise;
                dir = startDir;
            }
            if (tryMove(directions[dir])) {
                // Safeguard 1: dir might equal startDir if this robot was blocked by another robot last turn
                // that has since moved.
                if (dir != startDir) {
                    if (clockwise) startDir = (dir + 6) % 8;
                    else startDir = (dir + 2) % 8;

                    startDirMissingInARow = 0;
                } else {
                    // Safeguard 2: If the obstacle that should be at startDir is missing 2/3 turns in a row
                    // reset startDir to point towards bugTarget
                    if (++startDirMissingInARow == 3) {
                        startDir = rc.getLocation().directionTo(bugTarget).ordinal();
                        startDirMissingInARow = 0;
                    }
                    else{
                        if (clockwise) startDir = (dir + 6) % 8;
                        else startDir = (dir + 2) % 8;
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
                //rc.setIndicatorString("dre: " + directions[dir] + ", start: " + directions[startDir]);
                uwu.append("|"+directions[startDir]);
                return directions[dir];
            }

            if (clockwise) dir = (dir + 1) % 8;
            else dir = (dir + 7) % 8;
        }
        //rc.setIndicatorString(uwu.toString());
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
        // rc.setIndicatorString("FUZZY: " + goal);
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
        //here we need to consider both directions
        bugTarget = loc;
        bestSoFar = hybridDistance(rc.getLocation(), bugTarget);
        startDir = rc.getLocation().directionTo(loc).ordinal();
        clockwise = false;
        startDirMissingInARow = 0;
        unitObstacle = 0;
        goalRound = rc.getRoundNum();
        MapLocation curLoc = rc.getLocation();
        int st = startDir;
        //StringBuilder str = new StringBuilder();
        //str.append("RESET BUG|");
        for(int j = 0; j < 5; j++){
            int dd = st;
            for (int i = 0; i < 8; i++) {
                MapLocation next = curLoc.add(directions[dd]);
                if(!rc.canSenseLocation(next)){
                    clockwise = true;
                    return;
                }
                if(dd == st && rc.sensePassability(next)) return;
                if (!rc.onTheMap(next)) {
                    clockwise = true;
                    break;
                }
                if(rc.sensePassability(next)){
                    //str.append("|" + next + "|");
                    curLoc = next.add(directions[dd]);
                    if(dd != st) st = (dd + 2) % 8;
                    if (!rc.onTheMap(next.add(directions[st]))) {
                        clockwise = true;
                        return;
                    }
                    break;
                }
                dd = (dd + 7) % 8;
            }
        }
        //rc.setIndicatorString(str.toString());
        clockwise = true;
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
        if(!rc.canSenseLocation(rc.getLocation().add(dir))) return false;
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
