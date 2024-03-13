package frc.team5115.Commands.Arm;

import java.util.function.Supplier;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;

public class DeployArm extends Command {
    final Arm arm;
    double angle;
    final Intake intake;
    final Shooter shooter;
    final GenericEntry angleSupplier;

    public DeployArm(Intake intake, Shooter shooter, Arm arm, double angle) {
        this.arm = arm;
        this.angle = angle;
        this.intake = intake;
        this.shooter = shooter;
        angleSupplier = null;
        addRequirements(intake, shooter, arm);
    }

    public DeployArm(Intake intake, Shooter shooter, Arm arm, double angle, GenericEntry angleSupplier) {
        this.arm = arm;
        this.angle = angle;
        this.intake = intake;
        this.shooter = shooter;
        this.angleSupplier = angleSupplier;
        addRequirements(intake, shooter, arm);
    }

    @Override
    public void initialize() {
        if (angleSupplier != null) {
            angle = angleSupplier.getDouble(angle);
            System.out.println("Supplier supplies: " + angle);
        }
        arm.deployToAngle(angle);
    }

    @Override
    public void execute(){
        System.out.println("deploying to angle: " + angle);
        arm.deployToAngle(angle);
        System.out.println("PID Output: " + arm.getPID());
    }


    @Override
    public boolean isFinished(){
        return arm.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
        shooter.stop();
        double delta = (arm.getAngle().getDegrees(-90) - angle);
        System.out.println("DeployArm finished @ " + arm.getAngle() + " | off by: " + delta );
    }
}
