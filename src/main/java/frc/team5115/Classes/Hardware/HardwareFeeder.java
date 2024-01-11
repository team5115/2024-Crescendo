package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

public class HardwareFeeder {
    private CANSparkMax feederMotor;
    public HardwareFeeder() { 
        feederMotor = new CANSparkMax(0, MotorType.kBrushless);
}
public void setFeederSpeed(double speed) {
    feederMotor.set(speed);
}



    
    
    
}
