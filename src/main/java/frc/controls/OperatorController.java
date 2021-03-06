package frc.controls;

import frc.robot.Port;

public class OperatorController extends Logitech
{
    private static final String className = new String("[OperatorController]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    public enum OperatorButtonAction
    {
        kShoot(Button.kTrigger),
        kAutoAim(Button.kHandleSide),

        kOffTarget(Button.kHandleBottomLeft),
        kOnTarget(Button.kHandleBottomRight),
        // kNoAction(Button.kHandleTopLeft),
        // kNoAction(Button.kHandleTopRight),

        // kNoAction(Button.kOuterTop),
        // kNoAction(Button.kInnerTop),
        kFlywheelOverride(Button.kOuterMiddle),
        kShooterOverride(Button.kInnerMiddle),
        kWinch(Button.kOuterBottom),
        kShuttleOverride(Button.kInnerBottom) 
        ;

        public final Button button;

        private OperatorButtonAction(Button button)
        {
           this.button = button;
        }
    }

    public enum OperatorAxisAction
    {
        // kNoAction(Axis.kXAxis, 0.1, 0.0, 1.0, false, AxisScale.kLinear),
        kShroud(Axis.kYAxis, 0.2, 0.0, 1.0, true, AxisScale.kLinear),
        kTurret(Axis.kZAxis, 0.25, 0.0, 1.0, false, AxisScale.kLinear),
        kShooterPower(Axis.kSlider, 0.1, 0.0, 1.0, true, AxisScale.kLinear);

        public final Axis axis;
        public final double axisDeadzone;
        public final double axisMinOutput;
        public final double axisMaxOutput;
        public final boolean axisIsFlipped;
        public final AxisScale axisScale;

        private OperatorAxisAction(Axis axis, double axisDeadzone, double axisMinOutput, double axisMaxOutput, boolean axisIsFlipped, AxisScale axisScale)
        {
            this.axis = axis;
            this.axisDeadzone = axisDeadzone;
            this.axisMinOutput = axisMinOutput;
            this.axisMaxOutput = axisMaxOutput;
            this.axisIsFlipped = axisIsFlipped;
            this.axisScale = axisScale;
        }
    }

    private static OperatorController instance = new OperatorController(Port.Controller.OPERATOR);

    private OperatorController(int port)
    {
        super(port);

        System.out.println(className + " : Constructor Started");

        for(OperatorAxisAction action : OperatorAxisAction.values())
        {
            setAxisSettings(action.axis, action.axisDeadzone, action.axisMinOutput, action.axisMaxOutput, action.axisIsFlipped, action.axisScale);
        }

        System.out.println(className + ": Constructor Finished");
    }

    public static OperatorController getInstance()
    {
        return instance;
    }

    @Deprecated
    public boolean getRawButton(Button button)
    {
        return super.getRawButton(button);
    }

    @Deprecated
    public boolean getRawButton(int button)
    {
        return super.getRawButton(button);
    }

    @Deprecated
    public double getRawAxis(Axis axis)
    {
        return super.getRawAxis(axis);
    }

    @Deprecated
    public double getRawAxis(int axis)
    {
        return super.getRawAxis(axis);
    }

    public boolean getAction(OperatorButtonAction buttonAction)
    {
        return getRawButton(buttonAction.button);
    }

    public double getAction(OperatorAxisAction axisAction)
    {
        return getRawAxis(axisAction.axis);
    }
}