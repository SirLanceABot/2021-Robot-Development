package frc.autonomous.commands;


import frc.autonomous.commands.interfaces.*;
import frc.components.drivetrain.Drivetrain;

/**
 * drives to a point relative to your robot.
 * The leftmost corner of your driver station is 0,0
 * straight ahead to the intake is 0 degrees
 * counter clockwise is positive
 * clockwise is negative
 * units are in inches
 * 
 * *********ASSUMES ROBOT IS IN STARTING POSITION******
 * absolute coordinate system uses the robot as (0,0)
 * uses the gyro to go to the right point
 * 0 degrees will always be towards our alliance driver station
 * 180 is always the opponents driver station
 * 90 will be our trench
 * -90 will be the enemy trench
 * 
 *                0 Degrees
 * 
 *              |***Intake***|
 *              |            |
 *   90 Degrees |    Robot   |  -90 Degrees
 *              |  Starting  |    
 *              |  Position  |     
 *              |            |
 *              ****Shooter****
 * 
 * 
 *                +/-180 Degrees
 * @author Maxwell Li
 *  */
public class DriveTo implements Command
{
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private boolean isFinished;
    private double xPos = 0.0;
    private double yPos = 0.0;

    public DriveTo(double xPosition, double yPosition)
    {
        xPos = xPosition;
        yPos = yPosition;
    }

    public void init()
    {
        System.out.println("Initializing Drive To:" + xPos + "," + yPos);
    }

    public void execute()
    {   
        System.out.println("Executing Drive To: " + xPos + "," + yPos);
        //Need to add the math for an absolute coordiante system
        //will combine the drive and the rotate class,
        //merely acts as a wrapper for the two and coordinates them
    }


    public boolean isFinished()
    {
        return isFinished;
    }

    public void end()
    {
        System.out.println("Ending Drive" + xPos + "," + yPos);
    }
}