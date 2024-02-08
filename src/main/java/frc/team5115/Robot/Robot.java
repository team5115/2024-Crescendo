package frc.team5115.Robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private RobotContainer robotContainer;
    public Timer timer; 

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
        timer = new Timer();
        timer.start();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {

        //CameraServer.startAutomaticCapture();
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
      //  CameraServer.startAutomaticCapture();
    }


    public void autonomousPeriodic() {
        robotContainer.autoPeriod();
    }

    public void teleopInit () {
        //CameraServer.startAutomaticCapture();
    }
    
    @Override
    public void teleopPeriodic () {
       robotContainer.teleopPeriodic();
    }

    public void testInit () {
    }

    public void testPeriodic () {
    }

    public void practiceInit(){
        CameraServer.startAutomaticCapture();

    }

    public void practicePeriodic(){

    }
}
