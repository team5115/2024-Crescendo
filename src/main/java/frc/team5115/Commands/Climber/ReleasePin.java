package frc.team5115.Commands.Climber;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team5115.Classes.Software.Climber;

public class ReleasePin extends Command {
    final Climber climber;
    private double time;
    private final Timer timer;
    public ReleasePin(Climber climber, double time) {
        this.climber = climber;
        this.time = time;
        timer = new Timer();
    }

    @Override
    public void initialize() {
        timer.reset();
        climber.retractPins();
        climber.stop();
    }

    @Override
    public void execute() {
       if(climber.bothStopped()){
            timer.start();    
        } else {
            timer.reset();
        }
    }

    @Override
    public boolean isFinished() {
        return timer.get() > time;
    }
}
