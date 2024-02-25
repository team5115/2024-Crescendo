package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;

public class PrepareShoot extends SequentialCommandGroup{
    public PrepareShoot(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
        addRequirements(intake, shooter, arm);
        addCommands(
            // deploy
            new DeployArm(intake, shooter, arm, 5).withTimeout(4),
            
            // rack
            new InstantCommand(intake :: out),
            new WaitForSensorChange(false, sensor),
            new WaitCommand(0.05),
            new InstantCommand(intake :: stop),

            // spin up
            new SpinUpShooter(shooter, 5000, true)
        );
    }
}
