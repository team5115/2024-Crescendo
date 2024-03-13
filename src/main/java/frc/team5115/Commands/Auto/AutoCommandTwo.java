package frc.team5115.Commands.Auto;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.AutoAimAndRange;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Combo.IntakeSequence;
import frc.team5115.Commands.Combo.PrepareShoot;
import frc.team5115.Commands.Combo.TriggerShoot;


public class AutoCommandTwo extends SequentialCommandGroup {
    final Drivetrain drivetrain;
    private final AutoAimAndRange autoAimAndRange; 

    
    public AutoCommandTwo(Drivetrain drivetrain, boolean actuallyRun, Intake intake, Shooter shooter, Arm arm, DigitalInput d, AutoAimAndRange aAR){
        this.drivetrain = drivetrain;
        autoAimAndRange = aAR;
        if (!actuallyRun) return;
        PathPlannerPath path = PathPlannerPath.fromPathFile("surfers");


        addCommands(
            
        // new AutoPart1(drivetrain, actuallyRun, intake, shooter, arm, d),
        new PrepareShoot(intake, shooter, arm, d).withTimeout(2),
        new TriggerShoot(intake, shooter, arm, d),
        new IntakeSequence(intake, shooter, arm, d).raceWith(AutoBuilder.followPath(path)),
        new AutoAimAndRangeCommand(autoAimAndRange).withTimeout(10),
        new WaitCommand(0.5),
        new PrepareShoot(intake, shooter, arm, d).withTimeout(2),
        new TriggerShoot(intake, shooter, arm, d)


        );
    }
}
