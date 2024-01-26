package frc.team5115.Commands.Climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Climber;

public class Climb extends Command {
    final Climber climber;
    
    public Climb(Climber climber) {
        this.climber = climber;
    }

    @Override
    public void initialize() {
        // end early if we are not already deployed
        if (climber.eitherDetecting()) {
            System.out.println("Cannot climb unless both climber beam breaks are not detecting");
            cancel();
        }
        // maybe we won't do PID, then we would just set the voltage here
    }

    @Override
    public void execute() {
        // run those PID loops!
    }

    @Override
    public boolean isFinished() {
        return climber.bothDetecting();
    }
    
    @Override
    public void end(boolean interrupted) {
        
    }
}
