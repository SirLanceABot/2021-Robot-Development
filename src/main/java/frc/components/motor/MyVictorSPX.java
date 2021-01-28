package frc.components.motor;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;

public class MyVictorSPX extends Motor
{
    private WPI_VictorSPX motor;

    public MyVictorSPX(int port)
    {
        motor = new WPI_VictorSPX(port);
        motor.configFactoryDefault();
    }

    @Override
    public void setInverted(boolean isInverted)
    {
        motor.setInverted(isInverted);
    }

    public void setReverseSoftLimitEnabled(boolean isEnabled)
    {
        // motor.configReverseSoftLimitEnable(isEnabled);
    }

    public void setReverseSoftLimitThreshold(int threshold)
    {
        // motor.configReverseSoftLimitThreshold(threshold);
    }

    public void setReverseHardLimitEnabled(boolean isEnabled, boolean isNormallyOpen)
    {
        // if (isEnabled && isNormallyOpen)
        //     motor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, );
        // else if(isEnabled && !isNormallyOpen)
        //     motor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, );
        // else
        //     motor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, );
    }

    public void setForwardSoftLimitEnabled(boolean isEnabled)
    {
        // motor.configForwardSoftLimitEnable(isEnabled);
    }

    public void setForwardSoftLimitThreshold(int threshold)
    {
        // motor.configForwardSoftLimitThreshold(threshold);
    }

    public void setForwardHardLimitEnabled(boolean isEnabled, boolean isNormallyOpen)
    {
        // if (isEnabled && isNormallyOpen)
        //     motor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        // else if(isEnabled && !isNormallyOpen)
        //     motor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
        // else
        //     motor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    }

    public void setNeutralMode(MyNeutralMode mode)
    {
        if (mode == MyNeutralMode.kBrake)
            motor.setNeutralMode(NeutralMode.Brake);
        else if(mode == MyNeutralMode.kCoast)
            motor.setNeutralMode(NeutralMode.Coast);
    }

    public void setStatorCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {
        
    }

    public void setSupplyCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {
        
    }

    public void setOpenLoopRamp(double seconds)
    {
        // motor.configOpenloopRamp(seconds);
    }

    public void setFeedbackDevice(MyFeedbackDevice device)
    {
        // if (device == MyFeedbackDevice.kAnalog)
        //     motor.configSelectedFeedbackSensor(FeedbackDevice.Analog);
        // else if(device == MyFeedbackDevice.kQuadEncoder)
        //     motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        // else
        //     motor.configSelectedFeedbackSensor(FeedbackDevice.None);
    }

    public WPI_VictorSPX getSuper()
    {
        return motor;
    }
}