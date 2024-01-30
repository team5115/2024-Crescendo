package frc.team5115.Classes.Software;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareClimber;

public class Climber extends SubsystemBase {
    final static double STOPPED_MAX_VEL = 1; // TODO determine stopped tolerance (RPM)
    final static double UNLATCHED_MIN_VEL = 15; // TODO determine unlatched tolerance (RPM)
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

    public void stop(){
        leftClimber.setVoltZero();
        rightClimber.setVoltZero();
    }

    public void latchSpeed(){
        leftClimber.setPercentage(0.2);
        rightClimber.setPercentage(0.2);
    }
   
    public void retractPins() {
        leftClimber.retractPin();
        rightClimber.retractPin();
    }

    public void extendPins() {
        leftClimber.extendPin();
        rightClimber.extendPin();
    }

    public boolean isRightBottom(){
        return rightClimber.isBotttomDetecting();
    }

    public boolean isLeftBottom(){
        return leftClimber.isBotttomDetecting();

    }

    public boolean isRightTop(){
        return rightClimber.isTopDetecting();

    }

    public boolean isLeftTop(){
        return leftClimber.isTopDetecting();

    }

    public boolean isFullyDeployed(){
        return isRightTop() && isLeftTop() && !isLeftBottom() && !isRightBottom();
    }

    
    public boolean isFullyClimbed(){
        return !isRightTop() && !isLeftTop() && isLeftBottom() && isRightBottom();
    }
    public boolean bothStopped(){
        return Math.abs(leftClimber.getVelocity()) < STOPPED_MAX_VEL && Math.abs(rightClimber.getVelocity()) < STOPPED_MAX_VEL;
    }

    public boolean bothUnlatched(){
        return Math.abs(leftClimber.getVelocity()) > UNLATCHED_MIN_VEL && Math.abs(rightClimber.getVelocity()) > UNLATCHED_MIN_VEL;
    }
}
