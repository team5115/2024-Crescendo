package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;

public class Vomit extends SequentialCommandGroup{
    public Vomit(Shooter shooter, Intake intake){
        addCommands(
            new InstantCommand(shooter :: fastBackwards),
            new InstantCommand(intake :: fastOut)
        );        
    }
}
