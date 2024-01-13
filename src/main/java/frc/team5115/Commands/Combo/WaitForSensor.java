package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;

public class WaitForSensor extends Command{
    boolean detected;
    final boolean goalState;
    DigitalInput dioSensorFlywheel = new DigitalInput(0);

    
    public WaitForSensor(boolean goalState, DigitalInput dioSensorFlywheel) { // done
        this.goalState = goalState;

    }

    @Override
    public void execute() {
        detected = dioSensorFlywheel.get();

    }

    @Override
    public boolean isFinished() {
        return detected == goalState;
    }
}