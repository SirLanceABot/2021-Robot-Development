package frc.components.powercellsupervisor.intake;

import edu.wpi.first.wpilibj.Timer;
import frc.autonomous.commands.interfaces.Notified;
import frc.components.powercellsupervisor.intake.Roller;
import frc.components.powercellsupervisor.intake.Wrist;
import frc.controls.DriverController;
import frc.controls.DriverController.DriverButtonAction;

/**
 * Class to control the Intake subsystem
 * @author Maxwell Li   
 */
public class Intake implements Notified
{
    private enum State 
    {
        Off()
        {
            @Override
            void doAction() 
            {
                //System.out.println("Intake State: Off");
                roller.stop();
                if(driverController.getAction(DriverButtonAction.kIntakeOn) || notification)
                {
                    currentState = Transition.findNextState(currentState, Event.kIntakeButtonPressed);
                }
                else
                {
                    currentState = Transition.findNextState(currentState, Event.kNoPress);
                }
            }
        },
        Lowering()
        {
            @Override
            void doAction()
            {
                //System.out.println("Intake State: Lowering");

                roller.stop();
                wrist.lower();

                if(wrist.isDown(false))
                {
                    currentState = Transition.findNextState(currentState, Event.kLowered);
                }
            }
        },
        Raising()
        {
            @Override
            void doAction()
            {
                //System.out.println("Intake State: Raising");

                roller.stop();
                wrist.raise();
                
                if(wrist.isUp(false))
                {
                    currentState = Transition.findNextState(currentState, Event.kRaised);
                }
            }
        },
        TurningOn()
        {
            boolean initFlag = true;
            @Override
            void doAction() 
            {
                System.out.println("Turning On");
                if(initFlag)
                {
                    stopTimer();
                    resetTimer();
                    startTimer();
                    initFlag = false;
                }
                
                else if(getTimer() > 0.5)
                {
                    currentState = Transition.findNextState(currentState, Event.kTurnedOn);
                    initFlag = true;
                }
                roller.intake();
            }
        },
        Intaking()
        {
            @Override
            void doAction() 
            {
                System.out.println("Intake State: Intaking");

                if(roller.getCenterRollerAmps() > 10)
                {
                    currentState = Transition.findNextState(currentState, Event.kPinched);
                }

                roller.intake();
                if(driverController.getAction(DriverButtonAction.kIntakeOn) || notification)
                {
                    currentState = Transition.findNextState(currentState, Event.kIntakeButtonPressed);
                }
                else
                {
                    currentState = Transition.findNextState(currentState, Event.kNoPress);
                }            
            }
        },            
        Pinched()
        {
            @Override
            void doAction() 
            {
                System.out.println("Pinched");
                roller.intakeUsingOuter();           
                
                if(roller.getLeftRollerAmps() < 7.5 && roller.getRightRollerAmps() < 7.5)
                {
                    currentState = Transition.findNextState(currentState, Event.kNotPinched);
                }
            }

        };
        
        abstract void doAction();

        State() {}
    }

    private enum Event
    {
        kIntakeButtonPressed, kNoPress, kLowered, kRaised, kTurnedOn, kPinched, kNotPinched;
    }

      // ----------------------------------------------------------------------//
    private enum Transition 
    {
        //-----------Current State --------------------------Event---------------------------NextState------//
        Transition_O_01(State.Off,                      Event.kNoPress,                         State.Off),
        Transition_O_02(State.Off,                      Event.kIntakeButtonPressed,             State.Lowering),
        Transition_O_03(State.Off,                      Event.kLowered,                         State.Off),
        Transition_O_04(State.Off,                      Event.kRaised,                          State.Off),

        Transition_R_01(State.Raising,                  Event.kNoPress,                         State.Raising),
        Transition_R_02(State.Raising,                  Event.kIntakeButtonPressed,             State.Lowering),
        Transition_R_03(State.Raising,                  Event.kLowered,                         State.Off),
        Transition_R_04(State.Raising,                  Event.kRaised,                          State.Off),
        
        Transition_L_01(State.Lowering,                 Event.kNoPress,                         State.Off),
        Transition_L_02(State.Lowering,                 Event.kIntakeButtonPressed,             State.Lowering),
        Transition_L_03(State.Lowering,                 Event.kLowered,                         State.TurningOn),
        Transition_L_04(State.Lowering,                 Event.kRaised,                          State.Off),

        Transition_T_01(State.TurningOn,                Event.kTurnedOn,                        State.Intaking),

        //TODO: Find out if we want to have the driver hold the button, or tap to toggle 
        //This will be achieved in states I 01 and I 02.
        Transition_I_01(State.Intaking,                 Event.kNoPress,                         State.Off),
        Transition_I_02(State.Intaking,                 Event.kIntakeButtonPressed,             State.Intaking),
        Transition_I_03(State.Intaking,                 Event.kLowered,                         State.Intaking),
        Transition_I_04(State.Intaking,                 Event.kRaised,                          State.Off),
        Transition_I_05(State.Intaking,                 Event.kPinched,                         State.Pinched),


        Transition_P_01(State.Pinched,                  Event.kNotPinched,                        State.Intaking);


        private final State currentState;
        private final Event event;
        private final State nextState;

        Transition(State currentState, Event event, State nextState) {
        this.currentState = currentState;
        this.event = event;
        this.nextState = nextState;
        }

        // table lookup to determine new state given the current state and the event
        private static State findNextState(State currentState, Event event) 
        {
            for (Transition transition : Transition.values()) 
            {
                if (transition.currentState == currentState && transition.event == event) 
                {
                return transition.nextState;
                }
            }
            System.out.println("ERROR: NO STATE TO TRANSITION TO FOUND");
            return currentState; // throw an error if here
        }
    }

    private static Timer timer = new Timer();
    private static Roller roller;
    private static Wrist wrist;
    private static DriverController driverController;
    private static State currentState = State.Off;
    private static final boolean useSensor = true;
    private static boolean notification = false;
    private static Intake instance = new Intake();

    private Intake()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        roller = Roller.getInstance();
        wrist = Wrist.getInstance();

        driverController = DriverController.getInstance();
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    public static Intake getInstance()
    {
        return instance;
    }

    public void runFSM()
    {
        currentState.doAction();
    }

    public void overrideFSM()
    {
        currentState = State.Off;
    }

    public static void startTimer()
    {
        timer.start();
    }

    public static void stopTimer()
    {
        timer.stop();
    }

    public static void resetTimer()
    {
        timer.reset();
    }

    public static double getTimer()
    {
        return timer.get();
    }

    @Override
    public void getNotification(Boolean notification) 
    {
        this.notification = notification;
    }

    public static boolean getWristExtendedSensor()
    {
        return Wrist.getExtendedSensorValue();
    }

    public static boolean getWristRetractedSensor()
    {
        return Wrist.getRetractedSensorValue();
    }

    public double getRollerCenterEncoder()
    {
        return roller.getCenterEncoderValue();
    }

    public double getRollerLeftEncoder()
    {
        return roller.getLeftEncoderValue();
    }

    public double getRollerRightEncoder()
    {
        return roller.getRightEncoderValue();
    }

    public String getCenterRollerData()
    {
        return roller.getCenterRollerData();
    }

    public String getRightRollerData()
    {
        return roller.getRightRollerData();
    }

    public String getLeftRollerData()
    {
        return roller.getLeftRollerData();
    }
}