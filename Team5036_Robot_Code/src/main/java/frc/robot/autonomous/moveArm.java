package frc.robot.autonomous;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.hardware.ICoralMechanismHardware;
import frc.robot.subsystems.*;
public class MoveArm {
    private CoralMechanism coralMech;
    private static final double kGrav = 0.0289; 
    private static final double kP = 0.0711; 
    
        private static boolean isInAutoTime(double startTime){
            
            double currentTime = System.currentTimeMillis();
            if((currentTime - startTime) > 15000){
                return false;
            }
            return true;
        }
        
    
        public static void executeAngle(CoralMechanism coralMech, double desiredAngle, double startTime){
            if (isInAutoTime(startTime)) {
                coralMech.closedLoopCoralArticulation(desiredAngle, kGrav, kP);
                Timer.delay(0.02);
            }
        
    }
    
}
