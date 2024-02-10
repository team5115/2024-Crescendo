package frc.team5115.Commands.Arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;

public class StowArm extends Command {
    final Arm arm;
    
    public StowArm(Intake intake, Shooter shooter, Arm arm) {
        this.arm = arm;
        addRequirements(intake, shooter, arm);
    }

    @Override
    public void initialize() {
        arm.stow();
    }

    @Override
    public boolean isFinished(){
        return arm.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("StowArm finished");
    }
}
