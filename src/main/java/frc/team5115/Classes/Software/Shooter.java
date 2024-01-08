package frc.team5115.Classes.Software;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareShooter;

public class Shooter extends SubsystemBase {
    HardwareShooter hardwareShooter;
    
    public Shooter(HardwareShooter hardwareShooter) {
        this.hardwareShooter = hardwareShooter;
    }

    public void startFeed() {
        hardwareShooter.setFeederSpeed(0.2); // TODO find good feed speed [-1, +1]
    }

    public void stopFeed() {
        hardwareShooter.setFeederSpeed(0);
    }

    public void startShooter() {
        hardwareShooter.setShooterSpeed(1); // TODO find good shooter speed [0, ???]
    }

    public void stopShooter() {
        hardwareShooter.setShooterSpeed(0);
    }
}
