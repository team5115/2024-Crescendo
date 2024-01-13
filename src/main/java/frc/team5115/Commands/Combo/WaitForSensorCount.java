package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;

public class WaitForSensorCount extends Command{
    final DigitalInput sensor;
    final int goalCount;
    int count;
    boolean old;

    public WaitForSensorCount(int goalCount, DigitalInput sensor) {
        this.goalCount = goalCount;
        this.sensor = sensor;
        count = 0;
    }

    @Override
    public void initialize() {
        old = !sensor.get();
    }

    @Override
    public void execute() {
        boolean detected = !sensor.get();
        if (detected != old) {
            count++;
            old = detected;
        }
    }

    @Override
    public boolean isFinished() {
        return count == goalCount;
    }
}
