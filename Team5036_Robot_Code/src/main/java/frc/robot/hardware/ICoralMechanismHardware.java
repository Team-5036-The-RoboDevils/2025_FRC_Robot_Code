package frc.robot.hardware;

public interface ICoralMechanismHardware {
    public void run(double val);

    public void openLoopArticulation(double val);

    public double getPivotEncoderPos();
}
