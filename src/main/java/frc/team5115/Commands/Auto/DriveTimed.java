package frc.team5115.Commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Drivetrain;

public class DriveTimed extends Command{
    private final Timer timer;
    private final Drivetrain drivetrain;
    private final double timeToComplete;
    private final double speedNormalized;
    
    public DriveTimed(Drivetrain drivetrain, double timeToComplete, double speedNormalized) {
        timer = new Timer();
        this.drivetrain = drivetrain;
        this.timeToComplete = timeToComplete;
        this.speedNormalized = speedNormalized;
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        drivetrain.SwerveDrive(speedNormalized, 0, 0, false);
    }

    @Override
    public boolean isFinished() {
        return timer.get() > timeToComplete;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}
