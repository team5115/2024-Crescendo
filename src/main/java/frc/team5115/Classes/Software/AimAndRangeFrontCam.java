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



public class AimAndRangeFrontCam extends SubsystemBase{
    HardwareDrivetrain hd;
   // Drivetrain d;
    NAVx gyro;
    PhotonVision photonVision;
    int x = 0;

    
    public AimAndRangeFrontCam(HardwareDrivetrain hd, PhotonVision photonVision){  
        this.hd = hd;
        this.photonVision = photonVision;


    }


    PhotonCamera camera = new PhotonCamera("Microsoft_LifeCam_HD-3000");

    // PID constants should be tuned per robot
    final double LINEAR_P = 0.03;
    final double LINEAR_D = 0.0;
    PIDController forwardController = new PIDController(LINEAR_P, 0, LINEAR_D);

    final double ANGULAR_P = 0.0025;
    final double ANGULAR_D = 0.0;
    PIDController turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);




// Calculate robot's field relative pose

public void periodicIDBased() { 
    double forwardSpeed = 0;
    double rotationSpeed = 0;
    double GOAL_RANGE_METERS = 0.3; // cam height = 0.68
    

    // Vision-alignment mode
        // Query the latest result from PhotonVision

    if(photonVision.isTargetPresent()){

    //Pose3d robotPose = PhotonUtils.estimateFieldToRobotAprilTag(PhotonVision.target.getBestCameraToTarget(), photonVision.j2F(), VisionConstants.robotToCamL.times(-1));
    if(photonVision.isThereID4()){
    forwardSpeed = forwardController.calculate(photonVision.getRangeID4(), GOAL_RANGE_METERS);

    // Also calculate angular power
    // -1.0 required to ensure positive PID controller effort _increases_ yaw
        rotationSpeed = -turnController.calculate(photonVision.getAngleID4(), 0); 

        hd.drive(forwardSpeed, 0, rotationSpeed, false, false);
        
    }
    else if(photonVision.isThereID7()){
    forwardSpeed = forwardController.calculate(photonVision.getRangeID7(), GOAL_RANGE_METERS);
    System.out.println("Range: " + photonVision.getRangeID7());
    // Also calculate angular power
    // -1.0 required to ensure positive PID controller effort _increases_ yaw
        rotationSpeed = -turnController.calculate(photonVision.getAngleID7(), 0); 
        hd.drive(forwardSpeed, 0, rotationSpeed, false, false);
    }
    else{
    forwardSpeed = 100000;
    rotationSpeed = 10000;
        hd.drive(0, 0, 0, true, false);             
    }
}
 else{
    forwardSpeed = 100000;
    rotationSpeed = 10000;
   // hd.drive(0, 0, 0, true, false); 
 }

}
    
    public boolean isFinished(double[] i){ 

         
        if(Math.abs(i[0]) <= 0.75){
            if(Math.abs(i[1]) <= 1)
            return true;
        }

     return false;

    }

}