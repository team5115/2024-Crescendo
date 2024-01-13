package frc.team5115.Classes.Software;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareShooter;

public class Shooter extends SubsystemBase {
    HardwareShooter hardwareShooter;
    
    public Shooter(HardwareShooter hardwareShooter) {
        this.hardwareShooter = hardwareShooter;
    }

    public void fast() {
        hardwareShooter.setNormalized(0.9);
    }

    public void slow() {
        hardwareShooter.setNormalized(0.065);
    }

    public void stop() {
        hardwareShooter.setNormalized(0);
        System.out.println("stopping shooter");
    }

    public void fastBackwards() {
        hardwareShooter.setNormalized(-1);
    }

    public double getSpeed() {
        return hardwareShooter.getShooterVelocity();
    }
}
