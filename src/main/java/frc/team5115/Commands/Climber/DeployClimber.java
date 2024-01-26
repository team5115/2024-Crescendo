package frc.team5115.Commands.Climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Climber;

public class DeployClimber extends Command {
    final Climber climber;
    public DeployClimber(Climber climber) {
        this.climber = climber;
    }

    @Override
    public void initialize() {
        // deploy the climber because it is only a simple thing...
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
