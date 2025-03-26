package frc.robot.subsystems;

import frc.robot.hardware.DrivetrainHardware;
import frc.robot.hardware.IDrivetrainHardware;

public class Drivetrain {
    private IDrivetrainHardware hardware;
    double ACCEPTABLE_RANGE = 3; 
    double kPDriving = 0.0; // Change once tempP has been set and tested until successful
    double kPTurning = 0.0; // change once tempP has been set and tested until successful

    public Drivetrain(IDrivetrainHardware hardware) {
        this.hardware = hardware;
    }

    public double capInput(double input, double max, double min){
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

    public void resetGyro() {
        hardware.zeroGyroPos();
    }

    public void setGyroZero(){
        hardware.setGyroAngle();
    }

    public double convertEncoderTicksToCentimetres(double encoderTicks) {
        double converterConst = 37.03;
        double convertedDist = encoderTicks / converterConst; 
        return convertedDist;  
    }

    public double getRawEncoder() {
        return hardware.getDriveEncoderPos(); 
    }

    public double getDistanceTravelled() {
        double currentPos = hardware.getDriveEncoderPos();
        return convertEncoderTicksToCentimetres(currentPos); 
    }
}
