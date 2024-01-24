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

    @Override
    public void teleopPeriodic() { 
        double forwardSpeed;
        double rotationSpeed;

        Pose3d robotPose = PhotonUtils.estimateFieldToRobotAprilTag(PhotonVision.target.getBestCameraToTarget(), photonVision.fieldLayout.getTagPose(PhotonVision.target.getFiducialId()), VisionConstants.robotToCamL);

        if (xboxController.getAButton()) {
            // Vision-alignment mode
            // Query the latest result from PhotonVision
            
    }



}
