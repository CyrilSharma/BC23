package carrier_micro_unit_count;
import battlecode.common.*;

// Control flow.
// If no targets in range continue exploring, or seeking out some other target.
// If there are enemies, but they're all nice, path towards them aggresively.
// If there are attacking enemies, run attack micro.
public class Launcher extends Robot {
    RobotInfo[] enemies;
    MapLocation myloc;
    boolean hurt = false;
    enum State {
        ATTACK,
        HUNT,
        EXPLORE,
        DEFEND
    }

    public Launcher(RobotController rc) throws GameActionException {
        super(rc);
        communications.findOurHQs();
    }
    void run() throws GameActionException {
        initialize();
        communications.initial();
        /*
        if(communications.numEnemyHQ > 0){
            for(int i = 0; i < communications.numEnemyHQ; i++){
                System.out.println(communications.EnemyHQs[i]);
            }
            System.out.println("=====");
        }
         */
        State state = determineState();
        rc.setIndicatorString(state.toString());
        switch (state) {
            case ATTACK: attack(); break;
            case HUNT: hunt(); break;
            case EXPLORE: explore(); break;
            case DEFEND: defend(); break;
        }
        communications.last();
    }

    void initialize() throws GameActionException {
        enemies = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());
        myloc = rc.getLocation();
        if (rc.getHealth() < 20) 
            hurt = true;
    }

    State determineState() throws GameActionException {
        for (RobotInfo e : enemies) {
            if (e.type != RobotType.HEADQUARTERS) return State.ATTACK;
        }
        MapLocation m = communications.findBestAttackTarget();
        if (m != null && !rc.canSenseLocation(m)) return State.HUNT;
        if (rc.getLocation().distanceSquaredTo(communications.findClosestHQ()) > 9) return State.DEFEND;
        return State.EXPLORE;
    }

    void attack() throws GameActionException {
        boolean attacker = false;
        int cntEn = 0, cntAl = rc.getHealth(), numEn = 0, numAl = 0;
        int enX = 0, enY = 0;
        for (RobotInfo e: enemies){
            if (Util.isAttacker(e.getType())) {
                attacker = true;
                cntEn += e.health;
                numEn++;
                enX += e.location.x;
                enY += e.location.y;
            }
        }
        AttackTarget at = getBestAttackTarget();
        if(numEn > 0) {
            MapLocation enm = new MapLocation(enX / numEn, enY / numEn);
            RobotInfo[] r = rc.senseNearbyRobots(rc.getType().actionRadiusSquared, rc.getTeam());
            for (RobotInfo rob : r) {
                if (Util.isAttacker(rob.type)) {
                    if (rob.getLocation().distanceSquaredTo(enm) <= rob.getType().visionRadiusSquared){
                        cntAl += rob.health;
                    }
                }
            }
            if (cntAl < cntEn) {
                if (rc.canAttack(at.loc)) rc.attack(at.loc);
                greedyPath.flee();
                return;
            }
        }
        /*
        if(numEn > 0 && numAl > 0){
            MapLocation a = new MapLocation(enX / numEn, enY / numEn);
            MapLocation b = new MapLocation(alX / numAl, alY / numAl);
            if(a.distanceSquaredTo(b) > rc.getType().actionRadiusSquared){
                greedyPath.flee();
                return;
            }
        }
         */
        // TODO: change attack target if better target is found after moving.
        if (rc.canAttack(at.loc)) rc.attack(at.loc);
        if (!attacker) {
            follow(at.loc);
        } else {
            maneuver();
        }
    }

    // Relies on comms.
    void hunt() throws GameActionException {
        MapLocation huntTarget = communications.findBestAttackTarget();
        rc.setIndicatorDot(rc.getLocation(), 0, 0, 0);
        rc.setIndicatorLine(rc.getLocation(), huntTarget, 0, 0, 0);
        greedyPath.move(huntTarget);
    }

    // Relies on exploration code.
    void explore() throws GameActionException{
        exploration.moveLauncher(communications.HQs, communications.numHQ);
    }

    void defend() throws GameActionException {
        greedyPath.move(communications.findClosestHQ());
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

        MicroTarget best = microtargets[0];
        for (int i = 0; i < 9; i++) {
            if (microtargets[i].isBetterThan(best))
                best = microtargets[i];
        }
        if (rc.canMove(best.dir))
            rc.move(best.dir);
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
    // TODO: respond to effects of non-booster classes.
    class MicroTarget {
        Direction dir;
        double dps_received;
        double dps_targetting;
        double dps_defending;
        int minDistToEnemy;
        boolean canMove;
        MapLocation nloc;

        MicroTarget(Direction dir) throws GameActionException {
            this.dir = dir;
            dps_received = 0;
            dps_targetting = 0;
            dps_defending = 0;
            minDistToEnemy = 10000;
            nloc = myloc.add(dir);
            canMove = rc.canMove(dir);
            if (canMove) {
                MapInfo mi = rc.senseMapInfo(nloc);
                nloc.add(mi.getCurrentDirection());
            }
        }
        /*
        void addEnemy(RobotInfo r) throws GameActionException {
            if (!Util.isAttacker(r.type)) return;
            MapLocation m = r.location;
            MapInfo mi = rc.senseMapInfo(m);
            // ignore boosting effects of other units for now.
            if (r.type == RobotType.BOOSTER) {
                int d = nloc.distanceSquaredTo(m);
                if (d <= r.type.actionRadiusSquared) 
                    dps_received += r.type.damage * (1 / mi.getCooldownMuliplier(rc.getTeam().opponent()));
                if (d <= r.type.visionRadiusSquared)
                    dps_targetting += r.type.damage * (1 / mi.getCooldownMuliplier(rc.getTeam().opponent()));;
                if (d <= minDistToEnemy)
                    minDistToEnemy = d;
            }
        }
        
        void addAlly(RobotInfo r) throws GameActionException {
            if (Util.isAttacker(r.type)) {
                MapInfo mi = rc.senseMapInfo(r.location);
                dps_defending += r.type.damage * (1 / mi.getCooldownMuliplier(rc.getTeam()));
            }
        }
        */

        int safe() {
            if (dps_received > 0) return 1;
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
