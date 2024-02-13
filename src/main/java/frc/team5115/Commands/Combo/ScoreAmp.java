package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;

public class ScoreAmp extends SequentialCommandGroup {
    public ScoreAmp(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor) {
        addRequirements(intake, shooter, arm);
        addCommands(
            new DeployArm(intake, shooter, arm, 92).withTimeout(5),
            new InstantCommand(this :: printOuttake),
            new InstantCommand(intake :: out),
            new WaitForSensorChange(false, sensor, intake, null, 5),
            new InstantCommand(this :: printSensorChange),
            new WaitCommand(3),
            new InstantCommand(intake :: stop)
        );
    }

    private void printOuttake() {
        System.out.println("Outtaking! (after deploy)");
    }

    private void printSensorChange() {
        System.out.println("Sensor stopped detecting note");
    }
}
