package frc.team5115.Commands.Arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Arm;

public class StowArm extends Command {
    final Arm arm;
    
    public StowArm(Arm arm) {
        this.arm = arm;
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
