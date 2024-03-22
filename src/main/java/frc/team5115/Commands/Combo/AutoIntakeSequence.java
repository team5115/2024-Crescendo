package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.*;
import frc.team5115.Commands.Arm.DeployArm;

public class AutoIntakeSequence extends SequentialCommandGroup {
    final Intake intake;
        final Shooter shooter;

        public AutoIntakeSequence(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor, Drivetrain d){
            this.intake = intake;
            this.shooter = shooter;
            addCommands(
             new InstantCommand(intake :: fastIn),
             new DeployArm(intake, shooter, arm, +0).withTimeout(5).alongWith(new InstantCommand(intake :: fastIn)),
                // Intake
                new InstantCommand(intake :: fastIn),
                new InstantCommand(shooter :: slow),
                new InstantCommand(d :: driveForward),
                new WaitForSensorChange(true, sensor),
                new InstantCommand(d :: stop),
                new InstantCommand(intake :: stop),
                new InstantCommand(shooter :: stop),

                new WaitCommand(0.5)
                
            );
        }

        public void interrupt() {
            intake.stop();
            shooter.stop();
            cancel();
        }
    
}
