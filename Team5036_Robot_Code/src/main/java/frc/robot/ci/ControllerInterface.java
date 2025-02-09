package frc.robot.ci;

import edu.wpi.first.wpilibj.Joystick;

public class ControllerInterface {
    private Joystick drivetrainController;
    private Joystick operatorController; 
        
    public ControllerInterface() {
        drivetrainController = new Joystick(0);
    }

    public double getDriveTrainForward() {
        return -drivetrainController.getRawAxis(1);
    }

    public double getDriveTrainRotate() {
        return drivetrainController.getRawAxis(4);
    }

    public double getClimbMotorPower() {
        return drivetrainController.getRawAxis(3/*number insert here */);
    }

}

