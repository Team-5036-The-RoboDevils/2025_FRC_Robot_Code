package frc.robot.autonomous;

import frc.robot.subsystems.CoralMechanism;
import edu.wpi.first.wpilibj.Timer;

public class RunCoralIntake {
    public static void execute(CoralMechanism coral, double pow, double time) {
        double startTime = Timer.getTimestamp();
        double newTime = Timer.getTimestamp();
        if (pow < -1) {
            pow = -1;
        } else if (pow > 1) {
            pow = 1;
        }
        while (newTime-startTime < time) {
                coral.runIntake(pow);
                newTime = Timer.getTimestamp();
        }
        return;
    }
}
