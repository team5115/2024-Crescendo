package frc.team5115.Classes.Software;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareClimber;

public class Climber extends SubsystemBase {
    final static double STOPPED_TOLERANCE = 1; // TODO determine stopped tolerance (RPM)
    final HardwareClimber leftClimber;
    final HardwareClimber rightClimber;
    final PIDController leftPid;
    final PIDController rightPid;
    final static double LATCHVEL = 15; // TODO detremine unlatched velosictyu 
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

    public void stop(){
        leftClimber.setVoltZero();
        rightClimber.setVoltZero();
    }

    public void latchSpeed(){
        leftClimber.setPercentage();
        rightClimber.setPercentage();
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
        return leftClimber.isDetecting() && rightClimber.isDetecting();
    }

    public boolean eitherDetecting() {
        return leftClimber.isDetecting() || rightClimber.isDetecting(); 
    }

    public boolean bothStopped(){
        return Math.abs(leftClimber.getVelocity()) < STOPPED_TOLERANCE && Math.abs(rightClimber.getVelocity()) < STOPPED_TOLERANCE;
    }

    public boolean checkVelocity(){
        return Math.abs(leftClimber.getVelocity()) > LATCHVEL && Math.abs(rightClimber.getVelocity()) > LATCHVEL;
    }
}
