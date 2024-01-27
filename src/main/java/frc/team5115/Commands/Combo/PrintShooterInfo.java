package frc.team5115.Commands.Combo;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Shooter;

public class PrintShooterInfo extends Command{
    final Shooter shooter;
    final GenericEntry entry;
    final boolean runOnce;
    double rpm;
    public PrintShooterInfo(Shooter shooter, GenericEntry entry, double rpm, boolean runOnce) {
        this.shooter = shooter;
        this.entry = entry;
        this.runOnce = runOnce;
        this.rpm = rpm;
    }

    @Override
    public void initialize() {
        rpm = entry.getDouble(rpm);
    }

    @Override
    public void execute() {
        double avg = shooter.getAverageSpeed();
        double delta = avg - rpm;
        double percentError = (avg - rpm) / rpm;
        System.out.print("----ShooterInfo print: ");
        System.out.println("Goal: " + rpm
        + " | CW: " + shooter.getClockwiseSpeed()
        + " | CCW: " + shooter.getCounterClockwiseSpeed()
        + " | Average: " + avg
        + " | Avg. Delta: " + delta
        + " | Avg. Percent Error: " + percentError);
    }

    @Override
    public boolean isFinished() {
        return runOnce;
    }
}
