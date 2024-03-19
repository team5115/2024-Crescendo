package frc.team5115.Robot;

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
import frc.team5115.Commands.Auto.AutoAimAndRangeCommand;
import frc.team5115.Commands.Auto.CenterAuto;
import frc.team5115.Commands.Auto.SideAuto;
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
    private final GenericEntry doAutoLeft;
    private final GenericEntry doAutoRight;
    private final I2CHandler i2cHandler;
    private final NAVx navx;
    private final Climber climber;
    private final Arm arm;
    private final Intake intake;
    private final Shooter shooter;
    private final Amper amper;
    private final DigitalInput reflectiveSensor;
    private final AutoAimAndRange aAR;
    private Command autoCommandGroup;
    private final DeployClimber deployClimber;
    private final AimAndRangeFrontCam aimAndRangeFrontCam;
    private final LedStrip ledStrip;
    private double angleOfDrivetrain = 0;
    private final PhotonVision p;
    boolean inRange = false;
    boolean aligning = false;
    private final Command ezTenFootShot;

    boolean fieldOriented = true;

    public RobotContainer() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("SmartDashboard");
        rookie = shuffleboardTab.add("Rookie?", false).getEntry();

        doAuto = shuffleboardTab.add("Do auto at all?", false).getEntry();
        doAutoLeft = shuffleboardTab.add("Do red auto?", false).getEntry();
        doAutoRight = shuffleboardTab.add("Do blue auto?", false).getEntry();

        shuffleboardTab.addBoolean("In Rangle for 10ft Shot", () -> inRange);
        shuffleboardTab.addBoolean("Field Oriented?", () -> fieldOriented);

        joyDrive = new Joystick(0);
        joyManips = new Joystick(1);
        navx = new NAVx();
        i2cHandler = new I2CHandler();
        ledStrip = new LedStrip(0, 20);
        ledStrip.start();

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

        // the sign of the delta for this command can be used to change the direction
        deployClimber = new DeployClimber(climber, +1);

        aAR = new AutoAimAndRange(hardwareDrivetrain, p);
        aimAndRangeFrontCam = new AimAndRangeFrontCam(hardwareDrivetrain, p);

        ezTenFootShot =
            new PrepareShoot(intake, shooter, arm, reflectiveSensor, Constants.Arm10FtAngle, 5000, null, false)
            .alongWith(new AutoAimAndRangeCommand(aAR))
            .andThen(new TriggerShoot(intake, shooter, arm, reflectiveSensor))
            .withInterruptBehavior(InterruptionBehavior.kCancelSelf);
            
        configureButtonBindings();
    }

    public void configureButtonBindings() {

        new JoystickButton(joyManips, XboxController.Button.kLeftBumper.value)
        .onTrue(deployClimber);

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
        .onTrue(new PrepareShoot(intake, shooter, arm, reflectiveSensor, 15, 5000, null, true)
        .withInterruptBehavior(InterruptionBehavior.kCancelSelf))
        .onFalse(new TriggerShoot(intake, shooter, arm, reflectiveSensor)
        .withInterruptBehavior(InterruptionBehavior.kCancelIncoming));

        new JoystickButton(joyManips, XboxController.Button.kY.value)
        .onTrue(new StowArm(intake, shooter, arm)
        .withInterruptBehavior(InterruptionBehavior.kCancelSelf));

        new JoystickButton(joyDrive, XboxController.Button.kA.value)
        .onTrue(new InstantCommand(this :: switchFieldOriented));

        new JoystickButton(joyDrive, XboxController.Button.kStart.value)
        .onTrue(new InstantCommand(this :: resetAngleOfDrivetrain));

        new JoystickButton(joyDrive, XboxController.Button.kB.value).onTrue(ezTenFootShot);
        
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
        arm.stop();
    }

    public void startTest() {
    }

    public void testPeriodic() {
        amper.setSpeed(joyManips.getRawAxis(XboxController.Axis.kLeftY.value) * 0.3);
    }

    public void startAuto(){
        if(doAutoRight.getBoolean(false)) {
            angleOfDrivetrain = 60;
            autoCommandGroup = new SideAuto(drivetrain, fieldOriented, intake, shooter, arm, reflectiveSensor, aAR, p, navx, false, angleOfDrivetrain);
        }
        else if(doAutoLeft.getBoolean(false)) {
            angleOfDrivetrain = -60;
            autoCommandGroup = new SideAuto(drivetrain, fieldOriented, intake, shooter, arm, reflectiveSensor, aAR, p, navx, true, angleOfDrivetrain);
        }
        else {
            autoCommandGroup = new CenterAuto(fieldOriented, drivetrain, intake, shooter, arm, reflectiveSensor, aAR);
        } 

        drivetrain.resetEncoders();
        navx.resetNAVx();
        drivetrain.stop();
        //drivetrain.init();
        if(doAuto.getBoolean(false)){
             System.out.println("running auto");
             autoCommandGroup.schedule();
        }

        System.out.println("Starting auto");
    }

    public void autoPeriod() {
        // drivetrain.updateOdometry();
        //aAR.periodicIDBased();
        arm.updateController(i2cHandler);
    }

    public void startTeleop(){
        if(autoCommandGroup != null) autoCommandGroup.cancel();
        drivetrain.resetEncoders();
        System.out.println("Starting teleop");
    }

    private void resetAngleOfDrivetrain() {
        angleOfDrivetrain = 0;
        navx.resetYaw();
    }

    public void teleopPeriodic() {
        //System.out.println("The Skew: " + p.getSkewID7());
        aligning = ezTenFootShot.isScheduled();
        if(!aligning) {
            drivetrain.SwerveDrive(-joyDrive.getRawAxis(1), joyDrive.getRawAxis(4), -joyDrive.getRawAxis(0),rookie.getBoolean(false), fieldOriented, angleOfDrivetrain);
        }

        // manual climber
        if(climber.isDeployed()) {
            climber.setBoth(joyManips.getRawAxis(1));
        }

        ledStrip.update(!reflectiveSensor.get(), intake.stuck(), aligning, inRange);

        //System.out.println("absolute encoder angle: " + arm.getAngle().angle);

        arm.updateController(i2cHandler);
    }
}
