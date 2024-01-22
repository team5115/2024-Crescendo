package frc.team5115.Classes.Software;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareClimber;
import frc.team5115.Classes.Hardware.NAVx;

public class Climber extends SubsystemBase {
    HardwareClimber leftClimber;
    HardwareClimber rightClimber;
    PIDController pidController;
    double angle;
    public Climber(){
        leftClimber = new HardwareClimber(0, 1);
        leftClimber = new HardwareClimber(0, 2);
        pidController = new PIDController(1, 1, 1);

    }
    public void MoveUp(double angle){
        double xl = pidController.calculate(leftClimber.getAngle(), angle);
        leftClimber.setspeed(xl);
        double xr = pidController.calculate(leftClimber.getAngle(), angle);
        rightClimber.setspeed(xr);
    } 
    
}
