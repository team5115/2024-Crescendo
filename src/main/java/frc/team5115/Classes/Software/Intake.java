package frc.team5115.Classes.Software;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareIntake;

public class Intake extends SubsystemBase {
    HardwareIntake hardwareIntake;
    
    public Intake(HardwareIntake hardwareIntake){
        this.hardwareIntake = hardwareIntake;
    }

    public void in(){
        hardwareIntake.setIntakeSpeed(0.3); // TODO find intake speed [0, +1]
    }

    public void out() {
        hardwareIntake.setIntakeSpeed(-0.3); // TODO find outtake speed [-1,0]
    }

    public void stop(){
        hardwareIntake.setIntakeSpeed(0);
    }
}
