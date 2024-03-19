package frc.team5115.Classes.Software;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private final CANSparkMax motor;
    private final RelativeEncoder encoder;
    private boolean attemptingIntake;
    
    public Intake(int canId){
        motor = new CANSparkMax(canId, MotorType.kBrushless);
        motor.setIdleMode(IdleMode.kBrake);
        motor.setSmartCurrentLimit(30);
        encoder = motor.getEncoder();
        attemptingIntake = false;
    }

    public void in(){
        setPercent(+1);
    }

    public void fastIn() {
        setPercent(+1);
    }

    public void out() {
        setPercent(-1);
    }
    
    public void fastOut(){
        setPercent(-1);
    }

    public void ampOut() {
        setPercent(-0.75);
    }

    public void stop(){
        setPercent(0);
    }

    public void setPercent(double percent) {
        motor.set(percent);
        attemptingIntake = percent > 0.1;
    }

    public boolean stuck() {
        // Stuck if we are trying to move but we are not moving
        return attemptingIntake && Math.abs(encoder.getVelocity()) < 20; // TODO determine intake speed threshold to detect if "stuck"
    }
}
