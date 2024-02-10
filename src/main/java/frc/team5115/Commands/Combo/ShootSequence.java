package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;

public class ShootSequence extends SequentialCommandGroup{
    public ShootSequence(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
        addRequirements(intake, shooter, arm);
        addCommands(
            new DeployArm(arm, 5),

            // // Cock note
            // new InstantCommand(intake :: out),
            // new WaitForSensorChange(false, sensor),
            // new InstantCommand(intake :: stop),

            // Shoot
            new SpinUpShooter(shooter, 5000).withTimeout(4),
            new InstantCommand(intake :: fastIn),
            new WaitForSensorChange(true, sensor).withTimeout(0.5),
            new WaitCommand(0.15),

            // Stop stuff
            new InstantCommand(intake :: stop),
            new InstantCommand(shooter :: stop),
            new WaitCommand(0.35)
            // new StowArm(arm)
        );
    }
}
