package frc.robot.ci;

import edu.wpi.first.wpilibj.Joystick;

public class ControllerInterface {
    private Joystick drivetrainController;
    private Joystick operatorController;
        
    public ControllerInterface() {
        drivetrainController = new Joystick(0);
    }

    public double getDrivetrainForward() {
        return -drivetrainController.getRawAxis(1);
    }

    public double getDrivetrainRotate() {
        return drivetrainController.getRawAxis(4);
    }

    public boolean getWinchRetract() {
        return drivetrainController.getRawAxis(3) > .2;
    }

    public boolean getWinchRelease() {
        return drivetrainController.getRawButtonPressed(6);
    }

    public boolean coralIntake() {
        return operatorController.getRawButtonPressed(4);
    }

    public boolean coralOuttake() {
        return operatorController.getRawButtonPressed(1);
    }

    public double getCoralOpenLoopArticulation() {
        if(operatorController.getRawButtonPressed(7)) {
            return operatorController.getRawAxis(5);
        }
        return 0.;
    }

    public boolean getAlgaeIntake() {
        return operatorController.getRawAxis(2) > .3;
    }

    public boolean getAlgaeOuttake() {
        return operatorController.getRawButtonPressed(5);
    }

    public double getAlgaePivot() {
        return operatorController.getRawAxis(1);
    }
}

