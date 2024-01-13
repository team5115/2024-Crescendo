package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class HardwareIntake{
    
    private final CANSparkMax motor;    

    public HardwareIntake(){
        //TODO intake canIds
        motor = new CANSparkMax(0, MotorType.kBrushless);
        motor.setIdleMode(IdleMode.kBrake);        
    }

    public void setIntakeSpeed(double speed){
        motor.set(speed);
    }   
}