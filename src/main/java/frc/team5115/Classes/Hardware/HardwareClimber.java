package frc.team5115.Classes.Hardware;


import com.revrobotics.CANSparkLowLevel.MotorType;


import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
public class HardwareClimber {
    public enum State{Above,Below,Centered}
    //SimpleMotorFeedforward ff;
    CANSparkMax ClimberMotor;
    RelativeEncoder ClimbEncoder;
    DigitalInput beambreak;
    
    private static final double Kspring = 1 ;
    private static final double Ks = 1 ;
    private static final double Kvelocity = 1 ;
    int passes;
    boolean dectected;
    public HardwareClimber( int channel ){
        ClimberMotor = new CANSparkMax(0, MotorType.kBrushless);
        ClimbEncoder = ClimberMotor.getEncoder();
        //ff = new SimpleMotorFeedforward(0, 0);
        beambreak = new DigitalInput(channel);
        
    }

    public void update(){
        if (dectected && !isDecting()){
            passes ++;
   
        }
        dectected = isDecting();

        }

    public boolean isDecting(){
        return beambreak.get();
    }
    
    public double getAngle(){
        return ClimbEncoder.getPosition();
    }

    public void setspeed(double desiredVelocity){
        double voltage = Ks*Math.signum(desiredVelocity) + Kvelocity * desiredVelocity + Kspring * getAngle();
        ClimberMotor.setVoltage(voltage);
   }

   public State getState(){
    if (isDecting()){
        return State.Centered;
    }
    if (passes % 2 == 0){
        return State.Above;
    }
       return State.Below;
       }

}


