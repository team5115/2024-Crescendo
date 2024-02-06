package frc.team5115.Commands.Arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Arm;

public class DeployArm extends Command {
    final Arm arm;

    public DeployArm(Arm arm) {
        this.arm = arm;
    }

    @Override
    public void initialize() {
        arm.deploy();
    }

    @Override
    public boolean isFinished(){
        return arm.atSetpoint();

    }
}
