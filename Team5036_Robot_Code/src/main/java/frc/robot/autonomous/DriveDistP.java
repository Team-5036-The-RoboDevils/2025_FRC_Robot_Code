package frc.robot.autonomous;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.CoralMechanism;
import frc.robot.subsystems.Drivetrain;

public class DriveDistP {
    private static boolean isInAutoTime(double startTime) { 
        double currentTime = System.currentTimeMillis();
        if((currentTime - startTime) > 15000){
            return false;
    }
        return true; 
    }
       private static final double EPSILON_RANGE = 5;
       private static final int IN_RANGE_COUNT_REQ = 10;

       public static void execute(Drivetrain drivetrain, CoralMechanism coralMech, double holdAngle, double kGrav, double kP, double targetDistInCm, boolean driveBack, double forward, long startTime, long timeLimit, double kPDrive) {//, kPDist){
        
        int inRangeCount = 0;
        long autonStartTime = System.currentTimeMillis(); 
        int desiredDist = 300;
        double maxPower = 1;  

        drivetrain.resetEncoders();
        drivetrain.resetGyro(); 
        Timer.delay(0.2);

        PIDController driveStraightController = new PIDController(drivetrain.getDistanceTravelled(), desiredDist, -maxPower, maxPower, 0.07, 0, 0.175);
        PIDController turnPidController = new PIDController(drivetrain.getGyroAngle(), 0, -0.3, 0.3, 0.015, 0, 0.05);
        //PIDController turnPidController = new PIDController(drivetrain.getAngle(), 0, -0.1, 0.1, 0.015, 0, 0.0);

        while (inRangeCount < IN_RANGE_COUNT_REQ && isInAutoTime(autonStartTime)) {
            driveStraightController.updateError(drivetrain.getDistanceTravelled());
            turnPidController.updateError(drivetrain.getGyroAngle());
            coralMech.closedLoopCoralArticulation(holdAngle, kGrav, kP);
            
            //System.out.println("Target: " + desiredDist + ", Current sensor reading: " + drivetrain.getDistTravelled());
            //System.out.println("PID Drive straight Output: " + driveStraightController.getOutput());
            drivetrain.arcadeDrive(driveStraightController.getOutput(), turnPidController.getOutput());
            if (Math.abs(drivetrain.getDistanceTravelled() - desiredDist) <= EPSILON_RANGE) {
                inRangeCount += 1;
            }

            Timer.delay(0.02);
        }

        drivetrain.arcadeDrive(0, 0);
    }
}