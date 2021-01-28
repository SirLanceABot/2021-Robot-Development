package frc.autonomous.commands;

import frc.autonomous.commands.interfaces.*;
import frc.components.drivetrain.Drivetrain;

/**
 * Autonomous command to rotate to an absolute angle
 * @author Maxwell Li
 */
public class RotateDrivetrain implements Command
{
    //Init the instance variables
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private double absoluteAngle;
    private boolean isFinished;

    //construct the object
    public RotateDrivetrain(double angle)
    {
        absoluteAngle = angle;
    }

    //init function to be called first when executing commands
    @Override
    public void init()
    {
        System.out.println("Initializing Rotation");
    }

    //function to be called after init to execute 
    @Override
    public void execute()
    {   
        System.out.println("Executing Rotation");
        if(drivetrain.rotateTo(absoluteAngle))
        {
            isFinished = true;
        }
        else
        {
            isFinished = false;
        }
    }

    //getter funtion for the the isFinished flag
    @Override
    public boolean isFinished()
    {
        return isFinished;
    }

    //function that ends the command
    @Override
    public void end()
    {
        System.out.println("Ending Rotation");
    }
}