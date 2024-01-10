package frc.team5115.Classes.Software;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareIntake;

public class intake extends SubsystemBase {
    HardwareIntake HardwareIntake;
    
    public intake(HardwareIntake hardwareIntake){
        this.HardwareIntake = hardwareIntake;
    }



}
