package sprintBot1;
import java.util.PriorityQueue;

import battlecode.common.*;

public class Util {
    RobotController rc;
    public Util(RobotController rc) {
        this.rc = rc;
    }
    
    public static boolean isAttacker(RobotType t) {
        switch (t) {
            case HEADQUARTERS:
            case CARRIER:
            case AMPLIFIER:
                return false;
            default:
        }
        return true;
    }

    public static double absDistance(MapLocation a, MapLocation b) {
        return Math.sqrt(Math.pow((a.x - b.x),2) + Math.pow((a.y - b.y),2));
    }

    public static Direction getClosestDirection(double dx, double dy) {
        double absx = Math.abs(dx);
        double absy = Math.abs(dy);
        if (absx < 0.5 && absy < 0.5) return Direction.CENTER;
        if (absx > absy) {
            double half = absx * 0.4142;
            if (dx > 0) {
                if (dy > half) return Direction.NORTHEAST;
                if (dy < -half) return Direction.SOUTHEAST;
                return Direction.EAST;
            } else {
                if (dy > half) return Direction.NORTHWEST;
                if (dy < -half) return Direction.SOUTHWEST;
                return Direction.WEST;
            }
        } else {
            double half = absy * 0.4142;
            if (dy > 0) {
                if (dx > half) return Direction.NORTHEAST;
                if (dx < -half) return Direction.NORTHWEST;
                return Direction.NORTH;
            } else {
                if (dx > half) return Direction.SOUTHEAST;
                if (dx < -half) return Direction.SOUTHWEST;
                return Direction.SOUTH;
            }
        }
    }

    public static int getPriority(RobotInfo r) {
        int priority;
        switch (r.getType()) {
            case BOOSTER: priority=4; break;
            case AMPLIFIER: priority=3; break;
            case HEADQUARTERS: priority=1; break;
            case CARRIER: priority=2; break;
            case LAUNCHER: priority=6; break;
            case DESTABILIZER: priority=5; break;
            default: priority=7;
        }
        return priority;
    }

    RobotInfo getBestAttackTarget() throws GameActionException {
        RobotInfo[] friends = rc.senseNearbyRobots(-1, rc.getTeam());
        RobotInfo[] enemies = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        AttackTarget[] targets = new AttackTarget[enemies.length];
        int ind = 0;
        for (RobotInfo e: enemies) {
            targets[ind++] = new AttackTarget(e);
        }
        /* for (RobotInfo f: friends) {
            if (Clock.getBytecodesLeft()<8000)
            for (AttackTarget t: targets)
                t.updateAlly(f);
        } */
        AttackTarget best = null;
        for (AttackTarget t: targets) 
            if (t.isBetterThan(best)) best = t;
        if (best == null) return null;
        return best.r;
    }

    // Choose best candidate to attack.
    class AttackTarget {
        MapLocation loc;
        int priority;
        int health;
        boolean canAttack;
        RobotInfo r;
        int soldiersAttacking = 0;
        int d;

        AttackTarget(RobotInfo r) throws GameActionException {
            loc = r.location;
            priority = Util.getPriority(r);
            health = r.health;
            this.r = r;
            canAttack = rc.canSenseLocation(loc) && rc.canAttack(loc) && rc.isLocationOccupied(loc)
                && r.type != RobotType.HEADQUARTERS;
            // allows us to agree on who to attack.
            d = r.location.distanceSquaredTo(new MapLocation(0, 0));
        }

        void updateAlly(RobotInfo f) throws GameActionException {
            if (!canAttack) return;
            if (f.type == RobotType.LAUNCHER) {
                if (f.location.distanceSquaredTo(loc) <= RobotType.LAUNCHER.visionRadiusSquared)
                    soldiersAttacking++;
            }
        }

        boolean isBetterThan(AttackTarget at) {
            if (at == null) return true;
            if (at.canAttack && !canAttack) return false;
            if (!at.canAttack && canAttack) return true;
            if (at.priority > priority) return false;
            if (at.priority < priority) return true;
            if (at.health < health) return false;
            if (health < at.health) return true;
            //if (at.soldiersAttacking > soldiersAttacking) return false;
            //if (at.soldiersAttacking < soldiersAttacking) return true;
            return d <= at.d;
        }
    }
}
