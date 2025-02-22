package frc.robot.hardware;

public interface IDrivetrainHardware {
    public void setRightSide(double val); 

    public void setLeftSide(double val);

    public void zeroGyroPos();
}
