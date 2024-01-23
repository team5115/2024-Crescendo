package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HardwareShooter extends SubsystemBase{
    private final CANSparkMax cwMotor;
    private final CANSparkMax ccwMotor;
    private final RelativeEncoder cwEncoder;
    private final RelativeEncoder ccwEncoder;

    public HardwareShooter() {
        cwMotor = new CANSparkMax(9, MotorType.kBrushless);
        ccwMotor = new CANSparkMax(11, MotorType.kBrushless);
        cwMotor.setClosedLoopRampRate(0.1);
        ccwMotor.setClosedLoopRampRate(0.1);
        cwEncoder = cwMotor.getEncoder();
        ccwEncoder = ccwMotor.getEncoder();
    }

    public void setVoltage(double cwVolts, double ccwVolts) {
        cwMotor.setVoltage(cwVolts);
        ccwMotor.setVoltage(ccwVolts);
    }

    public void setVoltage(double voltage) {
        setVoltage(voltage, voltage);
    }

    public void setNormalized(double speed) {
        cwMotor.set(speed);
        ccwMotor.set(speed);
    }

    public double getClockwiseVelocity() {
        return cwEncoder.getVelocity();
    }

    public double getCounterClockwiseVelocity() {
        return ccwEncoder.getVelocity();
    }
}
