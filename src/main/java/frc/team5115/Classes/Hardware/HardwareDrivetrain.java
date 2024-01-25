package frc.team5115.Classes.Hardware;

import static frc.team5115.Constants.*;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.Units.*;
import edu.wpi.first.util.WPIUtilJNI;
import frc.team5115.Constants.DriveConstants;
import frc.team5115.Classes.Accessory.SwerveUtils;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics.SwerveDriveWheelStates;
/**
 * The drivetrain hardware subsystem. Provides methods to interact with the actual hardware of the drivetrain.
 */
public class HardwareDrivetrain{    
    private final NAVx gyro;

    SwerveDriveModule frontLeft = new SwerveDriveModule(FRONT_LEFT_MOTOR_ID, FRONT_LEFT_TURN_MOTOR_ID, Math.toRadians(90));
    SwerveDriveModule frontRight = new SwerveDriveModule(FRONT_RIGHT_MOTOR_ID, FRONT_RIGHT_TURN_MOTOR_ID, Math.toRadians(180));
    SwerveDriveModule backLeft = new SwerveDriveModule(BACK_LEFT_MOTOR_ID,BACK_LEFT_TURN_MOTOR_ID, 0);
    SwerveDriveModule backRight = new SwerveDriveModule(BACK_RIGHT_MOTOR_ID, BACK_RIGHT_TURN_MOTOR_ID, Math.toRadians(270));

    // Slew rate filter variables for controlling lateral acceleration
    private double m_currentRotation = 0.0;
    private double m_currentTranslationDir = 0.0;
    private double m_currentTranslationMag = 0.0;

    private SlewRateLimiter m_magLimiter = new SlewRateLimiter(1);
    private SlewRateLimiter m_rotLimiter = new SlewRateLimiter(1);
    private double m_prevTime = WPIUtilJNI.now() * 1e-6;

    /**
    * `HardwareDrivetrain` constructor.
    * @param arm - The arm subsystem to use
    */
    public HardwareDrivetrain(NAVx gyro){
        this.gyro = gyro;
        resetEncoders();
        //frontRightOld.setInverted(true);
    }

    public void x(){
       double x = frontLeft.getState().speedMetersPerSecond;
       double velocity = frontLeft.getState().speedMetersPerSecond;
    }

    public double getEncoderVelocity(int motorID){
        switch(motorID) {
            case BACK_LEFT_MOTOR_ID:
        return backLeft.getState().speedMetersPerSecond;
            case FRONT_LEFT_MOTOR_ID:
        return frontLeft.getState().speedMetersPerSecond;

            case FRONT_RIGHT_MOTOR_ID:
        return frontRight.getState().speedMetersPerSecond;
            case BACK_RIGHT_MOTOR_ID:
        return backRight.getState().speedMetersPerSecond;

        default:
         throw new Error("Not working yet buddy");
        }
         
    }
    public double getEncoder(int motorID){
        switch(motorID) {
            case BACK_LEFT_MOTOR_ID:
        return backLeft.getPosition().distanceMeters;
            case FRONT_LEFT_MOTOR_ID:
        return frontLeft.getPosition().distanceMeters;

            case FRONT_RIGHT_MOTOR_ID:
        return frontRight.getPosition().distanceMeters;
            case BACK_RIGHT_MOTOR_ID:
        return backRight.getPosition().distanceMeters;

        default:
         throw new Error("Not working yet bucko");
        }
        

         
    }

    public ChassisSpeeds getChassisSpeeds(){ 
        SwerveModuleState[] x = {frontLeft.getState(), frontRight.getState(), backLeft.getState(), backRight.getState()};
        return DriveConstants.kDriveKinematics.toChassisSpeeds(x);
    }

    public void setWheelSpeeds(ChassisSpeeds speeds){ 
        drive(speeds.vxMetersPerSecond, speeds.vyMetersPerSecond, speeds.omegaRadiansPerSecond, true, false);

        // plugandFFDrive(leftVelocity, rightVelocity);

    }
    // search on wpilib/chief delphi to find new swervedrivewheelspeeds code and new units code*

    public ChassisSpeeds discretize(
                  double vxMetersPerSecond,
                  double vyMetersPerSecond,
                  double omegaRadiansPerSecond,
                  double dtSeconds) {
                var desiredDeltaPose =
                    new Pose2d(
                        vxMetersPerSecond * dtSeconds,
                        vyMetersPerSecond * dtSeconds,
                        new Rotation2d(omegaRadiansPerSecond * dtSeconds));
                var twist = new Pose2d().log(desiredDeltaPose);
                return new ChassisSpeeds(twist.dx / dtSeconds, twist.dy / dtSeconds, twist.dtheta / dtSeconds);
              }
    public ChassisSpeeds discretize(
                ChassisSpeeds x,
                double dtSeconds) {
                    double vxMetersPerSecond = x.vxMetersPerSecond;
                    double vyMetersPerSecond = x.vyMetersPerSecond;
                    double omegaRadiansPerSecond = x.omegaRadiansPerSecond;
              var desiredDeltaPose =
                  new Pose2d(
                      vxMetersPerSecond * dtSeconds,
                      vyMetersPerSecond * dtSeconds,
                      new Rotation2d(omegaRadiansPerSecond * dtSeconds));
              var twist = new Pose2d().log(desiredDeltaPose);
              return new ChassisSpeeds(twist.dx / dtSeconds, twist.dy / dtSeconds, twist.dtheta / dtSeconds);
            }

    /**
     * Method to drive the robot using joystick info.
     *
     * @param xSpeed        Speed of the robot in the x direction (forward).
     * @param ySpeed        Speed of the robot in the y direction (sideways).
     * @param rot           Angular rate of the robot.
     * @param fieldRelative Whether the provided x and y speeds are relative to the
     *                      field.
     * @param rateLimit     Whether to enable rate limiting for smoother control.
     */
    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative, boolean rateLimit) {
        
        double xSpeedCommanded;
        double ySpeedCommanded;

        xSpeedCommanded = xSpeed;
        ySpeedCommanded = ySpeed;
        m_currentRotation = rot;

        // Convert the commanded speeds into the correct units for the drivetrain

        double rotDelivered = m_currentRotation * DriveConstants.kMaxAngularSpeed;
        double xSpeedDelivered = xSpeedCommanded * DriveConstants.kMaxSpeedMetersPerSecond+rotDelivered/20;
        double ySpeedDelivered = ySpeedCommanded * DriveConstants.kMaxSpeedMetersPerSecond;


        System.out.println(gyro.getYawDeg360());
        ChassisSpeeds x = 
            fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered, Rotation2d.fromDegrees(gyro.getYawDeg360()))
                : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered);
        x = discretize(x, 0.02);

        SwerveModuleState[] swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(x);
        SwerveDriveKinematics.desaturateWheelSpeeds(
            swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);
            
        frontLeft.setDesiredState(swerveModuleStates[0]);
        frontRight.setDesiredState(swerveModuleStates[1]);
        backLeft.setDesiredState(swerveModuleStates[2]);
        backRight.setDesiredState(swerveModuleStates[3]);
    }

    public void getAutonomousCommand(){
        getAutonomousCommand();


    }


    public void FollowPathCommand(){


    }

    public void buildAutoChooser(){
        buildAutoChooser(); 
        AutoBuilder.buildAutoChooser();


    }
    /**
     * Sets the wheels into an X formation to prevent movement.
     */
    public void setX() {
        frontLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
        frontRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
        backLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
        backRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
    }

    /**
     * Sets the swerve ModuleStates.
     *
     * @param desiredStates The desired SwerveModule states.
     */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(
            desiredStates, DriveConstants.kMaxSpeedMetersPerSecond);
        frontLeft.setDesiredState(desiredStates[0]);
        frontRight.setDesiredState(desiredStates[1]);
        backLeft.setDesiredState(desiredStates[2]);
        backRight.setDesiredState(desiredStates[3]);
    }

    /** Resets the drive encoders to currently read a position of 0. */
    public void resetEncoders() {
        frontLeft.resetEncoders();
        backLeft.resetEncoders();
        frontRight.resetEncoders();
        backRight.resetEncoders();
    }

    /** Zeroes the heading of the robot. */
    public void zeroHeading() {
        gyro.resetNAVx();
    }

    /**
     * Returns the heading of the robot.
     *
     * @return the robot's heading in degrees, from -180 to 180
     */
    public double getHeading() {
        return gyro.getYawDeg();
    }

    public SwerveModulePosition[] getModulePositions() {
        return new SwerveModulePosition[] {
            frontLeft.getPosition(),
            frontRight.getPosition(),
            backLeft.getPosition(),
            backRight.getPosition()
        };
    }
}
