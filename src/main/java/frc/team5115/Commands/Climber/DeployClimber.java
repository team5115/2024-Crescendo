package frc.team5115.Commands.Climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Climber;

public class DeployClimber extends SequentialCommandGroup {
     public DeployClimber(Climber climber) {
        addCommands(
            new ReleaseLatches(climber, 0.5),
            new ReachTop(climber)
        );
    }
}
