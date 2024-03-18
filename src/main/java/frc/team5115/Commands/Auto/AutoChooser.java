package frc.team5115.Commands.Auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import com.pathplanner.lib.auto.AutoBuilder;
public class AutoChooser extends Command {
    private final SendableChooser<Command> autoChooser;

    public AutoChooser() {
        // Build an auto chooser. This will use Commands.none() as the default option.
        autoChooser = AutoBuilder.buildAutoChooser();

      /*   Another option that allows you to specify the default auto by its name
        autoChooser = AutoBuilder.buildAutoChooser("My Default Auto"); */

        SmartDashboard.putData("Auto Chooser", autoChooser);
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}