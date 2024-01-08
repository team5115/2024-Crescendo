package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Accessory.Angle;
import frc.team5115.Classes.Accessory.I2CHandler;

public class HardwareArm extends SubsystemBase{
    private final CANSparkMax armTurn;
    private final CANSparkMax grabby;
    
    private final NAVx navx;
    private final I2CHandler i2c;

    private final double Ks = 0.13;
    private final double Kv = 4.5;
    private final double Ka = 0.1113;
    private final double Kg = 0.39;
    private final ArmFeedforward ff = new ArmFeedforward(Ks, Kg, Kv, Ka); // Rad Calibrated
    private final Angle armAngle;

    public HardwareArm(NAVx navx, I2CHandler i2c){
        this.navx = navx;
        this.i2c = i2c;
        
        grabby = new CANSparkMax(9,MotorType.kBrushless);
        armTurn = new CANSparkMax(10, MotorType.kBrushless);  
        armTurn.setIdleMode(IdleMode.kBrake);
        armTurn.setSmartCurrentLimit(80, 80);
        armAngle = new Angle(120); // ! The approx real starting angle of the arm when the robot starts
        armTurn.setInverted(true);
    }

    public void spinGrabbers(double speedNormalized) {
        grabby.set(speedNormalized);
    }

    public void setTurn(double speed){
        if(speed != speed) {
            speed = 0;
        }
        armTurn.setVoltage(Math.max(ff.calculate(getArmAngle().getRadians(-Math.PI), speed), -10));
    }

    public void stop(){
        setTurn(0);
    }
    
    public double getTurnCurrent(){
        return armTurn.getOutputCurrent();
    }
    
    public boolean getFault(CANSparkMax.FaultID f){
       return armTurn.getFault(f);
    }
    
    /**
     * This uses the navx and the bno to get the arm degree instead of motor encoder
     * @return the angle the arm is at relative to the horizontal
     */
    public Angle getArmAngle(){
        armAngle.angle = i2c.getPitch() - navx.getPitchDeg();
        return armAngle;
    }

    public void disableBrake(){
        // armTurn.setIdleMode(IdleMode.kCoast);
    }

    public void enableBrake(){
        // armTurn.setIdleMode(IdleMode.kBrake);
    }
}
