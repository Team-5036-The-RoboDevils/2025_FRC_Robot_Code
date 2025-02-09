package frc.robot.hardware;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Angle;
import frc.robot.RobotMap;
import com.ctre.phoenix6.*;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;

public class ClimberHardware implements IClimberHardware {
    private TalonFX climber; 
    //private TalonFXConfiguration climberencoderconfig = new TalonFXConfiguration(); 
    private StatusCode climbencoder; 

    public ClimberHardware() {
        climber = new TalonFX(RobotMap.CLIMB_TALONFX_ID); 
       // climber.getConfigurator().apply(climberencoderconfig); 
    }

    @Override 
    public void setClimberMotorPower(double speed) {
        climber.set(speed); 
    }

    @Override
    public double getClimberMotorPos() {
        double pos = Double.parseDouble((climber.getPosition().toString())); 
        return pos; 
    }

    public void setClimberEncoderPos(double val) {
        climber.setPosition(val); 
    }
    
}
