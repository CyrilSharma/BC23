package carrier_micro;
import battlecode.common.*;

public class Amplifier extends Robot {
    public Amplifier(RobotController rc) {
        super(rc);
    }
    void run() throws GameActionException {
        if (!seekOptimalSpot()) {
            rc.setIndicatorString("yo.");
            exploration.move();
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
        if (rc.canMove(best.dir)) {
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
        AmplifyTarget(Direction dir, MapLocation avg) {
            this.avg = avg;
            this.dir = dir;
            this.distToAverage = rc.getLocation().add(dir).distanceSquaredTo(avg);
            this.distToNearestAmp = 10000;
            canmove = rc.canMove(dir);
        }

        void updateAmp(MapLocation m) {
            int ampDist = rc.getLocation().distanceSquaredTo(m);
            if (ampDist < distToNearestAmp)
                distToNearestAmp = ampDist;
        }

        boolean isBetterThan(AmplifyTarget at) {
            if (at == null) return true;

            // canmove
            if (at.canmove && !canmove) return false;
            if (!at.canmove && canmove) return true;

            // stay away from other amplifiers.
            if (at.distToNearestAmp>9 && distToNearestAmp<=9) return false;
            if (at.distToNearestAmp<=9 && distToNearestAmp>9) return true;

            // if close prioritize location that is further away.
            if (at.distToNearestAmp<=9 && distToNearestAmp<=9)
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
