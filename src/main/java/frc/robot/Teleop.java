package frc.robot;

import frc.components.climber.Climber;
import frc.components.drivetrain.Drivetrain;
import frc.components.powercellsupervisor.PowerCellSupervisor;
import frc.components.powercellsupervisor.intake.Intake;
import frc.components.powercellsupervisor.intake.Roller;
import frc.components.powercellsupervisor.intake.Wrist;
import frc.components.powercellsupervisor.shooter.Flywheel;
import frc.components.powercellsupervisor.shooter.Shooter;
import frc.components.powercellsupervisor.shooter.Shroud;
import frc.components.powercellsupervisor.shooter.Turret;
import frc.components.powercellsupervisor.shuttle.Shuttle;

import frc.controls.DriverController;
import frc.controls.DriverController.DriverAxisAction;
import frc.controls.DriverController.DriverButtonAction;
import frc.controls.DriverController.DriverPOVAction;
import frc.controls.OperatorController;
import frc.controls.OperatorController.OperatorAxisAction;
import frc.controls.OperatorController.OperatorButtonAction;
import frc.shuffleboard.MainShuffleboard;

/**
 * Class for controlling the robot during the Teleop period.
 * @author Darren Fife
 */
public class Teleop
{
    private static final String className = new String("[Teleop]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    // private static Vision vision = Vision.getInstance();
    // private static Climber climber = Climber.getInstance();
    // private static Drivetrain drivetrain = Drivetrain.getInstance();
    // private static PowerCellSupervisor powercellsupervisor = PowerCellSupervisor.getInstance();
    
    // private static DriverController driverController = DriverController.getInstance();
    // private static OperatorController operatorController = OperatorController.getInstance();
    private static MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();

    private static Shuttle shuttle = Shuttle.getInstance();
    private static Shooter shooter = Shooter.getInstance();
    private static Drivetrain drivetrain = Drivetrain.getInstance();
    private static Climber climber = Climber.getInstance();
    private static OperatorController operatorController = OperatorController.getInstance();
    private static DriverController driverController = DriverController.getInstance();
    // private static PowerCellSupervisor powerCellSupervisor = PowerCellSupervisor.getInstance();
    private static Flywheel flywheel = Flywheel.getInstance();
    private static Turret turret = Turret.getInstance();
    private static Shroud shroud = Shroud.getInstance();
    private static Intake intake = Intake.getInstance();
    private static Roller roller = Roller.getInstance();
    private static Wrist wrist = Wrist.getInstance();
    private static Teleop teleop = new Teleop();

    private Teleop()
    {
        System.out.println(className + " : Constructor Started");

        System.out.println(className + ": Constructor Finished");
    }

    public static Teleop getInstance()
    {
        return teleop;
    } 

    /**
     * Contains all the intializations needed before teleop.
     */
    public void init()
    {
        // mainShuffleboard.setDriverControllerSettings();
        // mainShuffleboard.setOperatorControllerSettings();
        driverController.resetRumbleCounter();
        // shooter.turnLightOn();
    }

    /**
     * Contains the statements needed to be called periodically during teleop.
     */
    public void periodic()
    {
        
        // System.out.println(flywheel.getRPM());
        if(driverController.getAction(DriverButtonAction.kIntakeDown))
        {
            wrist.forceLower();
        }
        else if(driverController.getAction(DriverButtonAction.kIntakeUp))
        {
            wrist.forceRaise();
        }
        
        //running the shuttle with an override capability
        if(operatorController.getAction(OperatorButtonAction.kShuttleOverride))
        {
            shuttle.overrideFSM();
            shuttle.overrideSetSpeed(0.25);
        }
        else
        {
            shuttle.runFSM();
        }
   
        // running the shooter
        if (operatorController.getAction(OperatorButtonAction.kShooterOverride) 
            && operatorController.getAction(OperatorButtonAction.kFlywheelOverride))
        {
            shooter.overrideFSM();
            turret.setSpeed(operatorController.getAction(OperatorAxisAction.kTurret)); 
            flywheel.setSpeedOverride(operatorController.getAction(OperatorAxisAction.kShooterPower));
            shroud.setSpeed(operatorController.getAction(OperatorAxisAction.kShroud));        
        }
        else if(operatorController.getAction(OperatorButtonAction.kShooterOverride))
        {
            shooter.overrideFSM();
            turret.setSpeed(operatorController.getAction(OperatorAxisAction.kTurret)); 
            // flywheel.setSpeedOverride(operatorController.getAction(OperatorAxisAction.kShooterPower));
            shroud.setSpeed(operatorController.getAction(OperatorAxisAction.kShroud));
        }
        else
        {
            shooter.runFSM();
        }

        //running the intake
        if(driverController.getAction(DriverButtonAction.kIntakeReverse))
        {
            intake.overrideFSM();
            roller.setSpeedOverride(-1.0);
        }
        else if(driverController.getAction(DriverButtonAction.kIntakeOn))
        {
            intake.overrideFSM();
            roller.setSpeedOverride(1);
        }
        else
        {
            intake.runFSM();
        }

        //run the drivetrain
        drivetrain.westCoastDrive(driverController.getAction(DriverAxisAction.kMove)
                                , driverController.getAction(DriverAxisAction.kRotate));

        if(driverController.getAction() == DriverPOVAction.kShiftingUp.direction)
        {
            drivetrain.forceShiftUp();
        }
        else if(driverController.getAction() == DriverPOVAction.kShiftingDown.direction)
        {
            drivetrain.forceShiftDown();

        }

        //run the climber
        climber.run();
        //mainShuffleboard.updateSensors();

        driverController.checkRumbleEvent();
    }

    public void end()
    {
        shooter.overrideFSM();
    }
}