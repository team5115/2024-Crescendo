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



public class AutoAimAndRange extends SubsystemBase{
    HardwareDrivetrain j;
    Drivetrain d;
    NAVx gyro;
    PhotonVision photonVision;
    int x = 0;

    
    public AutoAimAndRange(HardwareDrivetrain j, PhotonVision photonVision){  
        this.j = j;
        this.photonVision = photonVision;
        x=1;


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

    public double[] periodic1() { 
        double forwardSpeed = 0;
        double rotationSpeed = 0;
        double GOAL_RANGE_METERS = 1.6;
        
    //    if (xboxController.getAButton()) {
            // Vision-alignment mode
            // Query the latest result from PhotonVision

        if(photonVision.isTargetPresent()){
                            
        //Pose3d robotPose = PhotonUtils.estimateFieldToRobotAprilTag(PhotonVision.target.getBestCameraToTarget(), photonVision.j2F(), VisionConstants.robotToCamL.times(-1));
        forwardSpeed = forwardController.calculate(photonVision.getRange(), GOAL_RANGE_METERS);

        // Also calculate angular power
        // -1.0 required to ensure positive PID controller effort _increases_ yaw
        rotationSpeed = -turnController.calculate(photonVision.getAngle(), 0); 
        //System.out.println(photonVision.getRange());
     
    



       j.drive(forwardSpeed, 0, rotationSpeed, true, false);
     }

     else{
        forwardSpeed = 0;
        rotationSpeed = 0;
        j.drive(0, 0, 0, true, false); 
     }

     double[] x = {forwardSpeed/0.03, rotationSpeed/0.0025};

     return x;

    }

     double[] periodic1 = periodic1();
    
    public boolean isFinished(double[] i){ 

        if(Math.abs(i[0]) <= 0.1){
            if(Math.abs(i[1]) <= 0.1)
            return true;
        }

     return false;

    }


     /*     Pose3d y = photonVision.j2F();
        Pose2d x = new Pose2d(y.getX(), y.getY(), new Rotation2d(y.getRotation().getZ()));

        double distanceToTarget = PhotonUtils.getDistanceToPose(d.getEstimatedPose(), x);

    // Calculate a translation from the camera to the target.
       Translation2d translation = PhotonUtils.estimateCameraToTargetTranslation(GOAL_RANGE_METERS, Rotation2d.fromDegrees(-PhotonVision.target.getYaw()));

        Rotation2d targetYaw = PhotonUtils.getYawToPose(d.getEstimatedPose(), x);
}
}
*/


}