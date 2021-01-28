package frc.autonomous.commands;

import frc.autonomous.commands.interfaces.*;
import frc.components.powercellsupervisor.shooter.Flywheel;
import frc.components.powercellsupervisor.shooter.Shooter;
import frc.components.powercellsupervisor.shooter.Shroud;
import frc.components.powercellsupervisor.shuttle.Shuttle;

public class Shooting implements Command, Notifies
{
    private boolean notification = false;
    // private static Shooter shooter = Shooter.getInstance();
    private boolean isFinished;

    private static Shuttle shuttle = Shuttle.getInstance();
    private static Flywheel flywheel = Flywheel.getInstance();
    private static Shroud shroud = Shroud.getInstance();


    public Shooting()
    {

    }

    // public void init()
    // {
    //     System.out.println("Initializing Shooter");
    //     sendNotification(true);
    //     shooter.runFSM();
    // }

    // public void execute()
    // {   
    //     System.out.println("Executing Shooter");
    //     shooter.runFSM();
    //     if(shooter.isOff())
    //     {
    //         isFinished = true;
    //         sendNotification(false);
    //     }
    // }

    public void init()
    {
        System.out.println("Initializing Shooter");
        flywheel.run(4800);
        shroud.setSpeed(0.7);
    }

    public void execute()
    {   
        System.out.println("Executing Shooter");

        if(flywheel.getRPM() > 4000)
        {
            shuttle.overrideSetSpeed(0.5);
        }
        if(shuttle.isEmpty())
        {
            isFinished = true;
        }
    }

    public boolean isFinished()
    {
        return isFinished;
    }

    public void end()
    {
        isFinished = false;
        System.out.println("Ending Shooter");
        shroud.setSpeed(-0.3);
        flywheel.setSpeedOverride(0.0);
        shuttle.overrideSetSpeed(0.0);
    }


    @Override
    public void sendNotification(boolean notification) 
    {
        // shooter.getNotification(notification);
    }
}