package frc.robot.autonomous;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.CoralMechanism;

public class DriveDistanceBangBang {

    private static boolean isInAutoTime(double startTime) { 
        double currentTime = System.currentTimeMillis();
        if((currentTime - startTime) > 15000){
            return false;
    }
        return true; 
    }

   /*  public static void execute(Drivetrain drivetrain, CoralMechanism coralMech, double holdAngle, double kGrav, double kP, double targetDistInCm, boolean driveBack, double forward, long startTime){
        //double rotate = 0; // We are not rotating. only driving straight 
        drivetrain.execute(drivetrain, coralMech, holdAngle, kGrav, kP, targetDistInCm, driveBack, forward, startTime, 60000); 
    }*/

    public static void execute(Drivetrain drivetrain, CoralMechanism coralMech, double holdAngle, double kGrav, double kP, double targetDistInCm, boolean driveBack, double forward, long startTime, long timeLimit, double kPDrive){
        double rotate = 0; // We are not rotating. only driving straight 
        double startOfCommand = System.currentTimeMillis(); 
        if (!driveBack){
            drivetrain.resetEncoders();
            while(drivetrain.convertEncoderTicksToCentimetres(drivetrain.getRawEncoder()) <= targetDistInCm && isInAutoTime(startTime)){
                drivetrain.arcadeDrive(forward, -kPDrive * drivetrain.getGyroAngle());
                if (System.currentTimeMillis() - startOfCommand >= timeLimit) {
                    break; 
                }
                coralMech.closedLoopCoralArticulation(holdAngle, kGrav, kP);
                //if (currentTime >= )
                Timer.delay(0.02);
            }
            drivetrain.arcadeDrive(0, 0);
        } else if (driveBack) {
            drivetrain.resetEncoders();
            while (drivetrain.convertEncoderTicksToCentimetres(drivetrain.getRawEncoder()) >= -targetDistInCm && isInAutoTime(startTime)) { 
                drivetrain.arcadeDrive(forward, rotate);
                if (System.currentTimeMillis() - startOfCommand >= timeLimit) {
                    break; 
                }
                coralMech.closedLoopCoralArticulation(holdAngle, kGrav, kP);
                Timer.delay(0.02);
            }
            drivetrain.arcadeDrive(0, 0);
        }
    }
}

