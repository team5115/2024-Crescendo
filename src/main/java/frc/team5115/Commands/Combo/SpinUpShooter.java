package frc.team5115.Commands.Combo;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Shooter;

public class SpinUpShooter extends Command{
    final Shooter shooter;
    double rpm;
    boolean atSpeed = false;

    /**
     * Construct a SpinUpShooter command given a shooter subsystem and a commanded RPM to spin up to
     * @param shooter 
     * @param rpm is the commanded RPM to spin the shooter up to
     */
    public SpinUpShooter(Shooter shooter, double rpm) {
        this.shooter = shooter;
        this.rpm = rpm;
    }

    @Override
    public void execute() {
        // ! commanded value is multiplied by 1.1 because of offset and we would rather go above the goal than below
        // from testing, the average rpm offset was around 50 rpm, and a percent error of between 0.3 and 0.1
        // ideally, the feedforward values would be so good that we don't need this thing here
        double[] x = shooter.spinByPid(rpm * 1);
        atSpeed = Math.abs(x[0]) < 10 && Math.abs(x[1]) < 10;
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
        System.out.println("SpinUpShooter ENDED");
    }
}
