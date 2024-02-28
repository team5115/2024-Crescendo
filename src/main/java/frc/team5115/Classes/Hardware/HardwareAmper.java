package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Accessory.Angle;

public class HardwareAmper extends SubsystemBase {
    private final CANSparkMax motor;
    public Encoder absEncoder;
    private final DutyCycleEncoder encoder;

    //public final DigitalInput out;
    //public final DigitalInput in;

    public HardwareAmper(){
        motor = new CANSparkMax(0, MotorType.kBrushed);
        encoder = new DutyCycleEncoder(0);
        //out = new DigitalInput(0);
        //in = new DigitalInput(0);

    }

    public void set(double speed){
        motor.set(speed);
    }

    public Angle getAngle(){
        return new Angle(encoder.get());
    }

    public void resetEncoder(){
        encoder.reset();
    }
}
