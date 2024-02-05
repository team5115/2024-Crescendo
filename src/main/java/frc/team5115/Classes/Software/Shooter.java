package frc.team5115.Classes.Software;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareShooter;

public class Shooter extends SubsystemBase {    

    // THESE CONSTANTS ARE FOR rotations/second
    // however, the rest of the code is in rpm
    // so we convert when using ff.calculate()
    // also convert for PID on both parameters 
    private static final double cwKs = 0.19894;
    private static final double cwKv = 0.12456;
    private static final double cwKa = 0.037234;
    private static final double cwKp = 0.1;
    private static final double cwKd = 0;

    private static final double ccwKs = 0.14687;
    private static final double ccwKv = 0.12423;
    private static final double ccwKa = 0.037858;
    private static final double ccwKp = 0.1;
    private static final double ccwKd = 0;

    final HardwareShooter hardwareShooter;

    final SimpleMotorFeedforward cwFF;
    final SimpleMotorFeedforward ccwFF;
    final PIDController cwPID;
    final PIDController ccwPID;
    
    public Shooter(HardwareShooter hardwareShooter) {
        this.hardwareShooter = hardwareShooter;

        cwFF = new SimpleMotorFeedforward(cwKs, cwKv, cwKa);
        ccwFF = new SimpleMotorFeedforward(ccwKs, ccwKv, ccwKa);
        cwPID = new PIDController(cwKp, 0, cwKd);
        ccwPID = new PIDController(ccwKp, 0, ccwKd); 
        cwPID.setTolerance(20);
        ccwPID.setTolerance(20);
    }

    public void slow() {
        hardwareShooter.setNormalized(0.065);
    }

    public void stop() {
        hardwareShooter.setNormalized(0);
    }

    public void fastBackwards() {
        hardwareShooter.setNormalized(-0.9);
    }

    public double getAverageSpeed() {
        return (hardwareShooter.getClockwiseVelocity() + hardwareShooter.getCounterClockwiseVelocity()) / 2.0;
    }

    public double getClockwiseSpeed() {
        return hardwareShooter.getClockwiseVelocity();
    }

    public double getCounterClockwiseSpeed() {
        return hardwareShooter.getCounterClockwiseVelocity();
    }

    public double[] spinByPid(double rpm) {
        // divide by 60 on speeds to go to rot/sec
        double rps = rpm / 60.0;
        double cwVolts = cwFF.calculate(rps);
        double ccwVolts = ccwFF.calculate(rps);
        double cwPIDValue = cwPID.calculate(hardwareShooter.getClockwiseVelocity()/60.0, rps);
        double ccwPIDValue = ccwPID.calculate(hardwareShooter.getCounterClockwiseVelocity()/60.0, rps);
        cwVolts += cwPIDValue;
        cwVolts += ccwPIDValue;
        hardwareShooter.setVoltage(cwVolts, ccwVolts);

        return new double[] { cwPIDValue, ccwPIDValue };
    }
}
