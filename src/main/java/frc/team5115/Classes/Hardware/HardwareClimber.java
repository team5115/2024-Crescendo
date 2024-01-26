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

    CANSparkMax climberMotor;
    RelativeEncoder climbEncoder;
    DigitalInput beambreak;
    Servo actuator;

    int passes;
    boolean detected;

    public HardwareClimber(int canId, int sensorChannel, int actuatorChannel){
        climberMotor = new CANSparkMax(canId, MotorType.kBrushless);
        climberMotor.setIdleMode(IdleMode.kBrake);
        beambreak = new DigitalInput(sensorChannel);
        actuator = new Servo(actuatorChannel);
        actuator.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);
        climbEncoder = climberMotor.getEncoder();
        actuator.setSpeed(-1);
    }

    public void update(){
        if (detected && !isDetecting()){
            passes ++;
        }

        detected = isDetecting();
    }

    public boolean isDetecting(){
        return beambreak.get();
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

    public void retractPin() {
        actuator.setSpeed(-1);
    }

    // i don't think we need this anymore :()
    public State getState(){
        if (isDetecting()){
            return State.Centered;
        }
        if (passes % 2 == 0){
            return State.Above;
        }
        return State.Below;
    }
}
