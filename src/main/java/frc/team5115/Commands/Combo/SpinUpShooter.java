package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Shooter;

public class SpinUpShooter extends Command{
    final Shooter shooter;
    double rpm;
    boolean atSpeed = false;
    final boolean neverExit;

    /**
     * Construct a SpinUpShooter command given a shooter subsystem and a commanded RPM to spin up to
     * @param shooter 
     * @param rpm is the commanded RPM to spin the shooter up to
     * @param neverExit set to true if you want it to never stop
     */
    public SpinUpShooter(Shooter shooter, double rpm, boolean neverExit) {
        this.shooter = shooter;
        this.rpm = rpm;
        this.neverExit = neverExit;
    }

    /**
     * Construct a SpinUpShooter command given a shooter subsystem and a commanded RPM to spin up to
     * @param shooter 
     * @param rpm is the commanded RPM to spin the shooter up to
     * @param neverExit set to true if you want it to never stop
     */
    public SpinUpShooter(Shooter shooter, double rpm) {
        this.shooter = shooter;
        this.rpm = rpm;
        this.neverExit = false;
    }

    @Override
    public void execute() {
        double[] x = shooter.spinGreenShooter(rpm * 1);
        System.out.println("error amount: " + x[0]);
        atSpeed = Math.abs(x[0]) < 10;
    }

    @Override
    public boolean isFinished() {
        return atSpeed && !neverExit;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            System.out.println("SpinUpShooter command interrupted!");
        }
        System.out.println("SpinUpShooter ENDED");
    }
}
