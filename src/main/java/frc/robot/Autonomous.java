package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.autonomous.AutonomousBuilder;
import frc.autonomous.AutonomousExecuter;
import frc.components.drivetrain.Drivetrain;
import frc.components.powercellsupervisor.shooter.Flywheel;
import frc.components.powercellsupervisor.shuttle.Shuttle;
import frc.controls.DriverController;

public class Autonomous
{
    private static final String className = new String("[Autonomous]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private static Autonomous instance = new Autonomous();
    private static AutonomousExecuter autonomousExecuter = AutonomousExecuter.getAutonomousExecuter();
    private static AutonomousBuilder autonomousBuilder = AutonomousBuilder.getInstance();
    private static Shuttle shuttle = Shuttle.getInstance();
    private static Flywheel flywheel = Flywheel.getInstance();
    private static Timer timer = new Timer();
    private static Drivetrain drivetrain = Drivetrain.getInstance();
    /**
     * The constructor for the Autonomous class. 
     */
    private Autonomous()
    {
        System.out.println(className + " : Constructor Started");
        
        System.out.println(className + ": Constructor Finished");
    }

    /**
     * The method to retrieve the instance of Autonomous.
     * @return instance 
     */
    public static Autonomous getInstance()
    {
        return instance;
    }

    public void init()
    {
        // timer.reset();
        // timer.start();
        autonomousBuilder.buildCommandList();
    }

    public void periodic()
    {
        autonomousExecuter.executeAuto();
        // flywheel.setSpeed(0.20);
        // if(timer.get() > 1.5)
        // {
        //     shuttle.overrideSetSpeed(0.15);
        // }
        
        // if(shuttle.isEmpty())
        // {
        //     shuttle.stop();
        //     flywheel.stop();
        //     drivetrain.westCoastDrive(0.5, 0);
        // }
        
        // if(timer.get() > 8.0)
        // {
        //     drivetrain.westCoastDrive(0.0, 0);

        // }
    }
}