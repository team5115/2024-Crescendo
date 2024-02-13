package frc.team5115;

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

public class Constants{

    public static final boolean MECANUM = false; 
  
    //motor ids
    public static final byte FRONT_LEFT_MOTOR_ID = 4;
    public static final byte FRONT_RIGHT_MOTOR_ID = 22;
    public static final byte BACK_LEFT_MOTOR_ID = 24;
    public static final byte BACK_RIGHT_MOTOR_ID = 26;

    public static final byte FRONT_LEFT_TURN_MOTOR_ID = 29;
    public static final byte FRONT_RIGHT_TURN_MOTOR_ID = 28;
    public static final byte BACK_LEFT_TURN_MOTOR_ID = 23;
    public static final byte BACK_RIGHT_TURN_MOTOR_ID = 25;

    public static final byte INTAKE_MOTOR_ID = 32;
    public static final byte ARM_LEFT_MOTOR_ID = 3;
    public static final byte ARM_RIGHT_MOTOR_ID = 33;
    public static final byte SHOOTER_CLOCKWISE_MOTOR_ID = 27;
    public static final byte SHOOTER_COUNTERCLOCKWISE_MOTOR_ID = 20;
    public static final byte CLIMBER_LEFT_MOTOR_ID = 30;
    public static final byte CLIMBER_RIGHT_MOTOR_ID = 31;

    public static final double TALON_ENCODER_CALIBRATION = (63.837/4104.5);
    public static final double NEO_VELOCITY_CALIBRATION = (0.47877871986/(60*10.71));
    public static final double NEO_DISTANCE_CALIBRATION = (0.47877871986/(10.71));
    public static final double MAX_VOLTAGE = 12;

    //X-Box
    public static final byte JOY_X_AXIS_ID = 0;
    public static final byte JOY_Y_AXIS_ID = 1;
    public static final byte JOY_Z_AXIS_ID = 4; 

    // 2023 Feedforward
    public static final double kS = 0.18296; 
    public static final double kV = 4.2023;
    public static final double kA = 0.28613;

    public static final double DRIVE_MOTOR_MAX_VOLTAGE = 12;

    public static final byte LEFT_SENSOR_ID = 0;
    public static final byte RIGHT_SENSOR_ID = 1;

    public static final double TARGET_ANGLE = 1;

    // Copyright (c) FIRST and other WPILib contributors.
    // Open Source Software; you can modify and/or share it under the terms of
    // the WPILib BSD license file in the root directory of this project.

  public static final class DriveConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double kMaxSpeedMetersPerSecond = 4.8;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second

    public static final double kDirectionSlewRate = 1.2; // radians per second
    public static final double kMagnitudeSlewRate = 1.8; // percent per second (1 = 100%)
    public static final double kRotationalSlewRate = 2.0; // percent per second (1 = 100%)

    // Chassis configuration
    public static final double kTrackWidth = Units.inchesToMeters(23.75);
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = Units.inchesToMeters(23.75);
    // Distance between front and back wheels on robot
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2), // front left
        new Translation2d(kWheelBase / 2, kTrackWidth / -2), // front right
        new Translation2d(kWheelBase / -2, kTrackWidth / 2), // back left
        new Translation2d(kWheelBase / -2, kTrackWidth / -2)); // back right

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kBackLeftChassisAngularOffset = Math.PI;
    public static final double kBackRightChassisAngularOffset = Math.PI / 2;

    // SPARK MAX CAN IDs
    public static final int kFrontLeftDrivingCanId = 11;
    public static final int kRearLeftDrivingCanId = 13;
    public static final int kFrontRightDrivingCanId = 15;
    public static final int kRearRightDrivingCanId = 17;

    public static final int kFrontLeftTurningCanId = 10;
    public static final int kRearLeftTurningCanId = 12;
    public static final int kFrontRightTurningCanId = 14;
    public static final int kRearRightTurningCanId = 16;

    public static final boolean kGyroReversed = false;


  }
    public static class SwerveConstants{
        public static final int DrivingMotorPinionTeeth = 13;

        // Invert the turning encoder, since the output shaft rotates in the opposite direction of
        // the steering motor in the MAXSwerve Module.
        public static final boolean TurningEncoderInverted = true;
    
        // Calculations required for driving motor conversion factors and feed forward
        public static final double WheelDiameterMeters = Units.inchesToMeters(3);
        public static final double WheelCircumferenceMeters = WheelDiameterMeters * Math.PI;
        // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15 teeth on the bevel pinion
        public static final double DrivingMotorReduction = (45.0 * 22) / (DrivingMotorPinionTeeth * 15);
    
        public static final double DrivingEncoderPositionFactor = (WheelDiameterMeters * Math.PI)
            / DrivingMotorReduction; // meters
        public static final double DrivingEncoderVelocityFactor = ((WheelDiameterMeters * Math.PI)
            / DrivingMotorReduction) / 60.0; // meters per second
    
        public static final double TurningEncoderPositionFactor = (2 * Math.PI); // radians
        public static final double TurningEncoderVelocityFactor = (2 * Math.PI) / 60.0; // radians per second
    
        public static final double TurningEncoderPositionPIDMinInput = 0; // radians
        public static final double TurningEncoderPositionPIDMaxInput = TurningEncoderPositionFactor; // radians
    
        public static final double DrivingP = 0.04;
        public static final double DrivingI = 0;
        public static final double DrivingD = 0;
        public static final double DrivingFF = 1;
        public static final double DrivingMinOutput = -1;
        public static final double DrivingMaxOutput = 1;
    
        public static final double TurningP = 1;
        public static final double TurningI = 0;
        public static final double TurningD = 0;
        public static final double TurningFF = 0;
        public static final double TurningMinOutput = -1;
        public static final double TurningMaxOutput = 1;
    
        public static final IdleMode DrivingMotorIdleMode = IdleMode.kBrake;
        public static final IdleMode TurningMotorIdleMode = IdleMode.kBrake;
    
        public static final int DrivingMotorCurrentLimit = 50; // amps
        public static final int TurningMotorCurrentLimit = 20; // amps 
    }

    // Photon vision constants    
    public static class FieldConstants {
        public static final double length = Units.feetToMeters(54);
        public static final double width = Units.feetToMeters(27);
        public static final double startX = 7;
        public static final double startY = -0; 
        public static final double startAngleDeg = 0;
        public static final Rotation2d startAngle = Rotation2d.fromDegrees(startAngleDeg);
    }


    public static class VisionConstants {
        public static final String leftCameraName = "HD_USB_Camera";
        public static final String rightCameraName = "Microsoft_LifeCam_HD-3000";
        public static final String frontCameraName = "OV5647";

        private static final double cameraPosX = 0.277813055626;
        private static final double cameraPosY = 0.346869443739;
        private static final double cameraPosZ = 0.0889001778004;
        private static final double cameraRoll = 0.0;
        private static final double cameraPitch = 158.0;
        private static final double cameraYaw = 22.5;

        public static final Transform3d robotToCamL = new Transform3d( new Translation3d(-cameraPosX, -cameraPosY, cameraPosZ), new Rotation3d(cameraRoll, cameraPitch, +cameraYaw)); 
        public static final Transform3d robotToCamR = new Transform3d( new Translation3d(+cameraPosX, -cameraPosY, cameraPosZ), new Rotation3d(cameraRoll, cameraPitch, -cameraYaw)); 
        
    }


}