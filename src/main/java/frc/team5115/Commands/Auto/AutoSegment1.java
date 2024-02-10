package frc.team5115.Commands.Auto;

import java.util.List;

import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Paths;
import frc.team5115.Classes.Software.Shooter;

public class AutoSegment1 extends SequentialCommandGroup{

    List<PathPlannerPath> pathGroup = PathPlannerAuto.getPathGroupFromAutoFile("Top Best Auto");

 

}
