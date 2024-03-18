package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;

public class WaitForSensorChange extends Command{
    final boolean goalState;
    final DigitalInput sensor;
    boolean detected;

    public WaitForSensorChange(boolean goalState, DigitalInput sensor) {
        this.goalState = goalState;
        this.sensor = sensor;
    }

    @Override
    public void execute() {
        detected = !sensor.get();
        //System.out.println("sensor state " + detected);
    }

    @Override
    public boolean isFinished() {
        return detected == goalState;
    }
}
