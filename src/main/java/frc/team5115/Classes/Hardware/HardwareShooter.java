package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HardwareShooter extends SubsystemBase{
    private final CANSparkMax cwMotor;
    private final CANSparkMax ccwMotor;
    private final RelativeEncoder cwEncoder;
    private final RelativeEncoder ccwEncoder;

    public HardwareShooter(int clockwiseCanId, int counterclockwiseCanId) {
        cwMotor = new CANSparkMax(clockwiseCanId, MotorType.kBrushless);
        ccwMotor = new CANSparkMax(counterclockwiseCanId, MotorType.kBrushless);
        cwMotor.setClosedLoopRampRate(0.1);
        ccwMotor.setClosedLoopRampRate(0.1);
        cwEncoder = cwMotor.getEncoder();
        ccwEncoder = ccwMotor.getEncoder();
        cwMotor.setInverted(true);
        ccwMotor.setInverted(false);
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

    public void setIdleMode(IdleMode mode) {
        cwMotor.setIdleMode(mode);
        ccwMotor.setIdleMode(mode);
    }
}
