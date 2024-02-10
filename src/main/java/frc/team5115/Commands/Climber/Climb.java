package frc.team5115.Commands.Climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Climber;

public class Climb extends Command {
    final Climber climber;
    final double desiredRotationDelta;
    double[] setpoints;

    public Climb(Climber climber, double desiredRotationDelta) {
        this.climber = climber;
        this.desiredRotationDelta = desiredRotationDelta;
    }

    @Override
    public void initialize() {
        setpoints = climber.getRotations();
        setpoints[0] += desiredRotationDelta;
        setpoints[1] += desiredRotationDelta;
    }

    @Override
    public void execute() {
        climber.loopPids(setpoints);
        // System.out.println(setpoints[0]);
    }

    @Override
    public boolean isFinished() {
        // return climber.isFullyClimbed();
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        climber.stop();
    }

}
