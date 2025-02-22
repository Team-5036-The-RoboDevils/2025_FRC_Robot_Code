package frc.robot.subsystems;

import frc.robot.hardware.IDrivetrainHardware;

public class Drivetrain {
    private IDrivetrainHardware hardware;

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
}

