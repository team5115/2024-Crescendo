package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;

public class ShootSequence extends Command{
    private final WrappedShootSequence wrapped;

    public ShootSequence(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
        addRequirements(intake, shooter, arm);
        wrapped = new WrappedShootSequence(intake, shooter, arm, sensor);
    }

    @Override
    public void initialize() {
        wrapped.schedule();
    }

    @Override
    public boolean isFinished() {
        return wrapped.isScheduled();
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            wrapped.interrupt();
        }
    }
    
    private class WrappedShootSequence extends SequentialCommandGroup {
        final Intake intake;
        final Shooter shooter;

        public WrappedShootSequence(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
            this.intake = intake;
            this.shooter = shooter;
            addCommands(
                new DeployArm(intake, shooter, arm, 4).withTimeout(5),

                // Shoot
                new SpinUpShooter(shooter, 5000).withTimeout(4),
                new InstantCommand(intake :: fastIn),
                new WaitForSensorChange(true, sensor).withTimeout(0.5),
                new WaitCommand(0.15),

                // Stop stuff
                new InstantCommand(intake :: stop),
                new InstantCommand(shooter :: stop),
                new WaitCommand(0.5)
            );
        }

        public void interrupt() {
            intake.stop();
            shooter.stop();
            cancel();
        }
    }
}
