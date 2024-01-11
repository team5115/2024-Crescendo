package frc.team5115.Classes.Software;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Classes.Hardware.HardwareFeeder;
import frc.team5115.Classes.Hardware.HardwareShooter;


public class Feeder extends SubsystemBase {
    HardwareFeeder hardwareFeeder;
        public Feeder(HardwareFeeder hardwareFeeder) {
            this.hardwareFeeder = hardwareFeeder;
        }
    public void startFeed() {
        hardwareFeeder.setFeederSpeed(0.2); // TODO find good feed speed [-1, +1]
    }

    public void stopFeed() {
        hardwareFeeder.setFeederSpeed(0);
    }
}
