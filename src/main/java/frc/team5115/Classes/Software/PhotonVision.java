package frc.team5115.Classes.Software; 
import java.util.ArrayList;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Constants.*;

public class PhotonVision extends SubsystemBase{
     private PhotonCamera photonCameraL;
     private PhotonCamera photonCameraR;
     private PhotonCamera photonCameraB;
     private PhotonCamera photonCameraF;

     private PhotonPoseEstimator photonPoseEstimatorL;
     private PhotonPoseEstimator photonPoseEstimatorR;
     private PhotonPoseEstimator photonPoseEstimatorB;
     private PhotonPoseEstimator photonPoseEstimatorF;
        
    public PhotonVision() {
        ArrayList<AprilTag> aprilTagList = new ArrayList<AprilTag>();

        //Left Camera
        photonCameraL = new PhotonCamera("Stereo_Vision_1");
        // Back camera
        photonCameraB = new PhotonCamera("limelight");
        //Right camera 
        photonCameraR = new PhotonCamera("Microsoft_LifeCam_HD-3000");
        // Front camera
        photonCameraF = new PhotonCamera("Mirosoft_LifeCam_Cinema");

        Transform3d robotToCam = new Transform3d(new Translation3d(0.5, 0.0, 0.5), new Rotation3d(0,0,0)); //Cam mounted facing forward, half a meter forward of center, half a meter up from center.

        // Add all the april tags
        
// Blue alliance amp station 
        aprilTagList.add(GenerateAprilTag(1, +593.68, +9.68, +53.38, 120)); // x, y, z, rotation
        aprilTagList.add(GenerateAprilTag(2, +637.21, +34.79, +53.38, 120)); 

// Red alliance speaker
        aprilTagList.add(GenerateAprilTag(3, +652.73, +196.17, +57.13, 180));
        aprilTagList.add(GenerateAprilTag(4, +652.73, +218.42, +57.13, 180));

// Source on red alliance
        aprilTagList.add(GenerateAprilTag(5, +578.77, +323.00, +53.38, 270));

// Source on blue alliance
        aprilTagList.add(GenerateAprilTag(6, +72.5, +323.00, +53.38, 270));

// Blue alliance speaker
        aprilTagList.add(GenerateAprilTag(7, -1.50, +218.42, +57.13, 000));
        aprilTagList.add(GenerateAprilTag(8, -1.50, +196.17, +57.13, 000));

// Red alliance amp station
        aprilTagList.add(GenerateAprilTag(9, +14.02, +34.79, +53.38, 60));
        aprilTagList.add(GenerateAprilTag(10, +57.54, +9.68, +53.38, 60));

// Red alliance stage
        aprilTagList.add(GenerateAprilTag(11, +468.69, +146.19, +52.00, 300));
        aprilTagList.add(GenerateAprilTag(12, +468.69, +177.10, +52.00, 60));
        aprilTagList.add(GenerateAprilTag(13, +441.74, +161.62, +52.00, 180));

// Blue alliance stage
        aprilTagList.add(GenerateAprilTag(14, +209.48, +161.62, +52.00, 000));
        aprilTagList.add(GenerateAprilTag(15, +182.73, +177.10, +52.00, 120));
        aprilTagList.add(GenerateAprilTag(16, +182.73, +146.19, +52.00, 240));


        AprilTagFieldLayout fieldLayout = new AprilTagFieldLayout(aprilTagList, FieldConstants.length, FieldConstants.width);

               // PhotonposeEstimators constructors:
               PhotonPoseEstimator PhotonPoseEstimatorR = new PhotonPoseEstimator(fieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE,photonCameraR, robotToCam);

               PhotonPoseEstimator photonPoseEstimatorL = new PhotonPoseEstimator(fieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, photonCameraL, robotToCam);

               PhotonPoseEstimator photonPoseEstimatorF = new PhotonPoseEstimator(fieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, photonCameraF, robotToCam);

               PhotonPoseEstimator photonPoseEstimatorB = new PhotonPoseEstimator(fieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, photonCameraB, robotToCam);

      
         photonPoseEstimatorL = new PhotonPoseEstimator(fieldLayout, PoseStrategy.AVERAGE_BEST_TARGETS, photonCameraL, VisionConstants.robotToCamL);
         PhotonPoseEstimatorR = new PhotonPoseEstimator(fieldLayout, PoseStrategy.AVERAGE_BEST_TARGETS, photonCameraR, VisionConstants.robotToCamR);
         photonPoseEstimatorB = new PhotonPoseEstimator(fieldLayout, PoseStrategy.AVERAGE_BEST_TARGETS, photonCameraR, VisionConstants.robotToCamR);
         photonPoseEstimatorF = new PhotonPoseEstimator(fieldLayout, PoseStrategy.AVERAGE_BEST_TARGETS, photonCameraR, VisionConstants.robotToCamR);


    }

    public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        // The team assignment of the first grid the robot looks at is the team assignment of the robot
        // otherwise if we cant see any april tags trust the team assignment inputted on shuffle board
        //Trusting the left camera more, no idea on how to use filters to get the most information out of both cameras 2-6-2022
        if(photonPoseEstimatorL.update().isPresent()) return photonPoseEstimatorL.update();
        return Optional.empty();

      /*   if(photonPoseEstimatorR.update().isPresent()) return photonPoseEstimatorR.update();
        return Optional.empty();

        if(photonPoseEstimatorB.update().isPresent()) return photonPoseEstimatorB.update();
        return Optional.empty();

        if(photonPoseEstimatorF.update().isPresent()) return photonPoseEstimatorF.update();
        return Optional.empty();
        */
    }

    private AprilTag GenerateAprilTag(int id, double x, double y, double z, double rotationDegrees) {
        return new AprilTag( id, new Pose3d( new Pose2d( x, y, Rotation2d.fromDegrees(rotationDegrees))));
    }
}