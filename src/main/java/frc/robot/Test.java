package frc.robot;

import frc.vision.TargetDataB;
import frc.vision.TargetDataE;
import frc.vision.Vision;
import frc.controls.DriverController;
import frc.controls.Xbox;
import frc.controls.OperatorController.OperatorButtonAction;
import frc.controls.OperatorController;
import frc.controls.Logitech;
import frc.shuffleboard.MainShuffleboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Relay.Direction;
import frc.components.climber.Arm;
import frc.components.climber.Winch;
import frc.components.drivetrain.Drivetrain;
import frc.components.drivetrain.Shifter;
import frc.components.powercellsupervisor.PowerCellSupervisor;
import frc.components.powercellsupervisor.intake.Intake;
import frc.components.powercellsupervisor.intake.Roller;
import frc.components.powercellsupervisor.intake.Wrist;
import frc.components.powercellsupervisor.shooter.Flywheel;
import frc.components.powercellsupervisor.shooter.Shooter;
import frc.components.powercellsupervisor.shooter.Shroud;
import frc.components.powercellsupervisor.shooter.Turret;
import frc.components.powercellsupervisor.shuttle.Shuttle;

/**
 * @author Elliot Measel
 * class for the test mode on the robot
 * this is a test....this is only a test
 * more testing
 * and again
 */
public class Test
{

    private static final String className = new String("[Test]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    // Elliot's testing
    // private static Vision vision = Vision.getInstance();

    // private static TargetDataB turret = new TargetDataB();
    // private static TargetDataE intake = new TargetDataE();

    // private static DriverController driverController = DriverController.getInstance();
    // private static OperatorController operatorController = OperatorController.getInstance();
    // private static MainShuffleboard mainShuffleBoard = MainShuffleboard.getInstance();

    // private static CANSparkMax leftMotor = new CANSparkMax(3, MotorType.kBrushless);
    // private static CANSparkMax rightMotor = new CANSparkMax(2, MotorType.kBrushless);

    // Darren's testing testing 1 2
    private enum Test_Options
    {
        CLIMBER,
        DRIVETRAIN,
        INTAKE,
        SHOOTER,
        SHUTTLE
    }
    private static Test_Options TEST_OPTION = Test_Options.SHOOTER; // The test that you want to conduct

    private static OperatorController operatorController = OperatorController.getInstance();
    private static DriverController driverController = DriverController.getInstance();
    private static Drivetrain drivetrain = Drivetrain.getInstance();
    private static Shuttle shuttle = Shuttle.getInstance();
    private static PowerCellSupervisor powerCellSupervisor = PowerCellSupervisor.getInstance();
    private static Roller roller = Roller.getInstance();
    private static Wrist wrist = Wrist.getInstance();
    private static Shifter shifter = Shifter.getInstance();
    private static Shroud shroud = Shroud.getInstance();
    private static Flywheel flywheel = Flywheel.getInstance();
    private static Turret turret = Turret.getInstance();
    private static Intake intake = Intake.getInstance();
    private static Shooter shooter = Shooter.getInstance();
    private static Arm arm = Arm.getInstance();
    private static Winch winch = Winch.getInstance();
    // private static Relay led = new Relay(0);
    private static boolean timerFlag = true;
    private static Timer timer = new Timer();


    private static Test instance = new Test();

    /**
     * private constructor for test class
     */
    private Test()
    {
        System.out.println(className + " : Constructor Started");
    
        System.out.println(className + ": Constructor Finished");
    }

    public static Test getInstance()
    {
        return instance;
    }

    public void init()
    {
        // Choose the components you want to test by uncommenting their init method call
        if(TEST_OPTION == Test_Options.CLIMBER) // Climber test
        {
            testArmInit();
            // testClimberInit();
            testWinchInit();
        }
        else if(TEST_OPTION == Test_Options.DRIVETRAIN) // Drivetrain test
        {
            testDrivetrainInit();
            testShifterInit();
        }
        else if(TEST_OPTION == Test_Options.INTAKE) // Intake test
        {
            testIntakeInit();
            testRollerInit();
            testWristInit();
        }
        else if(TEST_OPTION == Test_Options.SHOOTER) // Shooter test
        {
            testFlywheelInit();
            //testGateInit();
            testShooterInit();
            testShroudInit();
            testTurretInit();
        }
        else if(TEST_OPTION == Test_Options.SHUTTLE) // Shuttle test
        {
            testShuttleInit();
        }
        
    }

    public void periodic()
    {
        if(timerFlag)
        {
            timer.reset();
            timer.start();
            timerFlag = false;
        }
        // mainShuffleBoard.updateSensors();
        // testVisionUsingDrivetrain();

        // Choose the components you want to test by uncommenting their periodic method call
        if(TEST_OPTION == Test_Options.CLIMBER) // Climber test
        {
            testArmPeriodic();
            // testClimberPeriodic();
            testWinchPeriodic();
            testDrivetrainPeriodic();
        }
        else if(TEST_OPTION == Test_Options.DRIVETRAIN) // Drivetrain test
        {
            testDrivetrainPeriodic();
            testShifterPeriodic();
        }
        else if(TEST_OPTION == Test_Options.INTAKE) // Intake test
        {
            testIntakePeriodic();
            testRollerPeriodic();
            testWristPeriodic();
        }
        else if(TEST_OPTION == Test_Options.SHOOTER) // Shooter test
        {

            testDrivetrainPeriodic();
            //flywheel.run(5000);
            //testFlywheelPeriodic();
            //testGatePeriodic();
            //testShooterPeriodic();
            //testShroudPeriodic();
            
            //flywheel.run(6000);
            //System.out.println(operatorController.getAction(OperatorController.AxisAction.kShroud));
            //System.out.println(shroud.getEncoder());
            //shroud.setSpeed(operatorController.getAction(OperatorController.AxisAction.kShroud) / 2.0);

            //flywheel.run(1.0);

            //System.out.println("RPM: " + Math.round(flywheel.getRPM()) + '\t' + "Time: " + timer.get());
            testTurretPeriodic();
            // led.set(Relay.Value.kForward);
            intake.runFSM();
            shuttle.runFSM();
            shooter.runFSM();

            // if(driverController.getRawButton(1))
            // {
            //     shifter.forceShiftUp();
            //     //drivetrain.coolMotors();
            // }
            // else if(driverController.getRawButton(2))
            // {
            //     shifter.forceShiftDown();
            // }


        }
        else if(TEST_OPTION == Test_Options.SHUTTLE) // Shuttle test
        {
            
        }

    }

    // public void testVisionUsingDrivetrain()
    // {
    //     if(!Vision.isConnected()) // For the actual robot, we will have an else block to switch to manual mode.
    //     {
    //         System.out.println("Waiting for a connection.");
    //         return;
    //     }

    //     intake = vision.getIntake();
    //     turret = vision.getTurret();
      
    //     if(turret.isFreshData())
    //     {
    //         if(turret.getAngleToTurn() > 1)
    //         {
    //             System.out.println("turning to the right" + "\t\tangle to turn: " + turret.getAngleToTurn());
    //             rightMotor.set(-0.1);
    //             leftMotor.set(0.1);
    //         }
    //         else if(turret.getAngleToTurn() < -1)
    //         {
    //             System.out.println("turning to the left" +  "\t\tangle to turn: " + turret.getAngleToTurn());
    //             rightMotor.set(0.1);
    //             leftMotor.set(-0.1);
    //         }
    //         else
    //         {
    //             System.out.print("it is off" + "\t\tangle to turn: " + turret.getAngleToTurn());
    //             rightMotor.set(0.0);
    //             leftMotor.set(0.0);
    //         }
    //     }
    // }

    /**
     * Deals with the driver controls.
     */
    private void driverControls()
    {
        double move = driverController.getRawAxis(Xbox.Axis.kLeftY);
        double rotate = driverController.getRawAxis(Xbox.Axis.kRightX);
        double intake = driverController.getRawAxis(Xbox.Axis.kLeftTrigger);
        double wrist = driverController.getRawAxis(Xbox.Axis.kRightTrigger);

        //drivetrain.westCoastDrive(move, rotate);

        if(intake >= 0.5)
        {
            // Call up powercellsupervisor to turn on the roller
        }
        else if(wrist >= 0.5)
        {
            // Call up powercellsupervisor to toggle the wrist
        }
    }

    private void operatorControls()
    {
        // double turret = operatorController.getRawAxis(Logitech.Axis.kZAxis);
        // boolean shoot = operatorController.getRawButton(Logitech.Button.kTrigger);

        // if(turret >= 0.5)
        // {
        //     // Call up powercellsupervisor to turn the turret right
        // }
        // else if(turret <= -0.5)
        // {
        //     // Call up powercellsupervisor to turn the turret left
        // }
        // else
        // {
        //     // Call up powercellsupervisor to stop the turret
        // }
        
        // if(shoot)
        // {
        //     // Call up powercellsupervisor to shoot the ball
        // }
        // else
        // {
        //     // Call up powercellsupervisor to turn off the flywheel
        // }
    }

    /**
     * An Initialization method for testing the Arm.
     */
    private void testArmInit()
    {
        arm.setEncoderPosition(0);
    }

    /**
     * A Periodic method for testing the Arm.
     */
    private void testArmPeriodic()
    {
        if(driverController.getRawButton(DriverController.Button.kA))
        {
            arm.setExtensionSpeed(Math.abs(driverController.getRawAxis(DriverController.Axis.kLeftTrigger)));
        }
        else if(driverController.getRawButton(DriverController.Button.kB))
        {
            arm.setExtensionSpeed(-0.10);
        }
        else
        {
            arm.setExtensionSpeed(0.0);
        }
        System.out.println("Arm = " + arm.getEncoderPosition());
    }

    /**
     * An Initialization method for testing the Climber.
     */
    private void testClimberInit()
    {
        
    }

    /**
     * A Periodic method for testing the Climber.
     */
    private void testClimberPeriodic()
    {

    }

    /**
     * An Initialization method for testing the Winch.
     */
    private void testWinchInit()
    {
        
    }

    /**
     * A Periodic method for testing the Winch.
     */
    private void testWinchPeriodic()
    {
        if(driverController.getRawButton(DriverController.Button.kX))
        {
            winch.setWinchSpeed(1.0);
        }
        else if(driverController.getRawButton(DriverController.Button.kY))
        {
            winch.setWinchSpeed(-1.0);
        }
        else
        {
            winch.setWinchSpeed(0.0);
        }
    }

    /**
     * An Initialization method for testing the Drivetrain.
     */
    private void testDrivetrainInit()
    {
        
    }

    /**
     * A Periodic method for testing the Drivetrain.
     */
    private void testDrivetrainPeriodic()
    {
        //drivetrain.setLeftPower(0.1);
        //drivetrain.setRightPower(0.1);
        drivetrain.westCoastDrive(driverController.getAction(DriverController.DriverAxisAction.kMove), driverController.getAction(DriverController.DriverAxisAction.kRotate));
        
        
        
        // if(driverController.getRawButton(Xbox.Button.kA))
        // {
        //     drivetrain.setLeftPower(driverController.getRawAxis(Xbox.Axis.kLeftY));
        // }
        // else if(driverController.getRawButton(Xbox.Button.kB))
        // {
        //     drivetrain.setRightPower(driverController.getRawAxis(Xbox.Axis.kLeftY));
        // }
        // else
        // {
        //     drivetrain.stop();
        // }
    }

    /**
     * An Initialization method for testing the Shifter.
     */
    private void testShifterInit()
    {
        
    }

    /**
     * A Periodic method for testing the Shifter.
     */
    private void testShifterPeriodic()
    {
        // Button X Shifts Up the drive gear
        if(driverController.getRawButton(Xbox.Button.kX))
        {
            System.out.println("Force up shifting");
            drivetrain.forceShiftUp();
        }
        // Button Y Shifts Down the drive gear
        else if(driverController.getRawButton(Xbox.Button.kY))
        {
            System.out.println("Force down shifting");
            drivetrain.forceShiftDown();
        }
    }

    /**
     * An Initialization method for testing the Intake.
     */
    private void testIntakeInit()
    {
        
    }

    /**
     * A Periodic method for testing the Intake.
     */
    private void testIntakePeriodic()
    {   
        intake.runFSM();
    }

    /**
     * An Initialization method for testing the Roller.
     */
    private void testRollerInit()
    {
        
    }

    /**
     * A Periodic method for testing the Roller.
     */
    private void testRollerPeriodic()
    {
        // Button X makes the Rollers intake
        if(driverController.getRawButton(Xbox.Button.kX))
        {
            roller.intake();
        }
        // Button Y makes the Rollers eject
        else if(driverController.getRawButton(Xbox.Button.kY))
        {
            roller.eject();
        }
        else
        {
            roller.stop();
        }
    }

    /**
     * An Initialization method for testing the Wrist.
     */
    private void testWristInit()
    {
        
    }

    /**
     * A Periodic method for testing the Wrist.
     */
    private void testWristPeriodic()
    {
        // Button A raises the Wrist
        if(driverController.getRawButton(Xbox.Button.kA))
        {
            System.out.println("Force wrist up");
            wrist.forceRaise();
        }
        // Button B lowers the Wrist
        else if(driverController.getRawButton(Xbox.Button.kB))
        {
            System.out.println("Force wrist down");
            wrist.forceLower();
        }
    }

    /**
     * An Initialization method for testing the Flywheel.
     */
    private void testFlywheelInit()
    {
        
    }

    /**
     * A Periodic method for testing the Flywheel.
     */
    private void testFlywheelPeriodic()
    {
        //System.out.println(flywheel.getRPM());
        // Button A runs the Flywheel using the Left Y joystick
        if(operatorController.getAction(OperatorController.OperatorAxisAction.kShooterPower) > 0.25)
        {
            flywheel.run(operatorController.getAction(OperatorController.OperatorAxisAction.kShooterPower));
        }
        else
        {
            flywheel.stop();
        }
    }

    /**
     * An Initialization method for testing the Gate.
     */
    private void testGateInit()
    {
        
    }

    /**
     * A Periodic method for testing the Gate.
     */
    private void testGatePeriodic()
    {

    }

    /**
     * An Initialization method for testing the Shooter.
     */
    private void testShooterInit()
    {
        
    }

    /**
     * A Periodic method for testing the Shooter.
     */
    private void testShooterPeriodic()
    {

    }

    /**
     * An Initialization method for testing the Shroud.
     */
    private void testShroudInit()
    {
        
    }

    /**
     * A Periodic method for testing the Shroud.
     */
    private void testShroudPeriodic()
    {

    }

    /**
     * Amethod for testing the Turret.
     */
    private void testTurretInit()
    {
        
    }

    /**
     * A Periodic method for testing the Turret.
     */
    private void testTurretPeriodic()
    {
        // if(operatorController.getAction(ButtonAction.kAutoAim))
        // {
        //     turret.alignWithTarget();
        //     System.out.println("Aligned with target");
        // }
        // Button B rotates the Turret using the Left X joystick
        if(Math.abs(operatorController.getAction(OperatorController.OperatorAxisAction.kTurret)) > 0.2)
        {
            turret.setSpeed(operatorController.getAction(OperatorController.OperatorAxisAction.kTurret) / 2.0);
        }
        else
        {
            turret.stop();
        }
    }

    /**
     * An Initialization method for testing the Shuttle.
     */
    private void testShuttleInit()
    {
        
    }

    /**
     * A Periodic method for testing the Shuttle.
     */
    private void testShuttlePeriodic()
    {
        shuttle.runFSM();
    }
}

// TODO: Add test only setSpeeds and test only getInstances
    /**
     * FOR USE ONLY IN TEST!
     * @param speed
     */
    /*
    @Deprecated
    public void setSpeedTestOnly(double speed)
    {
        setSpeed(speed);
    }
    */