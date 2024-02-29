package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Amper;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;

public class ScoreAmp extends SequentialCommandGroup {
    final Intake intake;
    final Shooter shooter;

    public ScoreAmp(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor, Amper amper) {
        this.intake = intake;
        this.shooter = shooter;
        addCommands(
            // deploy both
            new DeployArm(intake, shooter, arm, 100).withTimeout(5),
            new SpinAmper(amper, Amper.OUT_ANGLE, +0.15),

            // score
            new InstantCommand(intake :: out),
            new WaitForSensorChange(false, sensor).withTimeout(5),
            new WaitCommand(3),
            new InstantCommand(intake :: stop),
            
            // stow amper
            new SpinAmper(amper, Amper.IN_ANGLE, -0.15)
        );
    }
}