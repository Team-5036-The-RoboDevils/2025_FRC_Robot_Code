package frc.robot.ci;

import edu.wpi.first.wpilibj.Joystick;

public class ControllerInterface {
    private Joystick drivetrainController;
    private Joystick operatorController;
    private Joystick tuningJoystick; 
        
    public ControllerInterface() {
        drivetrainController = new Joystick(0);
        operatorController = new Joystick(1);
        tuningJoystick = new Joystick(2); 
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
        return drivetrainController.getRawButton(6);
    }

    public boolean coralIntake() {
        return operatorController.getRawButton(4);
    }

    public boolean coralOuttake() {
        return operatorController.getRawButton(1);
    }

    public double getCoralOpenLoopArticulation() {
        if(operatorController.getRawButton(7)) {
            return operatorController.getRawAxis(5);
        }
        return 0.;
    }

    public boolean getAlgaeIntake() {
        return operatorController.getRawAxis(2) > .3;
    }

    public boolean getAlgaeOuttake() {
        return operatorController.getRawButton(5);
    }

    public double getAlgaePivot() {
        return operatorController.getRawAxis(1);
    }

    public double getArticulatedIntakePIDTuningAxis() {
        double a = 0; 
        double b = 0.2; 
        return ((-tuningJoystick.getRawAxis(3) + 1 / 2 ) * (b - a)) + a; 
    }

    public boolean getDebugTuningButton() {
        return tuningJoystick.getRawButton(4); 
    }
}

