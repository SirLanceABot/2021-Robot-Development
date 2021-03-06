package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.Trajectory.State;
import frc.autonomous.AutonomousBuilder;
import frc.autonomous.AutonomousExecuter;
import frc.components.drivetrain.Drivetrain;
import frc.components.powercellsupervisor.shooter.Flywheel;
import frc.components.powercellsupervisor.shuttle.Shuttle;
import frc.controls.DriverController;
import frc.shuffleboard.MainShuffleboard;
import frc.shuffleboard.SkillsCompetitionTab;
import frc.shuffleboard.SkillsCompetitionTab.SkillsCompetitionTabData;

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
    private static MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();

    private static final TrajectoryLoader trajectoryLoader = new TrajectoryLoader();
    //TODO: Change path driven using ifs
    private static ArrayList<Trajectory> trajectory = new ArrayList<>();// = trajectoryLoader.getTrajectory(TrajectoryLoader.PathOption.Slalom);

    // The Ramsete Controller to follow the trajectory.
    private static final RamseteController ramseteController = new RamseteController(2.0, 0.7);

    private static int currentPath = 0;
    private static boolean newPathStarted = false;
    private static boolean finished = false;
    private static double currentPathTotalTime = 0.0;
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
        
        // autonomousBuilder.buildCommandList();

        pathWeaverInit();
    }

    public void periodic()
    {
        // autonomousExecuter.executeAuto();

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

        // pathWeaverPeriodic();
    }

    public void end()
    {
        autonomousExecuter.endCommands();
    }

    public void pathWeaverInit()
    {
        // TODO: Use disabled periodic to query shuffleboard for autonav path
        SkillsCompetitionTabData skillsCompetitionTabData = mainShuffleboard.getSkillsCompetitionTabData();

        trajectory = trajectoryLoader.getTrajectory(skillsCompetitionTabData.autoNavPath);

        currentPath = 0;
        newPathStarted = true;
        finished = false;
        currentPathTotalTime = 0.0;

        // Initialize the timer.
        timer.reset();
        timer.start();

        // Reset the drivetrain's odometry to the starting pose of the trajectory.
        drivetrain.resetOdometry(trajectory.get(0).getInitialPose());
    }

    public void pathWeaverPeriodic()
    {
        if(!finished)
        {
            if(newPathStarted)
            {
                drivetrain.stopMotor();
                newPathStarted = false;
                currentPath++;
                currentPathTotalTime = 0.0;
                if(currentPath <= trajectory.size())
                {
                    currentPathTotalTime = trajectory.get(currentPath-1).getTotalTimeSeconds();
                }
                else
                {
                    finished = true;
                }

                System.out.println(currentPath);
                timer.reset();
            }

            // Update odometry.
            drivetrain.updateOdometry();

            if (currentPath <= trajectory.size() && timer.get() < currentPathTotalTime) 
            {
                // Get the desired pose from the trajectory.
                // State desiredPose = trajectory[m_currentPath-1].sample(m_timer.get());
                State desiredPose = trajectory.get(currentPath-1).sample(timer.get());

                // Get the reference chassis speeds from the Ramsete controller.
                ChassisSpeeds refChassisSpeeds = ramseteController.calculate(drivetrain.getPose(), desiredPose);

                // Set the linear and angular speeds.
                drivetrain.drive(refChassisSpeeds.vxMetersPerSecond, refChassisSpeeds.omegaRadiansPerSecond);
            } 
            else 
            {
                drivetrain.stopMotor();  // Redundant intentionally to stop the robot
                newPathStarted = true;
            }
        }
        drivetrain.feed();
    }
}