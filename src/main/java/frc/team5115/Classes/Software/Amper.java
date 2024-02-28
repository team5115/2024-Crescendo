package frc.team5115.Classes.Software;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Accessory.Angle;
import frc.team5115.Classes.Hardware.HardwareAmper;

public class Amper extends SubsystemBase {
    private HardwareAmper hardwareAmper;

    private PIDController pid;
    /*public final double kp;
    public final double ki;
    public final double kd;*/
    public final int PID_TOLERANCE = 0;

    private final Angle setpoint;
    
    public Amper(){
        hardwareAmper = new HardwareAmper();
        pid = new PIDController(0, 0, 0);
        setpoint = new Angle(0);
    }

    public Angle getSetpoint(){
        return setpoint;
    }

    public boolean updateController(){
        final double pidOutput = pid.calculate(getAngle().getDegrees(0), setpoint.getDegrees(0));

        boolean atSetpoint = atSetpoint();
        if(!atSetpoint) hardwareAmper.set(pidOutput);
        return atSetpoint;
    }

    public boolean atSetpoint(){
        return Math.abs(getAngle().angle-setpoint.angle)>PID_TOLERANCE;
    }

    public Angle getAngle(){
        return hardwareAmper.getAngle();
    }
    
}
