package frc.team5115.Classes.Software;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareShooter;

public class Shooter extends SubsystemBase {    
    final HardwareShooter hardwareShooter;
    final PIDController pid;
    // final SimpleMotorFeedforward ff;
    
    public Shooter(HardwareShooter hardwareShooter) {
        this.hardwareShooter = hardwareShooter;
        pid = new PIDController(0, 0, 0);
        // ff = new SimpleMotorFeedforward(0, 0, 0);
    }

    public void fast() {
        hardwareShooter.setNormalized(0.9);
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

    public void spinByPid(double rpm) {
        double volts = 0;
        // volts += ff.calculate(rpm);
        volts += pid.calculate(getSpeed(), rpm);
        hardwareShooter.setVoltage(volts);
    }

    public double getSpeed() {
        return hardwareShooter.getShooterVelocity();
    }
}
