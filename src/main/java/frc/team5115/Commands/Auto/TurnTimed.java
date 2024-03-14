package frc.team5115.Commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Drivetrain;
import edu.wpi.first.math.controller.PIDController;
import frc.team5115.Classes.Hardware.NAVx;
import frc.team5115.Classes.Software.PhotonVision;
  
public class TurnTimed extends Command {
    
    private final Timer timer;
    private final Drivetrain drivetrain;
    private final NAVx navx;
    private final PIDController pid; 
    private final PhotonVision p;

    /**
     * 
     * @param drivetrain the drivetrain
     * @param distance distance magnitude
     * @param speed the speed to move, sign doesn't matter
     * @param direction the direction to move in, magnitude doesn't matter
     */
    public TurnTimed(Drivetrain drivetrain, NAVx navx, PhotonVision p) {
        timer = new Timer();
        pid = new PIDController(0.003, 0, 0);
        this.drivetrain = drivetrain;
        this.navx = navx;
        this.p = p;
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        drivetrain.SwerveDrive(0, pid.calculate(navx.getYawDeg360(), 0), 0, false, false);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(navx.getYawDeg360())<0.1;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}
