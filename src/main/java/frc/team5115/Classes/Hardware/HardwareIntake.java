package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class HardwareIntake{
    
    private CANSparkMax intakeMotor;
    //private CANSparkMax intakeMotor2;
    private CANSparkMax scoocher;

    public HardwareIntake(){
        //TODO change deviceId
        intakeMotor = new CANSparkMax(0, MotorType.kBrushless);
        scoocher = new CANSparkMax(0, MotorType.kBrushless);
    }

    public void setIntakeSpeed(double speed){
        intakeMotor.set(speed);
    }

    public void setScooterSpeed(double speed){
        scoocher.set(speed);
    }
}