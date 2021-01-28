package frc.autonomous.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.autonomous.commands.interfaces.*;

public class Wait implements Command 
{
    private Timer timer = new Timer();
    private double timeToWait;
    private boolean isFinished;
    
    public Wait(double time)
    {
        timeToWait = time;
        isFinished = false;
    }

    public void init()
    {
        timer.stop();
        timer.reset();
        timer.start();
    }

    public void execute()
    {
        System.out.println(timer.get());
        if(timer.get() > timeToWait)
        {
            end();
        }
    }

    public boolean isFinished()
    {
        return isFinished;
    }

    public void end()
    {
        timer.stop();
        timer.reset();
        isFinished = true;
    }

}