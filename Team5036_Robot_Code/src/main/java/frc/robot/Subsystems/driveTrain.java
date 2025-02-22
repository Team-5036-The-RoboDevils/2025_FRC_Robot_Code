package frc.robot.Subsystems;
import frc.robot.hardware.IDrivetrainHardware;
import frc.robot.hardware.DrivetrainHardware;

public class Drivetrain {

    private IDrivetrainHardware hardware;
    public Drivetrain(IDrivetrainHardware hardware) {
        this.hardware = hardware;
        
    }

    public void setLeftSidePower(double val){
        hardware.setLeftSide(val);
    }

    public void setRightSidePower(double val){
        hardware.setRightSide(val);
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
