package frc.team5115.Commands.Auto;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Hardware.NAVx;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.AutoAimAndRange;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.PhotonVision;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Combo.IntakeSequence;
import frc.team5115.Commands.Combo.PrepareShoot;
import frc.team5115.Commands.Combo.TriggerShoot;


public class AutoCommandGroup extends SequentialCommandGroup {
    final Drivetrain drivetrain;
    private final AutoAimAndRange autoAimAndRange; 
    private final PhotonVision p;

    
    public AutoCommandGroup(Drivetrain drivetrain, boolean actuallyRun, Intake intake, Shooter shooter, Arm arm, DigitalInput d, AutoAimAndRange aAR, PhotonVision p, NAVx navx){
        this.drivetrain = drivetrain;
        autoAimAndRange = aAR;
        this.p = p;
        if (!actuallyRun) return;

        addCommands(
            
            // new AutoPart1(drivetrain, actuallyRun, intake, shooter, arm, d),
            //new IntakeSequence(intake, shooter, arm, d),
            new SideAuto(drivetrain, actuallyRun, intake, shooter, arm, d, aAR, p, navx)
            //new CenterAuto(actuallyRun, drivetrain, intake, shooter, arm, d, aAR)
        );
    }
}
