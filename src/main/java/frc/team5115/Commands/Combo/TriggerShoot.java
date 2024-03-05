package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;

public class TriggerShoot extends SequentialCommandGroup {
    public TriggerShoot(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
        addRequirements(intake, shooter, arm);
        addCommands(
            new InstantCommand(intake :: fastIn),
            new WaitCommand(0.5),
            new StopBoth(intake, shooter)
        );
    }
}
