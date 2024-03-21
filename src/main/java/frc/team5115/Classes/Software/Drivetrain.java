package frc.team5115.Classes.Software;

import org.photonvision.PhotonPoseEstimator;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.estimator.PoseEstimator;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Constants.DriveConstants;
import frc.team5115.Classes.Hardware.HardwareDrivetrain;
import frc.team5115.Classes.Hardware.NAVx;

public class Drivetrain extends SubsystemBase {
    private final HardwareDrivetrain hardwareDrivetrain;
    private final NAVx navx;
    private final HolonomicDriveController holonomicDriveController;
    private SwerveDrivePoseEstimator poseEstimator;
    private final PhotonVision photonVision;
   

    public Drivetrain(HardwareDrivetrain hardwareDrivetrain, NAVx navx, PhotonVision photonVision) {
        this.hardwareDrivetrain = hardwareDrivetrain;
        this.photonVision = photonVision;
        this.navx = navx;
        // ? do we need to tune the pid controllers for the holonomic drive controller?
        holonomicDriveController = new HolonomicDriveController(
            new PIDController(1, 0, 0),
            new PIDController(1, 0, 0),
            new ProfiledPIDController(1, 0, 0,
            new TrapezoidProfile.Constraints(6.28, 3.14)));    
            AutoBuilder.configureHolonomic(
            this::getEstimatedPose,
            this::resetPose,
            hardwareDrivetrain::getChassisSpeeds,
            hardwareDrivetrain::driveChassisSpeeds,
            new HolonomicPathFollowerConfig(
                new PIDConstants(0.005, 0.0, 0.0),
                new PIDConstants(0.05, 0.0, 0.0),
                2,
                DriveConstants.kRobotRadius,
                new ReplanningConfig()
            ),
            () -> {
          // Boolean supplier that controls when the path will be mirrored for the red alliance
          // This will flip the path being followed to the red side of the field.
          // THE ORIGIN WILL REMAIN ON THE BLUE SIDE
                    return false; // TODO make it do flipping for when at comp?
        //   var alliance = DriverStation.getAlliance();
        //   if (alliance.isPresent()) {
        //       return alliance.get() == DriverStation.Alliance.Red;
        //   }
        //   return false;
        },
            this
        );
            }
    


    public void init() {        
            poseEstimator = new SwerveDrivePoseEstimator(
                DriveConstants.kDriveKinematics,
                navx.getYawRotation2D(),
                hardwareDrivetrain.getModulePositions(),
                getStartingPoseGuess()
            );
        resetPose(getStartingPoseGuess());

        
        
        System.out.println("Angle from navx" + navx.getYawDeg());
    }

    private Pose2d getStartingPoseGuess() {
        final double x = 1.35;
        final double y = 5.55;
        return new Pose2d(new Translation2d(x, y), Rotation2d.fromDegrees(0));
    }

    /**
	 * Sets the encoder values to 0.
	 */
    public void resetEncoders() {
        navx.resetNAVx();
        hardwareDrivetrain.resetEncoders();
    }
    
    public void SwerveDrive(double forward, double turn, double right, boolean rookieMode, boolean fieldOriented, double angle){

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
            forward *= 0.1;
        }else{

        }

        if (!fieldOriented) {
            forward *= -1;
            right *= -1;
        }

        hardwareDrivetrain.drive(forward, right, turn, fieldOriented, false, angle);
    }

public void SwerveDrive(double forward, double turn, double right, boolean rookieMode, boolean fieldOriented){
        SwerveDrive(forward, turn, right, rookieMode, fieldOriented, 0);
    }

    public void driveTranslationBySpeeds(double xSpeed, double ySpeed) {
        hardwareDrivetrain.driveBySpeeds(xSpeed, ySpeed);
    }

	/**
	 * Updates the odometry of the robot.
     * should run every robot tick
	 */
	/**
	 * @return The estimated pose of the robot based on vision measurements COMBINED WITH drive motor measurements
	 */
    public Pose2d getEstimatedPose() {
        // System.out.println(poseEstimator.getEstimatedPosition());
        // var x = photonVision.getEstimatedGlobalPose();
        // if(x.isPresent()) poseEstimator.addVisionMeasurement(x.get().estimatedPose.toPose2d(), x.get().timestampSeconds);
        return poseEstimator.getEstimatedPosition();
    }

    public void followTrajectoryState(Trajectory trajectory, double time) {
        Trajectory.State goal = trajectory.sample(time);
        ChassisSpeeds adjustedSpeeds = holonomicDriveController.calculate(getEstimatedPose(), goal, goal.poseMeters.getRotation());
        SwerveModuleState[] moduleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(adjustedSpeeds);
        hardwareDrivetrain.setModuleStates(moduleStates);
    }

    public void updatePoseEstimator() {
        poseEstimator.update(navx.getYawRotation2D(), hardwareDrivetrain.getModulePositions());
        System.out.println("Estimated Yaw: " + navx.getYawDeg());
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

    public void resetPose(Pose2d pose){
        poseEstimator.resetPosition(navx.getYawRotation2D(), hardwareDrivetrain.getModulePositions(), pose);
    }

    // public Command pathplanner(){
    //     return AutoBuilder.getAutonomousCommand();
        
    // }
}