package frc.team5115.Classes.Software;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareIntake;

public class Intake extends SubsystemBase {
    final HardwareIntake hardwareIntake;
    
    public Intake(HardwareIntake hardwareIntake){
        this.hardwareIntake = hardwareIntake;
    }

    public void in(){
        hardwareIntake.set(0.5);
    }

    public void fastIn() {
        hardwareIntake.set(+1);
    }

    public void out() {
        hardwareIntake.set(-0.35);
    }
    
    public void fastOut(){
        hardwareIntake.set(-1);
    }

    public void stop(){
        hardwareIntake.set(0);
    }

    public void setPercent(double percent) {
        hardwareIntake.set(percent);
    }
}
