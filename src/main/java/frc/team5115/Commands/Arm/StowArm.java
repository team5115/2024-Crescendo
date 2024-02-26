package frc.team5115.Commands.Arm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;

public class StowArm extends Command {
    private static final double TIMEOUT = 1;
    final Arm arm;
    final Timer timer;
    final Intake intake;
    final Shooter shooter;
    
    public StowArm(Intake intake, Shooter shooter, Arm arm) {
        this.arm = arm;
        this.intake = intake;
        this.shooter = shooter;
        addRequirements(intake, shooter, arm);
        timer = new Timer();
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        arm.stow();
    }
    
    @Override
    public void execute(){
        arm.stow();
    }

    @Override
    public boolean isFinished(){
        return arm.atSetpoint() || timer.get() > TIMEOUT;
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
        shooter.stop();
        System.out.println("StowArm finished");
    }
}
