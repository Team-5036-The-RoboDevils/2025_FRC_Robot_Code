package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.RobotMap;

public class AlgaeInOuttakeHardware implements IAlgaeInOuttakeHardware {

    private TalonSRX pivot;
    private TalonSRX intake;

    public AlgaeInOuttakeHardware() {
        pivot = new TalonSRX(RobotMap.ALGAE_PIVOT_CAN_ID);
        intake = new TalonSRX(RobotMap.ALGAE_PIVOT_CAN_ID);
    }

    public void setPivotMotor(double val) {
        pivot.set(ControlMode.PercentOutput, val);
    }

    public void setIntakeMotor(double val) {
        intake.set(ControlMode.PercentOutput, val);
    }

    public double getPivotEncoderPos() {
        return 0.; // placeholder
    }

    public void resetPivotEncoderPos() {
        return; // placeholder
    }
}
