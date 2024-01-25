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

    public void teleopPeriodic(double GOAL_RANGE_METERS) { 
        double forwardSpeed;
        double rotationSpeed;

        if (xboxController.getAButton()) {
            // Vision-alignment mode
            // Query the latest result from PhotonVision

        Pose3d robotPose = PhotonUtils.estimateFieldToRobotAprilTag(PhotonVision.target.getBestCameraToTarget(), photonVision.fieldLayout.getTagPose(PhotonVision.target.getFiducialId()), VisionConstants.robotToCamL);

        forwardSpeed = -forwardController.calculate(photonVision.getRange(), GOAL_RANGE_METERS);

        // Also calculate angular power
        // -1.0 required to ensure positive PID controller effort _increases_ yaw
        rotationSpeed = -turnController.calculate(photonVision.getResult().getBestTarget().getYaw(), 0); 
    } 
 else {
    // Manual Driver Mode
    forwardSpeed = -xboxController.getRightY();
    rotationSpeed = xboxController.getLeftX();
}


 j.drive(forwardSpeed, rotationSpeed, 0, true, true);

        double distanceToTarget = PhotonUtils.getDistanceToPose(robotPose, targetPose);
    // Calculate a translation from the camera to the target.
        Translation2d translation = PhotonUtils.estimateCameraToTargetTranslation(distanceMeters, Rotation2d.fromDegrees(-target.getYaw()));

        Rotation2d targetYaw = PhotonUtils.getYawToPose(robotPose, targetPose);


}
}
