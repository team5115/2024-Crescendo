package frc.team5115.Commands.Combo;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Shooter;

public class SpinUpShooter extends Command{
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
        // ! commanded value is multiplied by 1.1 because of offset and we would rather go above the goal than below
        // from testing, the average rpm offset was around 50 rpm, and a percent error of between 0.3 and 0.1
        // ideally, the feedforward values would be so good that we don't need this thing here
        shooter.spinByPid(rpm * 1.1);
        // shooter.printInfo();
        
        atSpeed = 
            Math.abs(shooter.getClockwiseSpeed()) > rpm &&
            Math.abs(shooter.getCounterClockwiseSpeed()) > rpm;
    }

    @Override
    public boolean isFinished() {
        return atSpeed;
    }

    @Override
    public void end(boolean interrupted) {
        // double avg = shooter.getAverageSpeed();
        // double delta = avg - rpm;
        // double percentError = (avg - rpm) / rpm;
        // System.out.println("Goal: " + rpm
        // + " | CW: " + shooter.getClockwiseSpeed()
        // + " | CCW: " + shooter.getCounterClockwiseSpeed()
        // + " | Average: " + avg
        // + " | Avg. Delta: " + delta
        // + " | Avg. Percent Error: " + percentError);
    }
}
