// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.CoralMechanism;
import frc.robot.hardware.CoralMechanismHardware;
import frc.robot.hardware.ICoralMechanismHardware;
import frc.robot.Subsystems.algaeInOuttake;
import frc.robot.ci.ControllerInterface;
import frc.robot.hardware.IAlgaeInOuttakeHardware;
import frc.robot.hardware.AlgaeInOuttakeHardware;
import frc.robot.hardware.ClimberHardware;
import frc.robot.hardware.IClimberHardware;
import frc.robot.hardware.DrivetrainHardware;
import frc.robot.subsystems.Drivetrain;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  ControllerInterface ci;
  IAlgaeInOuttakeHardware algaeHardware;
  algaeInOuttake algae;
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  ICoralMechanismHardware coralHardware;
  IDrivetrainHardware drivetrainHardware;
  ControllerInterface ci;

  Drivetrain drivetrain;

  ControllerInterface ci;
  IClimberHardware climberhardware; 
  ClimberHardware climber; 
  private Drivetrain drivetrain;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  CoralMechanism coralMech;
  public Robot() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    coralHardware = new CoralMechanismHardware();
    coralMech = new CoralMechanism(coralHardware);
    ci = new ControllerInterface();
    algaeHardware = new AlgaeInOuttakeHardware();
    algae = new algaeInOuttake(algaeHardware);
    drivetrain = new Drivetrain(new DrivetrainHardware());
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    climber.setClimberMotorPower(0); 
    climber.zeroClimberEncoderPos(0); // maybe not needed here 

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double forward = ci.getDriveTrainForward();
    double rotate = ci.getDriveTrainRotate();
    
    if (ci.getClimbUp())  {
      climber.setClimberMotorPower(0.5);
    }

    if (ci.getClimbDown()) {
      climber.setClimberMotorPower(-0.5);
    }
    drivetrain.arcadeDrive(forward, rotate);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    if(ci.coralIntake()){
      coralMech.runIntake(0.4); // Testing purposes NOT set-in-stone value
    } else if (ci.coralOuttake()){
      coralMech.runOuttake(0.5); // Testing 
    }

    coralMech.openLoopCoralArticulation(ci.getOpenLoopArticulation());
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
