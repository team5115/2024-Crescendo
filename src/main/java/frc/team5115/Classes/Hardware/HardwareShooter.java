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

    private final CANSparkMax leaderMotor;
    private final CANSparkMax followerMotor;
    private final RelativeEncoder shooterEncoder;
    private final SimpleMotorFeedforward shooterFF;
    private final PIDController shooterPID;

    public HardwareShooter() {
        leaderMotor = new CANSparkMax(7, MotorType.kBrushless);
        followerMotor = new CANSparkMax(4, MotorType.kBrushless);

        leaderMotor.setIdleMode(IdleMode.kCoast);
        followerMotor.setIdleMode(IdleMode.kCoast);
        // TODO which one is inverted?
        leaderMotor.setInverted(false);
        followerMotor.setInverted(true); 
        followerMotor.follow(leaderMotor);

        shooterEncoder = leaderMotor.getEncoder();
        shooterEncoder.setVelocityConversionFactor(0); // TODO shooter velocity conversion factor
  
        // TODO tune shooter feed forward and PID
        shooterFF = new SimpleMotorFeedforward(0, 0, 0);
        shooterPID = new PIDController(0, 0, 0);
    }

    public void setShooterSpeedRPM(double speedRpm) {
        if (Math.abs(speedRpm) < SHOOTER_MIN_RPM) {
            leaderMotor.setVoltage(+0);
            return;
        }

        double voltage = shooterFF.calculate(speedRpm);
        voltage += shooterPID.calculate(speedRpm, getShooterVelocity());
        voltage = MathUtil.clamp(voltage, -SHOOTER_MAX_VOLTAGE, SHOOTER_MAX_VOLTAGE);
        leaderMotor.setVoltage(voltage);
    }

    public void setVoltage(double voltage) {
        leaderMotor.setVoltage(voltage);
    }

    public double getShooterVelocity() {
        return shooterEncoder.getVelocity();
    }
}
