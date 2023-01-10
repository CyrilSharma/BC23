package soldier_micro;
import battlecode.common.*;

public class Booster extends Robot {
    enum State {
        ATTACK,
        EXPLORE
    }

    public Booster(RobotController rc) {
        super(rc);
    }
    void run() {
        State state = determineState();
        switch (state) {
            case ATTACK:
                attack();
                break;
            case EXPLORE:
                explore();
                break;
        }
    }

    State determineState() {
        RobotInfo[] enemies = rc.senseNearbyRobots();
        if (enemies.length > 0) return State.ATTACK;
        return State.EXPLORE;
    }

    void attack() {
        ;
    }

    void explore() {
        ;
    }

    AttackTarget getBestTarget() {
        RobotInfo[] enemies = rc.senseNearbyRobots();
        AttackTarget best = null;
        for (RobotInfo e: enemies) {
            AttackTarget cur = new AttackTarget(e);
            if (cur.isBetterThan(best)) best = cur;
        }
        return best;
    }

    class AttackTarget {
        MapLocation loc;
        int priority;
        int health;

        AttackTarget(RobotInfo r) {
            loc = r.location;
            switch (r.getType()) {
                case BOOSTER: priority=6; break;
                case AMPLIFIER: priority=3; break;
                case HEADQUARTERS: priority=1; break;
                case CARRIER: priority=2; break;
                case LAUNCHER: priority=4; break;
                case DESTABILIZER: priority=5; break;
            }
            health = r.health;
        }

        boolean isBetterThan(AttackTarget at) {
            if (at == null) return true;
            if (at.priority > priority) return false;
            if (at.priority < priority) return true;
            return at.health <= health;
        }
    }
}
