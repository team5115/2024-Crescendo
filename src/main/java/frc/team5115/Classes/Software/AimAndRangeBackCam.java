package frc.team5115.Classes.Software; 

import frc.team5115.Constants;
import frc.team5115.Classes.Hardware.*;
import frc.team5115.Classes.Software.PhotonVision;
import org.photonvision.PhotonPoseEstimator;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.PoseEstimator;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Software.Drivetrain;

import frc.team5115.Constants.*;
import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;



public class AimAndRangeBackCam extends SubsystemBase{
    HardwareDrivetrain j;
    Drivetrain d;
    NAVx gyro;
    PhotonVision photonVision;
    int x = 0;

    
    public AimAndRangeBackCam(Drivetrain d){  
        gyro = new NAVx();
        j = new HardwareDrivetrain(gyro);
        photonVision = new PhotonVision();
        x=1;
        this.d = d;

    }


    PhotonCamera camera = new PhotonCamera("OV5647");

    // PID constants should be tuned per robot
    final double LINEAR_P = 0.3;
    final double LINEAR_D = 0.0;
    PIDController forwardController = new PIDController(LINEAR_P, 0, LINEAR_D);

    final double ANGULAR_P = 0.3;
    final double ANGULAR_D = 0.0;
    PIDController turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);

    XboxController xboxController = new XboxController(0);



// Calculate robot's field relative pose
   @Override
    public void periodic() { 
        double forwardSpeed;
        double rotationSpeed;
        double GOAL_RANGE_METERS = 1.6;
        
        if (xboxController.getAButton()) {
            // Vision-alignment mode
            // Query the latest result from PhotonVision

        if(photonVision.isTargetPresent()){
                            
        Pose3d robotPose = PhotonUtils.estimateFieldToRobotAprilTag(PhotonVision.target.getBestCameraToTarget(), photonVision.j2B(), VisionConstants.robotToCamL.times(-1));
        forwardSpeed = -forwardController.calculate(photonVision.getRange(), GOAL_RANGE_METERS);

        // Also calculate angular power
        // -1.0 required to ensure positive PID controller effort _increases_ yaw
        rotationSpeed = -turnController.calculate(photonVision.getAngleB(), 0); 
        }
        else{
        forwardSpeed = -xboxController.getRightY();
        rotationSpeed = xboxController.getLeftX();
        }
    } 
    else {
        // Manual Driver Mode
        forwardSpeed = -xboxController.getRightY();
        rotationSpeed = xboxController.getLeftX();
    }


    j.drive(forwardSpeed, rotationSpeed, 0, true, true);

        Pose3d y = photonVision.j2B();
        Pose2d x = new Pose2d(y.getX(), y.getY(), new Rotation2d(y.getRotation().getZ()));

        double distanceToTarget = PhotonUtils.getDistanceToPose(d.getEstimatedPose(), x);
    // Calculate a translation from the camera to the target.
        Translation2d translation = PhotonUtils.estimateCameraToTargetTranslation(GOAL_RANGE_METERS, Rotation2d.fromDegrees(-PhotonVision.target.getYaw()));

        Rotation2d targetYaw = PhotonUtils.getYawToPose(d.getEstimatedPose(), x);

        


}
}
