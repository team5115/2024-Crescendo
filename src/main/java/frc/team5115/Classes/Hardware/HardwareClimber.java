package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkMax;

import frc.team5115.Classes.Accessory.Angle;
import frc.team5115.Classes.Accessory.I2CHandler;

import com.revrobotics.CANSparkLowLevel.MotorType;

public class HardwareClimber {
    
    private CANSparkMax winch;

    public HardwareClimber(){
        winch = new CANSparkMax(0, MotorType.kBrushless);
    }

    private int ffCalculate(double positionRadians, double accelRadPerSecSquared){
         return 0;
    }

    public void moveArm(double speed){
        winch.setVoltage(Math.max(ffCalculate(getArmAngle(), speed), -10));
    }

    public double getArmAngle(){
        return 0;
    }

}
