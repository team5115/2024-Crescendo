package frc.team5115.Commands.Arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;

public class DeployArm extends Command {
    final Arm arm;
    final double angle;
    final Intake intake;
    final Shooter shooter;


    public DeployArm(Intake intake, Shooter shooter, Arm arm, double angle) {
        this.arm = arm;
        this.angle = angle;
        this.intake = intake;
        this.shooter = shooter;
        addRequirements(intake, shooter, arm);
    }

    @Override
    public void initialize() {
        arm.deployToAngle(angle);
    }

    @Override
    public void execute(){
        arm.deployToAngle(angle);
    }


    @Override
    public boolean isFinished(){
        return arm.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
        shooter.stop();
        System.out.println("DeployArm finished");
    }
}
