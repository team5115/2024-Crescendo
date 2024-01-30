package frc.team5115.Commands.Climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Climber;

public class ReleaseLatches extends Command {
    final Climber climber;
    final double desiredRotationDelta;
    double[] setpoints;

    public ReleaseLatches(Climber climber, double desiredRotationDelta) {
        this.climber = climber;
        this.desiredRotationDelta = desiredRotationDelta;
    }

    @Override
    public void initialize() {
        climber.latchSpeed();

        double[] startPoints = climber.getRotations();
        setpoints = new double[2];
        setpoints[0] = startPoints[0] + desiredRotationDelta;
        setpoints[1] = startPoints[1] + desiredRotationDelta;
    }

    @Override
    public boolean isFinished() {
        double[] current = climber.getRotations();
        // ! these greater than signs may need to be less than signs...
        return current[0] > setpoints[0] && current[1] > setpoints[1];
    }

    @Override
    public void end(boolean interrupted){
        climber.letOutSlow();
    }
}