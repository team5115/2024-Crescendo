package frc.team5115.Classes.Software;

import java.util.List;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Commands.Combo.PrepareShoot;
import frc.team5115.Commands.Combo.TriggerShoot;

public class PathAuto {
    
    public final static PathPlannerAuto SideAutoPt1 = new PathPlannerAuto("Example Auto");
   // public final static Commmand PrepareShootClose =  

    public final static List<PathPlannerPath> sideAutoPt1Group = PathPlannerAuto.getPathGroupFromAutoFile("Example Auto");

    public Command getSideAutoPt1(){
        return SideAutoPt1;
 
    }

    public PathPlannerPath getSideAutoList(int i){
        return sideAutoPt1Group.get(i);
    }

    public void registerCommands(PrepareShoot prepareShoot, TriggerShoot triggerShoot){
        NamedCommands.registerCommand("PrepareShootClose", prepareShoot);
        NamedCommands.registerCommand("PrepareShootFar", prepareShoot);
        NamedCommands.registerCommand("TriggerShoot", triggerShoot);



    }

}
