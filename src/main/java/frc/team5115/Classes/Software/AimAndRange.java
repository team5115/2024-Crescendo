package frc.team5115.Classes.Software; 

import frc.team5115.Classes.Hardware.HardwareDrivetrain;
import frc.team5115.Classes.Software.PhotonVision;
import org.photonvision.PhotonPoseEstimator;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;

// using apriltags -- aim at target and get in range of target 

public class AimAndRange extends SubsystemBase{


    final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(24); // get measurments
    final double TARGET_HEIGHT_METERS = Units.feetToMeters(5); // get measurments

    // Angle between horizontal and the camera.
    final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);  // get measurments

    // How far from the target we want to be
    final double GOAL_RANGE_METERS = Units.feetToMeters(3); 

    PhotonCamera camera = new PhotonCamera(null);

    // PID constants should be tuned per robot
    final double LINEAR_P = 0.1;
    final double LINEAR_D = 0.0;
    PIDController forwardController = new PIDController(LINEAR_P, 0, LINEAR_D);

    final double ANGULAR_P = 0.1;
    final double ANGULAR_D = 0.0;
    PIDController turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);

    XboxController xboxController = new XboxController(0);

// Drive motors

   /* {motor name} leftMotor = new {motor name}(0);
     {motor name} rightMotor = new {motor name}(1);
    SwerveDrive drive = new SwerveDrive(leftMotor, rightMotor);
*/
    @Override
    public void teleopPeriodic() { // fix error*

        double forwardSpeed;
        double rotationSpeed;

        if (xboxController.getAButton()) {
            // Vision-alignment mode
            // Query the latest result from PhotonVision
            var result = camera.getLatestResult(); // fix error*

            if (result.hasTargets()) { // fix error*
                // First calculate range
                double range =
                        PhotonUtils.calculateDistanceToTargetMeters(
                                CAMERA_HEIGHT_METERS,
                                TARGET_HEIGHT_METERS,
                                CAMERA_PITCH_RADIANS,
                                Units.degreesToRadians(result.getBestTarget().getPitch())); // fix error*

                // Use this range as the measurement we give to the PID controller.
                // -1.0 required to ensure positive PID controller effort _increases_ range
                forwardSpeed = -forwardController.calculate(range, GOAL_RANGE_METERS);

                // Also calculate angular power
                // -1.0 required to ensure positive PID controller effort _increases_ yaw
                rotationSpeed = -turnController.calculate(result.getBestTarget().getYaw(), 0); // fix error*
            } else {
                // If we have no targets, stay still.
                forwardSpeed = 0;
                rotationSpeed = 0;
            }
        } else {
            // Manual Driver Mode
            forwardSpeed = -xboxController.getRightY();
            rotationSpeed = xboxController.getLeftX();
        }

        // Use our forward/turn speeds to control the drivetrain
        HardwareDrivetrain.drive(forwardSpeed, rotationSpeed, 0, true, );
    }
}
