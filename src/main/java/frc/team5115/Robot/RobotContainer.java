package frc.team5115.Robot;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Classes.Hardware.HardwareIntake;
import frc.team5115.Classes.Hardware.HardwareShooter;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Combo.IntakeSequence;
import frc.team5115.Commands.Combo.ShootSequence;
import frc.team5115.Commands.Combo.SpinUpShooter;
import frc.team5115.Commands.Combo.Vomit;

public class RobotContainer {
    // private final Joystick joyDrive;
    private final Joystick joyManips;
    // private final Drivetrain drivetrain;
    // private final GenericEntry rookie;
    // private final GenericEntry doAuto;
    // private final I2CHandler i2cHandler;
    // private final NAVx navx;
    // private final Arm arm;
    // private final Climber climber;
    private final Intake intake;
    private final Shooter shooter;
    private final DigitalInput reflectiveSensor;
    // private AutoCommandGroup autoCommandGroup;
    private final GenericEntry rpmEntry;

    public RobotContainer() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("SmartDashboard");
        rpmEntry = shuffleboardTab.add("shooter rpm", 3500).getEntry();

        // rookie = shuffleboardTab.add("Rookie?", false).getEntry();
        // doAuto = shuffleboardTab.add("Do auto at all?", false).getEntry();

        // joyDrive = new Joystick(0);
        joyManips = new Joystick(1);
        // navx = new NAVx();
        // i2cHandler = new I2CHandler();

        // HardwareDrivetrain hardwareDrivetrain = new HardwareDrivetrain(navx);
        // PhotonVision photonVision = new PhotonVision();
        // drivetrain = new Drivetrain(hardwareDrivetrain, photonVision, navx);
        
        // HardwareArm hardwareArm = new HardwareArm(navx, i2cHandler);
        // arm = new Arm(hardwareArm);

        // TODO set climber canIDs, sensor channels, and PWM channels
        // HardwareClimber leftClimber = new HardwareClimber(0, 0, 0);
        // HardwareClimber rightClimber = new HardwareClimber(0, 0, 0);
        // climber = new Climber(leftClimber, rightClimber);

        HardwareIntake hardwareIntake = new HardwareIntake();
        HardwareShooter hardwareShooter = new HardwareShooter();
        intake = new Intake(hardwareIntake);
        shooter = new Shooter(hardwareShooter);
        reflectiveSensor = new DigitalInput(9);
        configureButtonBindings();
    }

    public void configureButtonBindings() {
        new JoystickButton(joyManips, XboxController.Button.kBack.value)
        .onTrue(new Vomit(true, shooter, intake))
        .onFalse(new Vomit(false, shooter, intake));

        new JoystickButton(joyManips, XboxController.Button.kA.value)
        .onTrue(new IntakeSequence(intake, shooter, null, reflectiveSensor));

        new JoystickButton(joyManips, XboxController.Button.kB.value)
        .onTrue(new ShootSequence(rpmEntry, intake, shooter, null, reflectiveSensor, 3900));

        // new JoystickButton(joyManips, XboxController.Button.kX.value)
        // .onTrue(new DeployClimber(climber));

        // new JoystickButton(joyManips, XboxController.Button.kY.value)
        // .onTrue(new Climb(climber));
    }

    public void disabledInit(){
        // drivetrain.stop();
    }

    public void stopEverything(){
        // drivetrain.stop();
        // arm.stop();
    }

    SpinUpShooter spinCommand;
    public void startTest() {
        spinCommand = new SpinUpShooter(shooter, rpmEntry.getDouble(3900));
    }

    public void testPeriodic() {
        if (joyManips.getRawButton(1) && !spinCommand.isScheduled()) spinCommand.schedule();
        else if (joyManips.getRawButton(2) && spinCommand.isScheduled()) spinCommand.cancel();
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
    }

    public void startTeleop(){
        // if(autoCommandGroup != null) autoCommandGroup.cancel();
        
        // System.out.println("Starting teleop");
        // drivetrain.resetEncoders();
    }

    public void teleopPeriodic() {
        // drivetrain.updateOdometry();
        // i2cHandler.updatePitch();

        // drivetrain.SwerveDrive(-joyDrive.getRawAxis(1), joyDrive.getRawAxis(4), joyDrive.getRawAxis(0), rookie.getBoolean(false));
    }
}
