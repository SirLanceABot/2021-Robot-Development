package frc.components.powercellsupervisor.shooter;

import frc.robot.Port;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

/**
 * Class for controlling the shroud 
 * @author Darren Fife and Maxwell Li
 */
public class Shroud
{
    private static final String className = new String("[Shroud]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private static final int TIMEOUT_MS = 30;
    private static final int UPPER_LIMIT = 122; //TODO: Find out the upper limit
    private static final int LOWER_LIMIT = -5;   //TODO: Find out the lower limit
    private static final int TRENCH_SHOT = 70;
    private static final int CLOSE_SHOT = 40;
    private static final int TOTAL_TICKS = 100; //TODO: Find out the total ticks
    private static final int TOTAL_DEGREES = 45; //TODO: Find the total degrees
    private static final int PLUS_MINUS_ERROR = 2;

    private static double currentAngle;
    private static boolean isMoving = false;
    private static TalonSRX motor = new TalonSRX(Port.Motor.CAN_SHROUD);
    private static Shroud instance = new Shroud();

    private Shroud()
    {
        System.out.println(className + " : Constructor Started");

        motor.configFactoryDefault();
        motor.setInverted(true);
        motor.setNeutralMode(NeutralMode.Brake);

        //feedback sensor
        motor.configSelectedFeedbackSensor(FeedbackDevice.Analog);
        motor.setSensorPhase(true);
        motor.setSelectedSensorPosition(0);
        motor.configClearPositionOnLimitR(true, 10);
        //motor.configFeedbackNotContinuous(false, 10);

        //soft limits
        motor.configReverseSoftLimitThreshold(LOWER_LIMIT);
        motor.configReverseSoftLimitEnable(false);
        motor.configForwardSoftLimitThreshold(UPPER_LIMIT);
        motor.configForwardSoftLimitEnable(true);

        //hard limits
        motor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
        motor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        
        //current limits
        motor.configOpenloopRamp(0.1);
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, 0.5), 10);

        System.out.println(className + ": Constructor Finished");
    }

    /**
     * @return returns THE (1) instance of the shroud 
     */
    public static Shroud getInstance()
    {
        return instance;
    }

    /**
     * the one method that sets the speed of the motor
     * @param speed
     */
    public void setSpeed(double speed)
    {
        // motor.set(ControlMode.PercentOutput, 0.0);
        motor.set(ControlMode.PercentOutput, speed);
        //System.out.println(getEncoderPosition());
        setCurrentAngle();
    }

    /**
     * Stops the motors
     */
    public void stop()
    {
        setSpeed(0.0);
    }

    public double getEncoder()
    {
        return(motor.getSelectedSensorPosition());
    }

    /**
     * Getter function for the current angle
     * @return the current angle of the robot
     */
    public double getCurrentAngle()
    {
        return currentAngle;
    }

    /**
     * finds the position and sets it the instance variable to it
     * current position is in absolute degrees
     */
    private void setCurrentAngle()
    {
        currentAngle = ((getEncoderPosition() - LOWER_LIMIT) / TOTAL_TICKS) * TOTAL_DEGREES;
    }

    /**
     * overides whatever value is stored in the variable and stores the value to it
     * current position is in absolute degrees
     * @param position
     */
    private void setCurrentAngle(double angle)
    {
        currentAngle = angle;
    }

    /**
     * converts the angle to encoder ticks
     * @param angle
     * @return the number of ticks corresponding to the sent angle
     */
    private double angleToTicks(double angle)
    {
        return (angle / TOTAL_DEGREES) * TOTAL_TICKS;  //need to add the calulation based on the total ticks
    }

    /**
     * gets the encoder value
     * @return the encoder value
     */
    public double getEncoderPosition()
    {
        return motor.getSelectedSensorPosition(0);
    }

    /**
     * sets the shroud to the angle of the trench shot
     */
    private void setAngleToTrenchShot()
    {
        if(motor.getSelectedSensorPosition(0) < TRENCH_SHOT - PLUS_MINUS_ERROR) {setSpeed(0.5);}
        if(motor.getSelectedSensorPosition(0) > TRENCH_SHOT + PLUS_MINUS_ERROR) {setSpeed(-0.5);}
    }
    
    /**
     * sets the shroud to the angle of the close shot
     */
    public void setAngleToCloseShot()
    {
        if(motor.getSelectedSensorPosition(0) < CLOSE_SHOT - PLUS_MINUS_ERROR) {setSpeed(0.3);}
        else if(motor.getSelectedSensorPosition(0) > CLOSE_SHOT + PLUS_MINUS_ERROR) {setSpeed(-0.3);}
        else setSpeed(0);
        System.out.println(motor.getSelectedSensorPosition(0));
    }

    /**
     * Set the absolute position (must be within _ and _)
     * 0 degrees is the lower limit (most likely horizontal) TODO: Find the angle of the lower limit and upper limit
     * @param angle in degrees
     */
    public void moveTo(double angle)
    {  
        double targetPosition = angleToTicks(angle);
        double currentPosition = angleToTicks(currentAngle);

        if(currentPosition < targetPosition - PLUS_MINUS_ERROR)
        {
            setSpeed(0.5);
        }
        else if(currentPosition > targetPosition + PLUS_MINUS_ERROR)
        {
            setSpeed(-0.5);
        }
        else
        {
            stop();
        }
    }

    /**
     * moves relative to the current position
     * positive to move up
     * negative to move down
     * @param angle in degrees
     */
    public void move(double angle)
    {
        double absolute_angle = angle + currentAngle;
        moveTo(absolute_angle);
    }

    // protected boolean getLimitSwitchPressed()
    // {
    //     return motor.rever// .getSensorCollection().get
    // }

    public String getShroudData()
    {
        return String.format("%+5.2f %6f %4.1f %4.1f", 
            motor.getMotorOutputPercent(), motor.getSelectedSensorPosition(), 
            motor.getStatorCurrent(), motor.getTemperature());
    }
}