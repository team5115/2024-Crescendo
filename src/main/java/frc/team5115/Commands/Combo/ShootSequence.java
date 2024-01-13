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
    
    public ShootSequence(Intake intake, Shooter shooter, Arm arm, DigitalInput dioSensorFlywheels) {
        
        addCommands(
            // new DeployArm(arm),
            new InstantCommand(intake :: out),
            new WaitForSensorChange(false, dioSensorFlywheels),
            new InstantCommand(intake :: stop),
            new SpinUpShooter(shooter, 3500.0).withTimeout(2.5),
            new InstantCommand(intake :: in),
            new WaitCommand(1),
            new InstantCommand(intake :: stop),
            new InstantCommand(shooter :: stop)
        );
    }
}
