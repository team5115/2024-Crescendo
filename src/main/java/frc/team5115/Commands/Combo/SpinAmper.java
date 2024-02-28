package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Accessory.Angle;
import frc.team5115.Classes.Software.Amper;

public class SpinAmper extends Command {
    private final Amper amper;
    private final Angle setpoint;
    private final double speed;
    private final double tolerance;
    
    public SpinAmper(Amper amper, Angle setpoint, double speed) {
        this.amper = amper;
        this.setpoint = setpoint;
        this.speed = speed;
        tolerance = 5;
    }

    @Override
    public void initialize() {
        amper.setSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        final double delta = amper.getAngle().getDelta(setpoint);
        return Math.abs(delta) < tolerance;
    }

    @Override
    public void end(boolean interrupted) {
        amper.stop();
    }
}
