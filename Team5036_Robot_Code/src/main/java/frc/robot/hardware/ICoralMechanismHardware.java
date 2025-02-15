package frc.robot.hardware;

public interface ICoralMechanismHardware {
    public void intakeCoral(double val);

    public void outtakeCoral(double val);

    public void openLoopArticulation(double val);

    public double getPivotEncoderPos();
}
