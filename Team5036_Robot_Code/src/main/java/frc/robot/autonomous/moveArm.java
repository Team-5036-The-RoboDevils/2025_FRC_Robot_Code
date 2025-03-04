package frc.robot.autonomous;
import frc.robot.hardware.ICoralMechanismHardware;
import frc.robot.subsystems.*;
public class moveArm {
    private CoralMechanism coralMech;
    private double tempG = 0;
    private double tempP = 0;
    
        private static boolean isInAutoTime(double startTime){
            
            double currentTime = System.currentTimeMillis();
            if((currentTime - startTime) > 15000){
                return false;
            }
            return true;
        }
        
    
        public void executeAngle(double desiredAngle, boolean putArmDown, double startTime){

            if(putArmDown && isInAutoTime(startTime)){
                coralMech.openLoopCoralArticulation(0);
            } else if (!putArmDown && isInAutoTime(startTime)) {
                coralMech.closedLoopCoralArticulation(desiredAngle, tempG, tempP);
            }
        
    }
    
}
