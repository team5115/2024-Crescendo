package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;

public class HardwareClimber {
    private static final double kGravity = 0;
    private static final double kSpring = 0;
    private static final double kStatic = 0;
    private static final double kVelocity = 0;
    public enum State { Above, Below, Centered }

    final CANSparkMax climberMotor;
    final RelativeEncoder climbEncoder;
    final DigitalInput bottomSensor;
    final DigitalInput topSensor;
    final Servo actuator;


    public HardwareClimber(int canId, int bottomChannel, int topChannel, int actuatorChannel){
        climberMotor = new CANSparkMax(canId, MotorType.kBrushless);
        climberMotor.setIdleMode(IdleMode.kBrake);
        bottomSensor = new DigitalInput(bottomChannel);
        topSensor = new DigitalInput(topChannel);
        actuator = new Servo(actuatorChannel);
        actuator.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);
        climbEncoder = climberMotor.getEncoder();
        actuator.setSpeed(-1);
    }

    public boolean isBotttomDetecting(){
        return bottomSensor.get();
    }

    public boolean isTopDetecting(){
        return topSensor.get();
    }
    
    public double getAngle(){
        return climbEncoder.getPosition();
    }

    public void setSpeed(double desiredVelocity){
        double voltage = kStatic * Math.signum(desiredVelocity) + kVelocity * desiredVelocity + kSpring * getAngle() + kGravity * Math.cos(getAngle());
        climberMotor.setVoltage(voltage);
    }

    public void extendPin() {
        actuator.setSpeed(+1);
    }

    public void setPercentage(double percentage){
        climberMotor.set(percentage);
    }

    public void retractPin() {
        actuator.setSpeed(-1);
    }

    public double getVelocity(){
        return climbEncoder.getVelocity();
    }

}
    