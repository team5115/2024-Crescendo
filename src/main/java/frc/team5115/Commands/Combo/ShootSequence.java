package frc.team5115.Commands.Combo;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;

public class ShootSequence extends SequentialCommandGroup{
    
    public ShootSequence(GenericEntry rpmEntry, Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
        
        addCommands(
            // new DeployArm(arm),
            new InstantCommand(intake :: out),
            new WaitForSensorChange(false, sensor),
            new InstantCommand(intake :: stop),
            new SpinUpShooter(shooter, rpmEntry, 3900, false).withTimeout(4),
            new InstantCommand(intake :: in),
            new WaitForSensorChange(true, sensor).withTimeout(0.5),
            new WaitCommand(0.1),
            new InstantCommand(intake :: stop),
            new InstantCommand(shooter :: stop)
        );
    }
}
