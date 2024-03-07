package frc.team5115.Robot;

import java.nio.file.Paths;

import org.photonvision.PhotonVersion;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Constants;
import frc.team5115.Classes.Hardware.HardwareAmper;
import frc.team5115.Classes.Hardware.HardwareArm;
import frc.team5115.Classes.Hardware.HardwareClimber;
import frc.team5115.Classes.Hardware.HardwareDrivetrain;
import frc.team5115.Classes.Hardware.HardwareShooter;
import frc.team5115.Classes.Hardware.I2CHandler;
import frc.team5115.Classes.Hardware.NAVx;
import frc.team5115.Classes.Software.Amper;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.AutoAimAndRange;
import frc.team5115.Classes.Software.Climber;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.PhotonVision;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.StowArm;
import frc.team5115.Commands.Auto.AutoCommandGroup;
import frc.team5115.Commands.Climber.Climb;
import frc.team5115.Commands.Climber.DeployClimber;
import frc.team5115.Commands.Combo.IntakeSequence;
import frc.team5115.Commands.Combo.PrepareAmp;
import frc.team5115.Commands.Combo.PrepareShoot;
import frc.team5115.Commands.Combo.ScoreAmp;
import frc.team5115.Commands.Combo.StopBoth;
import frc.team5115.Commands.Combo.TriggerShoot;
import frc.team5115.Commands.Combo.Vomit;

public class RobotContainer {
    private final Joystick joyDrive;
    private final Joystick joyManips;
    private final Drivetrain drivetrain;
    private final GenericEntry rookie;
    private final GenericEntry doAuto;
    private final I2CHandler i2cHandler;
    private final NAVx navx;
    private final Climber climber;
    private final Arm arm;
    private final Intake intake;
    private final Shooter shooter;
    private final Amper amper;
    private final DigitalInput reflectiveSensor;
    private AutoAimAndRange aAR;
    private AutoCommandGroup autoCommandGroup;
    private Paths paths;
    private final AutoBuilder autoBuilder;
    private final Climb climb;
    private final DeployClimber deployClimber;

    boolean fieldOriented = true;

    public RobotContainer() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("SmartDashboard");
        rookie = shuffleboardTab.add("Rookie?", false).getEntry();
        doAuto = shuffleboardTab.add("Do auto at all?", false).getEntry();
        shuffleboardTab.addBoolean("Field Oriented?", () -> fieldOriented);

        joyDrive = new Joystick(0);
        joyManips = new Joystick(1);
        navx = new NAVx();
        i2cHandler = new I2CHandler();
        autoBuilder = new AutoBuilder();


        PhotonVision p = new PhotonVision();

        HardwareDrivetrain hardwareDrivetrain = new HardwareDrivetrain(navx);
        drivetrain = new Drivetrain(hardwareDrivetrain, navx);
        
        HardwareArm hardwareArm = new HardwareArm(i2cHandler, Constants.ARM_RIGHT_MOTOR_ID, Constants.ARM_LEFT_MOTOR_ID);
        arm = new Arm(hardwareArm, i2cHandler);

        HardwareShooter hardwareShooter = new HardwareShooter(Constants.SHOOTER_CLOCKWISE_MOTOR_ID, Constants.SHOOTER_COUNTERCLOCKWISE_MOTOR_ID);
        shooter = new Shooter(hardwareShooter);
        intake = new Intake(Constants.INTAKE_MOTOR_ID);
        reflectiveSensor = new DigitalInput(Constants.SHOOTER_SENSOR_ID);
        shuffleboardTab.addBoolean("Note Sensor", () -> !reflectiveSensor.get());

        HardwareClimber leftClimber = new HardwareClimber(Constants.CLIMBER_LEFT_MOTOR_ID, true, Constants.CLIMB_LEFT_SENSOR_ID);
        HardwareClimber rightClimber = new HardwareClimber(Constants.CLIMBER_RIGHT_MOTOR_ID, false, Constants.CLIMB_RIGHT_SENSOR_ID);
        climber = new Climber(leftClimber, rightClimber);

        HardwareAmper hardwareAmper = new HardwareAmper(Constants.SNOWBLOWER_MOTOR_ID);
        amper = new Amper(hardwareAmper);

        // the sign of the delta for these commands can be used to change the direction
        climb = new Climb(climber, +12);
        deployClimber = new DeployClimber(climber, +1);
        aAR = new AutoAimAndRange(hardwareDrivetrain, p);
        configureButtonBindings();
    }

    // public void registerCommand() {

    // Register Named Commands for pathplanner

    //   NamedCommands.registerCommand("Example Path", drivetrain.pathplanner());

    //   NamedCommands.registerCommand("Path Uno", drivetrain.pathplanner());
    //   NamedCommands.registerCommand("Path Dos", drivetrain.pathplanner());

    //   NamedCommands.registerCommand("Path Tres", drivetrain.pathplanner());
    //   NamedCommands.registerCommand("Path Quatro", drivetrain.pathplanner());

    //   NamedCommands.registerCommand("Path Cinco", drivetrain.pathplanner());
    //   NamedCommands.registerCommand("Path Seis", drivetrain.pathplanner());

    //   NamedCommands.registerCommand("START middle to middle", drivetrain.pathplanner());
    //   NamedCommands.registerCommand("START middle to bottom", drivetrain.pathplanner());
    //   NamedCommands.registerCommand("START middle to top", drivetrain.pathplanner());

    //   NamedCommands.registerCommand("START top to top", drivetrain.pathplanner());
    //   NamedCommands.registerCommand("START top to middle", drivetrain.pathplanner());
    //   NamedCommands.registerCommand("START top to bottom", drivetrain.pathplanner());

    //   NamedCommands.registerCommand("START bottom to top", drivetrain.pathplanner());
    //   NamedCommands.registerCommand("START bottom to middle", drivetrain.pathplanner());
    //   NamedCommands.registerCommand("START bottom to bottom", drivetrain.pathplanner());

    // }

    public void configureButtonBindings() {

        new JoystickButton(joyManips, XboxController.Button.kLeftBumper.value)
        .onTrue(deployClimber);
        
        new JoystickButton(joyManips, XboxController.Button.kRightBumper.value)
        .onTrue(climb);

        new JoystickButton(joyManips, XboxController.Button.kBack.value)
        .onTrue(new Vomit(shooter, intake))
        .onFalse(new StopBoth(intake, shooter));

        new JoystickButton(joyManips, XboxController.Button.kA.value)
        .onTrue(new IntakeSequence(intake, shooter, arm, reflectiveSensor)
        .withInterruptBehavior(InterruptionBehavior.kCancelSelf));

        new JoystickButton(joyManips, XboxController.Button.kX.value)
        .onTrue(new PrepareAmp(intake, shooter, arm, reflectiveSensor, amper)
        .withInterruptBehavior(InterruptionBehavior.kCancelSelf))
        .onFalse(new ScoreAmp(intake, shooter, arm, reflectiveSensor, amper)
        .withInterruptBehavior(InterruptionBehavior.kCancelIncoming));

        new JoystickButton(joyManips, XboxController.Button.kB.value)
        .onTrue(new PrepareShoot(intake, shooter, arm, reflectiveSensor)
        .withInterruptBehavior(InterruptionBehavior.kCancelSelf))
        .onFalse(new TriggerShoot(intake, shooter, arm, reflectiveSensor)
        .withInterruptBehavior(InterruptionBehavior.kCancelIncoming));

        new JoystickButton(joyManips, XboxController.Button.kY.value)
        .onTrue(new StowArm(intake, shooter, arm)
        .withInterruptBehavior(InterruptionBehavior.kCancelSelf));

        new JoystickButton(joyDrive, XboxController.Button.kA.value)
        .onTrue(new InstantCommand(this :: switchFieldOriented));
    }

    private void switchFieldOriented() {
        fieldOriented = !fieldOriented;
    }

    public void disabledInit(){
        drivetrain.stop();
    }

    public void stopEverything(){
        drivetrain.stop();
        arm.stop();
    }

    public void startTest() {
    }

    public void testPeriodic() {
        amper.setSpeed(joyManips.getRawAxis(XboxController.Axis.kLeftY.value) * 0.3);
        System.out.println(amper.getAngle());
    }

    public void startAuto(){
        if(autoCommandGroup != null) autoCommandGroup.cancel();
        drivetrain.resetEncoders();
        navx.resetNAVx();
        drivetrain.stop();
        drivetrain.init();

        autoCommandGroup = new AutoCommandGroup(drivetrain, fieldOriented, intake, shooter, arm, reflectiveSensor, aAR);
        //autoCommandGroup.schedule();
        System.out.println("Starting auto");
    }

    public void autoPeriod() {
        // drivetrain.updateOdometry();
        aAR.if7();
        arm.updateController(i2cHandler);
    }

    public void startTeleop(){
        if(autoCommandGroup != null) autoCommandGroup.cancel();
        
        drivetrain.resetEncoders();
        System.out.println("Starting teleop");
    }

    public void teleopPeriodic() {

        // manual climber
        if(climber.isDeployed()) {
            climber.setBoth(joyManips.getRawAxis(1));
        }

        //aAR.periodic1();

        /*
        final boolean MANUAL_CLIMB = false;

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

        //   System.out.println("bno angle: " + i2cHandler.getPitch());

        arm.updateController(i2cHandler);
        drivetrain.SwerveDrive(-joyDrive.getRawAxis(1), joyDrive.getRawAxis(4), -joyDrive.getRawAxis(0),rookie.getBoolean(false), fieldOriented);
    }
}
