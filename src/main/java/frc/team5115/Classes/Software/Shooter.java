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
    private static final double cwKs = 0.14788;
    private static final double cwKv = 0.12323;
    private static final double cwKa = 0.031313;
    private static final double cwKp = 0; //0.018058;

    private static final double ccwKs = 0.22671;
    private static final double ccwKv = 0.12119;
    private static final double ccwKa = 0.029654;
    private static final double ccwKp = 0; //0.019965;

    final HardwareShooter hardwareShooter;

    final SimpleMotorFeedforward cwFF;
    final SimpleMotorFeedforward ccwFF;
    final PIDController cwPID;
    final PIDController ccwPID;
    
    public Shooter(HardwareShooter hardwareShooter) {
        this.hardwareShooter = hardwareShooter;

        cwFF = new SimpleMotorFeedforward(cwKs, cwKv, cwKa);
        ccwFF = new SimpleMotorFeedforward(ccwKs, ccwKv, ccwKa);
        cwPID = new PIDController(cwKp, 0, 0);
        ccwPID = new PIDController(ccwKp, 0, 0); 
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
        hardwareShooter.setNormalized(-1);
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

    public void spinByPid(double rpm) {
        // divide by 60 on speeds to go to rot/sec
        double rps = rpm / 60.0;
        double cwVolts = cwFF.calculate(rps);
        double ccwVolts = ccwFF.calculate(rps);
        cwVolts += cwPID.calculate(hardwareShooter.getClockwiseVelocity()/60.0, rps);
        cwVolts += cwPID.calculate(hardwareShooter.getCounterClockwiseVelocity()/60.0, rps);
        hardwareShooter.setVoltage(cwVolts, ccwVolts);
    }
}
