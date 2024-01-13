package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Shooter;

public class SpinUpShooter extends Command{
    final Shooter shooter;
    final Timer timer;
    boolean atSpeed;
    
    public SpinUpShooter(Shooter shooter) {
        this.shooter = shooter;
        timer = new Timer();
    }

    @Override
    public void initialize() {
        shooter.fast();
        timer.reset();
        timer.start();
    }

    @Override
    public boolean isFinished() {
        return timer.get() > 1.5; 
    }
}
