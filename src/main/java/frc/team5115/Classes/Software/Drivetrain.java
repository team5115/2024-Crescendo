package frc.team5115.Classes.Software;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Constants.DriveConstants;
import frc.team5115.Classes.Hardware.HardwareDrivetrain;
import frc.team5115.Classes.Hardware.NAVx;
import com.pathplanner.lib.util.*;
import frc.team5115.Commands.Auto.*;
import frc.team5115.Constants.DriveConstants;

public class Drivetrain extends SubsystemBase {
    private final HardwareDrivetrain hardwareDrivetrain;
    private final NAVx navx;
    HolonomicPathFollowerConfig x;
    private final HolonomicDriveController holonomicDriveController;
    public AutoBuilder autoBuilder;
    private SwerveDrivePoseEstimator poseEstimator;
   
    public Drivetrain(HardwareDrivetrain hardwareDrivetrain, PhotonVision photonVision, NAVx navx, AutoBuilder autoBuilder) {
        this.photonVision = photonVision;
        this.hardwareDrivetrain = hardwareDrivetrain;
        this.navx = navx;
        this.autoBuilder = autoBuilder;
        x = new HolonomicPathFollowerConfig(new PIDConstants(1, 0, 0),
            new PIDConstants(1, 0, 0),
       DriveConstants.kMaxSpeedMetersPerSecond, (DriveConstants.kTrackWidth/2),  new ReplanningConfig());
        // ? do we need to tune the pid controllers for the holonomic drive controller?
        holonomicDriveController = new HolonomicDriveController(
            new PIDController(1, 0, 0),
            new PIDController(1, 0, 0),
            new ProfiledPIDController(1, 0, 0,
                new TrapezoidProfile.Constraints(6.28, 3.14))); 
    }

    public void init() {
        poseEstimator = new SwerveDrivePoseEstimator(
            DriveConstants.kDriveKinematics,
            navx.getYawRotation2D(),
            hardwareDrivetrain.getModulePositions(),
            getStartingPoseGuess());
            addVisionMeasurement();

        System.out.println("Angle from navx" + navx.getYawDeg());
    }

    private void addVisionMeasurement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addVisionMeasurement'");
    }

    private Pose2d getStartingPoseGuess() {
            return new Pose2d();
    }



    /**
	 * Sets the encoder values to 0.
	 */
    public void resetEncoders() {
        navx.resetNAVx();
        hardwareDrivetrain.resetEncoders();
    }
    
    public void SwerveDrive(double forward, double turn, double right, boolean rookieMode, boolean fieldOriented){

        if(Math.abs(forward)< 0.1){
            forward = 0;
        }
        if(Math.abs(turn)< 0.1){
            turn = 0;
        }
        if(Math.abs(right)< 0.1){
            right = 0;
        }        
        if(rookieMode){
            right *= 0.1;
            turn *= 0.1;
            forward *= -0.1;
        }else{
            right *= 0.2;
            turn *= 0.2;
            forward *= -0.2;
        }
        hardwareDrivetrain.drive(forward, right, turn, fieldOriented, false);
    }

	/**
	 * Updates the odometry of the robot.
     * should run every robot tick
	 */
    public void updateOdometry() {
        poseEstimator.update(navx.getYawRotation2D(), hardwareDrivetrain.getModulePositions());

        Optional<EstimatedRobotPose> result = photonVision.getEstimatedGlobalPose(poseEstimator.getEstimatedPosition());
        if (result.isPresent()) {
            EstimatedRobotPose camPose = result.get();
            poseEstimator.addVisionMeasurement(camPose.estimatedPose.toPose2d(), camPose.timestampSeconds);
            System.out.println("vision is really working");
        }
    }

    public void resetPose(Pose2d x){
        Pose2d pose1 = x;
        poseEstimator.resetPosition(pose1.getRotation(), hardwareDrivetrain.getModulePositions(), pose1);
    }

//What should it return?

    public Command pathplanner(){
            //put stuff in
            AutoBuilder.configureHolonomic(poseEstimator::getEstimatedPosition, this::resetPose, hardwareDrivetrain::getChassisSpeeds, hardwareDrivetrain::setWheelSpeeds, x, hardwareDrivetrain::isRed, hardwareDrivetrain);
            return autoBuilder.getAutonomousCommand();
    }


	/**
	 * @return The estimated pose of the robot based on vision measurements COMBINED WITH drive motor measurements
	 */
    public Pose2d getEstimatedPose() {
        return poseEstimator.getEstimatedPosition();
    }

    public void followTrajectoryState(Trajectory trajectory, double time) {
        Trajectory.State goal = trajectory.sample(time);
        ChassisSpeeds adjustedSpeeds = holonomicDriveController.calculate(getEstimatedPose(), goal, goal.poseMeters.getRotation());
        SwerveModuleState[] moduleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(adjustedSpeeds);
        hardwareDrivetrain.setModuleStates(moduleStates);
    }

    public void stop() {
        hardwareDrivetrain.drive(0, 0, 0, false, false);
    }

	/**
	 * Resets the NAVx.
	 */
    public void resetNAVx(){
        navx.resetNAVx();
    }

	/**
	 * @return The current pitch in degrees, given by the NAVx
	 */
    public double getPitchDeg() {
        return navx.getPitchDeg();
    }

	/**
	 * @return The current yaw in degrees, given by the NAVx
	 */
    public double getYawDeg() {
        return navx.getPitchDeg();
    }

}
