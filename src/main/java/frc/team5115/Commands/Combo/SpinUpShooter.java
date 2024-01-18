package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Shooter;

public class SpinUpShooter extends Command{
    final Shooter shooter;
    final double rpm;
    boolean atSpeed;
    
    public SpinUpShooter(Shooter shooter, double rpm) {
        this.shooter = shooter;
        this.rpm = rpm;
    }

    @Override
    public void execute() {
        // System.out.println("cw motor rpm: " + shooter.getSpeed());
    }

    @Override
    public void initialize() {
        shooter.fast();
    }

    @Override
    public boolean isFinished() {
        return shooter.getSpeed() > rpm;
    }
}
