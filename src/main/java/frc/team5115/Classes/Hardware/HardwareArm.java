package frc.team5115.Classes.Hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Accessory.Angle;

public class HardwareArm extends SubsystemBase{
    public static final double STOWED_ANGLE = 166.0;
    public static final double DEPLOYED_ANGLE = -2.0;
    private final CANSparkMax turnRight;
    private final CANSparkMax turnLeft;
    
    private final NAVx navx;
    private final I2CHandler i2c;

    private final double Ks = 0.49155;
    private final double Kv = 0.19266;
    private final double Ka = 0.13019;
    private final double Kg = 0.13614;
    private final ArmFeedforward ff = new ArmFeedforward(Ks, Kg, Kv, Ka); // Degree Calibrated
    private final Angle armAngle;

    public HardwareArm(NAVx navx, I2CHandler i2c, int canIdRight, int canIdLeft){
        this.navx = navx;
        this.i2c = i2c;
        
        turnRight = new CANSparkMax(canIdRight, MotorType.kBrushless);
        turnRight.setIdleMode(IdleMode.kBrake);
        turnRight.setSmartCurrentLimit(80, 80);
        
        turnLeft = new CANSparkMax(canIdLeft, MotorType.kBrushless);
        turnLeft.setIdleMode(IdleMode.kBrake);
        turnLeft.setSmartCurrentLimit(80, 80);

        armAngle = new Angle(STOWED_ANGLE);
        // TODO hardware arm motor inversion
        turnRight.setInverted(true);
        turnLeft.setInverted(true);
    }

    public void setTurn(double speed){
        if(speed != speed) {
            speed = 0;
        }
        turnLeft.setVoltage(MathUtil.clamp(ff.calculate(getArmAngle().getDegrees(-180), speed), -10, 10));
        turnRight.setVoltage(MathUtil.clamp(ff.calculate(getArmAngle().getDegrees(-180), speed), -10, 10));
    }

    public void stop(){
        setTurn(0);
    }
    
    public double getTurnCurrent(){
        return turnRight.getOutputCurrent();
    }
    
    public boolean getFault(CANSparkMax.FaultID f){
       return turnRight.getFault(f);
    }
    
    /**
     * This uses the navx and the bno to get the arm degree instead of motor encoder
     * @return the angle the arm is at relative to the horizontal
     */
    public Angle getArmAngle(){
        armAngle.angle = i2c.getPitch() - navx.getPitchDeg();
        return armAngle;
    }

    public void setIdleMode(IdleMode mode) {
        turnRight.setIdleMode(mode);
        turnLeft.setIdleMode(mode);
    }
}
