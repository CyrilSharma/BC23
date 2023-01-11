package carrier_micro_unit_count;
import battlecode.common.*;

public class Amplifier extends Robot {
    public Amplifier(RobotController rc) {
        super(rc);
    }
    void run() throws GameActionException{
        communications.report();
        exploration.move();
    }
}
