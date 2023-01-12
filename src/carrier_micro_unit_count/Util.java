package carrier_micro_unit_count;
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
        RobotInfo r;

        AttackTarget(RobotInfo r) {
            loc = r.location;
            priority = Util.getPriority(r);
            health = r.health;
            this.r = r;
        }

        boolean isBetterThan(AttackTarget at) {
            if (at == null) return true;
            if (at.priority > priority) return false;
            if (at.priority < priority) return true;
            return health <= at.health;
        }
    }
}
