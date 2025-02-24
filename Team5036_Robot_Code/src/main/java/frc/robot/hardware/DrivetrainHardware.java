package frc.robot.hardware;
import com.ctre.phoenix6.configs.GyroTrimConfigs;
import com.revrobotics.*;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;
import com.studica.frc.jni.AHRSJNI;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.util.sendable.Sendable; 
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Encoder; 


public class DrivetrainHardware implements IDrivetrainHardware { // makes a promise to java to implement
    // the functions that are in IDrivetrainHardware 
    private SparkLowLevel left1; 
    private SparkLowLevel left2; 
    private SparkLowLevel right1; 
    private SparkLowLevel right2; 
    private AHRS gyro; 
    private Encoder driveEncoder; 

    public DrivetrainHardware() {
        left1 = new SparkMax(RobotMap.DRIVE_L1_CAN_ID, SparkLowLevel.MotorType.kBrushless); 
        left2 = new SparkMax(RobotMap.DRIVE_L2_CAN_ID, SparkLowLevel.MotorType.kBrushless); 
        right1 = new SparkMax(RobotMap.DRIVE_R1_CAN_ID, SparkLowLevel.MotorType.kBrushless); 
        right2 = new SparkMax(RobotMap.DRIVE_R2_CAN_ID, SparkLowLevel.MotorType.kBrushless); 
       // gyro = new AHRS(SPI.Port.kMXP); // RoboRIO gyro, to control robot position FIX THIS CODE
        gyro = new AHRS(NavXComType.kMXP_SPI); // RoboRIO gyro, to control robot position FIX THIS CODE
        //driveEncoder = new Encoder()

        // ATTENTION: Add external encoders here later, once we actually put them on LOL
        // If we have issues with the gyro's angles again, add in code to reset the gyro to zero while calibrating. 
    }
    
    @Override
    public void setRightSide(double val) {
        right1.set(-val); // Remember, this could be positive or negative. (Random, so change accordingly)
        right2.set(-val); // ---- This one is good.
    }

    @Override 
    public void setLeftSide(double val) {
        left1.set(val); 
        left2.set(val); 
    }

    @Override
    public void zeroGyroPos() {
        // TODO: do this
    }
}
