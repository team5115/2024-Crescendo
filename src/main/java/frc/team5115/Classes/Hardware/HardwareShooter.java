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
    private final CANSparkMax extraMotor;
    private final RelativeEncoder extraEncoder;

    public HardwareShooter(int clockwiseCanId, int counterclockwiseCanId) {
        cwMotor = new CANSparkMax(clockwiseCanId, MotorType.kBrushless);
        ccwMotor = new CANSparkMax(counterclockwiseCanId, MotorType.kBrushless);
        extraMotor = new CANSparkMax(35, MotorType.kBrushless);
        cwMotor.setClosedLoopRampRate(0.1);
        ccwMotor.setClosedLoopRampRate(0.1);
        extraMotor.setClosedLoopRampRate(0.1);
        cwEncoder = cwMotor.getEncoder();
        ccwEncoder = ccwMotor.getEncoder();
        extraEncoder = extraMotor.getEncoder();
        cwMotor.setInverted(true);
        ccwMotor.setInverted(false);
        extraMotor.setInverted(false);
    }

    public void setVoltage(double cwVolts, double ccwVolts, double thirdVolts) {
        cwMotor.setVoltage(cwVolts);
        ccwMotor.setVoltage(ccwVolts);
        extraMotor.setVoltage(thirdVolts);
    }

    public void setVoltage(double voltage) {
        setVoltage(voltage, voltage, voltage);
    }

    public void setNormalized(double speed) {
        setNormalized(speed, speed, speed);
    }

    public void setNormalized(double cwSpeed, double ccwSpeed, double extraSpeed) {
        cwMotor.set(cwSpeed);
        ccwMotor.set(ccwSpeed);
        extraMotor.set(extraSpeed);
    }

    public double getClockwiseVelocity() {
        return cwEncoder.getVelocity();
    }

    public double getCounterClockwiseVelocity() {
        return ccwEncoder.getVelocity();
    }

    public double getExtraVelocity() {
        return extraEncoder.getVelocity();
    }

    public void setIdleMode(IdleMode mode) {
        cwMotor.setIdleMode(mode);
        ccwMotor.setIdleMode(mode);
        extraMotor.setIdleMode(mode);
    }
}
