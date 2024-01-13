package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;

public class ShootSequence extends SequentialCommandGroup{
    
    public ShootSequence(Intake intake, Shooter shooter, Arm arm) {
        
        addCommands(
            new DeployArm(arm),
            new InstantCommand(intake :: out),
            new WaitForSensor(false),
            new InstantCommand(intake :: stop),
            new SpinUpShooter(shooter),
            new InstantCommand(intake :: in),
            new WaitCommand(2),
            new InstantCommand(intake :: stop),
            new InstantCommand(shooter :: stop)
        );
    }
}
