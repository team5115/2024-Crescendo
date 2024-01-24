package frc.team5115.Robot;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Classes.Accessory.I2CHandler;
import frc.team5115.Classes.Hardware.HardwareArm;
import frc.team5115.Classes.Hardware.HardwareDrivetrain;
import frc.team5115.Classes.Hardware.HardwareFeeder;
import frc.team5115.Classes.Hardware.HardwareIntake;
import frc.team5115.Classes.Hardware.HardwareShooter;
import frc.team5115.Classes.Hardware.NAVx;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Software.Feeder;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.PhotonVision;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Auto.AutoCommandGroup;
// import paths 

public class RobotContainer {
    private final Joystick joyDrive;
    private final Joystick joyManips;
    private final Drivetrain drivetrain;
    private final GenericEntry rookie;
    private final GenericEntry doAuto;
    private final I2CHandler i2cHandler;
    private final NAVx navx;
    private final Arm arm;
    private final Feeder feeder;
    private final Intake intake;
    private final Shooter shooter;
    private final Paths paths;
    private AutoCommandGroup autoCommandGroup;

public RobotContainer() {

    public registerPaths() {

        Paths = new Paths();

        // Register Named Commands for pathplanner

        NamedCommands.registerCommand("Example Path", getPathGroupFromAutoFile.SideAutoPt1());
     // NamedCommands.registerCommand("", getPathGroupFromAutoFile.exampleCommand());
     // NamedCommands.registerCommand("", getPathGroupFromAutoFile.exampleCommand());
    }
}
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("SmartDashboard");
        rookie = shuffleboardTab.add("Rookie?", false).getEntry();
        doAuto = shuffleboardTab.add("Do auto at all?", false).getEntry();

        joyDrive = new Joystick(0);
        joyManips = new Joystick(1);
        navx = new NAVx();
        i2cHandler = new I2CHandler();

        HardwareDrivetrain hardwareDrivetrain = new HardwareDrivetrain(navx);
        PhotonVision photonVision = new PhotonVision();
        drivetrain = new Drivetrain(hardwareDrivetrain, photonVision, navx);
        
        HardwareArm hardwareArm = new HardwareArm(navx, i2cHandler);
        arm = new Arm(hardwareArm);

        HardwareFeeder hardwareFeeder = new HardwareFeeder();
        HardwareIntake hardwareIntake = new HardwareIntake();
        HardwareShooter hardwareShooter = new HardwareShooter();
        feeder = new Feeder(hardwareFeeder);
        intake = new Intake(hardwareIntake);
        shooter = new Shooter(hardwareShooter);

        configureButtonBindings();
    }

    public void configureButtonBindings() {     
        new JoystickButton(joyManips, XboxController.Button.kA.value)
            .onTrue(new InstantCommand(shooter :: startShooter))
            .onFalse(new InstantCommand(shooter :: stopShooter));
        new JoystickButton(joyManips, XboxController.Button.kB.value)
            .onTrue(new InstantCommand(intake :: startIntakeMotor))
            .onFalse(new InstantCommand(intake :: stopIntakeMotor));
        new JoystickButton(joyManips, XboxController.Button.kX.value)
            .onTrue(new InstantCommand(feeder :: startFeed))
            .onFalse(new InstantCommand(feeder :: stopFeed));
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
    }

    public void startAuto(){
        if(autoCommandGroup != null) autoCommandGroup.cancel();
        drivetrain.resetEncoders();
        drivetrain.resetNAVx();
        drivetrain.stop();
        drivetrain.init();

        autoCommandGroup = new AutoCommandGroup(drivetrain, doAuto.getBoolean(true));
        autoCommandGroup.schedule();
    }

    public void autoPeriod() {
        drivetrain.updateOdometry();
        i2cHandler.updatePitch();
        arm.updateController();
    }

    public void startTeleop(){
        if(autoCommandGroup != null) autoCommandGroup.cancel();
        
        System.out.println("Starting teleop");
        drivetrain.resetEncoders();
    }

    public void teleopPeriodic() {
        drivetrain.updateOdometry();
        i2cHandler.updatePitch();
        arm.updateController();

        drivetrain.SwerveDrive(-joyDrive.getRawAxis(1), joyDrive.getRawAxis(4), joyDrive.getRawAxis(0), rookie.getBoolean(false));
    }

