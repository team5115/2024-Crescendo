package frc.team5115.Classes.Software;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareClimber;

public class Climber extends SubsystemBase {
    final HardwareClimber leftClimber;
    final HardwareClimber rightClimber;
    boolean deployed;

    public Climber(HardwareClimber leftClimber, HardwareClimber rightClimber){
        this.leftClimber = leftClimber; 
        this.rightClimber = rightClimber;
        deployed = false;
    }
    
    public void loopPids(double[] setpoints){
        leftClimber.updatePID(setpoints[0]);
        rightClimber.updatePID(setpoints[1]);
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

    public void setBoth(double speed){
        leftClimber.setPercentage(speed);
        rightClimber.setPercentage(speed);
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

    // public boolean isRightTop(){
    //     return rightClimber.isTopDetecting();
    // }

    // public boolean isLeftTop(){
    //     return leftClimber.isTopDetecting();
    // }

    // public boolean isFullyDeployed(){
    //     return isRightTop() && isLeftTop() && !isLeftBottom() && !isRightBottom();
    // }
    
    // public boolean isFullyClimbed(){
    //     return !isRightTop() && !isLeftTop() && isLeftBottom() && isRightBottom();
    // }

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
