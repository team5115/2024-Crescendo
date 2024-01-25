package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.DigitalInput;

public class HardwareClimber {
    private static final double kGravity = 0;
    private static final double kSpring = 0;
    private static final double kStatic = 0;
    private static final double kVelocity = 0;
    public enum State { Above, Below, Centered }

    CANSparkMax climberMotor;
    RelativeEncoder climbEncoder;
    DigitalInput beambreak;

    int passes;
    boolean detected;

    public HardwareClimber(int canId, int sensorChannel){
        climberMotor = new CANSparkMax(canId, MotorType.kBrushless);
        beambreak = new DigitalInput(sensorChannel);
        climbEncoder = climberMotor.getEncoder();
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
