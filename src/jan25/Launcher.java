package jan25;
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
    int prevEnemyRound = -10;
    int prevHuntRound = -10;
    MapLocation previousEnemy = null;
    MapLocation previousPos = null;
    MapLocation enemyHQ = null;
    Direction bestNeighborDir;
    boolean okToStray;
    boolean friendsMoved = false;
    boolean shouldRendevous = true;
    boolean shouldHarass = false;
    boolean neighborsAttacked = false;
    boolean shouldAvoidClouds = false;
    MapLocation rendevous;
    MapLocation huntTarget;
    MapLocation islandTarget;
    MapLocation harassTarget = null;
    MapLocation hqTarget;
    int harassTimer = 0;
    int harassDir = -1;
    int advanceTurns = 0;
    MapLocation[] harassLoc = {new MapLocation(0, 0), new MapLocation(1, 0), 
            new MapLocation(1, 1), new MapLocation(0, 1)};
    enum State {
        WAIT,
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
        //rc.disintegrate();
    }
    void run() throws GameActionException {
        islandTarget = null;
        huntTarget = null;
        friendsMoved = false;
        neighborsAttacked = false;
        shouldAvoidClouds = false;
        hurt = rc.getHealth() < 100;
        // if (hurt) findCloseIsland();
        if (rc.getRoundNum()%5 == prevEnemyRound) previousEnemy = null;
        // if (rc.getRoundNum() - prevHuntRound > 5) huntTarget = null;
        
        communications.initial();
        if (rc.getRoundNum()%3 != 1) updateNeighbors();
        State state = determineState();
        rc.setIndicatorString(state.toString());
        doAttack(true);
        switch (state) {
            case WAIT: break;
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
        advanceTurns--;
        if (rc.getRoundNum()%2 == 0) return State.WAIT;

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
        int numLaunchers = 0;
        for (RobotInfo f : rc.senseNearbyRobots(-1, rc.getTeam())) {
            if (f.type == RobotType.CARRIER) numCarriers++;
            if (f.type == RobotType.LAUNCHER) numLaunchers++;
        }
        // Conditions!!
        MapInfo mi = rc.senseMapInfo(rc.getLocation());
        MapLocation target = communications.findBestAttackTarget();
        boolean hasTargetClose = false;
        boolean hasTargetFar = false;
        if (target != null) {
            int d = rc.getLocation().distanceSquaredTo(target);
            hasTargetClose = (d <= 64);
            hasTargetFar = (d > 64);
            prevHuntRound = rc.getRoundNum();
        }
        // if you see an enemy, or you just saw an enemy and you're on a cloud...
        if (hasEnemy || (previousEnemy != null && mi.hasCloud())) {
            advanceTurns = 10;
            return State.ATTACK;
        }

        if (hasTargetClose) {
            advanceTurns = 10;
            huntTarget = target;
            return State.HUNT;
        }
        if (previousEnemy != null) {
            // advanceTurns = 10;
            return State.CHASE;
        }
        /* if (hasTargetFar) {
            huntTarget = target;
            return State.HUNT;
        } */
        hqTarget = communications.getClosestEnemyHQ();
        // advance if you recently encountered a threat.
        // or neighbor was attacked!.
        if (advanceTurns > 0 || neighborsAttacked) {
            return State.ADVANCE;
        }
        // if (mi.hasCloud()) return State.IMPROVE_VISION;
        hqTarget = communications.getClosestEnemyHQ();
        int distHQ = rc.getLocation().distanceSquaredTo(hqTarget);
        if (distHQ > 25 || distHQ <= RobotType.HEADQUARTERS.actionRadiusSquared) 
            return State.HUNT_HQ;
        return State.WAIT;
    }

    public Direction getMoveEstimate(MapLocation m) throws GameActionException {
        Direction best = null;
        int bestD = 1000000;
        for (Direction d: directions) {
            if (!rc.canMove(d)) continue;
            MapLocation cur = rc.getLocation().add(d);
            int dist = cur.distanceSquaredTo(m);
            if (dist < bestD) {
                bestD = dist;
                best = d;
            }
        }
        return best;
    }

    RobotInfo[] neighbors = new RobotInfo[100];
    StringBuilder neighborStr = new StringBuilder();
    void updateNeighbors() throws GameActionException {
        double x = 0;
        double y = 0;
        double totalW = 0;
        //HashMap<Integer,RobotInfo> nneighbors = new HashMap<Integer,RobotInfo>();
        RobotInfo[] nneighbors = new RobotInfo[100];
        StringBuilder nneighborStr = new StringBuilder();
        RobotInfo[] robots = rc.senseNearbyRobots(-1 , rc.getTeam());
        RobotInfo[] nbrs = rc.senseNearbyRobots(4, rc.getTeam());
        for (RobotInfo r: robots) {
            // int start = Clock.getBytecodesLeft();
            if (Clock.getBytecodesLeft() < 6750) break;
            if (r.type != RobotType.LAUNCHER) continue;
            if (neighborStr.toString().contains(""+r.ID)) {
                RobotInfo prev = neighbors[r.ID%100];
                // if a unit moved away from me, follow him.
                int count = 0;
                int netD = r.location.distanceSquaredTo(rc.getLocation()) - prev.location.distanceSquaredTo(rc.getLocation());
                for (RobotInfo n: nbrs) {
                    if (n.type != RobotType.LAUNCHER) continue;
                    if (count == 3) break;
                    if (neighborStr.toString().contains(""+n.ID)) {
                        MapLocation prev2 = neighbors[n.ID%100].location;
                        netD += r.location.distanceSquaredTo(prev2) - prev.location.distanceSquaredTo(prev2);
                        count++;
                    }
                }
                int weight = 6;
                if (prev.health > r.health) {
                    neighborsAttacked = true;
                    weight = 12;
                    shouldAvoidClouds = true;
                }
                if ((prev != null && prev.location != r.location && netD > 0) ||
                    prev.health > r.health) {
                    x += r.location.x * weight;
                    y += r.location.y * weight;
                    totalW += weight;
                    friendsMoved = true;
                }
            }
            // add in average position.
            x += r.location.x;
            y += r.location.y;
            totalW++;
            nneighbors[r.ID%100] = r;
            nneighborStr.append("|"+r.ID);
            // int end = Clock.getBytecodesLeft();
            // System.out.println("iter cost: " + (start-end));
        }
        //System.out.println(iters);
        neighbors = nneighbors;
        neighborStr = nneighborStr;
        if (!friendsMoved) {
            bestNeighborDir = Direction.CENTER;
            return;
        }
        x /= totalW;
        y /= totalW;
        // rc.setIndicatorString(""+x+" "+y);
        // rc.setIndicatorString(""+bestNeighborLoc);
        Direction bestDir = Direction.CENTER;
        MapLocation cur = rc.getLocation();
        boolean bestCloud = true;
        double bestD = Util.sqrDist(cur.x,cur.y,x,y);
        for (Direction d: directions) {
            if (!rc.canMove(d)) continue;
            MapLocation nloc = cur.add(d);
            MapInfo mi = rc.senseMapInfo(nloc);
            if (shouldAvoidClouds && (mi.hasCloud() && !bestCloud)) continue;
            nloc = nloc.add(mi.getCurrentDirection());
            double dist = Util.sqrDist(nloc.x,nloc.y,x,y);
            if (dist + 0.25 < bestD) {
                bestD = dist;
                bestDir = d; 
                bestCloud = mi.hasCloud();
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

    void hunt_hq() throws GameActionException {
        rc.setIndicatorDot(hqTarget, 0, 255, 0);
        if (hqTarget != null) hunt_hq(hqTarget);
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
            // rc.setIndicatorString("I'M TRYING 1");
            greedyPath.move(m, shouldAvoidClouds);
        } else if (m.distanceSquaredTo(rc.getLocation()) <= rad) {
            // rc.setIndicatorString("I'M TRYING 2");
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
            if (best != null) greedyPath.move(best, shouldAvoidClouds);
        }
    }


    void hunt() throws GameActionException {
        if (huntTarget != null) {
            rc.setIndicatorString("HUNT: "+huntTarget);
            greedyPath.move(huntTarget, shouldAvoidClouds);
        }
    }

    void advance() throws GameActionException {
        // rc.setIndicatorString("ADVANCE: "+bestNeighborDir);
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
            greedyPath.move(previousEnemy, true);
        else if (rc.canSenseLocation(previousEnemy)) {
            RobotInfo r = rc.senseRobotAtLocation(previousEnemy);
            if (r == null) previousEnemy = null;
        }
    }   

    void attack() throws GameActionException {
        maneuver();
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


    double currentDPS, cooldown;
    boolean curOnCloud;
    boolean robotOnCloud;
    int curActionRadius;
    int curVisionRadius;
    void maneuver() throws GameActionException {
        rc.setIndicatorString("Maneuvering");
        // Needs 1k Bytecode.
        MicroTarget[] microtargets = new MicroTarget[9];
        for (Direction d: directions) {
            microtargets[d.ordinal()] = new MicroTarget(d);
        }

        MapLocation m;
        MapInfo mi = rc.senseMapInfo(rc.getLocation());
        curOnCloud = mi.hasCloud();
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
    // Choose best candidate for maneuvering in close encounters.
    class MicroTarget {
        Direction dir;
        double dps_targetting = 0;
        double dps_defending = 0;
        double my_dps;
        int minDistToEnemy = 100000;
        int action;
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
            action = (hasCloud) ? GameConstants.CLOUD_VISION_RADIUS_SQUARED : RobotType.LAUNCHER.actionRadiusSquared;
            // minDistToEnemy = nloc.distanceSquaredTo(previousEnemy);
        }
        
        void addEnemy(RobotInfo r) throws GameActionException {
            //int start = Clock.getBytecodesLeft();
            if (!canMove) return;
            if (r.type == RobotType.LAUNCHER) {
                int d = nloc.distanceSquaredTo(r.location);
                if (d <= curActionRadius)
                    dps_targetting += currentDPS;
                if (d <= curVisionRadius)
                    dps_targetting += currentDPS;
                if (d <= minDistToEnemy)
                    minDistToEnemy = d;
            } else if (r.type == RobotType.CARRIER) {
                int d = nloc.distanceSquaredTo(r.location);
                if (d <= curActionRadius) 
                    dps_targetting += currentDPS;
            } else if (r.type == RobotType.HEADQUARTERS) {
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
            if (hasCloud) return 1;
            if (dps_targetting > my_dps) return 2;
            if (dps_defending < dps_targetting) return 3;
            return 4;
        }

        boolean inRange() {
            return minDistToEnemy <= action;
        }

        boolean isBetterThan(MicroTarget mt) {
            if (mt.canMove && !canMove) return false;
            if (!mt.canMove && canMove) return true;

            if (mt.safe() > safe()) return false;
            if (mt.safe() < safe()) return true;

            // If hurt move to where enemies are targetting the least.
            if (hurt) {
                if (mt.dps_defending > dps_defending) return false;
                if (mt.dps_defending < dps_defending) return true;
            }

            // the idea here is attack first, then move out of range.
            if (!mt.inRange() && inRange()) return false;
            if (mt.inRange() && !inRange()) return true;
            if (mt.inRange() && inRange()) return minDistToEnemy >= mt.minDistToEnemy;
            else return minDistToEnemy <= mt.minDistToEnemy;
        }
    }
}
