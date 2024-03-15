package frc.team5115.Commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Drivetrain;

public class DriveByTime extends Command {
    
    private final Timer timer;
    private final Drivetrain drivetrain;
    private final double time;
    private final double speed;
    private final double direction;

    private final boolean useYaxis;

    /**
     * 
     * @param drivetrain the drivetrain
     * @param speed the speed to move, sign doesn't matter
     * @param direction the direction to move in, magnitude doesn't matter
     * @param time the time to drive for
     */
    public DriveByTime(Drivetrain drivetrain, double speed, double direction, double time, boolean useYaxis) {
        timer = new Timer();
        this.drivetrain = drivetrain;
        this.useYaxis = useYaxis;

       // time = Math.abs(distance / speed);
       this.time = time;
        this.speed = Math.abs(speed);
        this.direction = Math.signum(direction);
    }

    public DriveByTime(Drivetrain drivetrain, double speed, double direction, double time) {
        this(drivetrain, speed, direction, time, false);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        if (useYaxis) {
            drivetrain.driveTranslationBySpeeds(0, speed * direction);
        } else {
            drivetrain.driveTranslationBySpeeds(speed * direction, 0);
        }
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
