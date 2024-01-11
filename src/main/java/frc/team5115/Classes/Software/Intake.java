package frc.team5115.Classes.Software;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareIntake;

public class Intake extends SubsystemBase {
    HardwareIntake HardwareIntake;
    
    public Intake(HardwareIntake hardwareIntake){
        this.HardwareIntake = hardwareIntake;
    }


    public void startIntakeMotor(){
        HardwareIntake.setIntakeSpeed(0.3); // TODO find good intake speed [-1, +1]
    }

    public void stopIntakeMotor(){
        HardwareIntake.setIntakeSpeed(0);
    } 

    
}
