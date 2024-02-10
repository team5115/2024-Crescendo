package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;

public class IntakeSequence extends SequentialCommandGroup{
    
    public IntakeSequence(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
        addRequirements(intake, shooter, arm);
        addCommands(
            new DeployArm(intake, shooter, arm, 4).withTimeout(5),

            // Intake
            new InstantCommand(intake :: fastIn),
            new InstantCommand(shooter :: slow),
            new WaitForSensorChange(true, sensor).withTimeout(10),
            new InstantCommand(intake :: stop),
            new InstantCommand(shooter :: stop),

            // Rack
            new WaitCommand(0.5),
            new InstantCommand(intake :: out),
            new WaitForSensorChange(false, sensor).withTimeout(1),
            new InstantCommand(intake :: stop)
        );
    }
}
