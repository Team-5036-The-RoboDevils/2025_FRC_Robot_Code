package frc.robot.subsystems;

import frc.robot.hardware.IDrivetrainHardware;

public class Drivetrain {
    private IDrivetrainHardware hardware;
    double ACCEPTABLE_RANGE = 3; 
    double kPDriving = 0.0; // Change once tempP has been set and tested until successful
    double kPTurning = 0.0; // change once tempP has been set and tested until successful

    public Drivetrain(IDrivetrainHardware hardware) {
        this.hardware = hardware;
    }

    private double capInput(double input, double max, double min){
        if(input > max){
            return max;
        } else if (input < min){
            return min;
        } else {
            return input;
        }
    }

    public void arcadeDrive(double speed, double rotate){
        double leftSidePower = capInput(speed + rotate, 1, -1);
        double rightSidePower = capInput(speed - rotate, 1, -1);
        hardware.setLeftSide(leftSidePower);
        hardware.setRightSide(rightSidePower);
    }

    public void resetEncoders() {
        hardware.resetDriveEncoders();
    }

    public double getGyroAngle() {
        return hardware.getGyroAngle(); 
    }

    public double convertEncoderTicksToCentimetres(double encoderTicks) {
        return encoderTicks; 
        // ATTENTION: IMPLEMENT THIS CLASS ONCE IN THE SHOP. CHECK HOW MANY ENCODER VALUES = TO ONE METRE. 
        // THEN FIX THIS CODE
    }

    public double getDistanceTravelled() {
        return convertEncoderTicksToCentimetres(hardware.getDriveEncoderPos()); 
    }
}
