package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HardwareAmper extends SubsystemBase {
    private final CANSparkMax motor;
    private final DutyCycleEncoder encoder;

    public HardwareAmper(int canID, int encoderID){
        motor = new CANSparkMax(canID, MotorType.kBrushed);
        encoder = new DutyCycleEncoder(encoderID);
    }

    public void set(double speed){
        motor.set(speed);
    }

    public double getAngle(){
        return encoder.get() * 360.0;
    }

    public void resetEncoder(){
        encoder.reset();
    }
}
