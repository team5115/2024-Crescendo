package frc.team5115.Commands.Combo;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;

public class PrepareShoot extends SequentialCommandGroup{
    public PrepareShoot(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor, double angle, double speed, GenericEntry angleSupplier, boolean neverExit) {
        addRequirements(intake, shooter, arm);
        addCommands(
            // deploy
            new DeployArm(intake, shooter, arm, 5).withTimeout(4),
            
            // rack
            new InstantCommand(intake :: out),
            new WaitForSensorChange(false, sensor),
            new WaitCommand(0.05),
            new InstantCommand(intake :: stop),

            new DeployArm(intake, shooter, arm, angle, angleSupplier).withTimeout(8),

            // spin up
            new SpinUpShooter(shooter, speed, neverExit).withTimeout(3)
        );
    }
} 
