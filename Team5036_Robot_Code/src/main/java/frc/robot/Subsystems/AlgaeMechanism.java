package frc.robot.subsystems;

import frc.robot.hardware.IAlgaeMechanismHardware;

public class AlgaeMechanism {
    
    private IAlgaeMechanismHardware hardware;
    public AlgaeMechanism(IAlgaeMechanismHardware hardware) {
        this.hardware = hardware;
    }

    public void setPivotMotor(double val) {
        hardware.setPivotMotor(capVal(val, -1, 1));
    }

    public void setIntakeMotor(double val) {
        hardware.setIntakeMotor(capVal(val, -1, 1));
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
