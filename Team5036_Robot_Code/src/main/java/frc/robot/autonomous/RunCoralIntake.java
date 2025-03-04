package frc.robot.autonomous;

import frc.robot.subsystems.CoralMechanism;
import edu.wpi.first.wpilibj.Timer;

public class RunCoralIntake {
    public static void execute(CoralMechanism coral, double pow, double time, double autoStartTime) {
        double startTime = Timer.getTimestamp();
        double newTime = Timer.getTimestamp();
        if (pow < -1) {
            pow = -1;
        } else if (pow > 1) {
            pow = 1;
        }
        coral.runIntake(pow);
        while (newTime-startTime < time && newTime-autoStartTime < 15) {
            newTime = Timer.getTimestamp();
            Timer.delay(.002);
        }
        coral.runIntake(0);
        return;
    }
}
