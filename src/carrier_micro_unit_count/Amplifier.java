package carrier_micro_unit_count;
import battlecode.common.*;

public class Amplifier extends Robot {
    public Amplifier(RobotController rc) {
        super(rc);
    }
    void run() throws GameActionException {
        communications.initial();
        if (!seekOptimalSpot()) {
            MapLocation m = communications.findBestAttackTarget();
            if (m != null) greedyPath.move(m);
            m = communications.findClosestHQ();
            if (m != null) greedyPath.move(m);
            else exploration.move();
        }
    }

    // TODO: this would be a lot better if we knew where the enemy is.
    // That way we could ensure the optimal spot was never in enemy territory.
    boolean seekOptimalSpot() throws GameActionException {
        RobotInfo[] robots = rc.senseNearbyRobots(-1, rc.getTeam());
        double avgx=0, avgy=0;
        int count=0;
        for (RobotInfo r: robots) {
            if (r.ID == rc.getID()) continue;
            if (!Util.isAttacker(r.type)) continue;
            avgx += r.location.x;
            avgy += r.location.y;
            count++;
        }
        if (count == 0) return false;

        avgx /= count;
        avgy /= count;
        MapLocation avg = new MapLocation((int) avgx, (int) avgy);

        AmplifyTarget[] targets = new AmplifyTarget[9];
        for (Direction d: directions) {
            targets[d.ordinal()] = new AmplifyTarget(d, avg);
        }

        for (RobotInfo r: robots) {
            if (Clock.getBytecodesLeft() <= Constants.BYTECODE_AMPLIFY) break;
            if (r.type != RobotType.AMPLIFIER) continue;
            for (AmplifyTarget target: targets) {
                target.updateAmp(r.location);
            }
        }

        AmplifyTarget best = null;
        for (Direction d: directions) {
            if (targets[d.ordinal()].isBetterThan(best)) 
                best = targets[d.ordinal()];
        }

        if (best != null && best.dir != null && rc.canMove(best.dir)) {
            rc.move(best.dir);
        }
        return true;
    }

    class AmplifyTarget {
        MapLocation avg;
        Direction dir;
        boolean canmove;
        int distToNearestAmp;
        int distToAverage;
        int distToHQ;
        int distAvgToHQ;
        AmplifyTarget(Direction dir, MapLocation avg) throws GameActionException {
            this.avg = avg;
            this.dir = dir;
            MapLocation nloc = rc.getLocation().add(dir);
            this.distToAverage = nloc.distanceSquaredTo(avg);
            this.distToNearestAmp = 10000;
            canmove = rc.canMove(dir);
            MapLocation m = communications.findClosestHQ();
            distToHQ = m.distanceSquaredTo(nloc);
            distAvgToHQ = m.distanceSquaredTo(avg);
        }

        void updateAmp(MapLocation m) {
            int ampDist = rc.getLocation().distanceSquaredTo(m);
            if (ampDist < distToNearestAmp)
                distToNearestAmp = ampDist;
        }

        // Not all of these are useful probably.
        boolean isBetterThan(AmplifyTarget at) throws GameActionException {
            if (at == null) return true;

            // canmove
            if (at.canmove && !canmove) return false;
            if (!at.canmove && canmove) return true;

            // Locations closer to HQ are better.
            if ((at.distToHQ <= at.distAvgToHQ) && 
                !(distToHQ <= at.distAvgToHQ)) return false;
            if (!(at.distToHQ <= at.distAvgToHQ) && 
                (distToHQ <= at.distAvgToHQ)) return true;

            // stay away from other amplifiers.
            if (at.distToNearestAmp>16 && distToNearestAmp<=16) return false;
            if (at.distToNearestAmp<=16 && distToNearestAmp>16) return true;

            // if close prioritize location that is further away.
            if (at.distToNearestAmp<=16 && distToNearestAmp<=16)
                return distToNearestAmp>at.distToNearestAmp;

            // not too far.
            int visrad = RobotType.AMPLIFIER.visionRadiusSquared;
            if (at.distToAverage <= visrad &&
                distToAverage > visrad) return false;
            if (at.distToAverage > visrad &&
                distToAverage <= visrad) return true;   
            
            // not too close.
            if (at.distToAverage > 9 && distToAverage <= 9) return false;
            if (at.distToAverage <= 9 && distToAverage >= 9) return true;   

            return distToAverage <= at.distToAverage;
        }
    }
}
