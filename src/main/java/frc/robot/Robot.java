/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.shuffleboard.MainShuffleboard;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot
{
    private static final String className = new String("[Robot]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    /**
     * This keeps track of the current state of the robot, from startup to auto, to teleop, etc.
     */
    public enum RobotState
    {
        kNone,
        kStartup,
        kDisabledBeforeGame,
        kAutonomous,
        kDisabledBetweenAutonomousAndTeleop,
        kTeleop,
        kDisabledAfterGame,
        kTest;
    }

    private static Test test = Test.getInstance();
    private static Autonomous autonomous = Autonomous.getInstance();
    private static Disabled disabled = Disabled.getInstance();
    private static Teleop teleop = Teleop.getInstance();
    private static MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();

    private static RobotState robotState = RobotState.kNone;

    public Robot()
    {
        System.out.println(className + " : Constructor Started");

        robotState = RobotState.kStartup;

        System.out.println(className + ": Constructor Finished");
    }

    /**
     * This method is run when the robot is first started up and should be used for any initialization code.
     */
    @Override
    public void robotInit()
    {
        System.out.println("2020-Robot-Development");
    }

    /**
     * This method is called periodically.
     */
    @Override
    public void robotPeriodic()
    {
        mainShuffleboard.updateMatchTime();
    }

    /**
     * This method is run once each time the robot enters autonomous mode.
     */
    @Override
    public void autonomousInit()
    {
        robotState = RobotState.kAutonomous;

        autonomous.init();
    }

    /**
     * This method is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic()
    {
        autonomous.periodic();
    }

    /**
     * This method is called once each time the robot enters teleoperated mode.
     */
    @Override
    public void teleopInit()
    {
        robotState = RobotState.kTeleop;

        teleop.init();
    }

    /**
     * This method is called periodically during teleoperated mode.
     */
    @Override
    public void teleopPeriodic()
    {
        teleop.periodic();
    }

    /**
     * This method is called once each time the robot enters test mode.
     */
    @Override
    public void testInit()
    {
        robotState = RobotState.kTest;

        test.init();
    }

    /**
     * This method is called periodically during test mode.
     */
    @Override
    public void testPeriodic()
    {
        test.periodic();
    }

    /**
     * This method is called once each time the robot is disabled.
     */
    @Override
    public void disabledInit()
    {
        if (robotState == RobotState.kStartup)
            robotState = RobotState.kDisabledBeforeGame;
        else if (robotState == RobotState.kAutonomous)
            robotState = RobotState.kDisabledBetweenAutonomousAndTeleop;
        else if (robotState == RobotState.kTeleop)
            robotState = RobotState.kDisabledAfterGame;
        else if (robotState == RobotState.kTest)
            robotState = RobotState.kDisabledBeforeGame;

        disabled.init();
    }

    /**
     * This method is called periodically when the robot is disabled.
     */
    @Override
    public void disabledPeriodic()
    {
        disabled.periodic();
    }

    /**
     * This method returns the current state of the robot
     * @return the robot state
     * @see RobotState
     */
    public static RobotState getRobotState()
    {
        return robotState;
    }
}