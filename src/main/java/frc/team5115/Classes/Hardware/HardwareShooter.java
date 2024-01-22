package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Config;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Mechanism;

public class HardwareShooter extends SubsystemBase{
    private final CANSparkMax cwMotor;
    private final CANSparkMax ccwMotor;
    private final RelativeEncoder cwEncoder;
    private final RelativeEncoder ccwEncoder;

    SysIdRoutine sysIdRoutine;

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

    public void setSysIdRoutine(boolean useCw) {
        if (useCw) {
            setSysIdRoutine(cwMotor, "Clockwise Motor");
        } else {
            setSysIdRoutine(ccwMotor, "Counter-clockwise Motor");
        }
    }

    public void setSysIdRoutine(CANSparkMax motor, String name) {
        RelativeEncoder encoder = motor.getEncoder();
        Config config = new Config();
        Mechanism mechanism = new Mechanism(
            voltage -> {
               motor.set(voltage.magnitude() / RobotController.getBatteryVoltage()); 
            }, log -> {
            log.motor(name).voltage(Units.Volts.of(motor.get() * RobotController.getBatteryVoltage()))
                .angularPosition(Units.Rotations.of(encoder.getPosition()))
                .angularVelocity(Units.RPM.of(encoder.getVelocity()));
        }, this);
        sysIdRoutine = new SysIdRoutine(config, mechanism);
    }

    public SysIdRoutine getSysIdRoutine() {
        return sysIdRoutine;
    }
}
