package frc.team5115.Commands.Auto;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Commands.Auto.AutoAimAndRangeCommand;
import frc.team5115.Commands.Combo.IntakeSequence;
import frc.team5115.Commands.Combo.PrepareShoot;
import frc.team5115.Commands.Combo.ShootSequence;
import frc.team5115.Commands.Combo.TriggerShoot;
import frc.team5115.Classes.Software.AutoAimAndRange;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Classes.Software.Arm;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Drivetrain;


public class AutoCommandGroup extends SequentialCommandGroup {
    final Drivetrain drivetrain;
    private final AutoAimAndRange autoAimAndRange; 

    
    public AutoCommandGroup(Drivetrain drivetrain, boolean actuallyRun, Intake intake, Shooter shooter, Arm arm, DigitalInput d, AutoAimAndRange a){
        this.drivetrain = drivetrain;
        autoAimAndRange = a;
        if (!actuallyRun) return;

        addCommands(
            
            // new AutoPart1(drivetrain, actuallyRun, intake, shooter, arm, d),
            new AutoAimAndRangeCommand(autoAimAndRange).withTimeout(10),
            new WaitCommand(0.5)
            ,new PrepareShoot(intake, shooter, arm, d).withTimeout(2)
            ,new TriggerShoot(intake, shooter, arm, d)

        );
    }
}
