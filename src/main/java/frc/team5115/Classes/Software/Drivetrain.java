package frc.team5115.Classes.Software;

import com.pathplanner.lib.auto.AutoBuilder;

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

public class Drivetrain extends SubsystemBase {
    private final HardwareDrivetrain hardwareDrivetrain;
    private final NAVx navx;
    private final PhotonVision p;
    private final HolonomicDriveController holonomicDriveController;
    private SwerveDrivePoseEstimator poseEstimator;
   
    public Drivetrain(HardwareDrivetrain hardwareDrivetrain, NAVx navx, PhotonVision p) {
        this.hardwareDrivetrain = hardwareDrivetrain;
        this.navx = navx;
        this.p = p;
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

        System.out.println("Angle from navx" + navx.getYawDeg());

        AutoBuilder.configureHolonomic(this :: getEstimatedPose, null, null, null, null, null, navx);
    }

    private Pose2d getStartingPoseGuess() {
            return new Pose2d();
    }

    /**
	 * Sets the encoder values to 0.
	 */
    public void resetEncoders() {
        //navx.resetNAVx();
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
            right *= 0.2;
            turn *= 0.2;
            forward *= 0.2;
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

    // public void resetPose(){
    //     poseEstimator.resetPosition(null, null, getEstimatedPose());
    // }

    // public Command pathplanner(){
    //     return AutoBuilder.getAutonomousCommand();
        
    // }
}
