package frc.robot.autonomous;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.CoralMechanism;
import frc.robot.subsystems.Drivetrain;

public class TurnToAnglePID {
    private static boolean isInAutoTime(double startTime) { 
        double currentTime = System.currentTimeMillis();
        if((currentTime - startTime) > 15000){
            return false;
    }
        return true; 
    }
       private static final double EPSILON_RANGE = 3;
       private static final int IN_RANGE_COUNT_REQ = 10;

       public static void execute(Drivetrain drivetrain, CoralMechanism coralMech, double holdAngle, double kGrav, double kP, double targetAngle, double forward, long startTime, long timeLimit, double kPDrive, double maxPower) {//, kPDist){
        
        int inRangeCount = 0;
        long autonStartTime = System.currentTimeMillis();  

        //PIDController driveStraightController = new PIDController(drivetrain.getDistanceTravelled(), targetDistInCm, -maxPower, maxPower, 0.0035, 0, 0.01);
        PIDController turnPidController = new PIDController(drivetrain.getGyroAngle(), targetAngle, -maxPower, maxPower, 0.015, 0, 0.05);
        //PIDController turnPidController = new PIDController(drivetrain.getAngle(), 0, -0.1, 0.1, 0.015, 0, 0.0);

        while (inRangeCount < IN_RANGE_COUNT_REQ && isInAutoTime(autonStartTime)) {
            if (Math.abs(drivetrain.getGyroAngle() - targetAngle) > EPSILON_RANGE) {
                turnPidController.updateError(drivetrain.getGyroAngle());
                coralMech.closedLoopCoralArticulation(holdAngle, kGrav, kP);
                drivetrain.arcadeDrive(0, turnPidController.getOutput());
            } else if (Math.abs(drivetrain.getGyroAngle() - targetAngle) <= EPSILON_RANGE) {
                inRangeCount += 1;
            }
            Timer.delay(0.02);
        }

        drivetrain.arcadeDrive(0, 0);
    }
}