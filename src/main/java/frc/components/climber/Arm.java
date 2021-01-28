package frc.components.climber;

import frc.robot.Port;

// Import material to implement motors, encoders, and controls.
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;

/**
 * Class to control the Arm subsystem of the Climber.
 * @author Annika Price
 */
public class Arm
{
    private static final String className = new String("[Arm]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    // Declare and initialize private instance variables.
    private static CANSparkMax armMotor = new CANSparkMax(Port.Motor.CAN_CLIMBER_ARM, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANEncoder armEncoder = armMotor.getEncoder();
    private static CANDigitalInput forwardLimitSwitch;
    private static CANDigitalInput reverseLimitSwitch;
    private static final int DEFAULT_POSITION = 10; // TODO: find actual default encoder position 
    private static final int ERROR_THRESHOLD = 5; // TODO: find actual threshold we want for the robot
    private static final double MINIMUM_HEIGHT = 0.0;
    private static final double MAXIMUM_HEIGHT = 50.0;

    private static Arm instance = new Arm();

    /**
     * The constructor for the Arm class. 
     */
    private Arm()
    {
        System.out.println(className + " : Constructor Started");

        armMotor.restoreFactoryDefaults();
        armMotor.setInverted(true);
        armMotor.setIdleMode(IdleMode.kBrake);

        armEncoder.setPosition(0);
        //soft limit switches
        // armMotor.setSoftLimit(SoftLimitDirection.kReverse, 0);
        armMotor.enableSoftLimit(SoftLimitDirection.kReverse, false);
        // armMotor.setSoftLimit(SoftLimitDirection.kForward, 140);
        armMotor.enableSoftLimit(SoftLimitDirection.kForward, false);

        //hard limit switches
        reverseLimitSwitch = armMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
        reverseLimitSwitch.enableLimitSwitch(false);
        forwardLimitSwitch = armMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
        forwardLimitSwitch.enableLimitSwitch(false);

        // armMotor.setOpenLoopRampRate(0.1);
        // armMotor.setSmartCurrentLimit(40);

        System.out.println(className + ": Constructor Finished"); 
    }

    /**
     * The method to retrieve the instance of an Arm.
     * @return instance 
     */
    public static Arm getInstance()
    {
        return instance;
    }

    /**
     * The method to retrieve the current encoder position.
     * @return extensionMotor.getSelectedSensorPosition(0) 
     */
    public double getEncoderPosition()
    {
        return armEncoder.getPosition();
    }

    /**
     * The method to set the position of the encoder.
     * @param position The position of the encoder.
     */
    public void setEncoderPosition(int position)
    {
        armEncoder.setPosition(position);
    }

    /**
     * Sets the speed of the extensionMotor.
     * @param speed The speed at which the Arm extends. Values are from -1 to 1.
     */
    public void setExtensionSpeed(double speed)
    {
        armMotor.set(speed);
    }

    /**
     * The method to retract the Arm.
     * @param speed (this value is modified to always be negative)
     */
    public void retractArm(double speed)
    {
        double currentPosition = getEncoderPosition();
        if(currentPosition > MINIMUM_HEIGHT - ERROR_THRESHOLD)
        {
            setExtensionSpeed(-Math.abs(speed));
        }
        else
        {
            System.out.println("Cannot lower the arm. It is already at the minimum height.");
        }
    }

    /**
     * The method to extend the Arm. 
     * @param speed (this value is modified to always be positive)
     */
    public void extendArm(double speed)
    {
        double currentPosition = getEncoderPosition();
        if(currentPosition < MAXIMUM_HEIGHT + ERROR_THRESHOLD)
        {
            setExtensionSpeed(Math.abs(speed));
        }
        else
        {
            System.out.println("Cannot raise the arm. It is already at the maximum height.");
        }
    }

    /**
     * The method to stop the extension or retraction of the Arm.
     */
    public void holdArm()
    {
        setExtensionSpeed(0.05);
    }

    public void stopArm()
    {
        setExtensionSpeed(0.0);
    }

    public void setCoastMode()
    {
        armMotor.setIdleMode(IdleMode.kCoast);
    }

    public void setBrakeMode()
    {
        armMotor.setIdleMode(IdleMode.kBrake);
    }
    /**
     * The method to set the Arm to the default position.
     */
    public void setArmToDefaultPosition()
    {
        double currentPosition = getEncoderPosition();
        if(currentPosition < DEFAULT_POSITION - ERROR_THRESHOLD)   
        {
            extendArm(0.5);
        }
        else if(currentPosition > DEFAULT_POSITION + ERROR_THRESHOLD)
        {
            retractArm(-0.5);
        }
        else 
        {
            holdArm();
        }
    }

    public String getArmData()
    {
        return String.format("%+5.2f %6f %5f %4.1f %4.1f", 
            armMotor.get(), armEncoder.getPosition(), armEncoder.getVelocity(),
            armMotor.getOutputCurrent(), armMotor.getMotorTemperature());
    }
}