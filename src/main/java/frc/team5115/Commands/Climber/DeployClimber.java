package frc.team5115.Commands.Climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Climber;

public class DeployClimber extends Command {
    final double speed;
    final Climber climber;
    final double desiredDeltaMag;
    double[] start;

    public DeployClimber(Climber climber, double desiredRotationDelta) {
        this.climber = climber;
        this.desiredDeltaMag = Math.abs(desiredRotationDelta);
        this.speed = 0.5 * Math.signum(desiredRotationDelta);
    }

    @Override
    public void initialize() {
        climber.setBoth(speed);
        start = climber.getRotations();
    }

    @Override
    public boolean isFinished() {
        double[] current = climber.getRotations();
        boolean leftComplete = isMoveComplete(start[0], current[0], desiredDeltaMag);
        boolean rightComplete = isMoveComplete(start[1],  current[1], desiredDeltaMag);
        return leftComplete && rightComplete;
    }

    @Override
    public void end(boolean interrupted){
        System.out.println("Climber Deployed");
        climber.setDeployed();
        climber.stop();
    }

    /**
     * A pretty well tested static math function to calculate if a delta has been travelled
     * @param startPosition the position that the movement started at
     * @param currentPosition the current position of the movement
     * @param desiredDelta the total delta of the movement -- sign has NO effect
     * @return if the movement has moved the desiredDelta IN EITHER DIRECTION
     */
    private static boolean isMoveComplete(final double startPosition, final double currentPosition, final double desiredDelta) {
        if (desiredDelta == 0) return true;
        final double currentDelta = currentPosition - startPosition;
        return Math.abs(currentDelta) > Math.abs(desiredDelta);
    }
}