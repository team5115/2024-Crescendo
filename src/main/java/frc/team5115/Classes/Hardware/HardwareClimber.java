package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

public class HardwareClimber {
    private static final double kGravity = 0;
    private static final double kSpring = 0;
    private static final double kStatic = 0;
    private static final double kVelocity = 0;

    final PIDController pid;
    final CANSparkMax climberMotor;
    final RelativeEncoder climbEncoder;
    final DigitalInput bottomSensor;
    // final DigitalInput topSensor;
    // final Servo actuator;

    public HardwareClimber(int canId, boolean reversed, int bottomChannel){
        climberMotor = new CANSparkMax(canId, MotorType.kBrushless);
        pid = new PIDController(0.01, 0, 0);
        climberMotor.setIdleMode(IdleMode.kBrake);
        bottomSensor = new DigitalInput(bottomChannel);
        // topSensor = new DigitalInput(topChannel);
        climbEncoder = climberMotor.getEncoder();
        climbEncoder.setPositionConversionFactor(1.0/16.0);
        climberMotor.setInverted(reversed);
        climberMotor.setIdleMode(IdleMode.kBrake);
        climberMotor.setSmartCurrentLimit(45);
    }

    public boolean isBottomDetecting(){
        return !bottomSensor.get();
    }

    // public boolean isTopDetecting(){
    //     return topSensor.get();
    // }
    
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

    public void updatePID(double setpoint){
        double pidout = pid.calculate(getRotations(), setpoint);
        climberMotor.set(pidout);
        // System.out.println(bottomSensor.getChannel() + " pushes " + pidout + " | sensor.get(): " + bottomSensor.get());
    }
}
    