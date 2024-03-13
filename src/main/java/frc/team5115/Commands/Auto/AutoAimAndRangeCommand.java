package frc.team5115.Commands.Auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.AutoAimAndRange;


public class AutoAimAndRangeCommand extends Command{
    private final AutoAimAndRange autoAimAndRange;


    double[] errors = {100000000000.0, 10000000000000.0};
    boolean isBlue;
    
    public AutoAimAndRangeCommand(AutoAimAndRange autoAimAndRange) {
        this.autoAimAndRange = autoAimAndRange;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute(){
        errors = autoAimAndRange.periodicIDBased();
    }

    @Override
    public boolean isFinished() {
        return autoAimAndRange.isFinished(errors);
    }

    @Override
    public void end(boolean interrupted) {
        autoAimAndRange.stop();
    }
}

