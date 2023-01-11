package carrier_micro_unit_count;
import battlecode.common.*;

public class Util {
    public static boolean isAttacker(RobotType t) {
        switch (t) {
            case HEADQUARTERS:
            case CARRIER:
            case AMPLIFIER:
                return true;
            default:
        }
        return true;
    }

    public static double absDistance(MapLocation a, MapLocation b) {
        return Math.sqrt(Math.pow((a.x - b.x),2) + Math.pow((a.y - b.y),2));
    }
}
