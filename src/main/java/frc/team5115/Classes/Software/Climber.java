package frc.team5115.Classes.Software;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareClimber;

public class Climber extends SubsystemBase {
    final HardwareClimber leftClimber;
    final HardwareClimber rightClimber;
    final PIDController leftPid;
    final PIDController rightPid;
    double angle;

    public Climber(HardwareClimber leftClimber, HardwareClimber rightClimber){
        this.leftClimber = leftClimber;
        this.rightClimber = rightClimber;
        leftPid = new PIDController(0, 0, 0);
        rightPid = new PIDController(0, 0, 0);
    }
    
    public void loopPids(double angle){
        double leftOutput = leftPid.calculate(leftClimber.getAngle(), angle);
        leftClimber.setSpeed(leftOutput);

        double rightOutput = rightPid.calculate(rightClimber.getAngle(), angle);
        rightClimber.setSpeed(rightOutput);
    }

    public void retractPins() {
        leftClimber.retractPin();
        rightClimber.retractPin();
    }

    public void extendPins() {
        leftClimber.extendPin();
        rightClimber.extendPin();
    }

    public boolean bothDetecting() {
        // TODO bothDetecting() in Climber.java
        return false; // delete this line
    }

    public boolean eitherDetecting() {
        // TODO eitherDetecting() in Climber.java
        return false; // delete this line
    }
}
