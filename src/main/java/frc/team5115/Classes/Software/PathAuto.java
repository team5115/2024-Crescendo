package frc.team5115.Classes.Software;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team5115.Commands.Arm.DeployArm;
import frc.team5115.Commands.Combo.PrepareShoot;
import frc.team5115.Commands.Combo.TriggerShoot;
import frc.team5115.Commands.Combo.WaitForSensorChange;

public class PathAuto {
    private static boolean commandsRegistered = false;
    public static PathPlannerAuto testAuto;

    public static Command getTestAuto(){
        return testAuto;
    }

    public static void registerCommands(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor){
        if (commandsRegistered) {
            return;
        }
        commandsRegistered = true;

        NamedCommands.registerCommand("PrepareShootClose", new PrepareShoot(intake, shooter, arm, sensor, 5, 5000, null, false));
        NamedCommands.registerCommand("PrepareShootFar", new PrepareShoot(intake, shooter, arm, sensor, 32.4, 5000, null, false));
        NamedCommands.registerCommand("TriggerShoot", new TriggerShoot(intake, shooter, arm, sensor));
        NamedCommands.registerCommand("IntakeAuto", getAutoIntakeCommand(intake, shooter, arm, sensor));
    }

    public static void loadPaths() {
        testAuto = new PathPlannerAuto("Test Auto");
    }

    private static Command getAutoIntakeCommand(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
        return new InstantCommand(intake :: fastIn).andThen(
            new DeployArm(intake, shooter, arm, 0).withTimeout(3).alongWith(new InstantCommand(intake :: fastIn)),
            // Intake
            new InstantCommand(intake :: fastIn),
            new InstantCommand(shooter :: slow),
            new WaitForSensorChange(true, sensor),
            new InstantCommand(intake :: stop),
            new InstantCommand(shooter :: stop)
        );
    }
}
