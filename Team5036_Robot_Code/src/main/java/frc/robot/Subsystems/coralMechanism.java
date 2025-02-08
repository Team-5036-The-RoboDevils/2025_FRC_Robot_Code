package frc.robot.Subsystems;

import frc.robot.hardware.ICoralMechanismHardware;

public class coralMechanism {
    private ICoralMechanismHardware coralHardware;
    private final double kFGravity = 0.082188;
    private final double kP = 0.0019; // copied kP values from last year, will change.
    private final double Extreme_Pos_Zero = -1;
    private final double Extreme_Pos_Ninety = 1;
    private final double AcceptableRange = 7;
    public coralMechanism(ICoralMechanismHardware coralHardware){
        this.coralHardware = coralHardware;
    }

    public void runIntake(double val){
        coralHardware.intakeCoral(val);
    }

    public void runOuttake(double val){
        coralHardware.outtakeCoral(val);
    }

    public void openLoopCoralArticulation(double val){
        coralHardware.openLoopArticulation(val);
    }

    private double encoderPosToAngle(double currentEncoderPos) {
        double m = (90 / (Extreme_Pos_Ninety -  Extreme_Pos_Zero));
        double b = -(Extreme_Pos_Zero * m);
        double angle = (m * currentEncoderPos) + b;
        return angle;
    }

    public double getCurrentAngle(){
        double currentEncoderPos = coralHardware.getPivotEncoderPos();
        return encoderPosToAngle(currentEncoderPos);
    }

    public void closedLoopCoralArticulation(double target){
        double currentAngle = getCurrentAngle();
        double error = target - currentAngle;
        double feedForwardVal = Math.cos(Math.toRadians(currentAngle) * kFGravity);

        if(error <= AcceptableRange){
            coralHardware.openLoopArticulation(feedForwardVal);
        } else {
            double modifiedOutput = (error * kP) + feedForwardVal;
            coralHardware.openLoopArticulation(modifiedOutput);
        }
    }
}
