package frc.robot.Subsystems;

public class PIDController {
    
    public final double kP;
    public final double kI;
    public final double kD;
    public double targetVal, currentError, lastError, outputMax, outputMin, current, errorSum;

    
    
    public PIDController(double currentSensorPos, double targetVal, double outputMin, double outputMax, double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.targetVal = targetVal;
        this.outputMax = outputMax;
        this.outputMin = outputMin;
        this.lastError = 0;
        this.currentError = targetVal - currentSensorPos;
    }

    public double getFinalOutput(){
        return capOutput((currentError*kP),1,-1); // only temporary, will change if we use kD/kI
        // return capOutput(currentError*kP + ((currentError-lastError)*kD) + (errorSum * kI));
    }

    public double capOutput(double input, double max, double min){
        if(input > max){
            return max;
        } else if (input < min){
            return min;
        } else {
            return input;
        }
    }

    public void updateError(double currentSensorPos){
        lastError = currentError;
        currentError = targetVal - currentSensorPos;
        // errorSum += Math.abs(currentError) < integralRange ? currentError : 0;

        // I still have yet to learn the different comparative symbols
    }

    
}
