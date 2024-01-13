package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class HardwareIntake{
    
    private CANSparkMax intakeMotor;
    //private CANSparkMax intakeMotor2;
    

    public HardwareIntake(){
        //TODO intake canIds
        intakeMotor = new CANSparkMax(0, MotorType.kBrushless);
        intakeMotor.setIdleMode(IdleMode.kBrake);        
    }

    public void setIntakeSpeed(double speed){
        intakeMotor.set(speed);
    }

    
}