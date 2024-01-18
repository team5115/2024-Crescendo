package frc.team5115.Commands.Combo;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Shooter;

public class SpinUpShooter extends Command{
    final Shooter shooter;
    double rpm;
    double percentage;
    boolean atSpeed;

    GenericEntry rpmEntry;
    GenericEntry percentEntry;
    
    // public SpinUpShooter(Shooter shooter, double rpm) {
    //     this.shooter = shooter;
    //     this.rpm = rpm;
    //     this.percentage = 0.9;
    // }

    public SpinUpShooter(Shooter shooter, double rpm, GenericEntry rpmEntry, GenericEntry percentageEntry) {
        this.shooter = shooter;
        this.rpmEntry = rpmEntry;
        this.percentEntry = percentageEntry;
    }

    @Override
    public void execute() {
        // System.out.println("cw motor rpm: " + shooter.getSpeed());
    }

    @Override
    public void initialize() {
        rpm = rpmEntry.getDouble(rpm);
        percentage = percentEntry.getDouble(0.9);
        shooter.customPercentage(percentage);
    }

    @Override
    public boolean isFinished() {
        return shooter.getSpeed() > rpm;
    }
}
