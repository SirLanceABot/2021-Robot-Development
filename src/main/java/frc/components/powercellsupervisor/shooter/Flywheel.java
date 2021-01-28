package frc.components.powercellsupervisor.shooter;

import frc.robot.Port;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

/**
 * Class for the flywheel that ejects the power cells from the shooter
 * @author Maxwell Lee
 */
public class Flywheel
{
    private static final String className = new String("[Flywheel]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    //----------------------------- Constants --------------------------//
    private static final int TIMEOUT_MS = 30;
    // //private static final int VELOCITY_ERROR = 3;

    //6000 rpm 
    // private static final double PROPORTIONAL = 0.8;
    // private static final double INTEGRAL = 0.000065;
    // private static final double DERIVATIVE = 0.0000001;
    // private static final double FEEDFORWARD = 0.00;

    //4000
    private static final double PROPORTIONAL = 0.4;
    private static final double INTEGRAL = 0.000025;
    private static final double DERIVATIVE = 0.0000001;
    private static final double FEEDFORWARD = 0.00;

    private static final double TICK_TO_RPM = (1 / 100.0) * (1/4096.0) * (18/32.0) * (1000/1.0) * (60/1.0);
    //------------------------------------------------------------------//

    private static TalonSRX masterMotor = new TalonSRX(Port.Motor.CAN_SHOOTER_MASTER);
    private static VictorSPX followerMotor = new VictorSPX(Port.Motor.CAN_SHOOTER_SLAVE);
    private static boolean isMoving = false;
    private static Flywheel instance = new Flywheel();

    private Flywheel()
    {
        System.out.println(className + " : Constructor Started");

        masterMotor.configFactoryDefault();
        masterMotor.setInverted(false);
        masterMotor.setNeutralMode(NeutralMode.Coast);

        //feedback sensor
        masterMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0 , TIMEOUT_MS);
        masterMotor.setSensorPhase(false);
        // motor.configClearPositionOnLimitR(true, 10);
        // motor.configFeedbackNotContinuous(false, 10);

        //soft limits
        masterMotor.configReverseSoftLimitThreshold(0);
        masterMotor.configReverseSoftLimitEnable(false);
        masterMotor.configForwardSoftLimitThreshold(0);
        masterMotor.configForwardSoftLimitEnable(false);

        //hard limits
        masterMotor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
        masterMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
        
        //current limits
        masterMotor.configOpenloopRamp(0.5);
        masterMotor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 35, 40, 0.5), 10);
        
        masterMotor.config_kF(0, FEEDFORWARD, TIMEOUT_MS);
		masterMotor.config_kP(0, PROPORTIONAL, TIMEOUT_MS);
		masterMotor.config_kI(0, INTEGRAL, TIMEOUT_MS);
        masterMotor.config_kD(0, DERIVATIVE, TIMEOUT_MS);
        
        followerMotor.configFactoryDefault();
        followerMotor.setNeutralMode(NeutralMode.Coast);
        followerMotor.setInverted(false);

        followerMotor.follow(masterMotor);

        System.out.println(className + ": Constructor Finished");
    }

    /**
     * Get the instance of the flywheel to be used
     * @return the flywheel instance
     */
    public static Flywheel getInstance()
    {
        return instance;
    }

        /**
     * sets the speed of the motor when in override state
     * @param speed
     */
    public void setSpeedOverride(double speed)
    {
        if(speed > 0)
        {
            masterMotor.set(ControlMode.Velocity, (speed * 5300.0) / TICK_TO_RPM);
        }
        else
        {
            masterMotor.set(ControlMode.PercentOutput, 0.0);
        }
        System.out.println(getRPM());
    }


    /**
     * sets the speed of the motor
     * @param speed
     */
    public void setSpeed(double speed)
    {
        if(speed == 0.0)
        {
            stop();
        }
        else
        {
            masterMotor.set(ControlMode.PercentOutput, speed);
            setIsMoving(true);
        }

    }

    /**
     * Stops the motor
     */
    public void stop()
    {
        masterMotor.set(ControlMode.PercentOutput, 0.0);
        setIsMoving(false);
    }

    /**
     * sets the instance variable that determines if it is running
     * @param running
     */
    private static void setIsMoving(boolean running)
    {
        isMoving = running;
    }

    /**
     * getter function for the isRunning boolean
     * @return true if it is running, false if it is not
     */
    public boolean getIsMoving()
    {
        return isMoving;
    }

    public double getRPM()
    {
        return masterMotor.getSelectedSensorVelocity() * TICK_TO_RPM;
    }

    /**
     * Method that should be called to run the flywheel
     * Uses a velocity PID Control for consistency when shooting
     * 
     * @param speedToRun in RPM
     */
    public void run(double speed)
    {
        
        if(speed > 0)
        {
            masterMotor.set(ControlMode.Velocity, (speed) / TICK_TO_RPM);
        }
        else
        {
            masterMotor.set(ControlMode.PercentOutput, 0.0);
        }
                System.out.println(getRPM());
        //setSpeed(speedToRun);
    }

    
    public int getEncoderPosition()
    {   
        // TODO: Typecasted this to int - jwood 1/28/21
        return (int) masterMotor.getSelectedSensorPosition();
    }
    

    public String getFlywheelData()
    {
        return String.format("%+5.2f %6f %5.1f %4.1f %4.1f", 
            masterMotor.getMotorOutputPercent(), masterMotor.getSelectedSensorPosition(), getRPM(),
            masterMotor.getStatorCurrent(), masterMotor.getTemperature());
    }

}
