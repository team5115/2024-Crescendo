package frc.team5115.Commands.Auto;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.AutoAimAndRange;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Paths;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Combo.IntakeSequence;
import frc.team5115.Commands.Combo.ShootSequence;
import frc.team5115.Commands.Combo.SpinUpShooter;


public class AutoCommandGroup extends SequentialCommandGroup {
    Drivetrain drivetrain; 
    Arm arm;
    Paths paths;
    AutoSegment1 autoSegment1;
    IntakeSequence intakeSequence;
    SpinUpShooter spinUpShooter;
    ShootSequence shootSequence;
    
    AutoAimAndRange autoAimAndRange;

    public AutoCommandGroup(Drivetrain drivetrain, boolean actuallyRun, SpinUpShooter spinUpShooter,ShootSequence shootSequence, IntakeSequence intakeSequence, Paths paths, Arm arm, AutoSegment1 autoSegment1, AutoAimAndRange autoAimAndRange){
        this.drivetrain = drivetrain;
        this.paths = paths;
        this.arm = arm;
        this.autoSegment1 = autoSegment1;
        this.spinUpShooter = spinUpShooter;
        this.arm = arm;
        this.shootSequence = shootSequence;
        this.intakeSequence = intakeSequence;
        this.autoAimAndRange = autoAimAndRange;

        if (!actuallyRun) return;

            addCommands(
                new AutoSegment1(),
                new AutoAimAndRange(drivetrain)
            );

       

    }

        
    

   }
