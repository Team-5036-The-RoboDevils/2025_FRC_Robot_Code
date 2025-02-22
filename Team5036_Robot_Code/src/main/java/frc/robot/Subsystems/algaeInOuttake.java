package frc.robot.Subsystems;

import frc.robot.hardware.IAlgaeInOuttakeHardware;

public class algaeInOuttake {
    
    private IAlgaeInOuttakeHardware hardware;
    public algaeInOuttake(IAlgaeInOuttakeHardware hardware) {
        this.hardware = hardware;
    }

    public void setPivotMotor(double val) {
        hardware.setPivotMotor(capVal(val, -.6, .6));
    }

    public void setIntakeMotor(double val) {
        hardware.setIntakeMotor(capVal(val, -.6, .6));
    }

    public double capVal(double val, double min, double max) {
        if (val < min) {
            return min;
        } else if (val > max) {
            return max;
        }
        return val;
    }

    public double getEncoderPos() {
        return hardware.getPivotEncoderPos();
    }

    public void resetEncoderpos() {
        hardware.resetPivotEncoderPos();
        return;
    }
}
