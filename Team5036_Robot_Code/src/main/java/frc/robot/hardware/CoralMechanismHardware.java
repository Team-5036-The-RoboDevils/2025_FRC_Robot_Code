package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.RobotMap;

public class CoralMechanismHardware implements ICoralMechanismHardware {
    private TalonSRX shooterMotor;
    private SparkMax pivotMotor;
    private RelativeEncoder pivotEncoder;

    public CoralMechanismHardware(){

        shooterMotor = new TalonSRX(RobotMap.CORAL_SHOOTER_CAN_ID);
        pivotMotor = new SparkMax(RobotMap.CORAL_PIVOT_CAN_ID, MotorType.kBrushless);
        pivotEncoder = pivotMotor.getEncoder();
     
    }

   
    public void intakeCoral(double val){
        shooterMotor.set(ControlMode.PercentOutput, val);
    }

    public void outtakeCoral(double val){
        shooterMotor.set(ControlMode.PercentOutput, val);
    }

    public void openLoopArticulation(double val){
        pivotMotor.set(val);
    }

   public double getPivotEncoderPos(){
        return pivotEncoder.getPosition();
   }
}
