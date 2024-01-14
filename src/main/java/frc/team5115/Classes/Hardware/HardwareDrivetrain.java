package frc.team5115.Classes.Hardware;

import static frc.team5115.Constants.*;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.util.WPIUtilJNI;
import frc.team5115.Constants.DriveConstants;
import frc.team5115.Classes.Accessory.SwerveUtils;
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

    public ChassisSpeeds getChassisSpeeds(){ // for autobuilder pathplanner
        SwerveDriveKinematics kinematics = new SwerveDriveKinematics(Units.inchesToMeters(27.75));
        ChassisSpeeds j = new ChassisSpeeds();
        var wheelSpeeds = new SwerveDriveWheelSpeeds(getEncoderVelocity(BACK_LEFT_MOTOR_ID), getEncoderVelocity(FRONT_LEFT_MOTOR_ID));
        ChassisSpeeds chassisSpeeds = kinematics.toChassisSpeeds(wheelSpeeds);

        return chassisSpeeds;
    }

    public void setWheelSpeeds(ChassisSpeeds speeds){ // pathplanner autobuilder
        SwerveDriveKinematics kinematics = new SwerveDriveKinematics(Units.inchesToMeters(27.0));
        SwerveDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(speeds);

        double leftVelocity = wheelSpeeds.leftMetersPerSecond;
        double rightVelocity = wheelSpeeds.rightMetersPerSecond;

        // plugandFFDrive(leftVelocity, rightVelocity);

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

        if (rateLimit) {
            // Convert XY to polar for rate limiting
            double inputTranslationDir = Math.atan2(ySpeed, xSpeed);
            double inputTranslationMag = Math.sqrt(Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2));

            // Calculate the direction slew rate based on an estimate of the lateral acceleration
            double directionSlewRate;
            if (m_currentTranslationMag != 0.0) {
                directionSlewRate = Math.abs(DriveConstants.kDirectionSlewRate / m_currentTranslationMag);
            } else {
                directionSlewRate = 500.0; //some high number that means the slew rate is effectively instantaneous
            }
            

            double currentTime = WPIUtilJNI.now() * 1e-6;
            double elapsedTime = currentTime - m_prevTime;
            double angleDif = SwerveUtils.AngleDifference(inputTranslationDir, m_currentTranslationDir);
            if (angleDif < 0.45*Math.PI) {
                m_currentTranslationDir = SwerveUtils.StepTowardsCircular(m_currentTranslationDir, inputTranslationDir, directionSlewRate * elapsedTime);
                m_currentTranslationMag = m_magLimiter.calculate(inputTranslationMag);
            }
            else if (angleDif > 0.85*Math.PI) {
                if (m_currentTranslationMag > 1e-4) { //some small number to avoid floating-point errors with equality checking
                // keep currentTranslationDir unchanged
                m_currentTranslationMag = m_magLimiter.calculate(0.0);
                }
                else {
                m_currentTranslationDir = SwerveUtils.WrapAngle(m_currentTranslationDir + Math.PI);
                m_currentTranslationMag = m_magLimiter.calculate(inputTranslationMag);
                }
            }
            else {
                m_currentTranslationDir = SwerveUtils.StepTowardsCircular(m_currentTranslationDir, inputTranslationDir, directionSlewRate * elapsedTime);
                m_currentTranslationMag = m_magLimiter.calculate(0.0);
            }
            m_prevTime = currentTime;
            
            xSpeedCommanded = m_currentTranslationMag * Math.cos(m_currentTranslationDir);
            ySpeedCommanded = m_currentTranslationMag * Math.sin(m_currentTranslationDir);
            m_currentRotation = m_rotLimiter.calculate(rot);

        } else {
            xSpeedCommanded = xSpeed;
            ySpeedCommanded = ySpeed;
            m_currentRotation = rot;
        }

        // Convert the commanded speeds into the correct units for the drivetrain
        double xSpeedDelivered = xSpeedCommanded * DriveConstants.kMaxSpeedMetersPerSecond;
        double ySpeedDelivered = ySpeedCommanded * DriveConstants.kMaxSpeedMetersPerSecond;
        double rotDelivered = m_currentRotation * DriveConstants.kMaxAngularSpeed;

        SwerveModuleState[] swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(
            fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered, Rotation2d.fromDegrees(gyro.getYawDeg()))
                : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));
        SwerveDriveKinematics.desaturateWheelSpeeds(
            swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);
            
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

    public SwerveModulePosition[] getModulePositions() {
        return new SwerveModulePosition[] {
            frontLeft.getPosition(),
            frontRight.getPosition(),
            backLeft.getPosition(),
            backRight.getPosition()
        };
    }
}
