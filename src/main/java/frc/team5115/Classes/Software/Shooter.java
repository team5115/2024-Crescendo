package frc.team5115.Classes.Software;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareShooter;

public class Shooter extends SubsystemBase {
    HardwareShooter hardwareShooter;
    
    public Shooter(HardwareShooter hardwareShooter) {
        this.hardwareShooter = hardwareShooter;
    }

    public void fast() {
        hardwareShooter.setVoltage(12); // TODO find correct fast shooter voltage
    }

    public void slow() {
        hardwareShooter.setVoltage(4); // TODO find correct slow shooter voltage
    }

    public void stop() {
        hardwareShooter.setVoltage(0);
    }

    public void fastBackwards() {
        hardwareShooter.setVoltage(-12);
    }
}
