package frc.robot.hardware;

public interface IAlgaeMechanismHardware {

    public void setPivotMotor(double val);

    public void setIntakeMotor(double val);

    public double getPivotEncoderPos();

    public void resetPivotEncoderPos();
}
