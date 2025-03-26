package frc.robot.autonomous;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.hardware.ICoralMechanismHardware;
import frc.robot.subsystems.*;
public class MoveArm {
    private CoralMechanism coralMech;
    private static final double kGrav = 0.0289; 
    private static final double kP = 0.0711; 
    private static final double ACCEPTABLE_RANGE = 0.5; 
    
        private static boolean isInAutoTime(double startTime){
            
            double currentTime = System.currentTimeMillis();
            if((currentTime - startTime) > 15000){
                return false;
            }
            return true;
        }
        
    
        public static void executeAngle(CoralMechanism coralMech, double desiredAngle, double autonStartTime){
            long startTime = System.currentTimeMillis();
            long newTime = System.currentTimeMillis();

            while (newTime-autonStartTime < 15000 && Math.abs(desiredAngle - coralMech.getCurrentAngle()) > ACCEPTABLE_RANGE) {
                    coralMech.closedLoopCoralArticulation(desiredAngle, kGrav, kP);
                    newTime = System.currentTimeMillis();
                    Timer.delay(0.02);
                }
             }
            
}
