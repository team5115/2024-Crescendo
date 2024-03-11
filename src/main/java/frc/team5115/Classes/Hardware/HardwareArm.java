package frc.team5115.Classes.Hardware;

import static frc.team5115.Constants.kS;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkAbsoluteEncoder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Accessory.Angle;
import frc.team5115.Classes.Hardware.I2CHandler.ReadAbortedException;

public class HardwareArm extends SubsystemBase{
    public static final double STOWED_ANGLE = 75.0;
    private final CANSparkMax turnRight;
    private final CANSparkMax turnLeft;
    
    private final AbsoluteEncoder absoluteEncoder;
    private final I2CHandler i2c;

    private final double Ks = 0.3;
    private final double Kv = 0.13509;
    private final double Ka = 0.048686;
    private final double Kg = 0.35;
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
        turnRight.setInverted(false);
        turnLeft.setInverted(true);

        absoluteEncoder = turnLeft.getAbsoluteEncoder(SparkAbsoluteEncoder.Type.kDutyCycle);
    }

    public void setTurn(double speed, Angle setpoint){
        if(speed != speed) {
            speed = 0;
        }
        double adjustedRads = getAngle().getRadians(-Math.PI);// + Math.toRadians(30);
        double voltage = MathUtil.clamp(ff.calculate(adjustedRads, speed), -10, 10);
        // double voltage = MathUtil.clamp(ff.calculate(setpoint.getRadians(-Math.PI), speed), -10, 10);
        
        if (Math.abs(voltage) < 2 * kS) {
            voltage = 0;
        }
        
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
        // try {
        //     armAngle.angle = i2c.getPitch();
        // } catch (ReadAbortedException exception) {
        //     armAngle.angle = getAngleFromEncoder();
        //     System.out.println("Using arm absolute encoder @ " + armAngle.getDegrees(-90));
        // }

        armAngle.angle = getAngleFromEncoder();
        // System.out.println("Using arm absolute encoder @ " + armAngle.getDegrees(-90));
        return armAngle;
    }

    private double getAngleFromEncoder() {
        // ! the -8.40 is because the zero offset in the spark max firmware is not at the zero point
        // this is because we want the encoder to never return a number that wraps down below zero
        // so yeah this isn't great but it should work
        return absoluteEncoder.getPosition() - 8.40; // this is a magic number
    }

    public void setIdleMode(IdleMode mode) {
        turnRight.setIdleMode(mode);
        turnLeft.setIdleMode(mode);
    }
}
