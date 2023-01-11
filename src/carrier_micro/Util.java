package carrier_micro;
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
}
