package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Classes.Software.Amper;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;

public class PrepareAmp extends SequentialCommandGroup {
    public PrepareAmp(Intake intake, Shooter shooter, Arm arm, DigitalInput sensor, Amper amper) {
        addCommands(
            // deploy both
            new DeployArm(intake, shooter, arm, 100).withTimeout(5),
            new SpinAmper(amper, Amper.OUT_ANGLE)
        );
    }
    
}
