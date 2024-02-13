package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;

public class WaitForSensorChange extends Command{
    final Intake intake;
    final Shooter shooter;
    final boolean goalState;
    final DigitalInput sensor;
    final double timeout;
    final Timer timer;
    boolean detected;

    public WaitForSensorChange(boolean goalState, DigitalInput sensor, Intake intake, Shooter shooter, double timeout) {
        this.goalState = goalState;
        this.sensor = sensor;
        this.intake = intake;
        this.shooter = shooter;
        this.timeout = timeout;
        timer = new Timer();
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        detected = !sensor.get();
        // System.out.println("sensor state " + detected);
    }

    @Override
    public boolean isFinished() {
        return detected == goalState || timer.get() > timeout;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            if (intake != null) intake.stop();
            if (shooter != null) shooter.stop();
        }
    }
}
