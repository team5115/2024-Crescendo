package frc.team5115.Classes.Software; 
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Constants.FieldConstants;
import frc.team5115.Constants.VisionConstants;

public class PhotonVision extends SubsystemBase{
     private PhotonCamera photonCameraF;
     private PhotonCamera photonCameraR;
     private PhotonCamera photonCameraB;
     private PhotonCamera photonCameraNA;
     static PhotonTrackedTarget target;

     public AprilTagFieldLayout fieldLayout;
     private PhotonPoseEstimator photonPoseEstimatorL;
     private PhotonPoseEstimator photonPoseEstimatorR;
     private PhotonPoseEstimator photonPoseEstimatorB;
     private PhotonPoseEstimator photonPoseEstimatorF;
     ArrayList<AprilTag> aprilTagList;
        
    public PhotonVision() {
        aprilTagList = new ArrayList<AprilTag>();

        //Left 
        photonCameraF = new PhotonCamera("Stereo_Vision_1");
        //Back camera
        photonCameraB = new PhotonCamera("OV5647");
        //Right camera 
        photonCameraR = new PhotonCamera("Mirosoft_LifeCam_Cinema");
        //Front camera
        photonCameraNA = new PhotonCamera("Microsoft_LifeCam_HD-3000");

        Transform3d robotToCam = new Transform3d(new Translation3d(0.5, 0.0, 0.5), new Rotation3d(0,0,0)); //Cam mounted facing forward, half a meter forward of center, half a meter up from center.

        // Add all the april tags
        
// Blue alliance amp station 
        aprilTagList.add(GenerateAprilTag(1, Units.inchesToMeters(593.68), Units.inchesToMeters(+9.68), Units.inchesToMeters(+53.38), (120))); // x, y, z, rotation
        aprilTagList.add(GenerateAprilTag(2, Units.inchesToMeters(+637.21), Units.inchesToMeters(+34.79), Units.inchesToMeters(+53.38), (120))); 

// Red alliance speaker
        aprilTagList.add(GenerateAprilTag(3, Units.inchesToMeters(+652.73), Units.inchesToMeters(+196.17), Units.inchesToMeters(+57.13),(180)));
        aprilTagList.add(GenerateAprilTag(4, Units.inchesToMeters(+652.73), Units.inchesToMeters(+218.42), Units.inchesToMeters(+57.13),(180)));

// Source on red alliance
        aprilTagList.add(GenerateAprilTag(5, Units.inchesToMeters(+578.77), Units.inchesToMeters(+323.00), Units.inchesToMeters(+53.38),(270)));

// Source on blue alliance
        aprilTagList.add(GenerateAprilTag(6, Units.inchesToMeters(+72.5), Units.inchesToMeters(+323.00), Units.inchesToMeters(+53.38), (270)));

// Blue alliance speaker
        aprilTagList.add(GenerateAprilTag(7, Units.inchesToMeters(-1.50), Units.inchesToMeters(+218.42), Units.inchesToMeters(+57.13), (000)));
        aprilTagList.add(GenerateAprilTag(8, Units.inchesToMeters(-1.50), Units.inchesToMeters(+196.17), Units.inchesToMeters(+57.13), (000)));

// Red alliance amp station
        aprilTagList.add(GenerateAprilTag(9, Units.inchesToMeters(+14.02), Units.inchesToMeters(+34.79), Units.inchesToMeters(+53.38), (60)));
        aprilTagList.add(GenerateAprilTag(10, Units.inchesToMeters(+57.54), Units.inchesToMeters(+9.68), Units.inchesToMeters(+53.38), (60)));

// Red alliance stage
        aprilTagList.add(GenerateAprilTag(11, Units.inchesToMeters(+468.69), Units.inchesToMeters(+146.19), Units.inchesToMeters(+52.00), (300)));
        aprilTagList.add(GenerateAprilTag(12, Units.inchesToMeters(+468.69), Units.inchesToMeters(+177.10), Units.inchesToMeters(+52.00), (60)));
        aprilTagList.add(GenerateAprilTag(13, Units.inchesToMeters(+441.74), Units.inchesToMeters(+161.62), Units.inchesToMeters(+52.00), (180)));

// Blue alliance stage
        aprilTagList.add(GenerateAprilTag(14, Units.inchesToMeters(+209.48), Units.inchesToMeters(+161.62), Units.inchesToMeters(+52.00), 000));
        aprilTagList.add(GenerateAprilTag(15, Units.inchesToMeters(+182.73), Units.inchesToMeters(+177.10), Units.inchesToMeters(+52.00), (120)));
        aprilTagList.add(GenerateAprilTag(16, Units.inchesToMeters(+182.73), Units.inchesToMeters(+146.19), Units.inchesToMeters(+52.00), (240)));


         fieldLayout = new AprilTagFieldLayout(aprilTagList, FieldConstants.length, FieldConstants.width);

               // PhotonposeEstimators constructors:
               PhotonPoseEstimator PhotonPoseEstimatorR = new PhotonPoseEstimator(fieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE,photonCameraR, robotToCam);

               PhotonPoseEstimator photonPoseEstimatorNA = new PhotonPoseEstimator(fieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, photonCameraF, robotToCam);

               PhotonPoseEstimator photonPoseEstimatorF = new PhotonPoseEstimator(fieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, photonCameraNA, robotToCam);

               PhotonPoseEstimator photonPoseEstimatorB = new PhotonPoseEstimator(fieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, photonCameraB, robotToCam);

      
         photonPoseEstimatorNA = new PhotonPoseEstimator(fieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, photonCameraF, VisionConstants.robotToCamL);
         PhotonPoseEstimatorR = new PhotonPoseEstimator(fieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, photonCameraR, VisionConstants.robotToCamR);
         photonPoseEstimatorB = new PhotonPoseEstimator(fieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, photonCameraR, VisionConstants.robotToCamR);
         photonPoseEstimatorF = new PhotonPoseEstimator(fieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, photonCameraR, VisionConstants.robotToCamR);




    }

    /**
 * 
 */

 
 public PhotonPipelineResult getResult(){
       
        return photonCameraF.getLatestResult(); 
 }
 
 
public Pose3d getBestPose(){
AprilTag target = new AprilTag(0, null);
var result = photonCameraF.getLatestResult(); 

 for(AprilTag i : aprilTagList){
                        if(i.ID == result.getBestTarget().getFiducialId()){
                                target = i;
                        }
                }
       return target.pose;
}




public Optional<PhotonTrackedTarget> getID7(){
        var result = photonCameraF.getLatestResult(); 
        var targets = result.getTargets();

        for (PhotonTrackedTarget i : targets){
                System.out.println(i.getFiducialId());
                if(i.getFiducialId() == 7) System.out.println("Matched");
                if(i.getFiducialId() == 7){
                        return Optional.of(i);
                }
                //edge case here where the id 7 goes under after the last check but before we extract targets
        }
        return Optional.empty();
}

public boolean isThereID7(){
        var result = photonCameraF.getLatestResult(); 
        var targets = result.getTargets();

        for (PhotonTrackedTarget i : targets){
                if(i.getFiducialId() == 7){
                        //System.out.println("True");
                        return true;
                }
        }
        return false;
}

public Optional<PhotonTrackedTarget> getID4(){
        var result = photonCameraF.getLatestResult(); 
        var targets = result.getTargets();

        for (PhotonTrackedTarget i : targets){
                if(i.getFiducialId() == 4){
                        return Optional.of(i);
                }
        }
        return Optional.empty();
}

public boolean isThereID4(){
        var result = photonCameraF.getLatestResult(); 
        var targets = result.getTargets();

        for (PhotonTrackedTarget i : targets){
                if(i.getFiducialId() == 4){
                        return true;
                }
        }
        return false;
}

 public boolean isTargetPresent(){
    return photonCameraF.getLatestResult().hasTargets();
 }

 public double getAngle(){

         var result = photonCameraF.getLatestResult(); 
        if (result.hasTargets()) return result.getBestTarget().getYaw() + VisionConstants.cameraYaw;
        return 0;
 }

// for specific IDs USE THESE

 public double getAngleID4(){
        var result = photonCameraF.getLatestResult(); 
        if(result.hasTargets()){
              if(isThereID4()){ 
         var PhotonVisionResult = getID4(); 
                if(PhotonVisionResult.isPresent()) return PhotonVisionResult.get().getYaw() + VisionConstants.cameraYaw;
        }
        return 0;
        }
        return 0;
}
 
 public double getAngleID7(){
        var result = photonCameraF.getLatestResult(); 
        if(result.hasTargets()){
                if(isThereID7()){ 
                        var PhotonVisionResult = getID7(); 
                        if(PhotonVisionResult.isPresent()) return PhotonVisionResult.get().getYaw() + VisionConstants.cameraYaw;
                }
                return 0;
        }
        return 0;
}
 
  

public double getRange(){
        var result = photonCameraF.getLatestResult(); 
            if (result.hasTargets()) { 
                int ID = -1;
                for(AprilTag i : aprilTagList){
                        if(i.ID == result.getBestTarget().getFiducialId()){
                                ID = result.getBestTarget().getFiducialId();  
                        }
                }
                // First calculate range
                double range =
                        PhotonUtils.calculateDistanceToTargetMeters(
                            VisionConstants.cameraPosY,
                            aprilTagList.get(ID-1).pose.getZ(),
                                Units.degreesToRadians(VisionConstants.cameraPitch),
                                Units.degreesToRadians(result.getBestTarget().getPitch())); 

                return (range);
                
                // Use this range as the measurement we give to the PID controller.
                // -1.0 required to ensure positive PID controller effort _increases_ range
        }

         return 0;

        // Use our forward/turn speeds to control the drivetrain
       // HardwareDrivetrain.drive(forwardSpeed, rotationSpeed, 0, true, );
       
    }

    public double getRangeID4(){
        var result = photonCameraF.getLatestResult(); 
        var p = result.getBestTarget();
            if (result.hasTargets()) { 
                int ID = -1;
                if(isThereID4()){
                p = getID4().get();
                for(PhotonTrackedTarget i : result.getTargets()){
                        if(i.getFiducialId() == 4){
                                ID = 4;
                        }
                }
                // First calculate range
                double range =
                        PhotonUtils.calculateDistanceToTargetMeters(
                                VisionConstants.cameraPosY,
                                aprilTagList.get(ID-1).pose.getZ(),
                                Units.degreesToRadians(VisionConstants.cameraPitch),
                                Units.degreesToRadians(p.getPitch())); 

                return (range);
        }
                // Use this range as the measurement we give to the PID controller.
                // -1.0 required to ensure positive PID controller effort _increases_ range
        }


         return 0;

        // Use our forward/turn speeds to control the drivetrain
       // HardwareDrivetrain.drive(forwardSpeed, rotationSpeed, 0, true, );
       
    }

      public double getRangeID7(){
        var result = photonCameraF.getLatestResult(); 
        var p = result.getBestTarget();
            if (result.hasTargets()) { 
                int ID = -1;
                if(isThereID7()){
                p= getID7().get();
                for(PhotonTrackedTarget i : result.getTargets()){
                        if(i.getFiducialId() == 7){
                                ID = 7;
                        }
                }
                // First calculate range
                double range =
                        PhotonUtils.calculateDistanceToTargetMeters(
                                VisionConstants.cameraPosY,
                                aprilTagList.get(ID-1).pose.getZ(),
                                Units.degreesToRadians(VisionConstants.cameraPitch),
                                Units.degreesToRadians(p.getPitch())); 
                System.out.println("Range: " + range);
                return (range);
        }
                // Use this range as the measurement we give to the PID controller.
                // -1.0 required to ensure positive PID controller effort _increases_ range
        }


         return 0;

        // Use our forward/turn speeds to control the drivetrain
       // HardwareDrivetrain.drive(forwardSpeed, rotationSpeed, 0, true, );
       
    }


   public double getBestID(){
        if(photonCameraF.getLatestResult().hasTargets()){ 
        double FidicualID = photonCameraF.getLatestResult().getBestTarget().getFiducialId();
        return (FidicualID);

        }
        return -1;
    }
    
    public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        // The team assignment of the first grid the robot looks at is the team assignment of the robot
        // otherwise if we cant see any april tags trust the team assignment inputted on shuffle board
        //Trusting the left camera more, no idea on how to use filters to get the most information out of both cameras 2-6-2022

        if(photonPoseEstimatorF.update().isPresent()) return photonPoseEstimatorF.update();
        return Optional.empty();

    }

    private AprilTag GenerateAprilTag(int id, double x, double y, double z, double rotationDegrees) {
        return new AprilTag(id, new Pose3d( x, y, z, new Rotation3d(0, 0, rotationDegrees)));
    }
}