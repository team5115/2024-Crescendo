package frc.team5115.Classes.Hardware;

import java.util.function.Function;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LedStrip extends SubsystemBase {
    private final int period = 2;
    private final double tailLength = 5;
    private final double decay = 1d / tailLength;
    private final int minPower = 10;
    private final int maxPower = 250;
    private final int ledCount;
    private final AddressableLED leds;
    private final AddressableLEDBuffer buffer;

    private int timer;
    private int counter;
    private int direction;
    
    public LedStrip(int port, int ledCount) {
        this.ledCount = ledCount;
        leds = new AddressableLED(port);
        leds.setLength(ledCount);
        buffer = new AddressableLEDBuffer(ledCount);

        counter = 0;
        direction = 1;
    }

    public void start() {
        leds.start();
    }

    public void stop() {
        leds.stop();
    }

    public void updateKnightRider() {
        timer ++;
        if (timer >= period) {
            timer = 0;
        } else {
            return;
        }

        counter += direction;
        if (counter == ledCount || counter == -1) {
            direction = -direction;
            counter += 2 * direction;
        }
        iterateAllLeds((index) -> {
            double percent = buffer.getLED(index).red - minPower / 1d / maxPower - decay;
            percent = Math.max(percent, 0);

            if (index == counter) {
                percent = 1.0;
            }

            final double power = (percent * (maxPower - minPower)) + minPower;
            return new Integer[] { (int)power, 0, 0 };
        });
    }

    public void setUniformColor(int red, int green, int blue) {
        iterateAllLeds((index) -> {
            return new Integer[] {red, green, blue};
        });
    }

    private void iterateAllLeds(Function<Integer, Integer[]> function) {
        for (int i = 0; i < ledCount; i++) {
            Integer[] color = function.apply(i);
            buffer.setRGB(i, color[0], color[1], color[2]);
        }
        leds.setData(buffer);
    }
}
