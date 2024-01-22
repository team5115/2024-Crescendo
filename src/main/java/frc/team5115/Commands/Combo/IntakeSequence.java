package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;

public class IntakeSequence extends SequentialCommandGroup{
    
    public IntakeSequence(Intake intake, Shooter shooter, Arm arm, DigitalInput dioSensorFlywheel) {
        
        addCommands(
            // new DeployArm(arm),
            new InstantCommand(intake :: in),
            new InstantCommand(shooter :: slow),
            new WaitForSensorChange(true, dioSensorFlywheel),
            new InstantCommand(intake :: stop),
            new InstantCommand(shooter :: stop)
        );
    }
}
