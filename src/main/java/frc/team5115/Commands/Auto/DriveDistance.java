package frc.team5115.Commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Drivetrain;

public class DriveDistance extends Command {
    
    private final Timer timer;
    private final Drivetrain drivetrain;
    private final double time;
    private final double speed;
    private final double direction;

    /**
     * 
     * @param drivetrain the drivetrain
     * @param distance distance magnitude
     * @param speed the speed to move, sign doesn't matter
     * @param direction the direction to move in, magnitude doesn't matter
     */
    public DriveDistance(Drivetrain drivetrain, double distance, double speed, double direction) {
        timer = new Timer();
        this.drivetrain = drivetrain;

        time = Math.abs(distance / speed);
        this.speed = Math.abs(speed);
        this.direction = Math.signum(direction);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        drivetrain.driveTranslationBySpeeds(speed * direction, 0);
    }

    @Override
    public boolean isFinished() {
        return timer.get() > time;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}
