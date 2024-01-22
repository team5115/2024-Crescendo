package frc.team5115.Commands.Combo;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Shooter;

public class SpinUpShooter extends Command{
    final static double TOLERANCE = 20;
    final Shooter shooter;
    final GenericEntry rpmEntry;
    double rpm;
    boolean atSpeed;

    // public SpinUpShooter(Shooter shooter, double rpm, GenericEntry rpmEntry, GenericEntry percentageEntry) {
    //     this.shooter = shooter;
    //     this.rpmEntry = rpmEntry;
    //     this.percentEntry = percentageEntry;
    // }

    public SpinUpShooter(Shooter shooter, GenericEntry rpmEntry, double defaultRpm) {
        this.shooter = shooter;
        this.rpmEntry = rpmEntry;
        this.rpm = defaultRpm;
    }

    @Override
    public void initialize() {
        rpm = rpmEntry.getDouble(rpm);
    }

    @Override
    public void execute() {
        shooter.spinByPid(rpm);
        atSpeed = MathUtil.isNear(rpm, shooter.getClockwiseSpeed(), TOLERANCE) && MathUtil.isNear(rpm, shooter.getCounterClockwiseSpeed(), TOLERANCE);
    }

    @Override
    public boolean isFinished() {
        return atSpeed;
    }

    @Override
    public void end(boolean interrupted) {
        double avg = shooter.getAverageSpeed();
        double delta = avg - rpm;
        System.out.println("Goal: " + rpm + " | CW: " + shooter.getClockwiseSpeed() + " | CCW: " + shooter.getCounterClockwiseSpeed() + " | Average: " + avg + " | Avg. Delta: " + delta);
    }
}
