package finalBot;
import battlecode.common.*;

public strictfp class RobotPlayer {
    static int turnCount = 0;
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
                if (rc.getRoundNum() > 200 && rc.getRobotCount() <= 5) {
                    rc.resign();
                }
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
            turnCount++;
        }
    }
}
