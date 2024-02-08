package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;
import frc.team5115.Commands.Arm.StowArm;

public class IntakeSequence extends SequentialCommandGroup{
    
    public IntakeSequence(double angle, Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
        
        addCommands(
            new DeployArm(arm, angle),
            new InstantCommand(intake :: in),
            new InstantCommand(shooter :: slow),
            new WaitForSensorChange(true, sensor),
            new InstantCommand(intake :: stop),
            new InstantCommand(shooter :: stop)
        );
    }
}
