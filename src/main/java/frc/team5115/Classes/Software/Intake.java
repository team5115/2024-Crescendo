package frc.team5115.Classes.Software;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareIntake;

public class Intake extends SubsystemBase {
    HardwareIntake HardwareIntake;
    
    public Intake(HardwareIntake hardwareIntake){
        this.HardwareIntake = hardwareIntake;
    }


    public void startIntakeMotor(){
        HardwareIntake.setIntakeSpeed(0.3);
    }

    public void stopIntakeMotor(){
        HardwareIntake.setIntakeSpeed(0);
    } 

    public void startScoocher(){
        HardwareIntake.setScoocherSpeed(0.6);
    }

    public void stopScoocher(){
        HardwareIntake.setScoocherSpeed(0);
    }
}
