package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Shooter;

public class SpinUpShooter extends Command{
    final Shooter shooter;
    
    public SpinUpShooter(Shooter shooter) {
        this.shooter = shooter;
    }
}
