package frc.team5115.Commands.Climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Climber;

public class Climb extends Command {
    final Climber climber;
    final double angle;
    public Climb(Climber climber, double angle) {
        this.climber = climber;
        this.angle = angle;
    }

    @Override
    public void initialize() {
        // end early if we are not already deployed
        if (climber.eitherDetecting()) {
            System.out.println("Cannot climb unless both climber beam breaks are not detecting");
            cancel();
            return;
        }
    }

    @Override
    public void execute() {
        // run those PID loops!
        climber.loopPids(angle);
    }

    @Override
    public boolean isFinished() {
        return climber.bothDetecting();
    }
}
