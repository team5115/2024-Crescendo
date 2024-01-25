package frc.team5115.Classes.Software;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareClimber;

public class Climber extends SubsystemBase {
    HardwareClimber leftClimber;
    HardwareClimber rightClimber;
    PIDController pidController;
    double angle;

    public Climber(HardwareClimber leftClimber, HardwareClimber rightClimber){
        this.leftClimber = leftClimber;
        this.rightClimber = rightClimber;
        pidController = new PIDController(0, 0, 0);
    }
    
    public void MoveUp(double angle){
        double leftOutput = pidController.calculate(leftClimber.getAngle(), angle);
        leftClimber.setSpeed(leftOutput);

        double rightOutput = pidController.calculate(rightClimber.getAngle(), angle);
        rightClimber.setSpeed(rightOutput);
    } 
    
}
