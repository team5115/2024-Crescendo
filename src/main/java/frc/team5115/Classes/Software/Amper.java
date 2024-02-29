package frc.team5115.Classes.Software;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareAmper;

public class Amper extends SubsystemBase {
    private HardwareAmper hardwareAmper;
    
    public Amper(){
        hardwareAmper = new HardwareAmper();
    }
    
    public boolean isOut(){
        return hardwareAmper.out();
    }

    public boolean isIn(){
        return hardwareAmper.in();
    }

    public void turnOut(){
        hardwareAmper.set(10);
    }

    public void turnIn(){
        hardwareAmper.set(-10);
    }

    public void stop(){
        hardwareAmper.set(0);
    }
    
}
