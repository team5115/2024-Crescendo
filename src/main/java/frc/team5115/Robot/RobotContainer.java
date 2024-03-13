package frc.team5115.Robot;

import java.nio.file.Paths;

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
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Constants;
import frc.team5115.Classes.Hardware.HardwareAmper;
import frc.team5115.Classes.Hardware.HardwareArm;
import frc.team5115.Classes.Hardware.HardwareClimber;
import frc.team5115.Classes.Hardware.HardwareDrivetrain;
import frc.team5115.Classes.Hardware.HardwareShooter;
import frc.team5115.Classes.Hardware.I2CHandler;
import frc.team5115.Classes.Hardware.LedStrip;
import frc.team5115.Classes.Hardware.NAVx;
import frc.team5115.Classes.Software.AimAndRangeFrontCam;
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
import frc.team5115.Commands.Auto.AutoCommandTwo;
import frc.team5115.Commands.Climber.Climb;
import frc.team5115.Commands.Climber.DeployClimber;
import frc.team5115.Commands.Combo.IntakeSequence;
import frc.team5115.Commands.Combo.PrepareAmp;
import frc.team5115.Commands.Combo.PrepareShoot;
import frc.team5115.Commands.Combo.ScoreAmp;
import frc.team5115.Commands.Combo.StopBoth;
import frc.team5115.Commands.Combo.TriggerShoot;
import frc.team5115.Commands.Combo.Vomit;
import frc.team5115.Commands.Auto.AutoPart1;

public class RobotContainer {
    private final Joystick joyDrive;
    private final Joystick joyManips;
    private final Drivetrain drivetrain;
    private final GenericEntry rookie;
    private final GenericEntry doAuto;
    private final GenericEntry shootAngle;
    private final I2CHandler i2cHandler;
    private final NAVx navx;
    private final AutoPart1 autoPart1;
    private final Climber climber;
    private final Arm arm;
     private final Intake intake;
     private final Shooter shooter;
    private final Amper amper;
     private final DigitalInput reflectiveSensor;
    private AutoAimAndRange aAR;
    private AutoCommandGroup autoCommandGroup;
    //photonPoseEstimator = new photonPoseEstimatorF(); 
    private Paths paths;
    private final Climb climb;
    private final DeployClimber deployClimber;
    private final AimAndRangeFrontCam aimAndRangeFrontCam;
    private final LedStrip ledStrip;
    private final PhotonVision p;

    boolean fieldOriented = true;
    private AutoCommandTwo autoCommandTwo;

    public RobotContainer() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("SmartDashboard");
        rookie = shuffleboardTab.add("Rookie?", false).getEntry();
        doAuto = shuffleboardTab.add("Do auto at all?", false).getEntry();
        shootAngle = shuffleboardTab.add("Shooter angle", 5).getEntry();
        shuffleboardTab.addBoolean("Field Oriented?", () -> fieldOriented);

        joyDrive = new Joystick(0);
        joyManips = new Joystick(1);
        navx = new NAVx();
        //private final PhotonPoseEstimator photonPoseEstimatorFP

        ledStrip = new LedStrip(0, 20);
        ledStrip.start();
        
        i2cHandler = new I2CHandler();

        p = new PhotonVision();

        HardwareDrivetrain hardwareDrivetrain = new HardwareDrivetrain(navx);
        drivetrain = new Drivetrain(hardwareDrivetrain, navx, p);
        
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
        aimAndRangeFrontCam = new AimAndRangeFrontCam(hardwareDrivetrain, p);
        
        autoPart1 = new AutoPart1(drivetrain, fieldOriented, intake, shooter, arm, reflectiveSensor, aAR);
        configureButtonBindings();
    }

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
        
        new JoystickButton(joyManips, XboxController.Button.kLeftStick.value)
        .onTrue(new PrepareShoot(intake, shooter, arm, reflectiveSensor, 34.5, 5000, null, true)
        .withInterruptBehavior(InterruptionBehavior.kCancelSelf))
        .onFalse(new TriggerShoot(intake, shooter, arm, reflectiveSensor)
        .withInterruptBehavior(InterruptionBehavior.kCancelIncoming));

        new JoystickButton(joyManips, XboxController.Button.kB.value)
        .onTrue(new PrepareShoot(intake, shooter, arm, reflectiveSensor, 5, 5000, null, true)
        .withInterruptBehavior(InterruptionBehavior.kCancelSelf))
        .onFalse(new TriggerShoot(intake, shooter, arm, reflectiveSensor)
        .withInterruptBehavior(InterruptionBehavior.kCancelIncoming));

        new JoystickButton(joyManips, XboxController.Button.kY.value)
        .onTrue(new StowArm(intake, shooter, arm)
        .withInterruptBehavior(InterruptionBehavior.kCancelSelf));

        new JoystickButton(joyDrive, XboxController.Button.kA.value)
        .onTrue(new InstantCommand(this :: switchFieldOriented));
        /* 
        new JoystickButton(joyDrive, XboxController.Button.kB.value).
        onTrue(new InstantCommand(this::AutoPart1))
        .onFalse(new InstantCommand(this :: AutoPart1Cancel));
        */
    }

    private void switchFieldOriented() {
        fieldOriented = !fieldOriented;
    }

    public void disabledInit(){
        drivetrain.stop();
        ledStrip.setUniformColor(0, 0, 0);
    }

    public void stopEverything(){
        drivetrain.stop();
        // arm.stop();
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
        //navx.resetNAVx();
        drivetrain.stop();
        drivetrain.init();

        // if (AutoBuilder.isConfigured()) {
        //     PathPlannerPath path = PathPlannerPath.fromPathFile("surfers");
        //     Command test = AutoBuilder.followPath(path);
        //     test.schedule();
        // } else {
        //     System.out.println("ERROR! AutoBuilder has not been configured!");
        // }

        // autoCommandGroup = new AutoCommandGroup(drivetrain, fieldOriented, intake, shooter, arm, reflectiveSensor, aAR);
        //autoCommandGroup.schedule();

        autoCommandTwo = new AutoCommandTwo(drivetrain, true, intake, shooter, arm, reflectiveSensor, aAR);
        autoCommandTwo.schedule();
        System.out.println("Starting auto");
    }

    public void autoPeriod() {
        // drivetrain.updateOdometry();
        drivetrain.updatePoseEstimator();
        aAR.if7();
        arm.updateController(i2cHandler);
    }

    public void startTeleop(){
        if(autoCommandGroup != null) autoCommandGroup.cancel();
        drivetrain.resetEncoders();
        System.out.println("Starting teleop");
    }

    public void teleopPeriodic() {
        System.out.println("The Skew: " + p.getSkewID7());
        if(joyDrive.getRawButton(2))
            aAR.periodicIDBased();    
        else 
            drivetrain.SwerveDrive(-joyDrive.getRawAxis(1), joyDrive.getRawAxis(4), -joyDrive.getRawAxis(0),rookie.getBoolean(false), fieldOriented);

        // manual climber
        if(climber.isDeployed()) {
            climber.setBoth(joyManips.getRawAxis(1));
        }

        if (reflectiveSensor.get()) {
            ledStrip.updateKnightRider();
        } else {
            ledStrip.setUniformColor(0, 150, 0);
        }

        //aAR.periodic1();

        //   System.out.println("bno angle: " + i2cHandler.getPitch());

        arm.updateController(i2cHandler);
    }
}
