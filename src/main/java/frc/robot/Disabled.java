package frc.robot;

import frc.robot.Robot.RobotState;
import frc.shuffleboard.MainShuffleboard;
import frc.shuffleboard.SkillsCompetitionTab.SkillsCompetitionTabData;
import frc.vision.Vision;

/**
 * @author Elliot Measel class for the disabled mode on the robot
 */
public class Disabled
{
    private static final String className = new String("[Disabled]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private static boolean threadInitializedMessage = true;
    
    private RobotState robotState;

    private static MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();
    private static TrajectoryLoader trajectoryLoader = TrajectoryLoader.getInstance();

    private static SkillsCompetitionTabData skillsCompetitionTabDataOld = new SkillsCompetitionTabData();

    private static Disabled instance = new Disabled();
    /**
     * private constructor for disabled class
     */
    private Disabled()
    {
        System.out.println(className + " : Constructor Started");
        
        System.out.println(className + ": Constructor Finished");
    }

    public static Disabled getInstance()
    {
        return instance;
    }

    public void init()
    {
        robotState = Robot.getRobotState();

        skillsCompetitionTabDataOld = mainShuffleboard.getSkillsCompetitionTabData();
    }

    public void periodic()
    {
        if (robotState == RobotState.kDisabledBeforeGame)
        {
            if(!Vision.isConnected())
            {
                if(threadInitializedMessage)
                {
                    System.out.println("Waiting for a connection");
                    threadInitializedMessage = false;
                }
            }
            else
            {
                if(!threadInitializedMessage)
                {
                    System.out.println("First connection established");
                    threadInitializedMessage = true;
                }
            }
            mainShuffleboard.updateMatchInfo();
            mainShuffleboard.checkForNewAutonomousTabData();
        }

        SkillsCompetitionTabData skillsCompetitionTabDataNew = mainShuffleboard.getSkillsCompetitionTabData();

        if (skillsCompetitionTabDataOld.autoNavPath != skillsCompetitionTabDataNew.autoNavPath)
        {
            trajectoryLoader.createTrajectoryFromPath(skillsCompetitionTabDataNew.autoNavPath);

            skillsCompetitionTabDataOld = skillsCompetitionTabDataNew;
        }
    }

    public void end()
    {
        
    }
}