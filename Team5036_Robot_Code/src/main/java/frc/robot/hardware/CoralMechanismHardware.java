package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Encoder; 
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

import frc.robot.RobotMap;

public class CoralMechanismHardware implements ICoralMechanismHardware {
    private TalonSRX shooterMotor;
    private SparkMax pivotMotor;
    private Encoder pivotCoralEncoder;

    public CoralMechanismHardware() {
        shooterMotor = new TalonSRX(RobotMap.CORALINTAKE_ROLLERS_CAN_ID);
        pivotMotor = new SparkMax(RobotMap.CORALINTAKE_PIVOT_CAN_ID, SparkLowLevel.MotorType.kBrushless);
        pivotCoralEncoder = new Encoder(RobotMap.DIO_PORT_A_CORAL_ENCODER, RobotMap.DIO_PORT_B_CORAL_ENCODER, false, Encoder.EncodingType.k4X); // make these constants uppercase
    }

    @Override
     public void run(double val) {
        shooterMotor.set(ControlMode.PercentOutput, val);
    }
    // WE BEAT ALT-F4!!!! But they broke our intake :( 


    @Override
    public void openLoopArticulation(double val) {
        pivotMotor.set(val);
    }

    @Override
    public double getPivotEncoderPos() {
        return pivotCoralEncoder.get();
    }
}
