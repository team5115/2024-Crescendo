package frc.team5115.Classes.Software;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    final CANSparkMax motor;
    RelativeEncoder intakeEncoder;
    
    public Intake(int canId){
        motor = new CANSparkMax(canId, MotorType.kBrushless);
        motor.setIdleMode(IdleMode.kBrake);
        motor.setSmartCurrentLimit(30);
        intakeEncoder = motor.getEncoder();
    }

    public void in(){
        motor.set(+1);
    }

    public void fastIn() {
        motor.set(+1);
    }

    public void out() {
        motor.set(-1);
    }
    
    public void fastOut(){
        motor.set(-1);
    }

    public void ampOut() {
        motor.set(-0.75);
    }

    public void stop(){
        motor.set(0);
    }

    public void setPercent(double percent) {
        motor.set(percent);
    }

    public double getIntakeSpeed(){
        return intakeEncoder.getVelocity();
    }
}
