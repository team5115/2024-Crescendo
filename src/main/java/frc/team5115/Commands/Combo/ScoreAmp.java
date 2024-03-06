package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Amper;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.StowArm;

public class ScoreAmp extends SequentialCommandGroup {

    public ScoreAmp(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor, Amper amper) {
        
        addCommands(
            // score
            new InstantCommand(shooter :: fastBackwards),
            new InstantCommand(intake :: fastOut),
            new WaitForSensorChange(false, sensor).withTimeout(3),
            new WaitCommand(0.22),
            new InstantCommand(intake :: stop),

            new WaitCommand(0.5),
            new StowArm(intake, shooter, arm).alongWith(new SpinAmper(amper, Amper.IN_ANGLE)).withTimeout(5)
        );
    }
}