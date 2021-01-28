package frc.components.motor;

import javax.lang.model.util.ElementScanner6;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.SparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MySparkMax extends Motor
{
    private CANSparkMax motor;
    // private CANEncoder encoder;
    private CANDigitalInput reverseLimitSwitch;
    private CANDigitalInput forwardLimitSwitch;

    public MySparkMax(int port)
    {
        motor = new CANSparkMax(port, MotorType.kBrushless);
        motor.restoreFactoryDefaults();

        forwardLimitSwitch = motor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
    }

    @Override
    public void setInverted(boolean isInverted)
    {
        motor.setInverted(isInverted);
    }

    public void setReverseSoftLimitEnabled(boolean isEnabled)
    {
        
    }

    public void setReverseSoftLimitThreshold(int threshold)
    {
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, threshold);
    }

    public void setReverseHardLimitEnabled(boolean isEnabled, boolean isNormallyOpen)
    {
        if (isNormallyOpen)
        {
            reverseLimitSwitch = motor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
        }
        else
        {
            reverseLimitSwitch = motor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
        }

        reverseLimitSwitch.enableLimitSwitch(isEnabled);
    }

    public void setForwardSoftLimitEnabled(boolean isEnabled)
    {
        
    }

    public void setForwardSoftLimitThreshold(int threshold)
    {
        motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, threshold);
    }

    public void setForwardHardLimitEnabled(boolean isEnabled, boolean isNormallyOpen)
    {
        if (isNormallyOpen)
        {
            forwardLimitSwitch = motor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
        }
        else
        {
            forwardLimitSwitch = motor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
        }

        forwardLimitSwitch.enableLimitSwitch(isEnabled);
    }

    public void setNeutralMode(MyNeutralMode mode)
    {
        if (mode == MyNeutralMode.kBrake)
            motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        else if(mode == MyNeutralMode.kCoast)
            motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    public void setStatorCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {
        motor.setSmartCurrentLimit((int)currentLimit);
    }

    public void setSupplyCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {
        motor.setSmartCurrentLimit((int)currentLimit);
    }

    public void setOpenLoopRamp(double seconds)
    {
        motor.setOpenLoopRampRate(seconds);
    }

    public void setFeedbackDevice(MyFeedbackDevice device)
    {
        //encoder = motor.getEncoder(EncoderType.kQuadrature, 4096);  

    }

    public CANSparkMax getSuper()
    {
        return motor;
    }
}