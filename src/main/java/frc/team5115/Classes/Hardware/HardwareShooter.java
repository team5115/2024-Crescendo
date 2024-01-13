package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public class HardwareShooter{
    // TODO find shooter motor max voltage
    private static final double SHOOTER_MAX_VOLTAGE = 12;
    private static final double SHOOTER_MIN_RPM = 1;

    private CANSparkMax shooterMotorLeft;
    private CANSparkMax shooterMotorRight;
    private RelativeEncoder shooterEncoder;
    private SimpleMotorFeedforward shooterFF;
    private PIDController shooterPID;

    public HardwareShooter() {
        // TODO shooter can IDs
        shooterMotorLeft = new CANSparkMax(0, MotorType.kBrushless);
        shooterMotorRight = new CANSparkMax(0, MotorType.kBrushless);

        shooterMotorLeft.setIdleMode(IdleMode.kCoast);
        shooterMotorRight.setIdleMode(IdleMode.kCoast);

        shooterEncoder = shooterMotorLeft.getEncoder();
        shooterEncoder.setVelocityConversionFactor(0); // TODO shooter velocity conversion factor
  
        // TODO tune shooter feed forward and PID
        shooterFF = new SimpleMotorFeedforward(0, 0, 0);
        shooterPID = new PIDController(0, 0, 0);
    }

    public void setShooterSpeedRPM(double speedRpm) {
        if (Math.abs(speedRpm) < SHOOTER_MIN_RPM) {
            shooterMotorLeft.setVoltage(+0);
            return;
        }

        double voltage = shooterFF.calculate(speedRpm);
        voltage += shooterPID.calculate(speedRpm, getShooterVelocity());
        voltage = MathUtil.clamp(voltage, -SHOOTER_MAX_VOLTAGE, SHOOTER_MAX_VOLTAGE);
        shooterMotorLeft.setVoltage(voltage);
    }

    public void setVoltage(double voltage) {
        shooterMotorLeft.setVoltage(voltage);
    }

    public double getShooterVelocity() {
        return shooterEncoder.getVelocity();
    }
}
