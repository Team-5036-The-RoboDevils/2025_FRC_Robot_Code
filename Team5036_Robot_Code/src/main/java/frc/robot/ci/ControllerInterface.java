package frc.robot.ci;

import edu.wpi.first.wpilibj.Joystick;

public class ControllerInterface {
    private Joystick drivetrainController;
        
    public ControllerInterface() {
        drivetrainController = new Joystick(0);
    }

    public double getDriveTrainForward() {
        return -drivetrainController.getRawAxis(1);
    }

    public double getDriveTrainRotate() {
        return drivetrainController.getRawAxis(4);
    }
}

