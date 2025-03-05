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
import frc.robot.subsystems.Climber; 
import frc.robot.hardware.DrivetrainHardware;
import frc.robot.hardware.IDrivetrainHardware;
import frc.robot.subsystems.CoralMechanism;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.AlgaeMechanism;
import frc.robot.autonomous.*;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private static final String taxi = "TAXI"; 

  private static final String leftPreload = "LEFT_CORAL_PRELOAD"; 
  private static final String leftPreloadAndHP = "LEFT_PRELOAD_AND_HP"; 
  private static final String leftPreloadAndScoreL1 = "LEFT_PRELOAD_AND_SCOREL1"; 
  private static final String leftPreloadAndScoreL2 = "LEFT_PRELOAD_AND_SCOREL2"; 

  private static final String rightPreload = "RIGHT_CORAL_PRELOAD"; 
  private static final String rightPreloadAndHP = "RIGHT_PRELOAD_AND_SCORE"; 
  private static final String rightPreloadAndScoreL1 = "RIGHT_PRELOAD_AND_HPL1"; 
  private static final String rightPreloadAndScoreL2 = "RIGHT_PRELOAD_AND_SCOREL2"; 

  private static final String middlePreload = "MIDDLE_PRELOAD"; 
  private static final String middlePreloadAndHPLeft = "MIDDLE_PRELOAD_AND_HP_Left"; 
  private static final String middlePreloadAndScoreL1 = "MIDDLE_PRELOAD_AND_SCOREL1"; 
  private static final String middlePreloadAndHPRight = "MIDDLE_PRELOAD_AND_HP_Right";
  private static final String middlePreloadAndHPRightL2 = "MIDDLE_PRELOAD_AND_HP_RIGHT_L2"; 
  private static final String middlePreloadAndHPLeftL2 = "MIDDLE_PRELOAD_AND_HP_LEFT_L2"; 

  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private ControllerInterface ci;
  private IAlgaeMechanismHardware algaeHardware;
  private AlgaeMechanism algaeMech;
  private ICoralMechanismHardware coralHardware;
  private IDrivetrainHardware drivetrainHardware;
  private IClimberHardware climberhardware; 

  private Climber climber; 

  private Drivetrain drivetrain;
  private CoralMechanism coralMech;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  public Robot() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    m_chooser.addOption("TAXI", taxi);
    m_chooser.addOption("LEFT_SCORE_PRELOAD", leftPreload);
    m_chooser.addOption("LEFT_SCORE_PRELOAD_AND_HP", leftPreloadAndHP); 
    m_chooser.addOption("LEFT_SCORE_PRELOAD_AND_L1", leftPreloadAndScoreL1); 
    m_chooser.addOption("LEFT_SCORE_PRELOAD_AND_L2", leftPreloadAndScoreL2); 
    m_chooser.addOption("RIGHT_SCORE_PRELOAD", rightPreload); 
    m_chooser.addOption("RIGHT_SCORE_PRELOAD_AND_HP", rightPreloadAndHP); 
    m_chooser.addOption("RIGHT_SCORE_PRELOAD_AND_L1", rightPreloadAndScoreL1); 
    m_chooser.addOption("RIGHT_SCORE_PRELOAD_AND_L2", rightPreloadAndScoreL2); 
    m_chooser.addOption("MIDDLE_SCORE_PRELOAD", middlePreload); 
    m_chooser.addOption("MIDDLE_SCORE_AND_HP_LEFT", middlePreloadAndHPLeft); 
    m_chooser.addOption("MIDDLE_PRELOAD_AND_HP_RIGHT", middlePreloadAndHPRight);
    m_chooser.addOption("MIDDLE_SCORE_PRELOAD_AND_L1_RIGHT_HP", middlePreloadAndScoreL1); 
    m_chooser.addOption("MIDDLE_SCORE_PRELOAD_AND_L1_LEFT_HP", middlePreloadAndHPLeft);
    m_chooser.addOption("MIDDLE_SCORE_PRELOAD_AND_L2_RIGHT_HP", middlePreloadAndHPRightL2); 
    m_chooser.addOption("MIDDLE_SCORE_PRELOAD_AND_L2_LEFT_HP", middlePreloadAndHPLeftL2);
    SmartDashboard.putData("Auto choices", m_chooser);
    

    ci = new ControllerInterface();
    climberhardware = new ClimberHardware(); 
    climber = new Climber(climberhardware); 
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
  public void robotPeriodic() {
    SmartDashboard.putNumber("Raw Encoder Value", coralMech.getRawEncoderPosition()); 
    //System.out.println(System.currentTimeMillis() + " " + coralMech.getRawEncoderPosition()); 
    SmartDashboard.putNumber("Converted Angle", coralMech.getCurrentAngle()); 
    //System.out.println(System.currentTimeMillis() + " " + coralMech.getCurrentAngle()); 
    SmartDashboard.putNumber("Tuning Axis", ci.getArticulatedIntakePIDTuningAxis()); 
    SmartDashboard.putNumber("Drivetrain Encoder Ticks ", drivetrain.getRawEncoder()); 
    SmartDashboard.putNumber("Drivetrain Distance (cm)", drivetrain.convertEncoderTicksToCentimetres(drivetrain.getRawEncoder()));
  }
  
  private static boolean isInAutoTime(double startTime){
            
    double currentTime = System.currentTimeMillis();
    if((currentTime - startTime) > 15000){
        return false;
    }
    return true;
  }
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

    if (m_autoSelected == taxi) {
      DriveDistanceBangBang.execute(drivetrain, coralMech, 11, 0.0711, 0.0289, 135, false, 0.4, System.currentTimeMillis()); 
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    climber.climberActuation(0); 
    climber.resetEncoder(); // maybe not needed here 
    //drivetrain.

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
      climber.climberActuation(0.5);
    }
    else if (ci.getWinchRelease()) {
      climber.climberActuation(-0.5);
    } else {
      climber.climberActuation(0); 
    }

    // coral INTAKE
    if(ci.coralIntake()){
      coralMech.runIntake(1); // Testing purposes NOT set-in-stone value
      //System.out.println("INTAKING" + System.currentTimeMillis());
    } else if (ci.coralOuttake() ){
      coralMech.runOuttake(1); // Testing 
      //System.out.println("OUTTAKING"+ System.currentTimeMillis());
    } else {
      //System.out.println("STOPPING" + System.currentTimeMillis());
      coralMech.runIntake(0);
    }

    // coral PIVOT
    if (ci.getHP() > 0.2) {
      coralMech.closedLoopCoralArticulation(25, 0.0711, 0.0289);
    } else if(ci.getToL1()){
      coralMech.closedLoopCoralArticulation(-28, 0.0711, 0.0289);
    } else if(ci.getToL2()){
      coralMech.closedLoopCoralArticulation(-11, 0.0711, 0.0289);
    } else if(ci.getInside() > 0.1) {
      coralMech.closedLoopCoralArticulation(200,0.0711, 0.0289);
    } else {
      coralMech.openLoopCoralArticulation(0);
    }
    //coralMech.openLoopCoralArticulation(ci.getCoralOpenLoopArticulation()); UNCOMMENT FOR OPEN LOOP STUFF 

    //algae INTAKE
    if (ci.getAlgaeIntake()) {
      algaeMech.setIntakeMotor(.5);
    } else if (ci.getAlgaeOuttake()) {
      algaeMech.setIntakeMotor(-.5);
    } else {
      algaeMech.setIntakeMotor(0); 
    }

    // algae PIVOT
    if (ci.getAlgaePivot() > 0.2 || ci.getAlgaePivot() < -0.2) {
      algaeMech.setPivotMotor(ci.getAlgaePivot()); 
    } else {
      algaeMech.setPivotMotor(0);
    }
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
