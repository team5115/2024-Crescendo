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

public class IntakeSequence extends Command{
    private final WrapperCommand wrapped;

    public IntakeSequence(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
        addRequirements(intake, shooter, arm);
        wrapped = new WrappedIntakeSequence(intake, shooter, arm, sensor).withInterruptBehavior(InterruptionBehavior.kCancelSelf);
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
            System.out.println("interupted ;)");
        }
    }

    private class WrappedIntakeSequence extends SequentialCommandGroup {
        final Intake intake;
        final Shooter shooter;

        public WrappedIntakeSequence(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor){
            this.intake = intake;
            this.shooter = shooter;
            addCommands(
             new InstantCommand(intake :: fastIn),
             new DeployArm(intake, shooter, arm, -1).withTimeout(5).alongWith(new InstantCommand(intake :: fastIn)),
                // Intake
                new InstantCommand(intake :: fastIn),
                new InstantCommand(shooter :: slow),
                new WaitForSensorChange(true, sensor),
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
