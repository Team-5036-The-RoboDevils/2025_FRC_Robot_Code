package frc.robot.hardware;

public interface IClimberHardware {
    public void setClimberMotorPower(double val); 

    public double getClimberMotorPos(); 

    public void setClimberEncoderPos(double val); 
}
