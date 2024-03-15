package frc.team5115.Commands.Auto;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.AutoAimAndRange;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Combo.IntakeSequence;
import frc.team5115.Commands.Combo.PrepareShoot;
import frc.team5115.Commands.Combo.TriggerShoot;
import frc.team5115.Classes.Software.PhotonVision;
import frc.team5115.Constants;
import frc.team5115.Classes.Hardware.NAVx;

public class SideAuto extends SequentialCommandGroup {
    final Drivetrain drivetrain;
    private final AutoAimAndRange autoAimAndRange; 
    private final NAVx navx;
    
    public SideAuto(Drivetrain drivetrain, boolean actuallyRun, Intake intake, Shooter shooter, Arm arm, DigitalInput d, AutoAimAndRange aAR, PhotonVision p, NAVx navx, boolean direction){
        this.drivetrain = drivetrain;
        autoAimAndRange = aAR;
        this.navx = navx;
        if (!actuallyRun) return;

        addCommands(
            new PrepareShoot(intake, shooter, arm, d, 5, 5000, null, false),
            new TriggerShoot(intake, shooter, arm, d)
            //new AutoPart1(drivetrain, actuallyRun, intake, shooter, arm, d, aAR),
            //new TriggerShoot(intake, shooter, arm, d)
            //, new DriveByTime(drivetrain, Units.feetToMeters(1), -1, 1.5)
            , new DriveByTime(drivetrain, 0.5, direction ? 1 : -1, 2, true)

        );
    }
}
