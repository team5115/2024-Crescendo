package frc.team5115.Commands.Arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Arm;

public class DeployArm extends Command {
    final Arm arm;
    final double angle;
    public DeployArm(Arm arm, double angle) {
        this.arm = arm;
        this.angle = angle;
    }

    @Override
    public void initialize() {
        arm.deploy();
    }
    @Override
    public void execute() {
        arm.updateController();
    }
    @Override
    public boolean isFinished(){
        return arm.atSetpoint();

    }
}
