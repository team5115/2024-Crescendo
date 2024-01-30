package frc.team5115.Commands.Climber;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Climber;

public class HoldClimbers extends Command {
    final Climber climber;
    final BooleanSupplier isFinishedSupplier;
    double setpoint;

    public HoldClimbers(Climber climber, BooleanSupplier isFinishedSupplier) {
        this.climber = climber;
        this.isFinishedSupplier = isFinishedSupplier;
    }

    @Override
    public void initialize() {
        setpoint = climber.getAverageAngle();
    }

    @Override
    public void execute() {
        climber.loopPids(setpoint);
    }

    @Override
    public boolean isFinished() {
        return isFinishedSupplier.getAsBoolean();
    }

    @Override
    public void end(boolean interrupted) {
        climber.stop();
    }
}
