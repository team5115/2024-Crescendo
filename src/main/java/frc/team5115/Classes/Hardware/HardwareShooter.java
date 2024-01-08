package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public class HardwareShooter{
    // TODO find shooter motor max voltage
    private static final double SHOOTER_MAX_VOLTAGE = 12;
    private static final double SHOOTER_MIN_VOLTAGE = 0.05;

    private CANSparkMax feederMotor;
    private CANSparkMax shooterMotor;
    private RelativeEncoder shooterEncoder;
    private SimpleMotorFeedforward shooterFF;
    private PIDController shooterPID;

    public HardwareShooter() {
        // TODO shooter can IDs
        feederMotor = new CANSparkMax(0, MotorType.kBrushless);
        shooterMotor = new CANSparkMax(0, MotorType.kBrushless);
        
        shooterEncoder = shooterMotor.getEncoder();
        shooterEncoder.setVelocityConversionFactor(0); // TODO shooter velocity conversion factor

        // TODO tune shooter feed forward and PID
        shooterFF = new SimpleMotorFeedforward(0, 0, 0);
        shooterPID = new PIDController(0, 0, 0);
    }

    public void setFeederSpeed(double speed) {
        feederMotor.set(speed);
    }

    // Units?
    public void setShooterSpeed(double speed) {
        if (Math.abs(speed) < SHOOTER_MIN_VOLTAGE) {
            shooterMotor.setVoltage(+0);
            return;
        }

        double voltage = shooterFF.calculate(speed);
        voltage += shooterPID.calculate(speed, getShooterVelocity());
        voltage = MathUtil.clamp(voltage, -SHOOTER_MAX_VOLTAGE, SHOOTER_MAX_VOLTAGE);
        shooterMotor.setVoltage(voltage);
    }

    public double getShooterVelocity() {
        return shooterEncoder.getVelocity();
    }
    
}
