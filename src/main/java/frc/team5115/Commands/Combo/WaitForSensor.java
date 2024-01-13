package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;

public class WaitForSensor extends Command{
    boolean detected;
    final boolean goalState;
    final DigitalInput sensor;
    final int count;
    
    public WaitForSensor(boolean goalState, DigitalInput sensor) {
        this.goalState = goalState;
        this.sensor = sensor;
        count = 1;
    }

    public WaitForSensor(boolean goalState, DigitalInput sensor, int count) {
        this.goalState = goalState;
        this.sensor = sensor;
        this.count = count;
    }

    @Override
    public void execute() {
        detected = !sensor.get();
        // System.out.println("sensor state " + detected);
    }

    @Override
    public boolean isFinished() {
        return detected == goalState;
    }
}
