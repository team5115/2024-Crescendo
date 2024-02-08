package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Accessory.Angle;

public class HardwareArm extends SubsystemBase{
    public static final double STOWED_ANGLE = 160.0;
    private final CANSparkMax turnRight;
    private final CANSparkMax turnLeft;
    
    private final I2CHandler i2c;

    private final double Ks = 0.35;
    private final double Kv = 0.13509;
    private final double Ka = 0.048686;
    private final double Kg = 0.15;
    private final ArmFeedforward ff = new ArmFeedforward(Ks, Kg, Kv, Ka);
    private final Angle armAngle;

    public HardwareArm(I2CHandler i2c, int canIdRight, int canIdLeft){
        this.i2c = i2c;
        
        turnRight = new CANSparkMax(canIdRight, MotorType.kBrushless);
        turnRight.setIdleMode(IdleMode.kBrake);
        turnRight.setSmartCurrentLimit(80, 80);
        
        turnLeft = new CANSparkMax(canIdLeft, MotorType.kBrushless);
        turnLeft.setIdleMode(IdleMode.kBrake);
        turnLeft.setSmartCurrentLimit(80, 80);

        armAngle = new Angle(STOWED_ANGLE);
        // TODO hardware arm motor inversion
        turnRight.setInverted(false);
        turnLeft.setInverted(true);
    }

    public void setTurn(double speed, Angle setpoint){
        if(speed != speed) {
            speed = 0;
        }
        double voltage = MathUtil.clamp(ff.calculate(getAngle().getRadians(-Math.PI), speed), -10, 10);
        // double voltage = MathUtil.clamp(ff.calculate(setpoint.getRadians(-Math.PI), speed), -10, 10);
        setVoltage(voltage);
    }

    public void setVoltage(double voltage) {
        turnLeft.setVoltage(voltage);
        turnRight.setVoltage(voltage);
    }

    public void stop(){
        setTurn(0, getAngle());
    }
    
    public double getCurrentAmps(){
        return turnRight.getOutputCurrent();
    }
    
    public boolean getFault(CANSparkMax.FaultID f){
       return turnRight.getFault(f);
    }
    
    /**
     * This uses the bno to get the arm angle relative to the ground 
     * @return the angle the arm is at relative to the horizontal
     */
    public Angle getAngle(){
        //armAngle.angle = i2c.getPitch() - navx.getPitchDeg();
        armAngle.angle = i2c.getPitch();
        return armAngle;
    }

    public void setIdleMode(IdleMode mode) {
        turnRight.setIdleMode(mode);
        turnLeft.setIdleMode(mode);
    }
}
