package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.StowArm;

public class Stow extends SequentialCommandGroup{
    public Stow(Intake intake, Shooter shooter, Arm arm) {
        addCommands(
            new InstantCommand(intake :: stop),
            new InstantCommand(shooter :: stop),
            new StowArm(arm)
        );
    }
}
