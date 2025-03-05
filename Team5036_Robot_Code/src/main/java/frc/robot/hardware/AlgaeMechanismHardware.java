package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

import frc.robot.RobotMap;

public class AlgaeMechanismHardware implements IAlgaeMechanismHardware {

    private SparkLowLevel pivot;
    private TalonSRX intake;

    public AlgaeMechanismHardware() {
        pivot = new SparkMax(RobotMap.ALGAE_PIVOT_CAN_ID, SparkLowLevel.MotorType.kBrushless);
        intake = new TalonSRX(RobotMap.ALGAE_ROLLER_CAN_ID);
    }

    public void setPivotMotor(double val) {
        pivot.set(val);
    }

    public void setIntakeMotor(double val) {
        intake.set(ControlMode.PercentOutput, val);
    }
}
