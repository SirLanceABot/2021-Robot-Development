package frc.autonomous.commands;

import frc.autonomous.commands.interfaces.*;
import frc.components.drivetrain.Drivetrain;

/**
 * Auotonomous command to drive a certain relative distance
 * (only forward and backward)
 * @author Maxwell Li
 */
public class Drive implements Command
{
    //creation/init of instance variables
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private boolean isFinished;
    private double distance;

    //constuctor (takes the distance to drive)
    public Drive(double distanceToDrive)
    {
        distance = distanceToDrive;
    }

    //initialization function, first called when executing the command
    @Override
    public void init()
    {
        drivetrain.resetEncoders();
        drivetrain.configLoopRampRate(0.75);
        System.out.println("Initializing Drive");
    }

    //execution function that is called when executing the command
    @Override
    public void execute()
    {   
        System.out.println("Executing Drive: " + distance);
        //drivetrain.drive(distance);
        //drivetrain.westCoastDrive(0.5, 0);
        if(drivetrain.drive(distance))
        {
            isFinished = true;
        }
    }

    //getter function for the isFinished flag
    @Override
    public boolean isFinished()
    {
        return isFinished;
    }

    //function to be called when the command ends
    @Override
    public void end()
    {
        drivetrain.configLoopRampRate(0.1);
        System.out.println("Ending Drive");
    }
}