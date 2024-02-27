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

public class ScoreAmp extends Command {
    private final WrappedScoreAmp wrapped;
    final Intake intake;
    final Shooter shooter;

    public ScoreAmp(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
        addRequirements(intake, shooter, arm);
        this.intake = intake;
        this.shooter = shooter;
        wrapped = new WrappedScoreAmp(intake, shooter, arm, sensor);
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
        intake.stop();
        shooter.stop();
        if (interrupted) {
            wrapped.interrupt();
        }
    }

    private class WrappedScoreAmp extends SequentialCommandGroup {
        final Intake intake;
        final Shooter shooter;

        public WrappedScoreAmp(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
            this.intake = intake;
            this.shooter = shooter;
            addCommands(
                new DeployArm(intake, shooter, arm, 100).withTimeout(5),
                new InstantCommand(intake :: out),
                new WaitForSensorChange(false, sensor).withTimeout(5),
                new WaitCommand(3),
                new InstantCommand(intake :: stop)
            );
        }

        public void interrupt() {
            intake.stop();
            shooter.stop();
            cancel(); 
        }
    }
}
