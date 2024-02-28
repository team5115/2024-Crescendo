package frc.team5115.Classes.Software;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Accessory.Angle;
import frc.team5115.Classes.Hardware.HardwareAmper;

public class Amper extends SubsystemBase {

    public final static Angle IN_ANGLE = new Angle(0);
    public final static Angle OUT_ANGLE = new Angle(90); // TODO determine deployed angle

    private final HardwareAmper hardwareAmper;
    private final Angle angle;
    
    public Amper(HardwareAmper hardwareAmper){
        this.hardwareAmper = hardwareAmper;
        angle = new Angle(IN_ANGLE.angle);
    }

    public Angle getAngle(){
        angle.angle = hardwareAmper.getAngle();
        return angle;
    }
    
    public void forward() {
        hardwareAmper.set(+0.2);
    }

    public void backward() {
        hardwareAmper.set(-0.2);
    }

    public void stop(){
        hardwareAmper.set(0);
    }

    public void setSpeed(double speed) {
        hardwareAmper.set(speed);
    }
}
