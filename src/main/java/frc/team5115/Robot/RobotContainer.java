package frc.team5115.Robot;

import org.photonvision.PhotonVersion;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Constants;
import frc.team5115.Classes.Accessory.Angle;

import frc.team5115.Classes.Hardware.HardwareDrivetrain;

import frc.team5115.Classes.Hardware.NAVx;
import frc.team5115.Commands.Auto.*;
import frc.team5115.Classes.Software.*;
import frc.team5115.Constants.VisionConstants;

public class RobotContainer {
    private final Joystick joyDrive;
   // private final AutoBuilder autoBuilder;
    private final Joystick joyManips;
    private Drivetrain drivetrain;
    private final GenericEntry rookie;
    private final GenericEntry doAuto;
    private final NAVx navx;
    private final boolean fieldOriented = true;
   // private final Climber climber;
   // private final Arm arm;
   // private final Intake intake;
   // private final Shooter shooter;
    private Paths paths;
    private AutoCommandGroup autoCommandGroup;
    private AimandRangeFrontCam aimandRange;
    private PhotonVision photonVision;
   // private final DigitalInput reflectiveSensor;
   // private AutoCommandGroup autoCommandGroup;
   // private final GenericEntry rpmEntry;
   // private final AimandRangeFrontCam aimAndRangeFrontCam;

    public RobotContainer() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("SmartDashboard");
        rookie = shuffleboardTab.add("Rookie?", false).getEntry();
        doAuto = shuffleboardTab.add("Do auto at all?", false).getEntry();
       // shuffleboardTab.addBoolean("Field Oriented?", () -> fieldOriented);

        joyDrive = new Joystick(0);
        joyManips = new Joystick(1);
        navx = new NAVx();

        photonVision = new PhotonVision();
        HardwareDrivetrain hardwareDrivetrain = new HardwareDrivetrain(navx);
        drivetrain = new Drivetrain(hardwareDrivetrain, navx, photonVision);
       
        aimandRange = new AimandRangeFrontCam(hardwareDrivetrain, photonVision);

        configureButtonBindings();
    }

    public void registerCommand() {

    // Register Named Commands for pathplanner
/* 
      NamedCommands.registerCommand("Example Path", drivetrain.pathplanner());

      NamedCommands.registerCommand("top to middle", drivetrain.pathplanner());
      NamedCommands.registerCommand("top to bottom", drivetrain.pathplanner());

      NamedCommands.registerCommand("middle to bottom", drivetrain.pathplanner());
      NamedCommands.registerCommand("middle to top", drivetrain.pathplanner());

      NamedCommands.registerCommand("bottom to top", drivetrain.pathplanner());
      NamedCommands.registerCommand("bottom to middle", drivetrain.pathplanner());

      NamedCommands.registerCommand("START middle to middle", drivetrain.pathplanner());
      NamedCommands.registerCommand("START middle to bottom", drivetrain.pathplanner());
      NamedCommands.registerCommand("START middle to top", drivetrain.pathplanner());

      NamedCommands.registerCommand("START top to top", drivetrain.pathplanner());
      NamedCommands.registerCommand("START top to middle", drivetrain.pathplanner());
      NamedCommands.registerCommand("START top to bottom", drivetrain.pathplanner());

      NamedCommands.registerCommand("START bottom to top", drivetrain.pathplanner());
      NamedCommands.registerCommand("START bottom to middle", drivetrain.pathplanner());
      NamedCommands.registerCommand("START bottom to bottom", drivetrain.pathplanner());
        */
    }

    public void configureButtonBindings() {

    }

    public void disabledInit(){
        drivetrain.stop();
    }

    public void stopEverything(){
        drivetrain.stop();
    }

    public void startTest() {
    }

    public void testPeriodic() {
    }

    public void startAuto(){

    }

    public void autoPeriod() {
        // drivetrain.updateOdometry();
    }

    public void startTeleop(){
        
        drivetrain.resetEncoders();
        System.out.println("Starting teleop");
    }

    public void teleopPeriodic() {
        /*
        final boolean MANUAL_CLIMB = true;

        if (MANUAL_CLIMB) {
            climber.setBoth(-joyManips.getRawAxis(XboxController.Axis.kLeftY.value));
        } else {
            if (climber.isDeployed()) {
                if (joyManips.getRawButton(XboxController.Button.kLeftBumper.value)
                && joyManips.getRawButton(XboxController.Button.kRightBumper.value)) {
                    climb.schedule();
                }
            } else {
                if (joyManips.getRawAxis(XboxController.Axis.kLeftTrigger.value) > 0.5
                && joyManips.getRawAxis(XboxController.Axis.kRightTrigger.value) > 0.5) {
                    deployClimber.schedule();
                }
            }
        } 
        */

        aimandRange.periodic1();
        // System.out.println("bno angle: " + i2cHandler.getPitch());
        // i2cHandler.updatePitch();
        //drivetrain.SwerveDrive(-joyDrive.getRawAxis(1), joyDrive.getRawAxis(4), -joyDrive.getRawAxis(0),rookie.getBoolean(false), fieldOriented);
    }
}
