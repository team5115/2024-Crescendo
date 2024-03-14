package frc.team5115.Commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Hardware.NAVx;
import frc.team5115.Classes.Software.PhotonVision;


public class DriveUntilYaw extends Command {
    
    private final Timer timer;
    private final Drivetrain drivetrain;
    private final PhotonVision p;

    /**
     * 
     * @param drivetrain the drivetrain
     * @param distance distance magnitude
     * @param speed the speed to move, sign doesn't matter
     * @param direction the direction to move in, magnitude doesn't matter
     */
    public DriveUntilYaw(Drivetrain drivetrain, PhotonVision p) {
        timer = new Timer();
        this.drivetrain = drivetrain;
        this.p = p;
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        drivetrain.driveTranslationBySpeeds(0.1, 0);
    }

    @Override
    public boolean isFinished() {
        return (Math.abs(p.getAngleID7() - 28.5) < 1);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}
