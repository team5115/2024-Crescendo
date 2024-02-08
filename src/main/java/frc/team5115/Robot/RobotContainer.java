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
import frc.team5115.Classes.Hardware.HardwareArm;
import frc.team5115.Classes.Hardware.HardwareClimber;
import frc.team5115.Classes.Hardware.HardwareDrivetrain;
import frc.team5115.Classes.Hardware.HardwareShooter;
import frc.team5115.Classes.Hardware.I2CHandler;
import frc.team5115.Classes.Hardware.NAVx;
import frc.team5115.Commands.Auto.*;
import frc.team5115.Classes.Software.*;
import frc.team5115.Commands.Auto.AutoCommandGroup;
import frc.team5115.Constants.VisionConstants;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.DeployArm;
import frc.team5115.Commands.Arm.StowArm;
import frc.team5115.Commands.Combo.IntakeSequence;
import frc.team5115.Commands.Combo.ShootSequence;
import frc.team5115.Commands.Combo.SpinUpShooter;
import frc.team5115.Commands.Combo.Vomit;

public class RobotContainer {
    private final Joystick joyDrive;
    private final AutoBuilder autoBuilder;
    private final Joystick joyManips;
    private final Drivetrain drivetrain;
    private final GenericEntry rookie;
    private final GenericEntry doAuto;
    private final I2CHandler i2cHandler;
    private final NAVx navx;
    // private final Climber climber;
    private final Arm arm;
    private final Intake intake;
    private final Shooter shooter;
    private final Paths paths;
    private AutoCommandGroup autoCommandGroup;
    private PhotonVision photonVision;
    private final DigitalInput reflectiveSensor;
    // private AutoCommandGroup autoCommandGroup;
    private final GenericEntry rpmEntry;

    // private final Climb climb;
    // private final DeployClimber deployClimber;

public RobotContainer() {

        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("SmartDashboard");
        rpmEntry = shuffleboardTab.add("shooter rpm", 3500).getEntry();

        rookie = shuffleboardTab.add("Rookie?", false).getEntry();
        doAuto = shuffleboardTab.add("Do auto at all?", false).getEntry();

        joyDrive = new Joystick(0);
        joyManips = new Joystick(1);
        navx = new NAVx();
        i2cHandler = new I2CHandler();

        HardwareDrivetrain hardwareDrivetrain = new HardwareDrivetrain(navx);
        drivetrain = new Drivetrain(hardwareDrivetrain, navx);
        
        autoBuilder = new AutoBuilder();
        drivetrain = new Drivetrain(hardwareDrivetrain, photonVision, navx, autoBuilder);
        photonVision = new PhotonVision();
        HardwareArm hardwareArm = new HardwareArm(navx, i2cHandler);
        HardwareArm hardwareArm = new HardwareArm(navx, i2cHandler, Constants.ARM_RIGHT_MOTOR_ID, Constants.ARM_LEFT_MOTOR_ID);
        arm = new Arm(hardwareArm);

        HardwareShooter hardwareShooter = new HardwareShooter(Constants.SHOOTER_CLOCKWISE_MOTOR_ID, Constants.SHOOTER_COUNTERCLOCKWISE_MOTOR_ID);
        shooter = new Shooter(hardwareShooter);
        paths = new Paths();        intake = new Intake(Constants.INTAKE_MOTOR_ID);
        reflectiveSensor = new DigitalInput(0);

        // TODO set climber canIDs, sensor channels, and PWM channels
        // HardwareClimber leftClimber = new HardwareClimber(Constants.CLIMBER_LEFT_MOTOR_ID, 0, 0, 0);
        // HardwareClimber rightClimber = new HardwareClimber(Constants.CLIMBER_RIGHT_MOTOR_ID, 0, 0, 0);
        // climber = new Climber(leftClimber, rightClimber);
        // climb = new Climb(climber, 12);
        // deployClimber = new DeployClimber(climber, 0.5);

        paths = new Paths();
        configureButtonBindings();
    }

    public void registerCommand() {

    // Register Named Commands for pathplanner

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

    }

    public void configureButtonBindings() {

        new JoystickButton(joyManips, XboxController.Button.kBack.value)
        .onTrue(new Vomit(true, shooter, intake))
        .onFalse(new Vomit(false, shooter, intake));

        new JoystickButton(joyManips, XboxController.Button.kA.value)
        .onTrue(new IntakeSequence(intake, shooter, arm, reflectiveSensor));

        new JoystickButton(joyManips, XboxController.Button.kB.value)
        .onTrue(new ShootSequence(intake, shooter, arm, reflectiveSensor));

        new JoystickButton(joyManips, XboxController.Button.kX.value)
        .onTrue(new DeployArm(arm, 20));

        new JoystickButton(joyManips, XboxController.Button.kY.value)
        .onTrue(new StowArm(arm));
    }

    public void disabledInit(){
        drivetrain.stop();
    }

    public void stopEverything(){
        drivetrain.stop();
        // arm.stop();
    }

    public void startTest() {
    }

    public void testPeriodic() {
    }

    public void startAuto(){
        // if(autoCommandGroup != null) autoCommandGroup.cancel();
        // drivetrain.resetEncoders();
        // drivetrain.resetNAVx();
        // drivetrain.stop();
        // drivetrain.init();

        // autoCommandGroup = new AutoCommandGroup(drivetrain, doAuto.getBoolean(true));
        // autoCommandGroup.schedule();
    }

    public void autoPeriod() {
        // drivetrain.updateOdometry();
        // i2cHandler.updatePitch();
        // arm.updateController();
    }

    public void startTeleop(){
        // if(autoCommandGroup != null) autoCommandGroup.cancel();
        
        System.out.println("Starting teleop");
        drivetrain.resetEncoders();
    }

    public void teleopPeriodic() {
        /*
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
        */

        // System.out.println("bno: " + i2cHandler.getPitch());
        arm.updateController(i2cHandler);
        // drivetrain.SwerveDrive(-joyDrive.getRawAxis(1), joyDrive.getRawAxis(4), joyDrive.getRawAxis(0), rookie.getBoolean(false), true);
    }


        i2cHandler.updatePitch();
        
        arm.updateController();
        photonVision.getRange();
        photonVision.getRangeF();
        photonVision.getRangeR();
        photonVision.getRangeB();

        //drivetrain.SwerveDrive(-joyDrive.getRawAxis(1), joyDrive.getRawAxis(4), joyDrive.getRawAxis(0), rookie.getBoolean(false));
             
        System.out.println(photonVision.getRange());

        // System.out.println(photonVision.getID());
    
}

}