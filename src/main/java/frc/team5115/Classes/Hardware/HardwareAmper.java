package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HardwareAmper extends SubsystemBase {
    private final CANSparkMax motor;
    public DigitalInput out;
    public DigitalInput in;

    //public final DigitalInput out;
    //public final DigitalInput in;

    public HardwareAmper(){
        motor = new CANSparkMax(0, MotorType.kBrushed);

        out = new DigitalInput(0);
        in = new DigitalInput(0);

    }

    public void set(double speed){
        motor.set(speed);
    }

    public boolean out(){
        return out.get();
    }

    public boolean in(){
        return in.get();
    }
}
