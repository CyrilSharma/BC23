package sprintBot1;
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
    int born;
    int fleeTurns = 0;
    boolean hurt = false;
    int prevEnemyRound = -1;
    MapLocation previousEnemy = null;
    MapLocation previousPos = null;
    MapLocation enemyHQ = null;
    Direction bestNeighborDir;
    boolean okToStray;
    boolean friendsMoved = false;
    boolean shouldRendevous = true;
    boolean shouldHarass = false;
    MapLocation rendevous;
    // may want to replace this with a custom implementation.
    private MapLocation enemyHQLoc;
    private MapLocation huntTarget;
    private MapLocation islandTarget;
    MapLocation harassTarget = null;
    int harassTimer = 0;
    int harassDir = -1;
    MapLocation[] harassLoc = {new MapLocation(0, 0), new MapLocation(1, 0), 
            new MapLocation(1, 1), new MapLocation(0, 1)};
    enum State {
        WAIT,
        RENDEVOUS,
        ADVANCE,
        CHASE,
        ATTACK,
        IMPROVE_VISION,
        HUNT,
        HUNT_HQ,
        HEAL,
        HARASS
    }

    public Launcher(RobotController rc) throws GameActionException {
        super(rc);
        communications.findOurHQs();
        born = rc.getRoundNum();
        rendevous = communications.getBestRendevous();
        if(rng.nextInt(5) == 0 && rc.getRoundNum() >= 150){
            shouldHarass = true;
            harassDir = rng.nextInt(2);
        }
    }
    void run() throws GameActionException {
        islandTarget = null;
        huntTarget = null;
        friendsMoved = false;
        hurt = rc.getHealth() < 100;
        // if (hurt) findCloseIsland();
        if (rc.getRoundNum()%5 == prevEnemyRound) previousEnemy = null;
        
        communications.initial();
        if (rc.getRoundNum()%3 != 1) updateNeighbors();
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
            case HUNT: hunt(); break;
            case HUNT_HQ: hunt_hq(); break;
            case HEAL: heal(); break;
            case HARASS: harass(); break;
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

    void improve_vision() throws GameActionException {
        MapLocation m = communications.findClosestHQ();
        int bestD = 1000000;
        Direction bestDir = Direction.CENTER;
        for (Direction d: directions) {
            int dist = rc.getLocation().add(d).distanceSquaredTo(m);
            if (dist < bestD && rc.canMove(d)) {
                bestD = dist;
                bestDir = d;
            }
        }
        if (rc.canMove(bestDir)) rc.move(bestDir);
    }
 
    void doAttack(boolean attackers) throws GameActionException {
        RobotInfo r = util.getBestAttackTarget();
        if (r == null && attackers) return;
        else if (r == null && attackers==false) {
            MapLocation[] clouds = rc.senseNearbyCloudLocations(RobotType.LAUNCHER.actionRadiusSquared);
            if (clouds.length == 0) return;
            MapLocation loc = null;
            for (int i = 0; (loc == null || rc.canSenseLocation(loc)) && i < 5; i++)
                loc = clouds[rng.nextInt(clouds.length)];
            if (loc != null && rc.canAttack(loc)) rc.attack(loc);
            return;
        }
        if (attackers && !Util.isAttacker(r.type)) return;
        if (rc.canAttack(r.location) && r.type != RobotType.HEADQUARTERS) {
            while (rc.canAttack(r.location)) rc.attack(r.location);
        }
    }

    State determineState() throws GameActionException {
        //if (shouldHarass) return State.HARASS;
        if (rc.getRoundNum()%3 == 1) return State.WAIT;

        boolean seesHQ = false;
        boolean hasEnemy = false;
        for (RobotInfo e : rc.senseNearbyRobots(-1, rc.getTeam().opponent())) {
            if (e.type != RobotType.HEADQUARTERS) hasEnemy = true;
            if (e.type == RobotType.HEADQUARTERS) {
                enemyHQ = e.location;
                seesHQ = true;
            }
        }

        int numCarriers = 0;
        for (RobotInfo f : rc.senseNearbyRobots(-1, rc.getTeam())) {
            if (f.type == RobotType.CARRIER) numCarriers++;
        }
        // Conditions!!
        MapLocation target = communications.findBestAttackTarget();
        // rc.setIndicatorString(""+target);
        // /rc.setIndicatorString(""+target);
        boolean hasTargetClose = false;
        boolean hasTargetFar = false;
        if (target != null) {
            int d = rc.getLocation().distanceSquaredTo(target);
            hasTargetClose = (d <= 64);
            hasTargetFar = (d > 64);
            if (enemyHQ != null) {
                hasTargetClose = hasTargetClose & (enemyHQ.distanceSquaredTo(target) > RobotType.HEADQUARTERS.actionRadiusSquared);
            }
        }
        boolean knowsSymmetry =  (communications.symmetryChecker.getSymmetry() != -1);
        boolean hasIslandTarget = islandTarget != null;
        MapInfo mi = rc.senseMapInfo(rc.getLocation());
        int exploreTurns = (rc.getMapHeight()+rc.getMapWidth())/6;
        // until we stop them from crashing into carriers.
        // if (rc.getRoundNum() <= 3) return State.WAIT;
        if (hasEnemy) return State.ATTACK;
        if (hasTargetClose) {
            huntTarget = target;
            return State.HUNT;
        }
        if (previousEnemy != null) return State.CHASE;
        if (hasTargetFar) {
            huntTarget = target;
            return State.HUNT;
        }
        if (knowsSymmetry || communications.EnemyHQEstimates != null) return State.HUNT_HQ;
        if (rc.getRoundNum()-born<=exploreTurns || rc.getRoundNum()<=exploreTurns ||
            numCarriers > 5) return State.RENDEVOUS;
        if (hurt && islandTarget != null) return State.HEAL;
        if (mi.hasCloud()) return State.IMPROVE_VISION;
        return State.ADVANCE;
    }

    MapLocation[] neighbors = new MapLocation[100];
    StringBuilder neighborStr = new StringBuilder();
    void updateNeighbors() throws GameActionException {
        double x = 0;
        double y = 0;
        double totalW = 0;
        //HashMap<Integer,RobotInfo> nneighbors = new HashMap<Integer,RobotInfo>();
        MapLocation[] nneighbors = new MapLocation[100];
        StringBuilder nneighborStr = new StringBuilder();
        RobotInfo[] robots = rc.senseNearbyRobots(9 , rc.getTeam());
        RobotInfo[] nbrs = rc.senseNearbyRobots(4, rc.getTeam());
        for (RobotInfo r: robots) {
            //int start = Clock.getBytecodesLeft();
            if (Clock.getBytecodesLeft() < 6750) break;
            if (r.type != RobotType.LAUNCHER) continue;
            if (neighborStr.toString().contains(""+r.ID)) {
                MapLocation prev = neighbors[r.ID%100];
                // if a unit moved away from me, follow him. unless, he was retreating.
                int netD = r.location.distanceSquaredTo(rc.getLocation()) - prev.distanceSquaredTo(rc.getLocation());
                for (int i = 0; i < nbrs.length && i < 3; i++) {
                    RobotInfo n = nbrs[i];
                    netD += r.location.distanceSquaredTo(n.location) - prev.distanceSquaredTo(n.location);
                }
                if (prev != null && prev != r.location
                    && r.type == RobotType.LAUNCHER && netD > 0) {
                    x += r.location.x * 6;
                    y += r.location.y * 6;
                    totalW += 6;
                    friendsMoved = true;
                }
            }
            // add in average position.
            x += r.location.x;
            y += r.location.y;
            totalW++;
            nneighbors[r.ID%100] = r.location;
            nneighborStr.append("|"+r.ID);
            //int end = Clock.getBytecodesLeft();
            //System.out.println("iter cost: " + (start-end));
        }
        //System.out.println(iters);
        neighbors = nneighbors;
        neighborStr = nneighborStr;
        if (totalW == 0) {
            bestNeighborDir = Direction.CENTER;
            return;
        }
        MapLocation bestNeighborLoc = new MapLocation((int)(x/totalW), (int)(y/totalW));
        Direction bestDir = null;
        int bestD = 10000000;
        for (Direction d: directions) {
            if (!rc.canMove(d)) continue;
            MapLocation nloc = rc.getLocation().add(d);
            MapInfo mi = rc.senseMapInfo(nloc);
            nloc = nloc.add(mi.getCurrentDirection());
            int dist = nloc.distanceSquaredTo(bestNeighborLoc);
            if (dist < bestD) {
                bestD = dist;
                bestDir = d; 
            }
        }
        bestNeighborDir = bestDir;
    }

    void heal() throws GameActionException {
        greedyPath.move(islandTarget);
    }

    void findCloseIsland() throws GameActionException {
        int[] islands = rc.senseNearbyIslands();
        MapLocation closestTarget = null;
        int d = 100000;
        for (int idx: islands) {
            if (Clock.getBytecodesLeft() < 1000) break;
            if (rc.senseAnchor(idx) == null) continue;
            MapLocation[] spots = rc.senseNearbyIslandLocations(idx);
            for (MapLocation spot: spots) {
                if (rc.getLocation().distanceSquaredTo(spot) >= d) continue;
                if (!rc.canSenseLocation(spot)) continue;
                if (rc.isLocationOccupied(spot)) continue;
                closestTarget = spot;
            }
        }
        islandTarget = closestTarget;
    }
 
    void rendevous() throws GameActionException {
        greedyPath.move(rendevous);
    }

    void hunt_hq() throws GameActionException {
        MapLocation m = communications.getClosestEnemyHQ();
        if (m != null) hunt_hq(m);
        for (RobotInfo r: rc.senseNearbyRobots(-1, rc.getTeam().opponent())) {
            if (r.type == RobotType.HEADQUARTERS) {
                hunt_hq(r.location);
                return;
            }
        }
    }

    void hunt_hq(MapLocation m) throws GameActionException {
        int rad = RobotType.HEADQUARTERS.actionRadiusSquared;
        if (m.distanceSquaredTo(rc.getLocation()) > rad + 10) {
            greedyPath.move(m);
        } else if (m.distanceSquaredTo(rc.getLocation()) <= rad) {
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
            greedyPath.move(best);
        }
    }


    void hunt() throws GameActionException {
        if (huntTarget != null) greedyPath.move(huntTarget, true);
    }

    void advance() throws GameActionException {
        for (RobotInfo r: rc.senseNearbyRobots(RobotType.HEADQUARTERS.actionRadiusSquared, rc.getTeam().opponent())) {
            if (r.type == RobotType.HEADQUARTERS) {
                hunt_hq(r.location);
                return;
            }
        }
        if (bestNeighborDir == null) return;
        if (rc.canMove(bestNeighborDir)) rc.move(bestNeighborDir);
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


    double currentDPS, cooldown;
    boolean robotOnCloud;
    int curActionRadius;
    int curVisionRadius;
    void maneuver() throws GameActionException {
        rc.setIndicatorString("Maneuvering");
        rc.setIndicatorString("BYTECODE: "+Clock.getBytecodesLeft());
        // Needs 1k Bytecode.
        MicroTarget[] microtargets = new MicroTarget[9];
        for (Direction d: directions) {
            microtargets[d.ordinal()] = new MicroTarget(d);
        }

        MapLocation m;
        MapInfo mi;
        for (RobotInfo r: rc.senseNearbyRobots()) {
            if (Clock.getBytecodesLeft() < 1500) break;
            if (!Util.isAttacker(r.type) || r.type == RobotType.HEADQUARTERS) continue;

            m = r.location;
            mi = rc.senseMapInfo(m);
            if (r.team == rc.getTeam()) {
                cooldown = mi.getCooldownMultiplier(rc.getTeam());
                robotOnCloud = mi.hasCloud();
                currentDPS = (double) r.type.damage * (1.0 / cooldown);
                curActionRadius = (robotOnCloud) ? GameConstants.CLOUD_VISION_RADIUS_SQUARED : r.type.actionRadiusSquared;
                curVisionRadius = (robotOnCloud) ? GameConstants.CLOUD_VISION_RADIUS_SQUARED : r.type.visionRadiusSquared;
                for (Direction d: directions) {
                    microtargets[d.ordinal()].addAlly(r);
                }
            } else {
                cooldown = mi.getCooldownMultiplier(rc.getTeam().opponent());
                currentDPS = (double) r.type.damage * (1.0 / cooldown);
                robotOnCloud = mi.hasCloud();
                curActionRadius = (robotOnCloud) ? GameConstants.CLOUD_VISION_RADIUS_SQUARED : r.type.actionRadiusSquared;
                curVisionRadius = (robotOnCloud) ? GameConstants.CLOUD_VISION_RADIUS_SQUARED : r.type.visionRadiusSquared;
                for (Direction d: directions) {
                    microtargets[d.ordinal()].addEnemy(r);
                }
            }
        }
        // Needs 1k Bytecode.
        MicroTarget best = microtargets[0];
        for (int i = 0; i < 9; i++) {
            if (microtargets[i].isBetterThan(best))
                best = microtargets[i];
        }
        if (rc.canMove(best.dir)) rc.move(best.dir);
    }

    void harass() throws GameActionException{
        rc.setIndicatorString("i am harass :) " + harassTarget + " " + harassTimer);
        if(harassTimer <= 0 || (harassTarget != null && rc.getLocation().distanceSquaredTo(harassTarget) <= 9)) harassTarget = null;
        if(harassTarget == null){
            harassTimer = 50;
            int ind = -1;
            int d = 1000000;
            for(int i = 0; i < 4; i++){
                MapLocation cr = new MapLocation((harassLoc[i].x == 0 ? 5 : rc.getMapWidth()) - 5, (harassLoc[i].y == 0 ? 5 : rc.getMapHeight() - 5));
                int ds = rc.getLocation().distanceSquaredTo(cr);
                if(ds < d){
                    d = ds;
                    ind = i;
                }
            }
            assert(ind != -1);
            if (harassDir == 0) harassTarget = new MapLocation((harassLoc[(ind + 1) % 4].x == 0 ? 5 : rc.getMapWidth()) - 5, 
                (harassLoc[(ind + 1) % 4].y == 0 ? 5 : rc.getMapHeight() - 5));
            else harassTarget = new MapLocation((harassLoc[(ind + 3) % 4].x == 0 ? 5 : rc.getMapWidth()) - 5,
                (harassLoc[(ind + 3) % 4].y == 0 ? 5 : rc.getMapHeight() - 5));
        }
        RobotInfo[] rob = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());
        if(rob.length > 0) greedyPath.launcherFlee();
        greedyPath.move(harassTarget);
        harassTimer--;
    }

    // Choose best candidate for maneuvering in close encounters.
    class MicroTarget {
        Direction dir;
        double dps_targetting = 0;
        double dps_defending = 0;
        double my_dps;
        int minDistToEnemy = 100000;
        boolean canMove;
        boolean hasCloud;
        MapLocation nloc;

        MicroTarget(Direction dir) throws GameActionException {
            this.dir = dir;
            nloc = rc.getLocation().add(dir);
            canMove = rc.canMove(dir);
            if (rc.canSenseLocation(nloc)) {
                MapInfo mi = rc.senseMapInfo(nloc);
                hasCloud = mi.hasCloud();
                nloc = nloc.add(mi.getCurrentDirection());
                my_dps = RobotType.LAUNCHER.damage * (1.0 / mi.getCooldownMultiplier(rc.getTeam()));
            }
        }
        
        void addEnemy(RobotInfo r) throws GameActionException {
            //int start = Clock.getBytecodesLeft();
            if (!canMove) return;
            if (r.type == RobotType.LAUNCHER) {
                int d = nloc.distanceSquaredTo(r.location);
                if (d <= curActionRadius) {
                    dps_targetting += currentDPS;
                } else if (d <= curVisionRadius)
                    dps_targetting += currentDPS;
                if (d <= minDistToEnemy)
                    minDistToEnemy = d;
            }
            if (r.type == RobotType.HEADQUARTERS) {
                int d = nloc.distanceSquaredTo(r.location);
                if (d <= r.type.actionRadiusSquared) 
                    dps_targetting += currentDPS;
            }
        } 

        
        void addAlly(RobotInfo r) throws GameActionException {
            if (!canMove) return;
            if (nloc.distanceSquaredTo(r.location) <= curVisionRadius)
                dps_defending += currentDPS;
        }
       
        int safe() {
            if (dps_targetting > my_dps) return 1;
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
                if (mt.hasCloud && !hasCloud) return false;
                if (!mt.hasCloud && hasCloud) return true;
                if (mt.dps_defending > dps_defending) return false;
                if (mt.dps_defending < dps_defending) return true;
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
