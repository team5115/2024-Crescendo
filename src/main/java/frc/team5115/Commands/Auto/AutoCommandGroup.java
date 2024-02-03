package frc.team5115.Commands.Auto;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Drivetrain;

public class AutoCommandGroup extends SequentialCommandGroup {
    final Drivetrain drivetrain;
    
    public AutoCommandGroup(Drivetrain drivetrain, boolean actuallyRun){
        this.drivetrain = drivetrain;

        if (!actuallyRun) return;
    }

     public Command getAutonomousCommand() {
        return new PathPlannerAuto("Example Path");
   }

   }
