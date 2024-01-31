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
        setpoints = climber.getRotations();
        setpoints[0] += desiredRotationDelta;
        setpoints[1] += desiredRotationDelta;
    }

    @Override
    public boolean isFinished() {
        double[] current = climber.getRotations();
        if (desiredRotationDelta > 0) {
            return current[0] > setpoints[0] && current[1] > setpoints[1];
        } else if (desiredRotationDelta < 0) {
            return current[0] < setpoints[0] && current[1] < setpoints[1];
        } else {
            return true;
        }
    }

    @Override
    public void end(boolean interrupted){
        climber.stop();
    }
}