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

    public boolean coralIntake(){
        return drivetrainController.getRawButton(1);
    }

    public boolean coralOuttake(){
        return drivetrainController.getRawButton(2);
    }

    public double getOpenLoopArticulation(){
        return drivetrainController.getRawAxis(3); // This is only Temporary and FOR TESTING, will change once we map to controller
    }

    public boolean closedLoopArticulation(){
        return drivetrainController.getRawButton(4);
    }
  
    public boolean getClimbUp() {
        return drivetrainController.getRawButton(3/*number insert here */); // winch winds up 
    }

    public boolean getClimbDown() {
        return drivetrainController.getRawButton(4 /*again, insert a button here */); // winch releases
    }
}

