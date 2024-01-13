package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class HardwareIntake{
    
    private final CANSparkMax motor;

    public HardwareIntake(){
        motor = new CANSparkMax(10, MotorType.kBrushless);
        motor.setIdleMode(IdleMode.kBrake);        
    }

    public void set(double speed){
        motor.set(speed);
    }   
}