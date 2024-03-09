package frc.team5115.Classes.Hardware;

import static frc.team5115.Constants.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.team5115.Constants.DriveConstants;
/**
 * The drivetrain hardware subsystem. Provides methods to interact with the actual hardware of the drivetrain.
 */
public class HardwareDrivetrain{    

    private static final double frontLeftKs = 0.19801;
    private static final double frontLeftKv = 156.6;
    private static final double frontLeftKa = 22.654;

    private static final double frontRightKs = 0.097546;
    private static final double frontRightKv = 155.28;
    private static final double frontRightKa = 22.616;
    
    private static final double backLeftKs = 0.11547;
    private static final double backLeftKv = 153.7;
    private static final double backLeftKa = 22.943;

    private static final double backRightKs = 0.12506;
    private static final double backRightKv = 153.68;
    private static final double backRightKa = 24.364;

    private final NAVx gyro;

    final SwerveDriveModule frontLeft = new SwerveDriveModule(FRONT_LEFT_MOTOR_ID, FRONT_LEFT_TURN_MOTOR_ID, Math.toRadians(90), frontLeftKs, frontLeftKv, frontLeftKa);
    final SwerveDriveModule frontRight = new SwerveDriveModule(FRONT_RIGHT_MOTOR_ID, FRONT_RIGHT_TURN_MOTOR_ID, Math.toRadians(180), frontRightKs, frontRightKv, frontRightKa);
    final SwerveDriveModule backLeft = new SwerveDriveModule(BACK_LEFT_MOTOR_ID,BACK_LEFT_TURN_MOTOR_ID, Math.toRadians(0), backLeftKs, backLeftKv, backLeftKa);
    final SwerveDriveModule backRight = new SwerveDriveModule(BACK_RIGHT_MOTOR_ID, BACK_RIGHT_TURN_MOTOR_ID, Math.toRadians(270), backRightKs, backRightKv, backRightKa);

    // Slew rate filter variables for controlling lateral acceleration
    private double m_currentRotation = 0.0;

    /**
    * `HardwareDrivetrain` constructor.
    * @param arm - The arm subsystem to use
    */
    public HardwareDrivetrain(NAVx gyro){
        this.gyro = gyro;
        resetEncoders();
        //frontRightOld.setInverted(true);
    }

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

        ChassisSpeeds x = 
            fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered, Rotation2d.fromDegrees(gyro.getYawDeg360()))
                : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered);
        driveChassisSpeeds(x);
    }

    public void driveChassisSpeeds(ChassisSpeeds x) {
        x = discretize(x, 0.02);
        SwerveModuleState[] swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(x);
        frontLeft.setDesiredState(swerveModuleStates[0]);
        frontRight.setDesiredState(swerveModuleStates[1]);
        backLeft.setDesiredState(swerveModuleStates[2]);
        backRight.setDesiredState(swerveModuleStates[3]);
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

    public ChassisSpeeds getChassisSpeeds() {
        return DriveConstants.kDriveKinematics.toChassisSpeeds(getModuleStates());
    }

    public SwerveModulePosition[] getModulePositions() {
        return new SwerveModulePosition[] {
            frontLeft.getPosition(),
            frontRight.getPosition(),
            backLeft.getPosition(),
            backRight.getPosition()
        };
    }

    public SwerveModuleState[] getModuleStates() {
        return new SwerveModuleState[] {
            frontLeft.getState(),
            frontRight.getState(),
            backLeft.getState(),
            backRight.getState()
        };
    }
}
