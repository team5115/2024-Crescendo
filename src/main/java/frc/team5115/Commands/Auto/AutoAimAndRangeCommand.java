package frc.team5115.Commands.Auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.AutoAimAndRange;


public class AutoAimAndRangeCommand extends Command{
    private final AutoAimAndRange autoAimAndRange;
   
    
    public AutoAimAndRangeCommand(AutoAimAndRange autoAimAndRange) {
        this.autoAimAndRange = autoAimAndRange;
    }

    @Override
    public void initialize() {
      autoAimAndRange.periodic1();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        autoAimAndRange.isFinished(null);
    }
}

