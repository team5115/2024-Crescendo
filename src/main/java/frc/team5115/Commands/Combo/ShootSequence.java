package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WrapperCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;

public class ShootSequence extends Command{
    private WrapperCommand wrapped;
        final Intake intake;
        final Shooter shooter;
        final Arm arm;
        final DigitalInput sensor;
    public ShootSequence(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
        this.intake = intake;
        this.shooter = shooter; 
        this.sensor = sensor;
        this.arm = arm;
        addRequirements(intake, shooter, arm);
    }

    @Override
    public void initialize() {
        wrapped = new WrappedShootSequence(intake, shooter, arm, sensor).withInterruptBehavior(InterruptionBehavior.kCancelIncoming);
        intake.stop();
        shooter.stop();
        wrapped.schedule();
    }

    @Override
    public boolean isFinished() {
        return wrapped.isScheduled();
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
        }
    }
    
    public class WrappedShootSequence extends SequentialCommandGroup {
        final Intake intake;
        final Shooter shooter;

        public WrappedShootSequence(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
            this.intake = intake;
            this.shooter = shooter;
            if(!arm.deployed()) addCommands(
             new DeployArm(intake, shooter, arm, -1).withTimeout(5)
            );
            addCommands(

                // Rack
                new InstantCommand(intake :: out),
                new WaitForSensorChange(false, sensor),
                new InstantCommand(intake :: stop),

                // Shoot
                new SpinUpShooter(shooter, 5000).withTimeout(4),
                new InstantCommand(intake :: fastIn),
                new WaitForSensorChange(true, sensor).withTimeout(0.5),
                new WaitCommand(0.1),

                // Stop stuff
                new InstantCommand(intake :: stop),
                new InstantCommand(shooter :: stop)
            );
        }

        public void interrupt() {
            intake.stop();
            shooter.stop();
            cancel();
        }
    }
}
