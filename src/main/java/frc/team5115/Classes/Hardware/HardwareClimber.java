package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;

public class HardwareClimber {
    private static final double kGravity = 0;
    private static final double kSpring = 0;
    private static final double kStatic = 0;
    private static final double kVelocity = 0;

    final CANSparkMax climberMotor;
    final RelativeEncoder climbEncoder;
    final DigitalInput bottomSensor;

    public HardwareClimber(int canId, int bottomChannel){
        climberMotor = new CANSparkMax(canId, MotorType.kBrushless);
        climberMotor.setIdleMode(IdleMode.kBrake);
        bottomSensor = new DigitalInput(bottomChannel);
        climbEncoder = climberMotor.getEncoder();
    }

    public boolean isBottomDetecting(){
        return bottomSensor.get();
    }
    
    public double getRotations(){
        return climbEncoder.getPosition();
    }

    public void setSpeed(double desiredVelocity){
        double voltage = kStatic * Math.signum(desiredVelocity) + kVelocity * desiredVelocity + kSpring * getRotations() + kGravity * Math.cos(getRotations());
        climberMotor.setVoltage(voltage);
    }

    public void setPercentage(double percentage){
        climberMotor.set(percentage);
    }

    public double getVelocity(){
        return climbEncoder.getVelocity();
    }

}
    