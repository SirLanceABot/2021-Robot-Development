package frc.autonomous.commands;

import frc.autonomous.commands.interfaces.*;
import edu.wpi.first.wpilibj.Timer;
import frc.components.powercellsupervisor.intake.Intake;

public class Intaking implements Command, Notifies
{
    private boolean notification = false;
    private Intake intake = Intake.getInstance();

    private Timer timer = new Timer();
    private double timeToWait;
    private boolean isFinished;

    public Intaking(double time)
    {
        timeToWait = time;
        isFinished = false;
    }

    public void init()
    {
        timer.stop();
        timer.reset();
        timer.start();
        sendNotification(true);
    }

    public void execute()
    {
        System.out.println("Intaking: " + timer.get());
        intake.runFSM();
        if(timer.get() > timeToWait)
        {
            sendNotification(false);
            intake.runFSM();
            end();
        }
    }

    public boolean isFinished()
    {
        intake.runFSM();
        return isFinished;
    }

    public void end()
    {
        timer.stop();
        timer.reset();
        isFinished = true;
        sendNotification(false);
        intake.runFSM();
    }


    @Override
    public void sendNotification(boolean notification) 
    {
        intake.getNotification(notification);
    }
}