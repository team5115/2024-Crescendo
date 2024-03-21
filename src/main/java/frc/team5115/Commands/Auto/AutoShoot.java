package frc.team5115.Commands.Auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.AutoAimAndRange;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Combo.IntakeSequence;
import frc.team5115.Commands.Combo.PrepareShoot;
import frc.team5115.Commands.Combo.TriggerShoot;

public class AutoShoot extends SequentialCommandGroup {
    public AutoShoot(boolean actuallyRun, Drivetrain drivetrain, Intake intake, Shooter shooter, Arm arm, DigitalInput sensor, AutoAimAndRange autoAimAndRange) {
        if (!actuallyRun) return;

        addCommands(
            // shoot the preloaded note
            new PrepareShoot(intake, shooter, arm, sensor, 5, 5000, null, false),
            new TriggerShoot(intake, shooter, arm, sensor)
        );
    }
    
}
