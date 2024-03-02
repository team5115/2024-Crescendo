package frc.team5115.Classes.Software;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Accessory.Angle;
import frc.team5115.Classes.Hardware.HardwareAmper;

public class Amper extends SubsystemBase {

    public final static Angle IN_ANGLE = new Angle(0);
    public final static Angle OUT_ANGLE = new Angle(178);

    private final HardwareAmper hardwareAmper;
    private final Angle angle;
    private final PIDController pidController;
    private final GenericEntry entry;
    
    public Amper(HardwareAmper hardwareAmper){
        this.hardwareAmper = hardwareAmper;
        angle = new Angle(IN_ANGLE.angle);
        pidController = new PIDController(0.005, 0, 0);

        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("SmartDashboard");
        entry = shuffleboardTab.add("amper kP", 0).getEntry();
    }

    public Angle getAngle(){
        angle.angle = hardwareAmper.getAngle();
        return angle;
    }
    
    public void forward() {
        hardwareAmper.set(+0.6);
    }

    public void backward() {
        hardwareAmper.set(-0.6);
    }

    public void stop(){
        hardwareAmper.set(0);
    }

    public void setSpeed(double speed) {
        hardwareAmper.set(speed);
    }

    public double spinPid(Angle setpoint) {
        pidController.setP(entry.getDouble(pidController.getP()));
        final double pidOutput = pidController.calculate(getAngle().getDegrees(-60), setpoint.getDegrees(-60));
        hardwareAmper.set(pidOutput);
        return pidOutput;
    }
}
