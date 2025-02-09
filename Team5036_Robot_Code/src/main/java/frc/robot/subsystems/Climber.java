package frc.robot.subsystems;

import frc.robot.hardware.IClimberHardware;

public class Climber {
    private IClimberHardware climber; 

    public Climber(IClimberHardware climber) {
        this.climber = climber;

    }

    public double capInput(double val, double min, double max) {
        if (val < min) {
            return min; 
        }
        if (val > max) {
            return max; 
        }
        return val; 
    } // This function might not be necessary. 

    public double getClimberEncoderPos() {
        return climber.getClimberMotorPos(); 
    }

    public void climberActuation(double val) {
        double cappedVal = capInput(val, -0.8, 0.8); 
        climber.setClimberMotorPower(cappedVal); 
    } // ngl, probably not necessary to have a capped value.

    public void resetEncoder() {
        climber.setClimberEncoderPos(0.0);
    }
 // We could add a PID if need be, to automate the climb and make it happen quickly. 
 
}
