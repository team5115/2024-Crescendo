package frc.team5115.Commands.Combo;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;

public class ShootSequence extends SequentialCommandGroup{
    public ShootSequence(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor, GenericEntry rpmEntry, double defaultRpm) {
        addCommands(
            // new DeployArm(arm),
            new InstantCommand(this :: logStarting),
            new InstantCommand(intake :: out),
            new WaitForSensorChange(false, sensor),
            new InstantCommand(intake :: stop),
            new SpinUpShooter(shooter, rpmEntry, defaultRpm, false, false).withTimeout(4),
            new InstantCommand(intake :: fastIn),
            new WaitForSensorChange(true, sensor).withTimeout(0.5),
            new PrintShooterInfo(shooter, rpmEntry, defaultRpm, false).withTimeout(0.1),
            new InstantCommand(intake :: stop),
            new InstantCommand(shooter :: stop)
        );
    }

    private void logStarting() {
        System.out.println("STARTING SHOOT SEQUENCE");
    }
}
