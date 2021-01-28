package frc.components.powercellsupervisor.shooter;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Class for the pneumatic gate that will allow power cells to contact the flywheel
 * @author Maxwell Li
 * @deprecated No plans on incorperating a gate into the design
 */
public class Gate
{   
    private static final String className = new String("[Gate]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private DoubleSolenoid gateSolenoid = new DoubleSolenoid(0, 1);
    private static Gate instance = new Gate();

    private Gate()
    {
        System.out.println(className + " : Constructor Started");

        System.out.println(className + ": Constructor Finished");
    }

    /**
     * @return the instance variable for the gate to be used
     */
    public static Gate getInstance()
    {
        return instance;
    }

    /**
     * opens the gate (solenoid retracts)
     */
    public void openGate()
    {
        gateSolenoid.set(Value.kReverse);
    }

    /**
     * closes the gate (solenoid extends)
     */
    public void closeGate()
    {
        gateSolenoid.set(Value.kForward);
    }

    /**
     * @return the current position of the solenoid ()
     */
    public DoubleSolenoid.Value getSolenoidPosition()
    {
        return gateSolenoid.get();
    }

}