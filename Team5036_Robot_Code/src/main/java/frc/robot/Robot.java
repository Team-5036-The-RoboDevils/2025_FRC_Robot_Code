// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
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

  private static final String leftPreloadL2 = "LEFT_CORAL_PRELOAD_L2"; 
  private static final String leftPreloadL1 = "LEFT_CORAL_PRELOAD_L1"; 
  private static final String leftPreloadAndHP = "LEFT_PRELOAD_AND_HP"; 
  private static final String leftPreloadAndScoreL1 = "LEFT_PRELOAD_AND_SCOREL1"; 
  private static final String leftPreloadAndScoreL2 = "LEFT_PRELOAD_AND_SCOREL2"; 

  private static final String rightPreloadL2 = "RIGHT_CORAL_PRELOAD_L2"; 
  private static final String rightPreloadL1 = "RIGHT_CORAL_PRELOAD_L1"; 
  private static final String rightPreloadAndHP = "RIGHT_PRELOAD_AND_SCORE"; 
  private static final String rightPreloadAndScoreL1 = "RIGHT_PRELOAD_AND_HPL1"; 
  private static final String rightPreloadAndScoreL2 = "RIGHT_PRELOAD_AND_SCOREL2"; 

  private static final String middlePreloadL2 = "MIDDLE_PRELOAD_L2"; 
  private static final String middlePreloadL1 = "MIDDLE_PRELOAD_L1"; 
  private static final String middlePreloadAndHPLeft = "MIDDLE_PRELOAD_AND_HP_Left"; 
  private static final String middlePreloadAndScoreL1 = "MIDDLE_PRELOAD_AND_SCOREL1"; 
  private static final String middlePreloadAndHPRight = "MIDDLE_PRELOAD_AND_HP_Right";
  private static final String middlePreloadAndHPRightL2 = "MIDDLE_PRELOAD_AND_HP_RIGHT_L2"; 
  private static final String middlePreloadAndHPLeftL2 = "MIDDLE_PRELOAD_AND_HP_LEFT_L2"; 
  private static final String right_left_Straight_L2 = "RIGHT_LEFT_STRAIGHT_L2"; 
  private static final String right_left_Straight_L1 = "RIGHT_LEFT_STRAIGHT_L1"; 

  private static final String right_left_Straight_L3 = "RIGHT_LEFT_STRAIGHT_L3"; 
  private static final String middlePreloadL3 = "MIDDLE_PRELOAD_L3"; 
  private static final String getAlgaeOffL3 = "GET_ALGAE_OFF_L3"; 
  
  private static final String pushAnotherRobot = "PUSH_ROBOT_TAXI"; 
  // private static final String RIGHTLEFT_STRAIGHT_HP_L1_STRING

  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private ControllerInterface ci;
  private IAlgaeMechanismHardware algaeHardware;
  private AlgaeMechanism algaeMech;
  private ICoralMechanismHardware coralHardware;
  private IDrivetrainHardware drivetrainHardware;
  private IClimberHardware climberhardware; 
  private final double kGrav = 0.0289; 
  private final double kP = 0.0711; 
  private final double L2 = -11; 
  private final double L1 = -28; 
  private final double L3 = 31; 
  private final double HP = 25; 
  private final double Inside = 199; 

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
    m_chooser.addOption("LEFT_SCORE_PRELOAD_L2", leftPreloadL2);
    m_chooser.addOption("LEFT_SCORE_PRELOAD_L1", leftPreloadL1); 
    m_chooser.addOption("LEFT_SCORE_PRELOAD_AND_HP", leftPreloadAndHP); 
    m_chooser.addOption("LEFT_SCORE_PRELOAD_AND_L1", leftPreloadAndScoreL1); 
    m_chooser.addOption("LEFT_SCORE_PRELOAD_AND_L2", leftPreloadAndScoreL2); 
    m_chooser.addOption("RIGHT_SCORE_PRELOAD_L2", rightPreloadL2); 
    m_chooser.addOption("RIGHT_SCORE_PRELOAD_L1", rightPreloadL1); 
    m_chooser.addOption("RIGHT_SCORE_PRELOAD_AND_HP", rightPreloadAndHP); 
    m_chooser.addOption("RIGHT_SCORE_PRELOAD_AND_L1", rightPreloadAndScoreL1); 
    m_chooser.addOption("RIGHT_SCORE_PRELOAD_AND_L2", rightPreloadAndScoreL2); 
    m_chooser.addOption("MIDDLE_SCORE_PRELOAD_L2", middlePreloadL2); 
    m_chooser.addOption("MIDDLE_SCORE_PRELOAD_L1", middlePreloadL1); 
    m_chooser.addOption("MIDDLE_SCORE_PRELOAD_L3", middlePreloadL3); 
    m_chooser.addOption("MIDDLE_SCORE_AND_HP_LEFT", middlePreloadAndHPLeft); 
    m_chooser.addOption("MIDDLE_PRELOAD_AND_HP_RIGHT", middlePreloadAndHPRight);
    m_chooser.addOption("MIDDLE_SCORE_PRELOAD_AND_L1_RIGHT_HP", middlePreloadAndScoreL1); 
    m_chooser.addOption("MIDDLE_SCORE_PRELOAD_AND_L1_LEFT_HP", middlePreloadAndHPLeft);
    m_chooser.addOption("MIDDLE_SCORE_PRELOAD_AND_L2_RIGHT_HP", middlePreloadAndHPRightL2); 
    m_chooser.addOption("MIDDLE_SCORE_PRELOAD_AND_L2_LEFT_HP", middlePreloadAndHPLeftL2);
    m_chooser.addOption("RIGHT/LEFT_SCORE_PRELOAD_L2_STRAIGHT", right_left_Straight_L2); 
    m_chooser.addOption("RIGHT/LEFT_SCORE_PRELOAD_L1_STRAIGHT", right_left_Straight_L1);
    m_chooser.addOption("RIGHT/LEFT_SCORE_PRELOAD_L3_STRAIGHT", right_left_Straight_L3);
    m_chooser.addOption("RIGHT/LEFT_SCORE_PRELOAD_L1_STRAIGHT", right_left_Straight_L1);
    m_chooser.addOption("GET_ALGAE_OFF_L3", getAlgaeOffL3);
    m_chooser.addOption("PUSH_ROBOT_TAXI", pushAnotherRobot);
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
    CameraServer.startAutomaticCapture(); 
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
    SmartDashboard.putNumber("Gyro Angle", drivetrain.getGyroAngle()); 
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
    long startOfAuto = System.currentTimeMillis(); 
    m_autoSelected = m_chooser.getSelected();
    // RIGHT = BLUE CAGES, LEFT = RED CAGES 
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    drivetrain.resetEncoders();
    drivetrain.resetGyro(); 
    System.out.println("Auto selected: " + m_autoSelected);

    if (m_autoSelected == taxi) { // TAXI WORKS FINE.
      DriveDistP.execute(drivetrain, coralMech, Inside, kGrav, kP, 250, false, 0.1, startOfAuto, 9000, 0.015); 
      RunCoralIntake.execute(coralMech, 1, 2000, startOfAuto);
    } else if (m_autoSelected == middlePreloadL1) { // THIS WORKS, BUT YOU MUST CHECK OUTTAKE.
      DriveDistanceBangBang.execute(drivetrain, coralMech, L1, kGrav, kP, 250, false, 0.1, startOfAuto, 9000, 0.015); 
      RunCoralIntake.execute(coralMech, 1, 3000, startOfAuto);
    } /* else if (m_autoSelected == middlePreloadL2) { // DOESN'T WORK. ALGAE ON L2. COULD ADD CODE TO REMOVE IT.
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, L1, kGrav, kP, 250, false, 0.1, startOfAuto); 
      RunCoralIntake.execute(coralMech, -1, 3000, startOfAuto);
    } */else if (m_autoSelected == rightPreloadL1) { // WANT TO TEST 
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, Inside, kGrav, kP, 185, false, 0.1, startOfAuto, 9000, 0.02); 
      TurnToAngleBangBang.execute(System.currentTimeMillis(), drivetrain, coralMech, 60, 0.1, false, Inside);
      DriveDistanceBangBang.execute(drivetrain, coralMech, L1, kGrav, kP, 270, false, 0.1, startOfAuto, 9000, 0.015); 
      RunCoralIntake.execute(coralMech, 1, 2000, startOfAuto); 
    } else if (m_autoSelected == rightPreloadL2) { // WANT TO TEST 
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, Inside, kGrav, kP, 185, false, 0.1, startOfAuto, 9000, 0.015); 
      TurnToAngleBangBang.execute(System.currentTimeMillis(), drivetrain, coralMech, 60, 0.1, false, Inside);
      DriveDistanceBangBang.execute(drivetrain, coralMech, L2, kGrav, kP, 270, false, 0.1, startOfAuto, 9000, 0.015); 
      RunCoralIntake.execute(coralMech, 1, 2000, startOfAuto); 
      // DriveDistanceBangBang.execute(drivetrain, coralMech, L2, kGrav, kP, 270, true, 0.1, startOfAuto); 
      // TurnToAngleBangBang.execute(System.currentTimeMillis(), drivetrain, coralMech, 60, 0.1, true, Inside);
    } else if (m_autoSelected == leftPreloadL1) { // WANT TO TEST 
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, Inside, kGrav, kP, 185, false, 0.1, startOfAuto, 9000, 0.015); 
      TurnToAngleBangBang.execute(System.currentTimeMillis(), drivetrain, coralMech, 60, 0.1, true, Inside);
      DriveDistanceBangBang.execute(drivetrain, coralMech, L1, kGrav, kP, 270, false, 0.1, startOfAuto, 9000, 0.015); 
      RunCoralIntake.execute(coralMech, 1, 2000, startOfAuto); 
    } else if (m_autoSelected == leftPreloadL2) { // WANT TO TEST 
      drivetrain.resetEncoders();
      DriveDistanceBangBang.execute(drivetrain, coralMech, Inside, kGrav, kP, 185, false, 0.1, startOfAuto, 9000, 0.015); 
      TurnToAngleBangBang.execute(System.currentTimeMillis(), drivetrain, coralMech, 60, 0.1, true, Inside);
      DriveDistanceBangBang.execute(drivetrain, coralMech, L2, kGrav, kP, 270, false, 0.1, startOfAuto, 9000, 0.015); 
      RunCoralIntake.execute(coralMech, 1, 2000, startOfAuto); 
    } else if (m_autoSelected == rightPreloadAndScoreL2) { // Scores the preloaded coral, goes to right HP, scores on L2
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, Inside, kGrav, kP, 130, false, 0.1, startOfAuto, 9000, 0.015); 
      TurnToAngleBangBang.execute(System.currentTimeMillis(), drivetrain, coralMech, 111.5, 0.1, false, Inside); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, L2, kGrav, kP, 203, false, 0.1, startOfAuto, 9000, 0.015);
      RunCoralIntake.execute(coralMech, 1, 2000 , System.currentTimeMillis()); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, Inside, kGrav, kP, 203, true, 0.1, startOfAuto, 9000, 0.015);
      TurnToAngleBangBang.execute(System.currentTimeMillis(), drivetrain, coralMech, 68.4, 0.1, true, Inside); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, Inside, kGrav, kP, 358, false, 0.1, startOfAuto, 9000, 0.015);
      TurnToAngleBangBang.execute(System.currentTimeMillis(), drivetrain, coralMech, 41.835, 0.1, true, Inside); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, HP, kGrav, kP, 115, false, 0.1, startOfAuto, 9000, 0.015);
      RunCoralIntake.execute(coralMech, -1, 1500, System.currentTimeMillis()); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, HP, kGrav, kP, 115, true, 0.1, startOfAuto, 9000, 0.015);
      TurnToAngleBangBang.execute(System.currentTimeMillis(), drivetrain, coralMech, 41.835, 0.1, false, Inside); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, Inside, kGrav, kP, 386, true, 0.1, startOfAuto, 9000, 0.015);
      TurnToAngleBangBang.execute(System.currentTimeMillis(), drivetrain, coralMech, 68.4, 0.1, false, Inside); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, Inside, kGrav, kP, 210, false, 0.1, startOfAuto, 9000, 0.015);
      RunCoralIntake.execute(coralMech, -1, 2000, System.currentTimeMillis()); 
    } else if (m_autoSelected == right_left_Straight_L1) {
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, L1, kGrav, kP, 300, false, 0.1, startOfAuto, 9000, 0.015); 
      RunCoralIntake.execute(coralMech, L1, 0.5, 3000, startOfAuto);
    } else if (m_autoSelected == right_left_Straight_L2) {
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, L2, kGrav, kP, 300, false, 0.1, startOfAuto,9000, 0.015); 
      RunCoralIntake.execute(coralMech, L2, 0.5, 3000, startOfAuto);
    } else if (m_autoSelected == right_left_Straight_L3) {
      drivetrain.resetEncoders();
      DriveDistanceBangBang.execute(drivetrain, coralMech, L3, kGrav, kP, 300, false, 0.1, startOfAuto, 9000, 0.015); 
      RunCoralIntake.execute(coralMech, L3, 1, 3000, startOfAuto);
    } else if (m_autoSelected == getAlgaeOffL3) {
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      MoveArm.executeAngle(coralMech, L2, startOfAuto);
      DriveDistP.execute(drivetrain, coralMech, L2, kGrav, kP, 230, false, 0.1, startOfAuto,7000, 0.03); 
      MoveArm.executeAngle(coralMech, Inside, startOfAuto);
    } else if (m_autoSelected == middlePreloadL3) {
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, L2, kGrav, kP, 190, false, 0.1, startOfAuto, 9000, 0.015); 
      MoveArm.executeAngle(coralMech, L3, startOfAuto);
      DriveDistanceBangBang.execute(drivetrain, coralMech, L3, kGrav, kP, 87, true, 0.1, System.currentTimeMillis(), 9000, 0.015); 
      TurnToAngleBangBang.execute(System.currentTimeMillis(), drivetrain, coralMech, 9, 0.1, false, Inside); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, L3, kGrav, kP, 88, false, 0.1, System.currentTimeMillis(), 9000, 0.015);
      RunCoralIntake.execute(coralMech, 1, 2000, startOfAuto);
    } else if (m_autoSelected == rightPreloadAndHP) {
      drivetrain.resetGyro(); 
      drivetrain.resetEncoders();
      MoveArm.executeAngle(coralMech, L2, startOfAuto);
      DriveDistP.execute(drivetrain, coralMech, L2, kGrav, kP, 260, false, 0.1, startOfAuto, 3000, 0.015); 
      RunCoralIntake.execute(coralMech, L2, 0.5, 1500, startOfAuto);
      drivetrain.resetGyro();
      TurnToAnglePID.execute(drivetrain, coralMech, 71.2, 0.1, true, HP, 33.5, 0, startOfAuto);
      //TurnToAnglePID.execute(drivetrain, coralMech, 71.2 /*unused parameter, reference */, 0.3, true, HP, 37.7, 0 /*unused parameter */, System.currentTimeMillis());
    } else if (m_autoSelected == pushAnotherRobot) {
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      DriveDistanceBangBang.execute(drivetrain, coralMech, Inside, kGrav, kP, 280, false, 0.6, startOfAuto, 9000, 0.015);
    }
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
    forward = forward * Math.abs(forward) * 0.6;
    rotate = rotate * rotate * rotate * 0.6;
    drivetrain.arcadeDrive(forward, rotate);
    // climb mechanism
    if (ci.getWinchRetract())  {
      climber.climberActuation(1);
    } else if (ci.getWinchRelease()) {
      climber.climberActuation(-1);
    } else if (ci.getWinchReleaseOperator()) {
      climber.climberActuation(-1); 
    } else if (ci.getWinchRetractOperator()) {
      climber.climberActuation(1);
    } else {
      climber.climberActuation(0);
    }

    // coral INTAKE
    if(ci.coralIntake() > 0.02){
      coralMech.runIntake(ci.coralIntake()); // Testing purposes NOT set-in-stone value
      //System.out.println("INTAKING" + System.currentTimeMillis());
    } else if (ci.coralOuttake() > 0.02) {
      coralMech.runOuttake(ci.coralOuttake()); // Testing 
      //System.out.println("OUTTAKING"+ System.currentTimeMillis());
    } else if (ci.getHoldCoral()) {
      coralMech.runIntake(0.3); 
    } else {
      //System.out.println("STOPPING" + System.currentTimeMillis());
      coralMech.runIntake(0);
    }

    // coral PIVOT
    if (ci.getHP()) {
      coralMech.closedLoopCoralArticulation(25, 0.0711, 0.0289);
    } else if(ci.getToL1()){
      coralMech.closedLoopCoralArticulation(-28, 0.0711, 0.0289);
    } else if(ci.getToL2()){
      coralMech.closedLoopCoralArticulation(-11, 0.0711, 0.0289);
    } else if(ci.getInside()) {
      coralMech.closedLoopCoralArticulation(200,0.0711, 0.0289);
    } else if(ci.getToL3()) {
      coralMech.closedLoopCoralArticulation(31, 0.0711, 0.0289);
    } else if(ci.getWinchRelease()) {
      coralMech.closedLoopCoralArticulation(-28, 0.0711, 0.0289); 
   // } else if(ci.getArticulatedIntakePIDTuningAxis() > 0.1) {
      //coralMech.closedLoopCoralArticulation(0, ci.getArticulatedIntakePIDTuningAxis(), 0); 
    } else if (ci.getWinchReleaseOperator()) {
      coralMech.closedLoopCoralArticulation(-28, 0.0711, 0.0289);
    } else if (Math.abs(ci.getCoralOpenLoopArticulation()) > 0.01) {
      coralMech.openLoopCoralArticulation(ci.getCoralOpenLoopArticulation()); 
    } else {
      coralMech.openLoopCoralArticulation(0);
    }

    if (ci.getDebugButton()) {
      drivetrain.resetGyro();
    }

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
