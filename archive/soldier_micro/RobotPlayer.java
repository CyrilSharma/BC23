package soldier_micro;
import battlecode.common.*;

public strictfp class RobotPlayer {
    public static void run(RobotController rc) throws GameActionException {
        Robot robot = null;
        switch (rc.getType()) {
            case HEADQUARTERS: robot = new HQ(rc);  break;
            case CARRIER: robot = new Carrier(rc);   break;
            case LAUNCHER: robot = new Launcher(rc); break;
            case BOOSTER: robot = new Booster(rc); break;
            case DESTABILIZER: robot = new Destabilizer(rc); break;
            case AMPLIFIER: robot = new Amplifier(rc); break;
        }

        while (true) {
            try {
                robot.run();
            } catch (GameActionException e) {
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();
            } finally {
                Clock.yield();
            }
        }
    }
}
