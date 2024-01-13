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
import frc.team5115.Classes.Hardware.HardwareIntake;
import frc.team5115.Classes.Hardware.HardwareShooter;
import frc.team5115.Classes.Hardware.NAVx;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.PhotonVision;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Auto.AutoCommandGroup;

public class RobotContainer {
    private final Joystick joyDrive;
    private final Joystick joyManips;
    private final Drivetrain drivetrain;
    private final GenericEntry rookie;
    private final GenericEntry doAuto;
    private final I2CHandler i2cHandler;
    private final NAVx navx;
    private final Arm arm;
    private final Intake intake;
    private final Shooter shooter;
    private AutoCommandGroup autoCommandGroup;

    public RobotContainer() {
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

        HardwareIntake hardwareIntake = new HardwareIntake();
        HardwareShooter hardwareShooter = new HardwareShooter();
        intake = new Intake(hardwareIntake);
        shooter = new Shooter(hardwareShooter);

        configureButtonBindings();
    }

    public void configureButtonBindings() {
        
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
        if (joyManips.getRawButton(XboxController.Button.kLeftBumper.value)) {
            intake.in();
        } else if (joyManips.getRawButton(XboxController.Button.kRightBumper.value)) {
            intake.out();
        } else {
            intake.stop();
        }

        if (joyManips.getRawButton(XboxController.Button.kA.value)) {
            shooter.fast();
        } else if (joyManips.getRawButton(XboxController.Button.kB.value)) {
            shooter.slow();
        } else {
            shooter.stop();
        }

        // drivetrain.updateOdometry();
        // i2cHandler.updatePitch();
        // arm.updateController();

        // drivetrain.SwerveDrive(-joyDrive.getRawAxis(1), joyDrive.getRawAxis(4), joyDrive.getRawAxis(0), rookie.getBoolean(false));
    }
}
