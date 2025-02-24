package frc.robot.subsystems;

import frc.robot.hardware.ICoralMechanismHardware;

public class CoralMechanism {
    private ICoralMechanismHardware coralHardware;
    private final double kFGravity = 0.0;
    private final double kP = 0.0; // copied kP values from last year, will change.
    private final double RAW_ENCODER_POS_ZERO = 1109;
    private final double RAW_ENCODER_POS_NINETY = 609;
    private final double ACCEPTABLE_RANGE = 0;
    public CoralMechanism(ICoralMechanismHardware coralHardware){
        this.coralHardware = coralHardware;
    }

    public void runIntake(double val){
        coralHardware.run(val);
    }

    public void runOuttake(double val){
        coralHardware.run(-val);
    }

    public void openLoopCoralArticulation(double val){
        coralHardware.openLoopArticulation(val);
    }

    private double encoderPosToAngle(double currentEncoderPos) {
        double m = (90 / (RAW_ENCODER_POS_NINETY -  RAW_ENCODER_POS_ZERO));
        double b = -(RAW_ENCODER_POS_ZERO * m);
        double angle = (m * currentEncoderPos) + b;
        return angle;
    }

    public double getCurrentAngle(){
        double currentEncoderPos = coralHardware.getPivotEncoderPos();
        return encoderPosToAngle(currentEncoderPos);
    }

    public void closedLoopCoralArticulation(double target, double tempGravity, double tempP) {
        double currentAngle = getCurrentAngle();
        double error = target - currentAngle;
       // double feedForwardVal = Math.cos(Math.toRadians(currentAngle) * kFGravity);
       double feedForwardVal = Math.cos(Math.toRadians(currentAngle)) * tempGravity;

        if (Math.abs(error) <= ACCEPTABLE_RANGE) {
            coralHardware.openLoopArticulation(feedForwardVal);
        } else {
            double modifiedOutput = (error * kP) + feedForwardVal;
            coralHardware.openLoopArticulation(modifiedOutput);
        }
    }

    public double getRawEncoderPosition() {
        return coralHardware.getPivotEncoderPos(); 
    }
}
