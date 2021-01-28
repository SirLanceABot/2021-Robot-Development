package frc.autonomous.commands;

import frc.autonomous.commands.interfaces.*;
import frc.components.powercellsupervisor.shooter.Flywheel;
import frc.components.powercellsupervisor.shooter.Shooter;
import frc.components.powercellsupervisor.shooter.Shroud;
import frc.components.powercellsupervisor.shuttle.Shuttle;

public class ShootingUsingVision implements Command, Notifies
{
    private boolean notification = false;
    private static Shooter shooter = Shooter.getInstance();
    private boolean isFinished = false;

    private static Shuttle shuttle = Shuttle.getInstance();
    private static Flywheel flywheel = Flywheel.getInstance();
    private static Shroud shroud = Shroud.getInstance();


    public ShootingUsingVision()
    {

    }

    public void init()
    {
        System.out.println("Initializing Shooter");
        sendNotification(true);
    }

    public void execute()
    {   
        System.out.println("Executing Shooter");
        shooter.runFSM();
        if(shooter.isOff())
        {
            isFinished = true;
            sendNotification(false);
        }
    }


    public boolean isFinished()
    {
        return isFinished;
    }

    public void end()
    {
        System.out.println("Ending Shooter");
        isFinished = true;
        
        shooter.runFSM();
        shuttle.overrideSetSpeed(0.0);
    }


    @Override
    public void sendNotification(boolean notification) 
    {
        shooter.getNotification(notification);
    }
}