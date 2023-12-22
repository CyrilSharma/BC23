package optimizedBot;
import battlecode.common.*;

public class Launcher extends Robot {
    boolean hurt = false;
    MapLocation huntTarget;
    MapLocation[] killedHQs = new MapLocation[5];
    int advanceTurns = 0;
    NeighborTracker nt;
    RobotInfo[] enemiesSeenBeforeClouds;

    enum State {
        ADVANCE,
        ATTACK,
        HUNT,
        HUNT_HQ,
        WAIT
    }

    public Launcher(RobotController rc) throws GameActionException {
        super(rc);
        communications.findOurHQs();
        nt = new NeighborTracker(rc);
    }

    void run() throws GameActionException {
        hurt = rc.getHealth() < (RobotType.LAUNCHER.health / 3);
        if (!rc.senseCloud(rc.getLocation())) {
            enemiesSeenBeforeClouds = rc.senseNearbyRobots(
                -1, rc.getTeam().opponent()
            );
        }
        communications.initial();
        nt.updateNeighbors();
        State state = determineState();
        rc.setIndicatorString(state.toString());
        while (doAttack(true)) {}
        switch (state) {
            case ADVANCE: advance(); break;
            case ATTACK: attack(); break;
            case HUNT: hunt(); break;
            case HUNT_HQ: hunt_hq(); break;
            case WAIT: break;
        }
        while (doAttack(false)) {}
        communications.last();
    }
 
    boolean doAttack(boolean attackers) throws GameActionException {
        if (rc.getActionCooldownTurns() >= 10) return false;
        if (rc.senseCloud(rc.getLocation()) && !attackers) {
            RobotInfo r = findAttackTarget();
            if ((r != null) && (rc.canAttack(r.location))) {
                rc.attack(r.location);
                return true;
            }
            return false;
        }
        RobotInfo r = util.getBestAttackTarget();
        if (r == null && attackers) return false;
        else if (r == null && !attackers) {
            MapLocation[] clouds = rc.senseNearbyCloudLocations(RobotType.LAUNCHER.actionRadiusSquared);
            if (clouds.length == 0) return false;
            MapLocation loc = clouds[rng.nextInt(clouds.length)];
            rc.attack(loc);
            return true;
        } else if (attackers && !Util.isAttacker(r.type)) return false;
        else if (rc.canAttack(r.location) && r.type != RobotType.HEADQUARTERS) {
            rc.attack(r.location);
            return true;
        }
        return false;
    }

    RobotInfo findAttackTarget() throws GameActionException {
        RobotInfo best = null;
        RobotInfo[] visible = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        if (best == null && visible.length > 0) best = visible[0];
        for (int i = visible.length; i-- > 0;) {
            RobotInfo r = visible[i];
            if ((best.health > r.health)) {
                best = r;
            }
        }
        if (best != null) return best;
        if (enemiesSeenBeforeClouds == null) return null;
        if (enemiesSeenBeforeClouds.length != 0) {
            best = enemiesSeenBeforeClouds[0];
            for (int i = enemiesSeenBeforeClouds.length; i-- > 0;) {
                RobotInfo r = enemiesSeenBeforeClouds[i];
                if ((best.health > r.health)) {
                    best = r;
                }
            }
            enemiesSeenBeforeClouds = null;
        }
        return best;
    }

 
    State determineState() throws GameActionException {
        advanceTurns--;
        if (rc.getRoundNum() % 2 == 0) return State.WAIT;
        if ((nt.hasLaunchersNear || nt.hasCarriersNear)) {
            advanceTurns = 5;
            return State.ATTACK;
        }

        MapLocation target = communications.findBestAttackTarget();
        if (target != null) {
            int d = rc.getLocation().distanceSquaredTo(target);
            if (d <= 64) {
                advanceTurns = 5;
                huntTarget = target;
                return State.HUNT;
            }
        }

        // advance if you recently encountered a threat.
        if (advanceTurns > 0) {
            return State.ADVANCE;
        }

        return State.HUNT_HQ;
    }

    void hunt_hq() throws GameActionException {
        MapLocation hqTarget = communications.getClosestEnemyHQ(killedHQs);
        if (hqTarget == null) hqTarget = communications.getClosestEnemyHQ();
        // no nullpointers in theory?
        rc.setIndicatorString("HUNT HQ: "+hqTarget);
        hunt_hq(hqTarget);
        for (RobotInfo r: rc.senseNearbyRobots(-1, rc.getTeam().opponent())) {
            if (r.type == RobotType.HEADQUARTERS) {
                hunt_hq(r.location);
                return;
            }
        }
    }

    void hunt_hq(MapLocation m) throws GameActionException {
        int rad = RobotType.HEADQUARTERS.actionRadiusSquared;
        if (m.distanceSquaredTo(rc.getLocation()) <= rad) {
            int bestD = 100000;
            MapLocation best = null;
            for (MapLocation a: rc.getAllLocationsWithinRadiusSquared(rc.getLocation(), -1)) {
                if (a.distanceSquaredTo(m) <= rad) continue;
                if (rc.canSenseLocation(a) && rc.isLocationOccupied(a)) continue;
                int curD = a.distanceSquaredTo(rc.getLocation());
                if (curD < bestD) {
                    bestD = curD;
                    best = a;
                }
            }
            if (best != null) greedyPath.move(best);
            return;
        } else if (m.distanceSquaredTo(rc.getLocation()) <= RobotType.LAUNCHER.visionRadiusSquared) {
            int enemyLaunchers = 0;
            int friendLaunchers = 0;
            for (RobotInfo r: rc.senseNearbyRobots()) {
                if (r.type != RobotType.LAUNCHER) continue;
                if (rc.getTeam() == r.team) {
                    friendLaunchers++;
                }
                else enemyLaunchers++;
            }
            if (friendLaunchers > 2 && enemyLaunchers == 0) {
                boolean exists = false;
                int empty = -1;
                for (int i = 0; i < communications.numHQ; i++) {
                    if (killedHQs[i] == null) {
                        empty = i;
                    } else if (killedHQs[i].equals(m)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists && empty != -1) {
                    killedHQs[empty] = m;
                }
            }
        }
        if (m.distanceSquaredTo(rc.getLocation()) > rad + 10)
            greedyPath.move(m);
    }

    void hunt() throws GameActionException {
        greedyPath.move(huntTarget);
    }

    void advance() throws GameActionException {
        Direction dir = nt.advance();
        rc.setIndicatorString("Advancing: " + dir);
        if (dir != null) rc.move(dir);
    }

    void attack() throws GameActionException {
        if (nt.hasCarriersNear && !nt.hasLaunchersNear) {
            RobotInfo r = util.getBestAttackTarget();
            if (r == null) return;
            follow(r.location);
        } else {
            maneuver();
        }
    }

    void follow(MapLocation m) throws GameActionException {
        ChaseTarget best = new ChaseTarget(directions[0], m);
        for (Direction dir: directions) {
            ChaseTarget cur = new ChaseTarget(dir, m);
            if (cur.isBetterThan(best)) best = cur;
        }
        if (rc.canMove(best.dir)) rc.move(best.dir);
    }

    boolean canAttack = false;
    void maneuver() throws GameActionException {
        rc.setIndicatorString("Maneuvering");

        canAttack = rc.isActionReady();

        // Needs 1k Bytecode.
        MicroTarget[] microtargets = new MicroTarget[9];
        microtargets[0] = new MicroTarget(directions[0]);
        microtargets[1] = new MicroTarget(directions[1]);
        microtargets[2] = new MicroTarget(directions[2]);
        microtargets[3] = new MicroTarget(directions[3]);
        microtargets[4] = new MicroTarget(directions[4]);
        microtargets[5] = new MicroTarget(directions[5]);
        microtargets[6] = new MicroTarget(directions[6]);
        microtargets[7] = new MicroTarget(directions[7]);
        microtargets[8] = new MicroTarget(directions[8]);

        int iters = 0;
        Team myTeam = rc.getTeam();
        RobotInfo[] robots = rc.senseNearbyRobots(-1, myTeam);
        for (int i = robots.length; i-- > 0;) {
            if (Clock.getBytecodesLeft() < 1500) break;
            RobotInfo r = robots[i];
            if (r.type != RobotType.LAUNCHER) continue; 
            microtargets[0].addAlly(r);
            microtargets[1].addAlly(r);
            microtargets[2].addAlly(r);
            microtargets[3].addAlly(r);
            microtargets[4].addAlly(r);
            microtargets[5].addAlly(r);
            microtargets[6].addAlly(r);
            microtargets[7].addAlly(r);
            microtargets[8].addAlly(r);
            iters++;
        }

        robots = rc.senseNearbyRobots(-1, myTeam.opponent());
        for (int i = robots.length; i-- > 0;) {
            if (Clock.getBytecodesLeft() < 1500) break;
            RobotInfo r = robots[i];
            if (r.type != RobotType.LAUNCHER) continue;
            microtargets[0].addEnemy(r);
            microtargets[1].addEnemy(r);
            microtargets[2].addEnemy(r);
            microtargets[3].addEnemy(r);
            microtargets[4].addEnemy(r);
            microtargets[5].addEnemy(r);
            microtargets[6].addEnemy(r);
            microtargets[7].addEnemy(r);
            microtargets[8].addEnemy(r);
            iters++;
        }

        // Needs 1k Bytecode.
        MicroTarget best = microtargets[0];
        if (microtargets[1].isBetterThan(best)) best = microtargets[1];
        if (microtargets[2].isBetterThan(best)) best = microtargets[2];
        if (microtargets[3].isBetterThan(best)) best = microtargets[3];
        if (microtargets[4].isBetterThan(best)) best = microtargets[4];
        if (microtargets[5].isBetterThan(best)) best = microtargets[5];
        if (microtargets[6].isBetterThan(best)) best = microtargets[6];
        if (microtargets[7].isBetterThan(best)) best = microtargets[7];
        if (microtargets[8].isBetterThan(best)) best = microtargets[8];
        if (rc.canMove(best.dir)) rc.move(best.dir);
        rc.setIndicatorString("ITERS: " + iters);
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

        boolean inRange() {
            return d <= RobotType.LAUNCHER.actionRadiusSquared;
        }

        boolean isBetterThan(ChaseTarget ct) {
            if (!canMove) return false;
            if (ct.inRange() && !inRange()) return false;
            if (!ct.inRange() && inRange()) return true;
            else return d <= ct.d;
        }
    }

    // Choose best candidate for maneuvering in close encounters.
    class MicroTarget {
        int minDistToEnemy = 100000;
        int minDistToAlly = 100000;
        int launchersAttackRange = 0;
        int launchersVisionRange = 0;
        boolean canMove;
        boolean cloud;
        int canLandHit;
        MapLocation nloc;
        Direction dir;

        MicroTarget(Direction dir) throws GameActionException {
            nloc = rc.getLocation().add(dir);
            canMove = rc.canMove(dir);
            cloud = !rc.canSenseLocation(nloc);
            this.dir = dir;
        }
        
        void addEnemy(RobotInfo r) throws GameActionException {
            int dist = r.location.distanceSquaredTo(nloc);
            if (dist < minDistToEnemy) minDistToEnemy = dist;
            if (cloud){
                if (dist <= GameConstants.CLOUD_VISION_RADIUS_SQUARED){
                    launchersAttackRange++;
                    launchersVisionRange++;
                }
            } else {
                if (dist <= RobotType.LAUNCHER.actionRadiusSquared) launchersAttackRange++;
                if (dist <= RobotType.LAUNCHER.visionRadiusSquared) launchersVisionRange++;
            }
            if (dist <= RobotType.LAUNCHER.actionRadiusSquared && canAttack){
                canLandHit = 1;
            }
        } 

        
        void addAlly(RobotInfo r) throws GameActionException {
            if (!canMove) return;
            int d = nloc.distanceSquaredTo(r.location);
            if (d < minDistToAlly) minDistToAlly = d;
        }

        boolean inRange() {
            return minDistToEnemy <= RobotType.LAUNCHER.actionRadiusSquared;
        }

        boolean isBetterThan(MicroTarget mt) {
            if (!canMove) return false;
            if (cloud && !mt.cloud) return false;
            if (!cloud && mt.cloud) return true;

            if (launchersAttackRange - canLandHit < mt.launchersAttackRange - mt.canLandHit) return true;
            if (launchersAttackRange - canLandHit > mt.launchersAttackRange - mt.canLandHit) return false;

            if (launchersVisionRange - canLandHit < mt.launchersVisionRange - mt.canLandHit) return true;
            if (launchersVisionRange - canLandHit > mt.launchersVisionRange - mt.canLandHit) return false;
            
            if (canLandHit > mt.canLandHit) return true;
            if (canLandHit < mt.canLandHit) return false;

            if (minDistToAlly < mt.minDistToAlly) return true;
            if (minDistToAlly > mt.minDistToAlly) return false;

            if (mt.inRange()) return minDistToEnemy >= mt.minDistToEnemy;
            else return minDistToEnemy <= mt.minDistToEnemy;
        }
    }
}
