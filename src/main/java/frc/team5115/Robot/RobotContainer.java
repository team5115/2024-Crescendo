package frc.team5115.Robot;

import org.photonvision.PhotonVersion;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Classes.Software.*;

public class RobotContainer {

    private PhotonVision photonVision;

public RobotContainer() {


        photonVision = new PhotonVision();

    }

    public void registerCommand() {
        // Where should the commands come from? (Hint Autobuilder/Drivetrain)
    // Register Named Commands for pathplanner

    }

    
    public void teleopPeriodic() {

        System.out.println(photonVision.getRange());

     //   System.out.println(photonVision.getID());
 }

}