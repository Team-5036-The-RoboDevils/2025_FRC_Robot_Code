package frc.robot.autonomous;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.CoralMechanism; 

public class TurnToAnglePID {
    private static final double kP = 0.0; 
    private static final double kFGravity = 0.0; 
    private static boolean isInAutoTime(double startTime) { 
        double currentTime = System.currentTimeMillis();
        if((currentTime - startTime) > 15000){
            return false;
    }
        return true; 
    }

    public static void execute(Drivetrain drivetrain, CoralMechanism coralMechanism, double angleToTurn, double turningPower, boolean turningClockwise, double angleToHoldArmAt, double target, double tempP,long startTime) {
        double currentAngle = drivetrain.getGyroAngle();
        double error = target - currentAngle;
        double ACCEPTABLE_RANGE = 1; 
       double feedForwardVal = Math.cos(Math.toRadians(currentAngle));

        if (Math.abs(error) <= ACCEPTABLE_RANGE) {
            drivetrain.arcadeDrive(0, 0); 
        } else if (Math.abs(error) >= ACCEPTABLE_RANGE && turningClockwise) {
            drivetrain.arcadeDrive(0, feedForwardVal);
        } else if (Math.abs(error) >= ACCEPTABLE_RANGE && !turningClockwise) {
            drivetrain.arcadeDrive(0, feedForwardVal);
        }
    }

    public static void execute(long startTime, Drivetrain drivetrain, CoralMechanism coralMechanism, double angleToTurn, double turningPower, boolean turningClockwise, double angleToHoldArmAt) {
        if (!turningClockwise) {
            while (isInAutoTime(startTime) && drivetrain.getGyroAngle() <= angleToTurn){ 
                coralMechanism.closedLoopCoralArticulation(angleToHoldArmAt, kP, kFGravity);
                Timer.delay(0.02);
            }
        } else if (turningClockwise) {
            while (isInAutoTime(startTime) && drivetrain.getGyroAngle() >= -angleToTurn) {
                drivetrain.arcadeDrive(0, -turningPower);
                coralMechanism.closedLoopCoralArticulation(angleToHoldArmAt, kP, kFGravity);
                Timer.delay(0.02);
            }
        }
    }
}
