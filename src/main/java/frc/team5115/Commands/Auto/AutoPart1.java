package frc.team5115.Commands.Auto;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Commands.Auto.AutoAimAndRangeCommand;
import frc.team5115.Commands.Combo.IntakeSequence;
import frc.team5115.Classes.Software.AutoAimAndRange;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Classes.Software.Arm;
import edu.wpi.first.wpilibj.DigitalInput;
 import frc.team5115.Commands.Combo.IntakeSequence;
 import frc.team5115.Commands.Combo.ShootSequence;
 import frc.team5115.Commands.Combo.SpinUpShooter;


public class AutoPart1 extends ParallelRaceGroup {
    Drivetrain drivetrain; 
    // Arm arm;
    AutoAimAndRangeCommand autoAimAndRangeCommand;
    // IntakeSequence intakeSequence;
    // SpinUpShooter spinUpShooter;
    // ShootSequence shootSequence;
    

//     public AutoCommandGroup(Drivetrain drivetrain, boolean actuallyRun, SpinUpShooter spinUpShooter,ShootSequence shootSequence, IntakeSequence intakeSequence, Paths paths, Arm arm, AutoSegment1 autoSegment1, AutoAimAndRange autoAimAndRange){
//         this.drivetrain = drivetrain;
//         this.paths = paths;
//         this.arm = arm;
//         this.autoSegment1 = autoSegment1;
//         this.spinUpShooter = spinUpShooter;
//         this.arm = arm;
//         this.shootSequence = shootSequence;
//         this.intakeSequence = intakeSequence;
//         this.autoAimAndRange = autoAimAndRange;

//         if (!actuallyRun) return;

//             addCommands(
//                 new AutoSegment1(),
//                 new AutoAimAndRange(drivetrain)
//             );

       

//     }

    

//    }
 public AutoPart1(Drivetrain drivetrain, boolean actuallyRun, Intake intake, Shooter shooter, Arm arm, DigitalInput d){
        this.drivetrain = drivetrain;
        this.autoAimAndRangeCommand = autoAimAndRangeCommand;

        if (!actuallyRun) return;

            addCommands(
                new DriveTimed(drivetrain, 10, 0),
                new IntakeSequence(intake, shooter, arm, d)     
                
            );

       

    }

        
    

   }