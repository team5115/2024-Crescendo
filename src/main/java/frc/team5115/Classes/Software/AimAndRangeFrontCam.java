// package frc.team5115.Classes.Software; 

// import frc.team5115.Constants;
// import frc.team5115.Classes.Hardware.*;

// import org.photonvision.PhotonPoseEstimator;
// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.math.estimator.PoseEstimator;
// import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Pose3d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.geometry.Transform3d;
// import edu.wpi.first.math.geometry.Pose3d;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
// import edu.wpi.first.math.util.Units;
// import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.team5115.Constants.*;
// import java.util.Optional;

// import org.photonvision.PhotonCamera;
// import org.photonvision.PhotonUtils;



// public class AimAndRangeFrontCam extends SubsystemBase{
//     HardwareDrivetrain j;
//     Drivetrain d;
//     NAVx gyro;
//     PhotonVision photonVision;
//     int x = 0;
//     double GOAL_RANGE_METERS = 1.6;


    
//     public AutoAimAndRange(Drivetrain d){  
//         gyro = new NAVx();
//         j = new HardwareDrivetrain(gyro);
//         photonVision = new PhotonVision();
//         x=1;
//         this.d = d;

//     }


//     PhotonCamera camera = new PhotonCamera("Microsoft_LifeCam_HD-3000");

//     // PID constants should be tuned per robot
//     final double LINEAR_P = 0.3;
//     final double LINEAR_D = 0.0;
//     PIDController forwardController = new PIDController(LINEAR_P, 0, LINEAR_D);

//     final double ANGULAR_P = 0.3;
//     final double ANGULAR_D = 0.0;
//     PIDController turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);


//     @Override
//         public void initialize(){ 

//     }



// // Calculate robot's field relative pose
//    @Override
//     public void execute() { 
//         double forwardSpeed;
//         double rotationSpeed;
//         double GOAL_RANGE_METERS = 1.6;
        
//             // Vision-alignment mode
//             // Query the latest result from PhotonVision

//         if(photonVision.isTargetPresent()){
                            
//         Pose3d robotPose = PhotonUtils.estimateFieldToRobotAprilTag(PhotonVision.target.getBestCameraToTarget(), photonVision.j2F(), VisionConstants.robotToCamL.times(-1));
//         forwardSpeed = -forwardController.calculate(photonVision.getRangeF(), GOAL_RANGE_METERS);

//         // Also calculate angular power
//         // -1.0 required to ensure positive PID controller effort _increases_ yaw
//         rotationSpeed = -turnController.calculate(photonVision.getAngle(), 0); 
//         }
//         else{
//         forwardSpeed = -10;
//         rotationSpeed = 10;
//         }

//     j.drive(forwardSpeed, rotationSpeed, 0, true, true);

//         Pose3d y = photonVision.j2F();
//         Pose2d x = new Pose2d(y.getX(), y.getY(), new Rotation2d(y.getRotation().getZ()));

//         double distanceToTarget = PhotonUtils.getDistanceToPose(d.getEstimatedPose(), x);
//     // Calculate a translation from the camera to the target.
//         Translation2d translation = PhotonUtils.estimateCameraToTargetTranslation(GOAL_RANGE_METERS, Rotation2d.fromDegrees(-PhotonVision.target.getYaw()));

//         Rotation2d targetYaw = PhotonUtils.getYawToPose(d.getEstimatedPose(), x);



// }
//         @Override
//             public boolean isFinished() {

//                 if((GOAL_RANGE_METERS - photonVision.getRangeF()) < 0.1)
//                 return true;
                
//         return false;
//         }

// }