package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class HardwareShooter{
    private final CANSparkMax cwMotor;
    private final CANSparkMax ccwMotor;
    private final RelativeEncoder shooterEncoder;

    public HardwareShooter() {
        cwMotor = new CANSparkMax(9, MotorType.kBrushless);
        ccwMotor = new CANSparkMax(11, MotorType.kBrushless);
        cwMotor.setClosedLoopRampRate(0.1);
        ccwMotor.setClosedLoopRampRate(0.1);
        shooterEncoder = cwMotor.getEncoder();
        shooterEncoder.setVelocityConversionFactor(1);
    }

    public void setVoltage(double voltage) {
        cwMotor.setVoltage(voltage);
        ccwMotor.setVoltage(voltage);
    }

    public void setNormalized(double speed) {
        cwMotor.set(speed);
        ccwMotor.set(speed);
    }

    public double getShooterVelocity() {
        return shooterEncoder.getVelocity();
    }
}
