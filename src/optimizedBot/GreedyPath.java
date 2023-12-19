package optimizedBot;
import battlecode.common.*;

/*
 *  I'm copying XSquare's pathing to measure
 *  How big of a deal it makes.
 */

public class GreedyPath {
    RobotController rc;
    static int H, W;

    GreedyPath(RobotController rc) {
        this.rc = rc;
        H = rc.getMapHeight();
        W = rc.getMapWidth();
        states = new int[W][];
    }

    int bugPathIndex = 0;

    int stateIndex = 0;

    boolean isReady(){
        return stateIndex >= W;
    }

    void fill(){
        while(stateIndex < W){
            if (Clock.getBytecodesLeft() < 1000) return;
            states[stateIndex++] = new int[H];
        }
    }

    Boolean rotateRight = null; //if I should rotate right or left
    //Boolean rotateRightAux = null;
    MapLocation lastObstacleFound = null; //latest obstacle I've found in my way

    MapLocation lastCurrent = null;
    int minDistToTarget = 1000000; //minimum distance I've been to the enemy while going around an obstacle
    MapLocation minLocationToTarget = null;
    MapLocation prevTarget = null; //previous target
    Direction[] dirs = Direction.values();

    int[][] states;

    MapLocation myLoc;
    boolean[] canMoveArray;
    int round;

    int turnsMovingToObstacle = 0;
    final int MAX_TURNS_MOVING_TO_OBSTACLE = 2;
    final int MIN_DIST_RESET = 3;

    void update(MapLocation target){
        if (!rc.isMovementReady()) return;
        myLoc = rc.getLocation();
        round = rc.getRoundNum();
        generateCanMove(target);
    }

    void generateCanMove(MapLocation target){
        canMoveArray = new boolean[9];
        for (Direction dir : dirs){
            switch (dir){
                case CENTER:
                    canMoveArray[dir.ordinal()] = true;
                    break;
                default:
                    canMoveArray[dir.ordinal()] = rc.canMove(dir);
                    break;
            }
        }
        if (lastCurrent != null){
            int d = rc.getLocation().distanceSquaredTo(lastCurrent);
            if (d > 0 && d <= 2){
                lastObstacleFound = lastCurrent;
                Direction dirCurrent = rc.getLocation().directionTo(lastCurrent);
                canMoveArray[dirCurrent.ordinal()] = false;
            }
        }

        try {

            if (lastObstacleFound == null) {
                for (Direction dir : dirs) {
                    if (!canMoveArray[dir.ordinal()]) continue;
                    MapLocation newLoc = rc.getLocation().add(dir);
                    if (newLoc.distanceSquaredTo(target) <= 2) continue;
                    Direction cur = rc.senseMapInfo(newLoc).getCurrentDirection();
                    if (cur == null || cur == Direction.CENTER) continue;
                    MapLocation newLoc2 = newLoc.add(cur);
                    if (newLoc2.distanceSquaredTo(target) >= rc.getLocation().distanceSquaredTo(target)){
                        canMoveArray[dir.ordinal()] = false;
                    }
                }
            }
        } catch (GameActionException e){
            e.printStackTrace();
        }


    }

    void debugMovement(){
        try{
            for (Direction dir : dirs){
                MapLocation newLoc = myLoc.add(dir);
                if (rc.canSenseLocation(newLoc) && canMoveArray[dir.ordinal()]) rc.setIndicatorDot(newLoc, 0, 0, 255);
            }
        } catch (Throwable t){
            t.printStackTrace();
        }
    }

    void move(MapLocation target){
        //No target? ==> bye!
        if (!rc.isMovementReady()) return;
        if (target == null) target = rc.getLocation();
        //if (Constants.DEBUG == 1)
        //rc.setIndicatorLine(rc.getLocation(), target, 255, 0, 255);

        update(target);
        //if (target == null) return;


        //different target? ==> previous data does not help!
        if (prevTarget == null) {
            resetPathfinding();
            rotateRight = null;
            //rotateRightAux = null;
        }


        else {
            int distTargets = target.distanceSquaredTo(prevTarget);
            if (distTargets > 0) {
                if (distTargets >= MIN_DIST_RESET){
                    rotateRight = null;
                    //rotateRightAux = null;
                    resetPathfinding();
                }
                else{
                    softReset(target);
                }
            }
        }

        //Update data
        prevTarget = target;

        checkState();
        myLoc = rc.getLocation();


        int d = myLoc.distanceSquaredTo(target);
        if (d == 0){
            return;
        }

        //If I'm at a minimum distance to the target, I'm free!
        if (d < minDistToTarget) {
            resetPathfinding();
            minDistToTarget = d;
            minLocationToTarget = myLoc;
        }

        //If there's an obstacle I try to go around it [until I'm free] instead of going to the target directly
        Direction dir = myLoc.directionTo(target);
        if (lastObstacleFound == null){
            if (tryGreedyMove()) {
                resetPathfinding();
                return;
            }
        }
        else{
            dir = myLoc.directionTo(lastObstacleFound);
            rc.setIndicatorDot(lastObstacleFound, 0, 255, 0);
            if (lastCurrent != null) rc.setIndicatorDot(lastCurrent, 255, 0, 0);
        }

        try {

            if (canMoveArray[dir.ordinal()]){
                rc.move(dir);
                if (lastObstacleFound != null) {
                    ++turnsMovingToObstacle;
                    lastObstacleFound = rc.getLocation().add(dir);
                    if (turnsMovingToObstacle >= MAX_TURNS_MOVING_TO_OBSTACLE){
                        resetPathfinding();
                    } else if (!rc.onTheMap(lastObstacleFound)){
                        resetPathfinding();
                    }
                }
                return;
            } else turnsMovingToObstacle = 0;

            checkRotate(dir);

            //I rotate clockwise or counterclockwise (depends on 'rotateRight'). If I try to go out of the map I change the orientation
            //Note that we have to try at most 16 times since we can switch orientation in the middle of the loop. (It can be done more efficiently)
            int i = 16;
            while (i-- > 0) {
                if (canMoveArray[dir.ordinal()]) {
                    rc.move(dir);
                    return;
                }
                MapLocation newLoc = myLoc.add(dir);
                if (!rc.onTheMap(newLoc)) rotateRight = !rotateRight;
                    //If I could not go in that direction and it was not outside of the map, then this is the latest obstacle found
                else lastObstacleFound = newLoc;
                if (rotateRight) dir = dir.rotateRight();
                else dir = dir.rotateLeft();
            }

            if (canMoveArray[dir.ordinal()]){
                rc.move(dir);
                return;
            }
        } catch (Throwable t){
            t.printStackTrace();
        }
    }

    boolean tryGreedyMove() {
        try {
            //if (rotateRightAux != null) return false;
            MapLocation myLoc = rc.getLocation();
            Direction dir = myLoc.directionTo(prevTarget);
            if (canMoveArray[dir.ordinal()]) {
                rc.move(dir);
                return true;
            }
            int dist = myLoc.distanceSquaredTo(prevTarget);
            int dist1 = 1000000;
            int dist2 = 1000000;
            Direction dir1 = dir.rotateRight();
            MapLocation newLoc = myLoc.add(dir1);
            if (canMoveArray[dir1.ordinal()]) dist1 = newLoc.distanceSquaredTo(prevTarget);
            Direction dir2 = dir.rotateLeft();
            newLoc = myLoc.add(dir2);
            if (canMoveArray[dir2.ordinal()]) dist2 = newLoc.distanceSquaredTo(prevTarget);
            if (dist1 < dist && dist1 < dist2) {
                rc.move(dir1);
                return true;
            }
            if (dist2 < dist && dist2 < dist1) {
                rc.move(dir2);
                return true;
            }
        } catch(Throwable t){
            t.printStackTrace();
        }
        return false;
    }

    void checkRotate(Direction dir){
        if (rotateRight != null) return;
        Direction dirLeft = dir;
        Direction dirRight = dir;
        int i = 8;
        while (--i >= 0) {
            if (!canMoveArray[dirLeft.ordinal()]) dirLeft = dirLeft.rotateLeft();
            else break;
        }
        i = 8;
        while (--i >= 0){
            if (!canMoveArray[dirRight.ordinal()]) dirRight = dirRight.rotateRight();
            else break;
        }
        int distLeft = myLoc.add(dirLeft).distanceSquaredTo(prevTarget), distRight = myLoc.add(dirRight).distanceSquaredTo(prevTarget);
        if (distRight < distLeft) rotateRight = true;
        else rotateRight = false;
    }

    //clear some of the previous data
    void resetPathfinding() {
        lastObstacleFound = null;
        minDistToTarget = 1000000;
        ++bugPathIndex;
        turnsMovingToObstacle = 0;
    }

    void softReset(MapLocation target){
        if (rc.getType() == RobotType.AMPLIFIER){
            resetPathfinding();
            return;
        }
        if (minLocationToTarget != null) minDistToTarget = minLocationToTarget.distanceSquaredTo(target);
        else resetPathfinding();
    }

    void checkState(){
        if (!isReady()) return;
        if (lastObstacleFound == null) return;
        int state = (bugPathIndex << 14) | (lastObstacleFound.x << 8) |  (lastObstacleFound.y << 2);
        if (rotateRight != null) {
            if (rotateRight) state |= 1;
            else state |= 2;
        }
        if (states[myLoc.x][myLoc.y] == state){
            resetPathfinding();
        }

        states[myLoc.x][myLoc.y] = state;
    }

    void checkCurrent() throws GameActionException {
        if (lastObstacleFound == null){
            lastCurrent = null;
            return;
        }
        MapInfo mi = rc.senseMapInfo(rc.getLocation());
        if (mi.getCurrentDirection() == null || mi.getCurrentDirection() == Direction.CENTER){
            if (lastCurrent != null && lastObstacleFound.distanceSquaredTo(lastCurrent) == 0) return;
            lastCurrent = null;
            return;
        }
        lastCurrent = rc.getLocation();
    }

    public boolean flee() throws GameActionException {
        int dist = 0;
        Direction bst = Direction.CENTER;
        RobotInfo[] r = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        if (r == null) return false;
        for(Direction dir : Direction.values()) {
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
}