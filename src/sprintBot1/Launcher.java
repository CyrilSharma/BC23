package sprintBot1;
import java.util.HashMap;

import battlecode.common.*;

// xSquare Control flow.
// If your squad is not formed, wait.
// If you have not reached first rendevous, go there.
// If your homie moves, and you can't see his target, move with him. 
// -> this takes precedence over attack micro, 
// -> it's better to move as a group then to greedily maximize your interests.
// If you see an enemy, use attack micro.
// If you see an enemy HQ, suffocate it.
// Also does some stuff with amplifiers but I will ignore that for now.

// Notes:
// Launchers always arrive with their squad.
// In essence, HQs should place launchers near each other so the form a cluster.
// While that cluster is unfinished Launchers should just wait.
// Turn order is based on spawn order, so HQs should spawn the front of the 
// Cluster first, then the back of the cluster, so that it can move as a unit.
// Note the above logic depends on the direction the cluster is intended to go.
// Also, maybe only one robot should be sent the meetup location, and the rest should just be followers.
// That's harder to implement though, so I will save off on doing that for now.

// Moving in the same direction as those who moved may actually be kind of stupid.
// If your cluster gets broken apart, then you will never try to reform it.
// Instead, move towards the average location of the robots who moved.
// OR. employ some heuristic, select from the eight direction and choose the square which
// minimizes the distance to robots who moved. <- [Probably the better option]
// OR. minimize distance to robots period. but only if you can't see anything to attack.
// Still stupid because now you're just gonna want to sit in one spot all the time.

// Two potential targets. At first robots path towards some central location.
// In fact, robots continue to do this unless they see enemies throughout the game.
// Once they reach their destination, they head to an enemy HQ if it's available (they memorize it at the beginning)
// Also they don't just stand at HQs, they actively rotate around it, although not sure how important that is.
// Squads are only informed of one enemy HQ, and they just head to that one.

// Everything attacks outside of clouds if at all possible.
// Spam rc.canAttack() for all tiles in vision radius ig.
public class Launcher extends Robot {
    RobotInfo[] enemies;
    MapLocation myloc;
    boolean hurt = false;
    boolean rendevous = false;
    // may want to replace this with a custom implementation.
    HashMap<Integer,RobotInfo> neighbors = new HashMap<Integer,RobotInfo>();
    Direction netComradeDir;
    enum State {
        RENDEVOUS,
        ADVANCE,
        ATTACK,
    }

    public Launcher(RobotController rc) throws GameActionException {
        super(rc);
        communications.findOurHQs();
    }
    void run() throws GameActionException {
        communications.initial();
        State state = determineState();
        rc.setIndicatorString(state.toString());
        doAttack(true);
        switch (state) {
            case RENDEVOUS: rendevous(); break;
            case ADVANCE: advance(); break;
            case ATTACK: attack(); break;
        }
        doAttack(false);
        communications.last();
    }

    void doAttack(boolean attackers) throws GameActionException {
        RobotInfo r = util.getBestAttackTarget();
        if (r == null) return;
        if (attackers && !Util.isAttacker(r.type)) return;
        rc.setIndicatorString("Attacking: " + r.location);
        if (r != null && rc.canAttack(r.location)) 
            rc.attack(r.location);
    }

    State determineState() throws GameActionException {
        if (!rendevous) return State.RENDEVOUS;
        boolean comradeMoved = updateNeighbors();
        if (comradeMoved) return State.ADVANCE;
        for (RobotInfo e : enemies) {
            if (e.type != RobotType.HEADQUARTERS) return State.ATTACK;
        }
    }


    boolean updateNeighbors() throws GameActionException {
        double dx = 0;
        double dy = 0;
        int count = 0;
        RobotInfo[] robots = rc.senseNearbyRobots();
        for (RobotInfo r: robots) {
            if (neighbors.containsKey(r.ID)) {
                RobotInfo prev = neighbors.get(r.ID);
                // if you have a teammate who moved, add him to the average.
                if (r.team == rc.getTeam() && prev != null && prev.location != r.location) {
                    Direction diff = prev.location.directionTo(r.location);
                    dx += (double) diff.getDeltaX();
                    dy += (double) diff.getDeltaY();
                    count++;
                }
                neighbors.remove(r.ID);
            }
            neighbors.put(r.ID, r);
        }
        if (count == 0) return false;
        netComradeDir = Util.getClosestDirection(dx/count, dy/count);
        return true;
    }

    void rendevous() throws GameActionException {
        MapLocation r = communications.getBestRendevous();
        if (myloc.distanceSquaredTo(r) > 4) greedyPath.move(r);
        else rendevous = false;
    }

    void advance() throws GameActionException {
        Direction d = netComradeDir;
        if (rc.canMove(d)) rc.move(d);
        if (rc.canMove(d.rotateLeft())) rc.move(d.rotateLeft());
        if (rc.canMove(d.rotateRight())) rc.move(d.rotateRight());
    }

    void attack() throws GameActionException {
        boolean attacker = false;
        for (RobotInfo e: enemies) {
            if (Util.isAttacker(e.getType())) {
                attacker = true;
            }
        }
        if (!attacker) {
            RobotInfo r = util.getBestAttackTarget();
            follow(r.location);
        } else {
            maneuver();
        }
    }

    void follow(MapLocation m) throws GameActionException {
        ChaseTarget best = null;
        for (Direction dir: directions) {
            ChaseTarget cur = new ChaseTarget(dir, m);
            if (cur.isBetterThan(best)) best = cur;
        }
        if (rc.canMove(best.dir))
            rc.move(best.dir);
    }

    void maneuver() throws GameActionException {
        MicroTarget[] microtargets = new MicroTarget[9];
        for (Direction d: directions) {
            microtargets[d.ordinal()] = new MicroTarget(d);
        }

        for (RobotInfo r: rc.senseNearbyRobots()) {
            if (Clock.getBytecodesLeft() < 1000) break;
            for (Direction d: directions) {
                if (r.team == rc.getTeam()) microtargets[d.ordinal()].addAlly(r);
                else microtargets[d.ordinal()].addEnemy(r);
            }
        }

        MicroTarget best = microtargets[0];
        for (int i = 0; i < 9; i++) {
            if (microtargets[i].isBetterThan(best))
                best = microtargets[i];
        }
        if (rc.canMove(best.dir)) rc.move(best.dir);
    }

    AttackTarget getBestAttackTarget() {
        AttackTarget best = null;
        for (RobotInfo e: enemies) {
            AttackTarget cur = new AttackTarget(e);
            if (cur.isBetterThan(best)) best = cur;
        }
        return best;
    }

    // Choose best candidate for maneuvering in close encounters.
    class MicroTarget {
        Direction dir;
        double net_dps = 0;
        double dps_targetting = 0;
        double dps_defending = 0;
        int minDistToEnemy = 100000;
        boolean canMove;
        MapLocation nloc;

        MicroTarget(Direction dir) throws GameActionException {
            this.dir = dir;
            net_dps -= ((double) rc.getType().damage) * (1.0 / rc.senseCooldownMultiplier(myloc));
            nloc = myloc.add(dir);
            canMove = rc.canMove(dir);
            if (canMove) {
                MapInfo mi = rc.senseMapInfo(nloc);
                nloc.add(mi.getCurrentDirection());
            }
        }
        
        void addEnemy(RobotInfo r) throws GameActionException {
            if (!Util.isAttacker(r.type)) return;
            MapLocation m = r.location;
            MapInfo mi = rc.senseMapInfo(m);
            // ignore boosting effects of other units for now.
            double cooldown = mi.getCooldownMultiplier(rc.getTeam().opponent());
            if (r.type == RobotType.BOOSTER) {
                int d = nloc.distanceSquaredTo(m);
                if (d <= r.type.actionRadiusSquared) 
                    net_dps += (double) r.type.damage * (1.0 / cooldown);
                if (d <= r.type.visionRadiusSquared)
                    dps_targetting += (double) r.type.damage * (1.0 / cooldown);;
                if (d <= minDistToEnemy)
                    minDistToEnemy = d;
            }
        }
        
        void addAlly(RobotInfo r) throws GameActionException {
            if (Util.isAttacker(r.type)) {
                MapInfo mi = rc.senseMapInfo(r.location);
                double cooldown = mi.getCooldownMultiplier(rc.getTeam());
                dps_defending += (double) r.type.damage * (1.0 / cooldown);
            }
        }
       
        int safe() {
            if (net_dps > 0) return 1;
            if (dps_defending < dps_targetting) return 2;
            return 3;
        }

        boolean isBetterThan(MicroTarget mt) {
            if (mt.canMove && !canMove) return false;
            if (!mt.canMove && canMove) return true;
            
            // the idea here is attack first, then move out of range.
            if (mt.safe() > safe()) return false;
            if (mt.safe() < safe()) return true;

            // If hurt move to where enemies are targetting the least.
            if (hurt) {
                if (mt.dps_targetting < dps_targetting) return false;
                if (mt.dps_targetting > dps_targetting) return true;
                // run away!!!!
                return minDistToEnemy >= mt.minDistToEnemy;
            }

            // get as far away from enemies while still being in range.
            if (minDistToEnemy <= rc.getType().actionRadiusSquared)
                return minDistToEnemy >= mt.minDistToEnemy;
            else
                return minDistToEnemy <= mt.minDistToEnemy;
        }
    }
    // Choose best candidate to attack.
    class AttackTarget {
        MapLocation loc;
        int priority;
        int health;

        AttackTarget(RobotInfo r) {
            loc = r.location;
            switch (r.getType()) {
                case BOOSTER: priority=4; break;
                case AMPLIFIER: priority=3; break;
                case HEADQUARTERS: priority=1; break;
                case CARRIER: priority=2; break;
                case LAUNCHER: priority=6; break;
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

    // Choose best square to chase a defenseless target.
    class ChaseTarget {
        int d;
        Direction dir;
        boolean canMove;

        ChaseTarget(Direction dir, MapLocation m) {
            this.dir = dir;
            MapLocation newloc = myloc.add(dir);
            d = newloc.distanceSquaredTo(m);
            canMove = rc.canMove(dir);
        }

        boolean isBetterThan(ChaseTarget ct) {
            if (ct == null) return true;
            if (ct.canMove && !canMove) return false;
            if (!ct.canMove && canMove) return true;
            // if sufficently close, do not move closer.
            if (d <= rc.getType().actionRadiusSquared / 2) return true;
            return d <= ct.d;
        }
    }
}
