package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;

public class Vomit extends SequentialCommandGroup{
    boolean start;
    public Vomit(boolean start, Shooter shooter, Intake intake){
       
        if(start){
            addCommands(
                new InstantCommand(shooter :: fastBackwards),
                new InstantCommand(intake :: fastOut)
        
            );
        }
        else{
            addCommands(
                new InstantCommand(shooter :: stop),
                new InstantCommand(intake :: stop)
            );
        }
    }
}
