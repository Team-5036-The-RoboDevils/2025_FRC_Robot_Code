package frc.robot.hardware;

import edu.wpi.first.units.measure.Angle;

public interface IClimberHardware {
    public void setClimberMotorPower(double val); 

    public double getClimberMotorPos(); 

    public void zeroClimberEncoderPos(double val); 
}
