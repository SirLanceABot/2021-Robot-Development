package frc.components.climber;

import frc.robot.Port;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;

/**
 * Reels in rope to lift the robot.
 * @author Joey Pietrogallo
 */
public class Winch
{
    private static final String className = new String("[Winch]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private static CANSparkMax winchMotor = new CANSparkMax(Port.Motor.CAN_CLIMBER_WINCH, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANEncoder winchEncoder = winchMotor.getEncoder();
    private static CANDigitalInput forwardLimitSwitch;
    private static CANDigitalInput reverseLimitSwitch;

    //TODO: determine actual height constraints.
    private static final double MINIMUM_HEIGHT = 0.0;
    private static final double MAXIMUM_HEIGHT = 50.0;
    private static final int ERROR_THRESHOLD = 5; // TODO: find actual threshold we want for the robot

    private static Winch instance = new Winch();
    
    /**
     * The constructor for the Winch.
     */
    private Winch()
    {
        System.out.println(className + " : Constructor Started");

        winchMotor.restoreFactoryDefaults();
        winchMotor.setInverted(false);
        winchMotor.setIdleMode(IdleMode.kBrake);

        //soft limit switches
        winchMotor.setSoftLimit(SoftLimitDirection.kReverse, 0);
        winchMotor.enableSoftLimit(SoftLimitDirection.kReverse, false);
        winchMotor.setSoftLimit(SoftLimitDirection.kForward, 0);
        winchMotor.enableSoftLimit(SoftLimitDirection.kForward, false);

        //hard limit switches
        reverseLimitSwitch = winchMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
        reverseLimitSwitch.enableLimitSwitch(false);
        forwardLimitSwitch = winchMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
        forwardLimitSwitch.enableLimitSwitch(false);

        // winchMotor.setOpenLoopRampRate(0.1);
        winchMotor.setSmartCurrentLimit(40);
    
        System.out.println(className + ": Constructor Finished");
    }

    /**
     * Returns an instance of the Winch.
     * @return instance
     */
    public static Winch getInstance()
    {
        return instance;
    }

    /**
     * Returns the encoder value of winchMotor.
     * @return winchEncoder.getPosition()   
     * The location of the encoder (ticks?).
     */
    public double getEncoderPosition()
    {
        return winchEncoder.getPosition();
    }

    /**
     * Sets the speed of winchMotor.
     * @param speed The speed at which the winch spools the rope. Values are from -1 to 1.
     */
    public void setWinchSpeed(double speed)
    {
        winchMotor.set(speed);
    }
    
    //--------------------------------------------------------//
    //             TODO: HOW FAST TO REEL IN ROPE
    //--------------------------------------------------------//

    //constant for how fast the winch reels in rope
    private final double WINCH_WIND_SPEED = 0.5;

    public void windWinch()
    {
        setWinchSpeed(WINCH_WIND_SPEED);
    }

    /**
     * The method to stop the winch.
     */
    public void stopWinch()
    {
        setWinchSpeed(0.0);
    }

    /**
     * The method to lower the Winch.
     */
    public void lowerWinch(double speed)
    {
        setWinchSpeed(-Math.abs(speed));

        // double currentPosition = getEncoderPosition();
        // if(currentPosition > MINIMUM_HEIGHT - ERROR_THRESHOLD)
        // {
        //     setWinchSpeed(-Math.abs(speed));
        // }
        // else
        // {
        //     System.out.println("Cannot lower the winch. It is already at the minimum height.");
        // }
    }

    /**
     * The method to raise the Winch.
     */
    public void raiseWinch(double speed)
    {
        setWinchSpeed(Math.abs(speed));
        // double currentPosition = getEncoderPosition();
        // if(currentPosition < MAXIMUM_HEIGHT + ERROR_THRESHOLD)
        // {
        //     setWinchSpeed(Math.abs(speed));
        // }
        // else
        // {
        //     System.out.println("Cannot raise the winch. It is already at the maximum height.");
        // }
    }

    public String getWinchData()
    {
        return String.format("%+5.2f %6f %5f %4.1f %4.1f", 
            winchMotor.get(), winchEncoder.getPosition(), winchEncoder.getVelocity(),
            winchMotor.getOutputCurrent(), winchMotor.getMotorTemperature());
    }
}