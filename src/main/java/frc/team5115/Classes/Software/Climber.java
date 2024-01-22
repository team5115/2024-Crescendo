package frc.team5115.Classes.Software;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareClimber;

public class Climber extends SubsystemBase {
    HardwareClimber leftClimber;
    HardwareClimber rightClimber;
    public Climber(){
        leftClimber = new HardwareClimber(1,1);
        rightClimber = new HardwareClimber(2,2);

    }
    
}
