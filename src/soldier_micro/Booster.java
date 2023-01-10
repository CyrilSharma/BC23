package soldier_micro;
import battlecode.common.*;

public class Booster extends Robot {
    RobotInfo[] enemies;
    enum State {
        ATTACK,
        EXPLORE
    }

    public Booster(RobotController rc) {
        super(rc);
    }
    void run() throws GameActionException {
        initialize();
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

    void initialize() throws GameActionException {
        enemies = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());
    }

    State determineState() {
        if (enemies.length > 0) return State.ATTACK;
        return State.EXPLORE;
    }

    void attack() {
        ;
    }

    void explore() {
        ;
    }

    MapLocation getTarget() {
        boolean attacker = false;
        for (RobotInfo e: enemies) {
            if (Util.isAttacker(e.getType())) {
                attacker = true;
            }
        }
        if (!attacker) {
            AttackTarget at = getBestAttackTarget();
            follow(at.loc);
        } else {
            maneuver();
            // attack best thing.
        }

    }

    AttackTarget getBestAttackTarget() {
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
            return health <= at.health;
        }
    }
}
