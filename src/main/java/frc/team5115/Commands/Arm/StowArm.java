package frc.team5115.Commands.Arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Arm;

public class StowArm extends Command {
    final Arm arm;
    final double angle;
    public StowArm(Arm arm, double angle) {
        this.arm = arm;
        this.angle = angle;
    }

    @Override
    public void initialize() {
        arm.stow();
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
