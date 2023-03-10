package jan18;
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
        AttackTarget best = null;
        for (RobotInfo e: rc.senseNearbyRobots(-1, rc.getTeam().opponent())) {
            AttackTarget cur = new AttackTarget(e);
            if (cur.isBetterThan(best)) best = cur;
        }
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
        int d;

        AttackTarget(RobotInfo r) {
            loc = r.location;
            priority = Util.getPriority(r);
            health = r.health;
            this.r = r;
            canAttack = rc.canAttack(loc);
            // allows us to agree on who to attack.
            d = r.location.distanceSquaredTo(new MapLocation(0, 0));
        }

        boolean isBetterThan(AttackTarget at) {
            if (at == null) return true;
            if (at.canAttack && !canAttack) return false;
            if (!at.canAttack && canAttack) return true;
            if (at.priority > priority) return false;
            if (at.priority < priority) return true;
            if (at.health < health) return false;
            if (at.health > health) return true;
            return d <= at.d;
        }
    }
}
