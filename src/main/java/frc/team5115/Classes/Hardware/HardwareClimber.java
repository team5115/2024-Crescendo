package frc.team5115.Classes.Hardware;


import com.revrobotics.CANSparkLowLevel.MotorType;


import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
public class HardwareClimber {

    private static final double kSpring = 1;
    private static final double kStatic = 1;
    private static final double kVelocity = 1;
    public enum State { Above, Below, Centered }

    CANSparkMax climberMotor;
    RelativeEncoder climbEncoder;
    DigitalInput beambreak;

    int passes;
    boolean detected;

    public HardwareClimber( int channel ){
        climberMotor = new CANSparkMax(0, MotorType.kBrushless);
        climbEncoder = climberMotor.getEncoder();
        beambreak = new DigitalInput(channel);
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

    public void setspeed(double desiredVelocity){
        double voltage = kStatic * Math.signum(desiredVelocity) + kVelocity * desiredVelocity + kSpring * getAngle();
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


