package frc.robot.autonomous;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.CoralMechanism;

public class DriveDistanceBangBang {
    
    private static boolean isInAutoTime(double startTime) { 
        double currentTime = System.currentTimeMillis();
        if((currentTime - startTime) > 15000){
            return false;
    }
        return true; 
    }

    public static void execute(Drivetrain drivetrain, CoralMechanism coralMech, double holdAngle, double kGrav, double kP, double targetDistInCm, boolean driveBack, double forward, double startTime){
        double rotate = 0; // We are not rotating. only driving straight 

        if (!driveBack){
            drivetrain.resetEncoders();
            while(drivetrain.convertEncoderTicksToCentimetres(drivetrain.getRawEncoder()) <= targetDistInCm && isInAutoTime(startTime)){
                drivetrain.arcadeDrive(forward, rotate);
                coralMech.closedLoopCoralArticulation(holdAngle, kGrav, kP);
            }
            drivetrain.arcadeDrive(0, 0);
        } else if (driveBack) {
            drivetrain.resetEncoders();
            while (drivetrain.convertEncoderTicksToCentimetres(drivetrain.getRawEncoder()) >= -targetDistInCm && isInAutoTime(startTime)) { 
                drivetrain.arcadeDrive(forward, rotate);
                coralMech.closedLoopCoralArticulation(holdAngle, kGrav, kP);
            }
            drivetrain.arcadeDrive(0, 0);
        }
    }
}
