package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Intake;

public class Rack extends SequentialCommandGroup{
    public Rack(Intake intake, DigitalInput sensor) {
        addCommands(
            new InstantCommand(intake :: out),
            new WaitForSensorChange(false, sensor).withTimeout(1),
            new InstantCommand(intake :: stop)
        );
    }
}
