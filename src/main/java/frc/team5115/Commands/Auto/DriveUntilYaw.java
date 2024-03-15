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
    private final NAVx navx;

    /**
     * 
     * @param drivetrain the drivetrain
     * @param distance distance magnitude
     * @param speed the speed to move, sign doesn't matter
     * @param direction the direction to move in, magnitude doesn't matter
     */
    public DriveUntilYaw(Drivetrain drivetrain, PhotonVision p, NAVx navx) {
        timer = new Timer();
        this.drivetrain = drivetrain;
        this.p = p;
        this.navx = navx;
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        System.out.println(navx.getYawDeg360());
        drivetrain.SwerveDrive(0.1, 0, 0, false, true, 0);
    }

    @Override
    public boolean isFinished() {
        System.out.println(navx.getYawDeg360());
        //System.out.println("Difference: " + (Math.abs(p.getAngleID7()) - 28.5));
        return (Math.abs(Math.abs(p.getAngleID7()) - 28.5) < 1);
    }

    public void periodic(){
        double direction = Math.signum((Math.abs(p.getAngleID7()) - 28.5));
        drivetrain.SwerveDrive(0.1*direction, 0, 0, false, true, 0);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}
