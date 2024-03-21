package frc.team5115.Commands.Auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.AutoAimAndRange;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;
import frc.team5115.Commands.Combo.IntakeSequence;
import frc.team5115.Commands.Combo.PrepareShoot;
import frc.team5115.Commands.Combo.TriggerShoot;
import frc.team5115.Commands.Combo.WaitForSensorChange;

public class DriveWhileIntaking extends ParallelRaceGroup {
    public DriveWhileIntaking(boolean actuallyRun, Drivetrain drivetrain, Intake intake, Shooter shooter, Arm arm, DigitalInput sensor, AutoAimAndRange autoAimAndRange) {

        addCommands(
            // distance is not used
            new DriveByTime(drivetrain, 1.25, 1, 4),
            new IntakeSequence(intake, shooter, arm, sensor)
            )
            
            // // auto aim and range to line up the note while preparing shot
            // new AutoAimAndRangeCommand(autoAimAndRange)
            //     .alongWith(new PrepareShoot(intake, shooter, arm, sensor, 28, 5000, null, false)),

            // // trigger the shot
            // new TriggerShoot(intake, shooter, arm, sensor)
        ;
    }
    
}
