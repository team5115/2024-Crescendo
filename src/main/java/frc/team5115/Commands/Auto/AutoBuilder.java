package frc.team5115.Commands.Auto;

import frc.team5115.Classes.Software.Drivetrain;
import frc.team5115.Classes.Hardware.HardwareDrivetrain;
import org.photonvision.EstimatedRobotPose;


import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.PhotonVision;

import com.pathplanner.lib.auto.AutoBuilder.QuadFunction;
import com.pathplanner.lib.auto.AutoBuilder.TriFunction;
import com.pathplanner.lib.auto.AutoBuilderException;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.*;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.GeometryUtil;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AutoBuilder extends Command{

/** Utility class used to build auto routines */


  private static boolean configured = false;

  private static Function<PathPlannerPath, Command> pathFollowingCommandBuilder;
  private static Supplier<Pose2d> getPose;
  private static Consumer<Pose2d> resetPose;
  private static BooleanSupplier shouldFlipPath;

  // Pathfinding builders
  private static boolean pathfindingConfigured = false;
  private static QuadFunction<Pose2d, PathConstraints, Double, Double, Command>
      pathfindToPoseCommandBuilder;
  private static TriFunction<PathPlannerPath, PathConstraints, Double, Command>
      pathfindThenFollowPathCommandBuilder;

  public static void configureHolonomic(
      Supplier<Pose2d> poseSupplier,
      Consumer<Pose2d> resetPose,
      Supplier<ChassisSpeeds> robotRelativeSpeedsSupplier,
      Consumer<ChassisSpeeds> robotRelativeOutput,
      HolonomicPathFollowerConfig config,
      BooleanSupplier shouldFlipPath,
      Subsystem driveSubsystem) {
    if (configured) {
      throw new AutoBuilderException(
          "Auto builder has already been configured//You can only configure once");
    }

    AutoBuilder.pathFollowingCommandBuilder =
        (path) ->
            new FollowPathHolonomic(
                path,
                poseSupplier,
                robotRelativeSpeedsSupplier,
                robotRelativeOutput,
                config,
                shouldFlipPath,
                driveSubsystem);
    AutoBuilder.getPose = poseSupplier;
    AutoBuilder.resetPose = resetPose;
    AutoBuilder.configured = true;
    AutoBuilder.shouldFlipPath = shouldFlipPath;

    AutoBuilder.pathfindToPoseCommandBuilder =
        (pose, constraints, goalEndVel, rotationDelayDistance) ->
            new PathfindHolonomic(
                pose,
                constraints,
                goalEndVel,
                poseSupplier,
                robotRelativeSpeedsSupplier,
                robotRelativeOutput,
                config,
                rotationDelayDistance,
                driveSubsystem);
    AutoBuilder.pathfindThenFollowPathCommandBuilder =
        (path, constraints, rotationDelayDistance) ->
            new PathfindThenFollowPathHolonomic(
                path,
                constraints,
                poseSupplier,
                robotRelativeSpeedsSupplier,
                robotRelativeOutput,
                config,
                rotationDelayDistance,
                shouldFlipPath,
                driveSubsystem);
    AutoBuilder.pathfindingConfigured = true;
  }

         

    // public AutoBuilder() {
    //     private AutoBuilder = AutoBuilder;
    //     // Configure AutoBuilder 

    //     AutoBuilder.configureHolonomic( 
    //             this::getEstimatedPose, // Robot pose supplier
    //             this::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
    //             this::getChassisSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
    //             this::setWheelSpeeds, // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds
    //             new HolonomicPathFollowerConfig( // HolonomicPathFollowerConfig, this should likely live in your Constants class
    //                     new PIDConstants(5.0, 0.0, 0.0), // Translation PID constants
    //                     new PIDConstants(5.0, 0.0, 0.0), // Rotation PID constants
    //                     4.5, // Max module speed, in m/s
    //                     0.4, // Drive base radius in meters. Distance from robot center to furthest module.
    //                     new ReplanningConfig() // Default path replanning config. See the API for the options here
    //             ),
    //             () -> {
    //                 // Boolean supplier that controls when the path will be mirrored for the red alliance
    //                 // This will flip the path being followed to the red side of the field.
    //                 // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

    //                 var alliance = DriverStation.getAlliance();
    //                 if (alliance.isPresent()) {
    //                     return alliance.get() == DriverStation.Alliance.Red;
    //                 }
    //                 return false;
    //             },
    //             this // Reference to this subsystem to set requirements
    //     );
    // }
    
  

    public Command getAutonomousCommand() {

        
        // Load the path you want to follow using its name in the GUI
        PathPlannerPath path = PathPlannerPath.fromPathFile("Unbasic Test");


        // Create a path following command using AutoBuilder. This will also trigger event markers.
        return AutoBuilder.FollowPathCommand(path); 
    }



  
private static Command FollowPathCommand(PathPlannerPath path) {
    return pathFollowingCommandBuilder.apply(path);
    //throw new UnsupportedOperationException("Unimplemented method 'FollowPathCommand'");
    }





public static SendableChooser<Command> buildAutoChooser() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'buildAutoChooser'");
}

}