package feb2;
import java.util.Random;

import battlecode.common.*;

public class Exploration {
    RobotController rc;
    GreedyPath greedyPath;
    Communications communications;
    MapLocation target;
    MapLocation[] keypos;
    int height, width;
    final Random rng = new Random();
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
    public Exploration(RobotController rc){
        this.rc = rc;
        greedyPath = new GreedyPath(rc);
        height = rc.getMapHeight();
        width = rc.getMapWidth();
        rng.setSeed((long) rc.getID());
        communications = new Communications(rc);
        keypos = new MapLocation[5];
        keypos[0] = new MapLocation(width, 0);
        keypos[1] = new MapLocation(0, height);
        keypos[2] = new MapLocation(width, height);
        keypos[3] = new MapLocation(0, 0);
        keypos[4] = new MapLocation(width/2, height/2);
    }

    public void moveLauncher(MapLocation[] hqs, int nm) throws GameActionException{
        if (target == null) target = generateTargetLauncher(hqs, nm);
        if (rc.getLocation().distanceSquaredTo(target) <= 9) {
            target = generateTargetLauncher(hqs, nm);
        }
        greedyPath.move(target);
    }

    public MapLocation randomReflect(MapLocation m){
        int r = rng.nextInt(3);
        if(r == 0) return new MapLocation(width - m.x - 1, m.y);
        if(r == 1) return new MapLocation(m.x, height - m.y - 1);
        return new MapLocation(width - m.x - 1, height - m.y - 1);
    }
    public MapLocation generateTargetLauncher(MapLocation[] hqs, int nm) {
        int r = rng.nextInt(2);
        if(r == 0){
            return randomReflect(hqs[rng.nextInt(nm)]);
        }
        else {
            MapLocation m = rc.getLocation();
            while (rc.getLocation().distanceSquaredTo(m) <= 80) {
                m = new MapLocation(rng.nextInt(width), rng.nextInt(height));
            }
            return m;
        }
    }

    public void move(MapLocation[] hqs, int num) throws GameActionException {
        if (target == null) target = generateTarget(hqs, num);
        if (rc.getLocation().distanceSquaredTo(target) <= 9) {
            target = generateTarget(hqs, num);
        }
        greedyPath.move(target);
    }

    public MapLocation generateTarget(MapLocation[] hqs, int num) throws GameActionException {
        MapLocation m = rc.getLocation();
        while (rc.getLocation().distanceSquaredTo(m) <= 80 || communications.isEnemyTerritory(m)) {
            m = new MapLocation(rng.nextInt(width), rng.nextInt(height));
        }
        return m;
    }
}
