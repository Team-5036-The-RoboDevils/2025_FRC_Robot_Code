// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.CoralMechanismHardware;
import frc.robot.hardware.ICoralMechanismHardware;
import frc.robot.ci.ControllerInterface;
import frc.robot.hardware.IAlgaeMechanismHardware;
import frc.robot.hardware.AlgaeMechanismHardware;
import frc.robot.hardware.ClimberHardware;
import frc.robot.hardware.IClimberHardware;
import frc.robot.hardware.DrivetrainHardware;
import frc.robot.hardware.IDrivetrainHardware;
import frc.robot.subsystems.CoralMechanism;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.AlgaeMechanism;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private ControllerInterface ci;
  private IAlgaeMechanismHardware algaeHardware;
  private AlgaeMechanism algaeMech;
  private ICoralMechanismHardware coralHardware;
  private IDrivetrainHardware drivetrainHardware;
  private IClimberHardware climberhardware; 
  private ClimberHardware climber; 
  private Drivetrain drivetrain;
  private CoralMechanism coralMech;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  public Robot() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    ci = new ControllerInterface();
    algaeHardware = new AlgaeMechanismHardware();
    algaeMech = new AlgaeMechanism(algaeHardware);
    coralHardware = new CoralMechanismHardware();
    coralMech = new CoralMechanism(coralHardware);
    drivetrainHardware = new DrivetrainHardware();
    drivetrain = new Drivetrain(drivetrainHardware);
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
    //drive
    double forward = ci.getDrivetrainForward();
    double rotate = ci.getDrivetrainRotate();
    drivetrain.arcadeDrive(forward, rotate);
    
    // climb mechanism
    if (ci.getWinchRetract())  {
      climber.setClimberMotorPower(0.5);
    }
    if (ci.getWinchRelease()) {
      climber.setClimberMotorPower(-0.5);
    }

    // coral
    if(ci.coralIntake()){
      coralMech.runIntake(0.4); // Testing purposes NOT set-in-stone value
    } else if (ci.coralOuttake()){
      coralMech.runOuttake(0.5); // Testing 
    }
    coralMech.openLoopCoralArticulation(ci.getCoralOpenLoopArticulation());

    //algae
    if (ci.getAlgaeIntake()) {
      algaeMech.setIntakeMotor(.5);
    } else if (ci.getAlgaeOuttake()) {
      algaeMech.setIntakeMotor(-.5);
    }
    algaeMech.setPivotMotor(ci.getAlgaePivot());
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
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
