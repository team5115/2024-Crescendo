package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj2.command.Command;

public class WaitForSensor extends Command{
    boolean detected;
    final boolean goalState;
    
    public WaitForSensor(boolean goalState) { // TODO pass in sensor
        this.goalState = goalState;
    }

    @Override
    public void execute() {
        detected = false;
    }

    @Override
    public boolean isFinished() {
        return detected == goalState;
    }
}
