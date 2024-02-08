package frc.team5115.Commands.Combo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.team5115.Classes.Software.Arm;
import frc.team5115.Classes.Software.Intake;
import frc.team5115.Classes.Software.Shooter;
import frc.team5115.Commands.Arm.StowArm;

public class StowAndRack extends ParallelCommandGroup {
    public StowAndRack(boolean stow, Intake intake,  Shooter shooter, Arm arm, DigitalInput sensor ){

           if(stow){
            addCommands(
                new StowArm(arm)
                );
            }
            addCommands(
                new InstantCommand(intake :: out),
                new WaitForSensorChange(false, sensor),
                new InstantCommand(intake :: stop)
            );
        }
    }
