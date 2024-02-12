package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.StowArm;

public class CancelIntake extends SequentialCommandGroup {
    public CancelIntake(Intake intake, Shooter shooter, Arm arm, IntakeSequence intakeSequence) {   
        addCommands(
            new InstantCommand(intakeSequence :: cancel),
            new StopBoth(intake, shooter),
            new StowArm(intake, shooter, arm)
        );
    }
}
