package frc.robot.hardware;

public interface IAlgaeInOuttakeHardware {

    public void setPivotMotor(double val);

    public void setIntakeMotor(double val);

    public double getPivotEncoderPos();

    public void resetPivotEncoderPos();
}
