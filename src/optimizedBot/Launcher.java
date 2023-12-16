package optimizedBot;
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
    boolean shouldRendevous = true;
    boolean neighborsAttacked = false;
    boolean shouldAvoidClouds = false;
    boolean hasCarriersNear = false;
    boolean hasLaunchersNear = false;
    MapLocation rendevous;
    MapLocation huntTarget;
    MapLocation islandTarget;
    MapLocation harassTarget = null;
    MapLocation hqTarget;
    MapLocation bestNeighborLoc;
    MapLocation[] killedHQs = new MapLocation[5];
    int killedHQInd = 0;
    int advanceTurns = 0;
    enum State {
        WAIT,
        ADVANCE,
        CHASE,
        ATTACK,
        IMPROVE_VISION,
        HUNT,
        HUNT_HQ,
        HEAL
    }

    public Launcher(RobotController rc) throws GameActionException {
        super(rc);
        communications.findOurHQs();
        born = rc.getRoundNum();
        rendevous = communications.getBestRendevous();
    }

    void run() throws GameActionException {
        islandTarget = null;
        huntTarget = null;
        neighborsAttacked = false;
        shouldAvoidClouds = false;
        hasLaunchersNear = false;
        hasCarriersNear = false;
        hurt = rc.getHealth() < 100;
        if (rc.getRoundNum() % 5 == prevEnemyRound) 
            previousEnemy = null;
        communications.initial();
        updateNeighbors();
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
        if (rc.getActionCooldownTurns() >= 10) return;
        MapInfo mi = rc.senseMapInfo(rc.getLocation());
        if (mi.hasCloud() && !attackers) {
            // I might want to bring back the "attack players assuming they stayed still" strat.
            // Yet, this has a moderate chance of not finding an enemy,
            // And it doesn't account for cooldown, so not sure it was helping.
            return;
        }
        RobotInfo r = util.getBestAttackTarget();
        if (r == null && attackers) return;
        else if (r == null && attackers == false) {
            MapLocation[] clouds = rc.senseNearbyCloudLocations(RobotType.LAUNCHER.actionRadiusSquared);
            if (clouds.length == 0) return;
            MapLocation loc = clouds[rng.nextInt(clouds.length)];
            rc.attack(loc);
            return;
        }
        if (attackers && !Util.isAttacker(r.type)) return;
        if (rc.canAttack(r.location) && r.type != RobotType.HEADQUARTERS) {
            rc.attack(r.location);
        }
    }
 
    State determineState() throws GameActionException {
        advanceTurns--;
        if (rc.getRoundNum()%2 == 0) return State.WAIT;
        if (hasLaunchersNear || hasCarriersNear) {
            advanceTurns = 5;
            return State.ATTACK;
        }

        MapLocation target = communications.findBestAttackTarget();
        boolean hasTargetClose = false;
        if (target != null) {
            int d = rc.getLocation().distanceSquaredTo(target);
            hasTargetClose = (d <= 64);
            prevHuntRound = rc.getRoundNum();
        }

        if (hasTargetClose) {
            advanceTurns = 5;
            huntTarget = target;
            return State.HUNT;
        }

        // advance if you recently encountered a threat, or neighbor was attacked!.
        if (advanceTurns > 0 || neighborsAttacked) {
            return State.ADVANCE;
        }
        if (previousEnemy == null && !neighborsAttacked) {
            return State.HUNT_HQ;
        }
        return State.WAIT;
    }

    static final int FE_MASK_WIDTH = 11;
    static final int FE_MASK_HEIGHT = 5;
    static final int FE_MASK_CROSSOVER = 5;
    static final int HEALTH_LEN = 31;
    long friend_mask[] = { 0, 0 };
    long enemy_mask[] = { 0, 0 };
    long fupdates[][] = { {0, 0}, {0, 0}, {0, 0} };
    long eupdates[][] = { {0, 0}, {0, 0}, {0, 0} };
    long friend_healths[] = {
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0
    };

    void updateNeighbors() throws GameActionException {
        Team myTeam = rc.getTeam();
        int new_healths[] = new int[HEALTH_LEN];
        for (int i = HEALTH_LEN - 1; i-- >= 0;)
            new_healths[i] = 0;

        // Location of the bottom left corner.
        MapLocation myloc = rc.getLocation();
        int blx = myloc.x - (FE_MASK_WIDTH / 2);
        int bly = myloc.y - (FE_MASK_HEIGHT);

        double x = 0;
        double y = 0;
        double totalW = 0;
        long tfriend_mask[] = { 0, 0 };
        long tenemy_mask[] = { 0, 0 };
        RobotInfo[] robots = rc.senseNearbyRobots(-1);
        for (int j = robots.length; j-- >= 0; ) {
            RobotInfo r = robots[j];
            switch (r.type) {
                case LAUNCHER: break;
                case CARRIER:  
                    if (r.team == myTeam) {
                        hasCarriersNear = true;
                    }
                default: continue;
            }

            int dx = r.location.x - blx;
            int dy = r.location.y - bly;
            if (r.team == myTeam) {
                if (dy < FE_MASK_CROSSOVER) {
                    int shift = (dy * FE_MASK_WIDTH + dx);
                    tfriend_mask[0] |= (1 << shift);
                } else {
                    int shift = (dy * (FE_MASK_WIDTH - FE_MASK_CROSSOVER) + dx);
                    tfriend_mask[1] |= (1 << shift);
                }

                // Track average position of team, with priority to those attacked.
                new_healths[(r.ID % HEALTH_LEN)] = r.health;
                long phealth = friend_healths[r.ID % HEALTH_LEN];
                if (phealth == 0) continue;
                if ((phealth > r.health)) {
                    totalW += 6;
                    x += r.location.x * 6;
                    y += r.location.y * 6;
                    neighborsAttacked = true;
                } else {
                    x += r.location.x;
                    y += r.location.y;
                    totalW++;
                }
            } else {
                if (dy < FE_MASK_CROSSOVER) {
                    int shift = (dy * FE_MASK_WIDTH + dx);
                    tenemy_mask[0] |= (1 << shift);
                } else {
                    int shift = (dy * (FE_MASK_WIDTH - FE_MASK_CROSSOVER) + dx);
                    tenemy_mask[1] |= (1 << shift);
                }
                hasLaunchersNear = true;
            }
        }

        x /= totalW;
        y /= totalW;
        bestNeighborLoc = new MapLocation((int) x, (int) y);

        for (int i = HEALTH_LEN - 1; i-- >= 0;)
            friend_healths[i] = new_healths[i];

        int mod = rc.getRoundNum() % 3;
        friend_mask[0] &= ~fupdates[0][mod];
        enemy_mask[1]  &= ~fupdates[1][mod];
        friend_mask[0] &= ~eupdates[0][mod];
        enemy_mask[1]  &= ~eupdates[1][mod];
        friend_mask[0] |= tfriend_mask[0];
        enemy_mask[1]  |= tfriend_mask[1];
        friend_mask[0] |= tenemy_mask[0];
        enemy_mask[1]  |= tenemy_mask[1];
        fupdates[0][mod] = tfriend_mask[0];
        fupdates[1][mod] = tfriend_mask[1];
        eupdates[0][mod] = tenemy_mask[0];
        eupdates[1][mod] = tenemy_mask[1];
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
        hqTarget = communications.getClosestEnemyHQ(killedHQs);
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
            if (best != null) greedyPath.move(best, shouldAvoidClouds);
            return;
        } else if (m.distanceSquaredTo(rc.getLocation()) <= RobotType.LAUNCHER.visionRadiusSquared) {
            int enemyLaunchers = 0;
            int friendLaunchers = 0;
            int cntSmaller = 0;
            for (RobotInfo r: rc.senseNearbyRobots()) {
                if (r.type != RobotType.LAUNCHER) continue;
                if (rc.getTeam() == r.team) {
                    friendLaunchers++;
                    if (r.ID < rc.getID()) cntSmaller++;
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
            greedyPath.move(m, shouldAvoidClouds);
    }

    MapLocation findClosestLivingHQ() throws GameActionException {
        if (communications.symmetryChecker.getSymmetry() == -1) return null;
        int bestD = 10000000;
        MapLocation bestM = null;
        for (MapLocation e: communications.getEnemyHQs()) {
            if (e == null) continue;
            //System.out.println("HI");
            boolean marked = false;
            for (MapLocation a : killedHQs) {
                if (a == null) continue;
                if (a.equals(e)) marked = true;
            }
            if (marked) continue;
            if (e.distanceSquaredTo(rc.getLocation()) < bestD) {
                bestM = e;
                bestD = e.distanceSquaredTo(rc.getLocation());
            }
        }
        System.out.println(bestM);
        return bestM;
    }


    void hunt() throws GameActionException {
        if (huntTarget != null) {
            rc.setIndicatorString("HUNT: "+huntTarget);
            greedyPath.move(huntTarget, shouldAvoidClouds);
        }
    }

    void advance() throws GameActionException {
        for (RobotInfo r: rc.senseNearbyRobots(RobotType.HEADQUARTERS.actionRadiusSquared, rc.getTeam().opponent())) {
            if (r.type == RobotType.HEADQUARTERS) {
                hunt_hq(r.location);
                return;
            }
        }
        Direction bestNeighborDir = computeBestNeighborDir();
        if (bestNeighborDir == null) return;
        if (rc.canMove(bestNeighborDir)) 
            rc.move(bestNeighborDir);
    }

    Direction computeBestNeighborDir() throws GameActionException {
        Direction bestDir = Direction.CENTER;
        MapLocation cur = rc.getLocation();
        boolean bestCloud = true;
        int bestD = cur.distanceSquaredTo(bestNeighborLoc);
        for (Direction d: directions) {
            if (!rc.canMove(d)) continue;
            MapLocation nloc = cur.add(d);
            MapInfo mi = rc.senseMapInfo(nloc);
            if (shouldAvoidClouds && (mi.hasCloud() && !bestCloud)) continue;
            nloc = nloc.add(mi.getCurrentDirection());
            int dist = nloc.distanceSquaredTo(bestNeighborLoc);
            if (dist + 0.25 < bestD) {
                bestD = dist;
                bestDir = d; 
                bestCloud = mi.hasCloud();
            }
        }
        return bestDir;
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
        if (hasCarriersNear && !hasLaunchersNear) {
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
    boolean curOnCloud;
    boolean robotOnCloud;
    int curActionRadius;
    int curVisionRadius;
    MapLocation enemyLauncherLoc = null;
    void maneuver() throws GameActionException {
        rc.setIndicatorString("Maneuvering");

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

        MapLocation m;
        Team myTeam = rc.getTeam();
        Team opponentTeam = myTeam.opponent();
        MapInfo mi = rc.senseMapInfo(rc.getLocation());
        curOnCloud = mi.hasCloud();
        int iters = 0;
        for (LauncherInfo r: neighbors) {
            if (r == null) continue;
            if (Clock.getBytecodesLeft() < 1500) break;
            m = r.location;
            boolean canSense = rc.canSenseLocation(m);
            if (canSense) mi = rc.senseMapInfo(m);
            if (r.team == myTeam) {
                cooldown = (canSense) ? mi.getCooldownMultiplier(myTeam) : 1;
                robotOnCloud = (canSense) ? mi.hasCloud() : false;
                currentDPS = (double) RobotType.LAUNCHER.damage * (1.0 / cooldown);
                curActionRadius = (robotOnCloud) ? GameConstants.CLOUD_VISION_RADIUS_SQUARED : RobotType.LAUNCHER.actionRadiusSquared;
                curVisionRadius = (robotOnCloud) ? GameConstants.CLOUD_VISION_RADIUS_SQUARED : RobotType.LAUNCHER.visionRadiusSquared;
                microtargets[0].addAlly(r);
                microtargets[1].addAlly(r);
                microtargets[2].addAlly(r);
                microtargets[3].addAlly(r);
                microtargets[4].addAlly(r);
                microtargets[5].addAlly(r);
                microtargets[6].addAlly(r);
                microtargets[7].addAlly(r);
                microtargets[8].addAlly(r);
            } else {
                cooldown = (canSense) ? mi.getCooldownMultiplier(opponentTeam) : 1;
                currentDPS = (double) RobotType.LAUNCHER.damage * (1.0 / cooldown);
                robotOnCloud = (canSense) ? mi.hasCloud() : false;
                curActionRadius = (robotOnCloud) ? GameConstants.CLOUD_VISION_RADIUS_SQUARED : RobotType.LAUNCHER.actionRadiusSquared;
                curVisionRadius = (robotOnCloud) ? GameConstants.CLOUD_VISION_RADIUS_SQUARED : RobotType.LAUNCHER.visionRadiusSquared;
                microtargets[0].addEnemy(r);
                microtargets[1].addEnemy(r);
                microtargets[2].addEnemy(r);
                microtargets[3].addEnemy(r);
                microtargets[4].addEnemy(r);
                microtargets[5].addEnemy(r);
                microtargets[6].addEnemy(r);
                microtargets[7].addEnemy(r);
                microtargets[8].addEnemy(r);
            }
            iters++;
        }
        /*

        int targets[] = new int[9];
        for (int i = 0; i < 9; i++) {
            Direction dir = directions[i];
            MapLocation cur = myloc.add(dir);
            boolean canMove = rc.canMove(dir);
            boolean hasCloud = rc.canSenseLocation(nloc); 
            targets[i] = (canMove * 2) | hasCloud;
 
            action = (hasCloud) ? GameConstants.CLOUD_VISION_RADIUS_SQUARED : RobotType.LAUNCHER.actionRadiusSquared;
            vision = (hasCloud) ? GameConstants.CLOUD_VISION_RADIUS_SQUARED : RobotType.LAUNCHER.visionRadiusSquared;
            
            // minDistToEnemy will be replaced with a check in the local adjaceny map.
            targets[i] |= (minDistToEnemy <= action) * 4;
        }


         */

        // Needs 1k Bytecode.
        MicroTarget best = microtargets[0];
        if (microtargets[0].isBetterThan(best)) best = microtargets[0];
        if (microtargets[1].isBetterThan(best)) best = microtargets[1];
        if (microtargets[2].isBetterThan(best)) best = microtargets[2];
        if (microtargets[3].isBetterThan(best)) best = microtargets[3];
        if (microtargets[4].isBetterThan(best)) best = microtargets[4];
        if (microtargets[5].isBetterThan(best)) best = microtargets[5];
        if (microtargets[6].isBetterThan(best)) best = microtargets[6];
        if (microtargets[7].isBetterThan(best)) best = microtargets[7];
        if (microtargets[8].isBetterThan(best)) best = microtargets[8];
        if (rc.canMove(best.dir)) rc.move(best.dir);

        rc.setIndicatorString("ITERS: "+iters);
        for (MicroTarget mt: microtargets) {
            switch (mt.safe()) {
                case 1: rc.setIndicatorDot(mt.nloc, 255, 0, 0); break;
                case 2: rc.setIndicatorDot(mt.nloc, 255, 0, 255); break;
                case 3: rc.setIndicatorDot(mt.nloc, 0, 0, 255); break;
                case 4: rc.setIndicatorDot(mt.nloc, 0, 255, 0); break;
                default:
            }
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

        boolean inRange() {
            return d <= rc.getType().actionRadiusSquared;
        }

        boolean isBetterThan(ChaseTarget ct) {
            if (ct == null) return true;
            if (!canMove) return false;
            if (ct.inRange() && !inRange()) return false;
            if (!ct.inRange() && inRange()) return true;
            if (ct.inRange() && inRange()) return d >= ct.d;
            else return d <= ct.d;
        }
    }

    // Choose best candidate for maneuvering in close encounters.
    class MicroTarget {
        Direction dir;
        double dps_targetting = 0;
        double dps_defending = 0;
        double net_dps;
        int minDistToEnemy = 100000;
        int action;
        int vision;
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
                net_dps -= RobotType.LAUNCHER.damage * (1.0 / mi.getCooldownMultiplier(rc.getTeam()));
            }
            action = (hasCloud) ? GameConstants.CLOUD_VISION_RADIUS_SQUARED : RobotType.LAUNCHER.actionRadiusSquared;
            vision = (hasCloud) ? GameConstants.CLOUD_VISION_RADIUS_SQUARED : RobotType.LAUNCHER.visionRadiusSquared;
            // minDistToEnemy = nloc.distanceSquaredTo(previousEnemy);
        }
        
        void addEnemy(LauncherInfo r) throws GameActionException {
            //int start = Clock.getBytecodesLeft();
            if (!canMove) return;
            int d = nloc.distanceSquaredTo(r.location);
            if (d <= Math.min(curVisionRadius, vision)) {
                dps_targetting += currentDPS;
                net_dps += currentDPS;
            }
            if (d <= minDistToEnemy)
                minDistToEnemy = d;
        } 

        
        void addAlly(LauncherInfo r) throws GameActionException {
            if (!canMove) return;
            if (nloc.distanceSquaredTo(r.location) <= curVisionRadius)
                dps_defending += currentDPS;
        }
       
        int safe() {
            if (curOnCloud && hasCloud) return 1;
            if (net_dps > 0) return 2;
            if (dps_defending < dps_targetting) return 3;
            return 4;
        }

        boolean inRange() {
            return minDistToEnemy <= action;
        }

        boolean isBetterThan(MicroTarget mt) {
            if (!canMove) return false;
            if (mt.safe() > safe()) return false;
            if (mt.safe() < safe()) return true;

            // 
            if (mt.safe() == 1 && safe() == 1) {
                if (mt.dps_targetting < dps_targetting) return false;
                if (mt.dps_targetting > dps_targetting) return true;
            }

            // the idea here is attack first, then move out of range.
            if (mt.inRange() && !inRange()) return false;
            if (!mt.inRange() && inRange()) return true;
            
            // If hurt, run a different compare function.
            if (!hurt) {
                if (mt.dps_defending > dps_defending) return false;
                if (mt.dps_defending < dps_defending) return true;
            }

            if (mt.inRange() && inRange()) return minDistToEnemy >= mt.minDistToEnemy;
            else return minDistToEnemy <= mt.minDistToEnemy;
        }
    }
}
