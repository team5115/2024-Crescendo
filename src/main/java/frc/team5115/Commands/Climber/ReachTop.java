package frc.team5115.Commands.Climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Climber;

public class ReachTop extends Command {
    final Climber climber;

    public ReachTop(Climber climber) {
        this.climber = climber;
    }

    @Override
    public void initialize() {
        climber.letOutSlow();
    }

    @Override
    public boolean isFinished() {
        return climber.isFullyDeployed();
    }

    @Override
    public void end(boolean interrupted) {
        climber.stop();
    }
}
