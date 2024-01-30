package frc.team5115.Commands.Climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Climber;

public class LatchSequence extends SequentialCommandGroup {
     public LatchSequence(Climber climber) {
        
        addCommands(
            new ReleaseLatch(climber, 0.075),
            new ReachTop(climber, 0.2)
        );
    }
}
