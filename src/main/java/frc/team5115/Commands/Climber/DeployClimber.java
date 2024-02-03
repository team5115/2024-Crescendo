package frc.team5115.Commands.Climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Climber;

public class DeployClimber extends SequentialCommandGroup {
     public DeployClimber(Climber climber, double desiredRotationDelta) {
        addCommands(
            new ReleaseLatches(climber, desiredRotationDelta),
            new WaitForDeployed(climber),
            new InstantCommand(climber :: setDeployed)
        );
    }
}
