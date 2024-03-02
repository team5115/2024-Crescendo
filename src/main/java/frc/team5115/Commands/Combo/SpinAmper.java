package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Accessory.Angle;
import frc.team5115.Classes.Software.Amper;

public class SpinAmper extends Command {
    private final Amper amper;
    private final Angle setpoint;
    private final double tolerance;
    private double pid;
    
    public SpinAmper(Amper amper, Angle setpoint) {
        this.amper = amper;
        this.setpoint = setpoint;
        tolerance = 3.5;
    }

    @Override
    public void execute() {
        pid = amper.spinPid(setpoint);
    }

    @Override
    public boolean isFinished() {
        final double delta = amper.getAngle().getDelta(setpoint);
        System.out.println("Amper delta: " + delta);
        return Math.abs(delta) < tolerance;
    }

    @Override
    public void end(boolean interrupted) {
        amper.stop();
    }
}
