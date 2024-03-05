package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HardwareAmper extends SubsystemBase {
    private final CANSparkMax motor;
    private final AbsoluteEncoder encoder;

    public HardwareAmper(int canID){
        motor = new CANSparkMax(canID, MotorType.kBrushed);
        encoder = motor.getAbsoluteEncoder(SparkAbsoluteEncoder.Type.kDutyCycle);
        encoder.setPositionConversionFactor(360);
    }

    public void set(double speed){
        motor.set(speed);
    }

    public double getAngle(){
        return encoder.getPosition();
    }
}
