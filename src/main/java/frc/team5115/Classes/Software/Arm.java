package frc.team5115.Classes.Software;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Accessory.Angle;
import frc.team5115.Classes.Hardware.HardwareArm;
import frc.team5115.Classes.Hardware.I2CHandler;
import edu.wpi.first.math.controller.PIDController;

/**
 * The arm subsystem. Provides methods for controlling and getting information about the arm.
 */
public class Arm extends SubsystemBase{
    //private final GenericEntry rookie;
    private static final double MIN_DEGREES = -90.0;
    private static final double TURN_PID_TOLERANCE = 10;
    private static final double TURN_PID_KP = 0.25;
    private static final double TURN_PID_KI = 0.0;
    private static final double TURN_PID_KD = 0.0;
    private final I2CHandler bno;
    private final HardwareArm hardwareArm;
    private final Angle setpoint;

    private PIDController turnController = new PIDController(TURN_PID_KP, TURN_PID_KI, TURN_PID_KD);

    public Arm(HardwareArm hardwareArm, I2CHandler bno){
        this.bno = bno;
        this.hardwareArm = hardwareArm;
        turnController.setTolerance(TURN_PID_TOLERANCE);
        setpoint = new Angle(HardwareArm.STOWED_ANGLE);
    }

    public Angle getSetpoint() {
        return setpoint;
    }

    /**
     * Changes the setpoint, but doesn't actually change the reference so that it doesn't become linked to the parameter passed in
     * @param newSetpoint the new setpoint as an angle, but might as well be a double
     */
    public void setSetpoint(Angle newSetpoint) {
        setpoint.angle = newSetpoint.getDegrees(MIN_DEGREES);
    }

    public void disableBrake(){
        hardwareArm.setIdleMode(IdleMode.kCoast);
    }

    public void enableBrake(){
        hardwareArm.setIdleMode(IdleMode.kBrake);
    }

    /**
     * Update the pid controller to try to approach the setpoint angle by changing hardware arm's speed
     * @param bno this is required to be passed in so that you don't forget to udpate the bno; this method updatesPitch() for you
     * @return true if the arm is at the setpoint
     */
    public void updateController(I2CHandler bno){
        bno.updatePitch();
        final double pidOutput = turnController.calculate(getAngle().getDegrees(MIN_DEGREES), setpoint.getDegrees(MIN_DEGREES));
        // System.out.println("Setpoint: " + setpoint.getDegrees(MIN_DEGREES) + " current angle: "+ getAngle().getDegrees(MIN_DEGREES) + " pid: " + pidOutput);
        
        //boolean atSetpoint = atSetpoint();
        hardwareArm.setTurn(pidOutput, setpoint);
    }

    public void setVoltage(double voltage) {
        hardwareArm.setVoltage(voltage);
    }


    public boolean deployed() {
        updateController(bno);
                return Math.abs(getAngle().angle-(-1)) < 20;

    }

    public boolean atSetpoint() {
        updateController(bno);
        return turnController.atSetpoint();
        //return Math.abs(getAngle().angle-setpoint.angle) < TURN_PID_TOLERANCE;
    }

    public boolean getFault(CANSparkMax.FaultID f){
        return hardwareArm.getFault(f);
    }

    public void stop(){
        hardwareArm.stop();
    }

    public Angle getAngle() {
        return hardwareArm.getAngle();
    }

    public void deployToAngle(double newSetpoint){
        setpoint.angle = newSetpoint;
    }

    public void stow() {
        setpoint.angle = HardwareArm.STOWED_ANGLE;
    }
}
