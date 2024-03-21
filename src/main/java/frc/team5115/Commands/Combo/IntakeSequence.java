package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WrapperCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;
import edu.wpi.first.wpilibj.Timer;

public class IntakeSequence extends Command{
    private final WrapperCommand wrapped;
    Timer timer;

    public IntakeSequence(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
        addRequirements(intake, shooter, arm);
        wrapped = new WrappedIntakeSequence(intake, shooter, arm, sensor).withInterruptBehavior(InterruptionBehavior.kCancelSelf);
    }

    @Override
    public void initialize() {
        timer = new Timer();
        wrapped.schedule();
        timer.start();
        timer.reset();
    }

    @Override
    public boolean isFinished() {
        return timer.get()>5;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("done");
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
             new DeployArm(intake, shooter, arm, +0).withTimeout(5).alongWith(new InstantCommand(intake :: fastIn)),
                // Intake
                new InstantCommand(intake :: fastIn),
                new InstantCommand(shooter :: slow),
                new WaitForSensorChange(true, sensor),
                new InstantCommand(this :: print),
                new InstantCommand(intake :: stop),
                new InstantCommand(shooter :: stop)
                
            );
        }

        public void interrupt() {
            intake.stop();
            shooter.stop();
            cancel();
        }

        public void print(){
            System.out.println("command over");
        }
    }
}

