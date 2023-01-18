package jan18;
import java.util.HashMap;
import java.util.HashSet;

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
    boolean hurt = false;
    boolean rendevous = false;
    int prevEnemyRound = -1;
    MapLocation previousEnemy = null;
    MapLocation previousPos = null;
    boolean okToStray;
    // may want to replace this with a custom implementation.
    HashMap<Integer,RobotInfo> neighbors = new HashMap<Integer,RobotInfo>();
    Direction bestNeighborDir;
    private MapLocation enemyHQLoc;
    enum State {
        WAIT,
        RENDEVOUS,
        ADVANCE,
        CHASE,
        ATTACK,
        IMPROVE_VISION,
        SUFFOCATE
    }

    public Launcher(RobotController rc) throws GameActionException {
        super(rc);
        communications.findOurHQs();
    }
    void run() throws GameActionException {
        okToStray = rc.getRoundNum() > (rc.getMapHeight() * rc.getMapWidth())/10;
        if (rc.getHealth() < 12) hurt = true;
        if (rc.getRoundNum()%5 == prevEnemyRound) previousEnemy = null;
        communications.initial();
        if (rc.getRoundNum()%2 == 1) updateNeighbors();
        State state = determineState();
        rc.setIndicatorString(state.toString());
        doAttack(true);
        switch (state) {
            case WAIT: break;
            case RENDEVOUS: rendevous(); break;
            case CHASE: chase(); break;
            case ADVANCE: advance(); break;
            case ATTACK: attack(); break;
            case IMPROVE_VISION: improve_vision(); break;
            case SUFFOCATE: suffocate();
        }
        doAttack(false);
        updateEnemy();
        communications.last();
        previousPos = rc.getLocation();
    }

    void updateEnemy() throws GameActionException {
        int d = -1;
        MapLocation best = null;
        for (RobotInfo e: rc.senseNearbyRobots(-1, rc.getTeam().opponent())) {
            if (e.type != RobotType.HEADQUARTERS) {
                int cur = rc.getLocation().distanceSquaredTo(e.location);
                if (cur > d) {
                    d = cur;
                    best = e.location;
                }
            }
        }
        if (best != null) {
            previousEnemy = best;
            prevEnemyRound = rc.getRoundNum()%5;
        }
    }

    void suffocate() throws GameActionException {
        greedyPath.move(enemyHQLoc);
    }

    void improve_vision() throws GameActionException {
        MapLocation m = communications.findClosestHQ();
        int dist= -1;
        Direction bestDir = Direction.CENTER;
        for (Direction d: directions) {
            if (rc.getLocation().add(d).distanceSquaredTo(m) > dist
                && rc.canMove(d)) {
                dist = rc.getLocation().add(d).distanceSquaredTo(m);
                bestDir = d;
            }
        }
        if (rc.canMove(bestDir)) rc.move(bestDir);
    }
 
    void doAttack(boolean attackers) throws GameActionException {
        RobotInfo r = util.getBestAttackTarget();
        if (r == null) return;
        if (attackers && !Util.isAttacker(r.type)) return;
        if (r != null && rc.canAttack(r.location)) 
            rc.attack(r.location);
    }

    State determineState() throws GameActionException {
        if (rc.getRoundNum()%2 == 0) return State.WAIT;

        boolean hasHq = false;
        boolean hasEnemy = false;
        for (RobotInfo e : rc.senseNearbyRobots(-1, rc.getTeam().opponent())) {
            if (e.type != RobotType.HEADQUARTERS) hasEnemy = true;
            else {
                hasHq = true;
                enemyHQLoc = e.location;
            }
        }

        // waiting for squad.
        if (rc.getRoundNum() <= 7) return State.WAIT;
        // initially, only rendevous until you encounter an enemy.
        if (!rendevous && !hasEnemy && previousEnemy == null &&
            (bestNeighborDir == Direction.CENTER || okToStray))
            return State.RENDEVOUS;

        if (bestNeighborDir != Direction.CENTER) return State.ADVANCE;
        if (hasEnemy) return State.ATTACK;
        // chase enemy only if you're not already chasing one with the group.
        if (previousEnemy != null) return State.CHASE;
        // if (hasHq) return State.SUFFOCATE; // ignore for now.
        MapInfo mi = rc.senseMapInfo(rc.getLocation());
        if (mi.hasCloud()) return State.IMPROVE_VISION;
        if (hasHq) return State.SUFFOCATE;
        return State.WAIT;
    }

    void updateNeighbors() throws GameActionException {
        ClusterTarget[] targets = new ClusterTarget[9];
        for (Direction d: directions) {
            targets[d.ordinal()] = new ClusterTarget(d);
        }

        int count = 0;
        // only act on local movement.
        RobotInfo[] robots = rc.senseNearbyRobots(16, rc.getTeam());
        for (RobotInfo r: robots) {
            if (Clock.getBytecodesLeft() < 5000) break;
            if (neighbors.containsKey(r.ID)) {
                RobotInfo prev = neighbors.get(r.ID);
                // if a unit moved away from me, follow him.
                if (prev != null && prev.location != r.location
                    && r.type == RobotType.LAUNCHER && 
                    r.location.distanceSquaredTo(rc.getLocation()) >
                    prev.location.distanceSquaredTo(rc.getLocation())) {
                    for (ClusterTarget t: targets) t.updateAlly(r);
                    count++;
                }
                neighbors.remove(r.ID);
            }
            neighbors.put(r.ID, r);
        }
        if (count == 0) {
            bestNeighborDir = Direction.CENTER;
            return;
        }
        ClusterTarget best = null;
        for (Direction d: directions) {
            if (targets[d.ordinal()].isBetterThan(best))
                best = targets[d.ordinal()];
        }
        bestNeighborDir = best.dir;
    }

    void rendevous() throws GameActionException {
        MapLocation r = communications.getBestRendevous();
        greedyPath.move(r);
    }

    void advance() throws GameActionException {
        //rc.setIndicatorString("" + bestNeighborDir);
        Direction d = bestNeighborDir;
        if (d != Direction.CENTER && rc.canMove(d) && rc.getRoundNum()%2==1) 
            rc.move(d);
    }

    void chase() throws GameActionException {
        if (rc.getLocation().distanceSquaredTo(previousEnemy) > 4)
            greedyPath.move(previousEnemy);
        else if (rc.canSenseLocation(previousEnemy)) {
            RobotInfo r = rc.senseRobotAtLocation(previousEnemy);
            if (r == null) previousEnemy = null;
        }
    }   

    void attack() throws GameActionException {
        boolean attacker = false;
        for (RobotInfo e: rc.senseNearbyRobots(-1, rc.getTeam().opponent())) {
            if (Util.isAttacker(e.getType())) {
                attacker = true;
            }
        }
        if (!attacker) {
            RobotInfo r = util.getBestAttackTarget();
            if (r == null) return;
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
        rc.setIndicatorString("Maneuvering");
        MicroTarget[] microtargets = new MicroTarget[9];
        for (Direction d: directions) {
            microtargets[d.ordinal()] = new MicroTarget(d);
        }

        for (RobotInfo r: rc.senseNearbyRobots()) {
            if (Clock.getBytecodesLeft() < 5000) break;
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

    class ClusterTarget {
        int sumD = 0;
        boolean canMove;
        boolean needsMove;
        MapLocation m;
        Direction dir;
        ClusterTarget(Direction dir) throws GameActionException {
            this.dir = dir;
            m = rc.getLocation().add(dir);
            if (rc.canSenseLocation(m)) {
                MapInfo mi = rc.senseMapInfo(m);
                m = m.add(mi.getCurrentDirection());
            }
            canMove = rc.canMove(dir);
            needsMove = dir != Direction.CENTER;
        }
        void updateAlly(RobotInfo r) {
            sumD += m.distanceSquaredTo(r.location);
        }
        boolean isBetterThan(ClusterTarget ct) {
            if (ct == null) return true;
            if (ct.canMove && !canMove) return false;
            if (!ct.canMove && canMove) return true;
            if (ct.sumD < sumD) return false;
            if (ct.sumD > sumD) return true;
            // moving conveys information, so avoid moving if unnecessary.
            if (!ct.needsMove && needsMove) return false;
            if (ct.needsMove && !needsMove) return true;
            return sumD <= ct.sumD;
        }
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
            net_dps -= ((double) rc.getType().damage) * (1.0 / rc.senseCooldownMultiplier(rc.getLocation()));
            nloc = rc.getLocation().add(dir);
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

            if (r.type == RobotType.LAUNCHER) {
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
            if (minDistToEnemy >= rc.getType().actionRadiusSquared &&
                mt.minDistToEnemy >= rc.getType().actionRadiusSquared)
                return minDistToEnemy <= mt.minDistToEnemy;
            if (minDistToEnemy <= rc.getType().actionRadiusSquared &&
                mt.minDistToEnemy <= rc.getType().actionRadiusSquared)
                return minDistToEnemy >= mt.minDistToEnemy;
            return minDistToEnemy <= mt.minDistToEnemy;
        }
    }

    // Choose best square to chase a defenseless target.
    class ChaseTarget {
        int d;
        Direction dir;
        boolean canMove;

        ChaseTarget(Direction dir, MapLocation m) {
            this.dir = dir;
            MapLocation newloc = rc.getLocation().add(dir);
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
