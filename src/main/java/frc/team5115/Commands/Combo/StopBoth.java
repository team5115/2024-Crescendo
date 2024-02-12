package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;

public class StopBoth extends SequentialCommandGroup {

    public StopBoth(Intake intake, Shooter shooter) {
        addCommands(
            new InstantCommand(intake :: stop),
            new InstantCommand(shooter :: stop)
        );
    }
}
