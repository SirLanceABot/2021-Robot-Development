package frc.components.motor;

public abstract class Motor
{
    public enum MyNeutralMode
    {
        kBrake, kCoast;
    }

    public enum MyFeedbackDevice
    {
        kNone, kQuadEncoder, kAnalog;
    }

    abstract void setInverted(boolean isInverted);
    abstract void setReverseSoftLimitEnabled(boolean isEnabled);
    abstract void setReverseSoftLimitThreshold(int threshold);
    abstract void setReverseHardLimitEnabled(boolean isEnabled, boolean isNormallyOpen);
    abstract void setForwardSoftLimitEnabled(boolean isEnabled);
    abstract void setForwardSoftLimitThreshold(int threshold);
    abstract void setForwardHardLimitEnabled(boolean isEnabled, boolean isNormallyOpen);
    abstract void setNeutralMode(MyNeutralMode mode);
    abstract void setStatorCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime);
    abstract void setSupplyCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime);
    abstract void setOpenLoopRamp(double seconds);
    abstract void setFeedbackDevice(MyFeedbackDevice device);
    //abstract Motor getSuper();

    void configMotor(boolean setInverted, boolean reverseSoftLimitEnabled, int reverseSoftLimitThreshold, boolean reverseHardLimitEnabled, boolean reverseHardLimitNormallyOpen, boolean forwardSoftLimitEnabled, int forwardSoftLimitThreshold, boolean forwardHardLimitEnabled, boolean forwardHardLimitNormallyOpen, MyNeutralMode mode, boolean statorEnabled, double statorCurrentLimit, double statorTriggerThresholdCurrent, double statorTriggerThresholdTime, boolean supplyEnabled, double supplyCurrentLimit, double supplyTriggerThresholdCurrent, double supplyTriggerThresholdTime, double openLoopRamp)
    {
        setInverted(setInverted);
        setReverseSoftLimitEnabled(reverseSoftLimitEnabled);
        setReverseSoftLimitThreshold(reverseSoftLimitThreshold);
        setReverseHardLimitEnabled(reverseHardLimitEnabled, reverseHardLimitNormallyOpen);
        setForwardSoftLimitEnabled(forwardSoftLimitEnabled);
        setForwardSoftLimitThreshold(forwardSoftLimitThreshold);
        setForwardHardLimitEnabled(forwardHardLimitEnabled, forwardHardLimitNormallyOpen);
        setNeutralMode(mode);
        setStatorCurrentLimit(statorEnabled, statorCurrentLimit, statorTriggerThresholdCurrent, statorTriggerThresholdTime);
        setSupplyCurrentLimit(supplyEnabled, supplyCurrentLimit, supplyTriggerThresholdCurrent, supplyTriggerThresholdTime);
        setOpenLoopRamp(openLoopRamp);
    }
}