package frc.team5115.Commands.Auto;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Drivetrain;

public class FollowTrajectory extends Command {

    private final Drivetrain drivetrain;
    private final Trajectory trajectory;
    private final Timer timer;

    public FollowTrajectory(Drivetrain drivetrain, Trajectory trajectory) {
        this.drivetrain = drivetrain;
        this.trajectory = trajectory;
        this.timer = new Timer();
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        drivetrain.followTrajectoryState(trajectory, timer.get());
    }

    @Override
    public boolean isFinished() {
        return timer.get() >= trajectory.getTotalTimeSeconds();
    }

    @Override
    public void end(boolean interrupted) {
        // drivetrain.stop();
    }
    
}
