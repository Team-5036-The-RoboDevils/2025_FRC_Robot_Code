package frc.robot.autonomous;

import frc.robot.subsystems.CoralMechanism;
import edu.wpi.first.wpilibj.Timer;


public class RunCoralIntake {
    public static void execute(CoralMechanism coral, double pow, long time, long autoStartTime) {
        long startTime = System.currentTimeMillis();
        long newTime = System.currentTimeMillis();
        if (pow < -1) {
            pow = -1;
        } else if (pow > 1) {
            pow = 1;
        }
        while (newTime-startTime < time && newTime-autoStartTime < 15000) {
            coral.runOuttake(pow);
            newTime = System.currentTimeMillis();
            Timer.delay(0.02);
        }
        coral.runIntake(0);
        return;
    }
}
