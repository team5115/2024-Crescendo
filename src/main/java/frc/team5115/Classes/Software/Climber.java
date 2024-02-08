package frc.team5115.Classes.Software;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareClimber;

public class Climber extends SubsystemBase {
    final HardwareClimber leftClimber;
    final HardwareClimber rightClimber;
    final PIDController leftPid;
    final PIDController rightPid;
    boolean deployed;

    public Climber(HardwareClimber leftClimber, HardwareClimber rightClimber){
        this.leftClimber = leftClimber;
        this.rightClimber = rightClimber;
        leftPid = new PIDController(0, 0, 0);
        rightPid = new PIDController(0, 0, 0);
        deployed = false;
    }
    
    public void loopPids(double[] setpoints){
        double leftOutput = leftPid.calculate(leftClimber.getRotations(), setpoints[0]);
        double rightOutput = rightPid.calculate(rightClimber.getRotations(), setpoints[1]);
        
        leftClimber.setSpeed(leftOutput);
        rightClimber.setSpeed(rightOutput);
    }

    public void stop(){
        leftClimber.setPercentage(0);
        rightClimber.setPercentage(0);
    }

    // TODO determine latch release speed
    public void latchSpeed(){
        leftClimber.setPercentage(0.2);
        rightClimber.setPercentage(0.2);
    }

    // TODO determine slow release speed
    public void letOutSlow() {
        leftClimber.setPercentage(-0.2);
        rightClimber.setPercentage(-0.2);
    }

    public double[] getRotations() {
        return new double[] {leftClimber.getRotations(), rightClimber.getRotations()};
    }

    public boolean isRightBottom(){
        return rightClimber.isBottomDetecting();
    }

    public boolean isLeftBottom(){
        return leftClimber.isBottomDetecting();
    }
    
    public boolean isFullyClimbed(){
        return isLeftBottom() && isRightBottom();
    }

    /**
     * Doesn't actually deploy the climber, just tells it that it is deployed
     */
    public void setDeployed() {
        deployed = true;
    }

    public boolean isDeployed() {
        return deployed;
    }
}
