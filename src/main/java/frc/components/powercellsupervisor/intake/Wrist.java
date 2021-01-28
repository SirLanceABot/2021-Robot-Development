package frc.components.powercellsupervisor.intake;

import frc.robot.Port;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 * Class for controlling the Wrist of the Intake system.
 * @author Darren Fife
 */
// TODO: Test if the system works with only one solenoid.
public class Wrist
{
    // Constants
    private static enum WristState
    {
        kUp, kLowering, kDown, kRaising;
    }

    private static DigitalInput magneticSensorExtended = new DigitalInput(Port.Sensor.WRIST_EXTEND_LIMIT_SWITCH);
    private static DigitalInput magneticSensorRetracted = new DigitalInput(Port.Sensor.WRIST_RETRACT_LIMIT_SWITCH);

    private static DoubleSolenoid wristSolenoid = new DoubleSolenoid(Port.Pneumatic.INTAKE_EXTEND, Port.Pneumatic.INTAKE_RETRACT);
    // private static DoubleSolenoid wristSolenoidRight = new DoubleSolenoid(WRIST_SOLENOID_RIGHT_EXTEND_PORT, WRIST_SOLENOID_RIGHT_RETRACT_PORT);

    private WristState wristState = WristState.kUp;
    private static Timer timer = new Timer();
    private double loweringTimeOut = 1.0;
    private double raisingTimeOut = 1.0;

    private static Wrist instance = new Wrist();

    public Wrist()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    public static Wrist getInstance()
    {
        return instance;
    }

    /**
     * Raises the Wrist on the Intake system.
     */
    public void raise()
    {
        if(wristState == WristState.kDown || wristState == WristState.kLowering)
        {
            timer.reset();
            setPneumatics(DoubleSolenoid.Value.kForward);
            wristState = WristState.kRaising;
            timer.start();
        }
    }

    /**
     * Checks if the Wrist is up or should be up by this time.
     * @return Whether the Wrist is up (true) or not.
     */
    public boolean isUp(boolean useSensor)
    {
        if(wristState == WristState.kUp)
        {
            return true;
        }
        else if(!useSensor && (wristState == WristState.kRaising) && (timer.get() >= raisingTimeOut)) // TODO: Check limit switch possibly
        {
            wristState = WristState.kUp;
            timer.stop();
            return true;
        }
        else if(useSensor && (wristState == WristState.kRaising) && !magneticSensorRetracted.get())
        {
            wristState = WristState.kUp;
            timer.stop();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Lowers the Wrist on the Intake system.
     */
    public void lower()
    {
        if(wristState == WristState.kUp || wristState == WristState.kRaising)
        {
            timer.reset();
            setPneumatics(DoubleSolenoid.Value.kReverse);
            wristState = WristState.kLowering;
            timer.start();
        }
    }

    /**
     * Checks if the Wrist is down or should be down by this time.
     * @return Whether the Wrist is down (true) or not.
     */
    public boolean isDown(boolean useSensor)
    {
        if(wristState == WristState.kDown)
        {
            return true;
        }
        else if(!useSensor && (wristState == WristState.kLowering) && (timer.get() >= loweringTimeOut)) // TODO: Check limit switch possibly
        {
            wristState = WristState.kDown;
            timer.stop();
            return true;
        }
        else if(useSensor && (wristState == WristState.kLowering) && !magneticSensorExtended.get())
        {
            wristState = WristState.kDown;
            timer.stop();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Synchronizes the position with the state by force raising the Wrist.
     */
    public void forceRaise()
    {
        setPneumatics(DoubleSolenoid.Value.kForward);
        wristState = WristState.kUp;
    }

    /**
     * Synchronizes the position with the state by force lowering the Wrist.
     */
    public void forceLower()
    {
        setPneumatics(DoubleSolenoid.Value.kReverse);
        wristState = WristState.kDown;
    }

    /**
     * Sets the time out for lowering the Wrist.
     * @param newTimeOut
     */
    public void setLoweringTimeOut(double newTimeOut)
    {
        loweringTimeOut = newTimeOut;
    }

    /**
     * Sets the time out for raising the Wrist.
     * @param newTimeOut
     */
    public void setRaisingTimeOut(double newTimeOut)
    {
        raisingTimeOut = newTimeOut;
    }

    /**
     * Sets the pneumatics position of the Wrist on the Intake system.
     * <p>DoubleSolenoid.Value.kForward raises the Wrist.
     * <p>DoubleSolenoid.Value.kReverse lowers the Wirst.
     * @param position The position of the cylinder.
     */
    private static void setPneumatics(DoubleSolenoid.Value position)
    {
        wristSolenoid.set(position);
        // wristSolenoidRight.set(position);
    }

    
    public static boolean getExtendedSensorValue()
    {
        return magneticSensorExtended.get();
    }

    public static boolean getRetractedSensorValue()
    {
        return magneticSensorRetracted.get();
    }
}