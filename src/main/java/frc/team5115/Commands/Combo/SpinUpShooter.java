package frc.team5115.Commands.Combo;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Shooter;

public class SpinUpShooter extends Command{
    final Shooter shooter;
    final GenericEntry rpmEntry;
    final boolean debug;
    double rpm;
    boolean atSpeed;

    /**
     * Construct a SpinUpShooter command given a shooter subsystem and a commanded RPM to spin up to
     * @param shooter 
     * @param rpm is the commanded RPM to spin the shooter up to
     */
    public SpinUpShooter(Shooter shooter, double rpm) {
        this(shooter, null, rpm, false);
    }

    /**
     * Construct a SpinUpShooter command given a shooter subsystem,
     * a shuffleboard entry to get the commanded RPM from, and a default RPM
     * @param shooter
     * @param rpmEntry a Shuffleboard entry that will give the commanded RPM at runtime
     * @param defaultRpm the default RPM
     * @param debug is whether or not to print debug info during runtime
     */
    public SpinUpShooter(Shooter shooter, GenericEntry rpmEntry, double defaultRpm, boolean debug) {
        this.shooter = shooter;
        this.rpmEntry = rpmEntry;
        this.rpm = defaultRpm;
        this.debug = debug;
    }

    @Override
    public void initialize() {
        if (rpmEntry != null) {
            rpm = rpmEntry.getDouble(rpm);
        }
    }

    @Override
    public void execute() {
        // ! commanded value is multiplied by 1.1 because of offset and we would rather go above the goal than below
        // from testing, the average rpm offset was around 50 rpm, and a percent error of between 0.3 and 0.1
        // ideally, the feedforward values would be so good that we don't need this thing here
        shooter.spinByPid(rpm * 1.1);
        
        atSpeed = 
            Math.abs(shooter.getClockwiseSpeed()) > rpm &&
            Math.abs(shooter.getCounterClockwiseSpeed()) > rpm;

        if (debug) printInfo();
    }

    @Override
    public boolean isFinished() {
        return atSpeed;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            System.out.println("SpinUpShooter command interrupted!");
        }
    }

    private void printInfo() {
        double avg = shooter.getAverageSpeed();
        double delta = avg - rpm;
        double percentError = (avg - rpm) / rpm;
        System.out.println("Goal: " + rpm
        + " | CW: " + shooter.getClockwiseSpeed()
        + " | CCW: " + shooter.getCounterClockwiseSpeed()
        + " | Average: " + avg
        + " | Avg. Delta: " + delta
        + " | Avg. Percent Error: " + percentError);
    }
}
