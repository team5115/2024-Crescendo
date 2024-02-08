package frc.team5115.Commands.Climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Climber;

public class WaitForDeployed extends Command {
    final Climber climber;
    
    public WaitForDeployed(Climber climber) {
        this.climber = climber;
    }
    
    @Override
    public boolean isFinished() {
        return climber.isFullyDeployed();
    }
}
