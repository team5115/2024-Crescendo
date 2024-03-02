package frc.team5115.Commands.Combo;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Amper;

public class WiggleAmper extends Command {
    private final Amper amper;
    private final Timer timer;
    private int loops;

    public WiggleAmper(Amper amper){
        this.amper = amper;
        timer = new Timer();
    }

    @Override
    public void initialize() {
        loops = 0;
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        if (loops % 2 == 0) {
            amper.backward();
        } else {
            amper.forward();
        }

        if (timer.get() >= 0.15) {
            loops++;
            timer.reset();
            timer.start();
        }
    }

    @Override
    public boolean isFinished() {
        return loops > 16;
    }

    @Override
    public void end(boolean interrupted) {
        amper.stop();
    }
}
