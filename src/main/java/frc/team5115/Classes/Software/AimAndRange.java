package frc.team5115.Classes.Software; 

import frc.team5115.Constants;
import frc.team5115.Classes.Hardware.*;
import frc.team5115.Classes.Software.PhotonVision;
import org.photonvision.PhotonPoseEstimator;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.team5115.Constants.*;


import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;



public class AimAndRange extends SubsystemBase{
    HardwareDrivetrain j;
    NAVx gyro;
    PhotonVision photonVision;

    public AimAndRange(){  
        gyro = new NAVx();
        j = new HardwareDrivetrain(gyro);
        photonVision = new PhotonVision();
    }

    final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(24); // get measurments
    final double TARGET_HEIGHT_METERS = Units.feetToMeters(5); // get measurments

    // Angle between horizontal and the camera.
    final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);  // get measurments

    // How far from the target we want to be
    final double GOAL_RANGE_METERS = Units.feetToMeters(3); // change measurement based on gameplay

    PhotonCamera camera = new PhotonCamera("Mirosoft_LifeCam_Cinema");

    // PID constants should be tuned per robot
    final double LINEAR_P = 0.1;
    final double LINEAR_D = 0.0;
    PIDController forwardController = new PIDController(LINEAR_P, 0, LINEAR_D);

    final double ANGULAR_P = 0.1;
    final double ANGULAR_D = 0.0;
    PIDController turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);

    XboxController xboxController = new XboxController(0);



// Calculate robot's field relative pose
Pose3d robotPose = PhotonUtils.estimateFieldToRobotAprilTag(PhotonVision.target.getBestCameraToTarget(), photonVision.fieldLayout.getTagPose(PhotonVision.target.getFiducialId()), VisionConstants.robotToCamL);

// Calculate robot's field relative pose
Pose2D robotPose = PhotonUtils.estimateFieldToRobot(VisionConstants.cameraPosX, VisionConstants.cameraPosY, Rotation2d.fromDegrees(-PhotonVision.target.getYaw()), gyro.getRotation2d(), targetPose, VisionConstants.robotToCamL);

    @Override
    public void teleopPeriodic() { 
        double forwardSpeed;
        double rotationSpeed;

        if (xboxController.getAButton()) {
            // Vision-alignment mode
            // Query the latest result from PhotonVision
            var result = photonCameraF.getLatestResult(); 

            if (result.hasTargets()) { 
                // First calculate range
                double range =
                        PhotonUtils.calculateDistanceToTargetMeters(
                                CAMERA_HEIGHT_METERS,
                                TARGET_HEIGHT_METERS,
                                CAMERA_PITCH_RADIANS,
                                Units.degreesToRadians(result.getBestTarget().getPitch())); 

                // Use this range as the measurement we give to the PID controller.
                // -1.0 required to ensure positive PID controller effort _increases_ range
                forwardSpeed = -forwardController.calculate(range, GOAL_RANGE_METERS);

                // Also calculate angular power
                // -1.0 required to ensure positive PID controller effort _increases_ yaw
                rotationSpeed = -turnController.calculate(result.getBestTarget().getYaw(), 0); 
            } else {
                // If we have no targets, stay still.
                forwardSpeed = 0;
                rotationSpeed = 0;
            }
        } else {
            // Manual Driver Mode
            forwardSpeed = -xboxController.getRightY();
            rotationSpeed = xboxController.getLeftX();
        }

        // Use our forward/turn speeds to control the drivetrain
       // HardwareDrivetrain.drive(forwardSpeed, rotationSpeed, 0, true, );
        j.drive(forwardSpeed, rotationSpeed, 0, true, true);

        double distanceToTarget = PhotonUtils.getDistanceToPose(robotPose, targetPose);
    // Calculate a translation from the camera to the target.
        Translation2d translation = PhotonUtils.estimateCameraToTargetTranslation(distanceMeters, Rotation2d.fromDegrees(-target.getYaw()));

        Rotation2d targetYaw = PhotonUtils.getYawToPose(robotPose, targetPose);
    }


    private void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative, boolean rateLimit) {
       
    }
  


}
