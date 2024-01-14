package frc.team5115.Commands.Auto;

// add imports


public class FollowPathCommand extends Commands{

    public Command getAutonomousCommand() {
        // Load the path you want to follow using its name in the GUI
        PathPlannerPath path = PathPlannerPath.fromPathFile("NewPath");

        // Create a path following command using AutoBuilder. This will also trigger event markers.
        return AutoBuilder.followPathWithEvents(path);
    }
}