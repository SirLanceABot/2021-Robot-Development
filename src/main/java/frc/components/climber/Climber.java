// Import all the code involved in the robot's components.
package frc.components.climber;

// Import all the material from the Arm and Winch subclasses to be packaged together in this class.
import frc.components.climber.Arm;
import frc.components.climber.Winch;
import frc.controls.DriverController;
import frc.controls.OperatorController;
import frc.controls.DriverController.DriverAxisAction;
import frc.controls.DriverController.DriverButtonAction;
import frc.controls.OperatorController.OperatorAxisAction;
import frc.controls.OperatorController.OperatorButtonAction;
import frc.controls.Xbox.Button;

public class Climber
{
    private static final String className = new String("[Climber]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    
    // Get private instances of the Arm and Winch subclasses.
    private static Winch winch = Winch.getInstance();
    private static Arm arm = Arm.getInstance();
    private static DriverController driverController = DriverController.getInstance();

    // Create constants
    //TODO: verify actual buttons
    private static final int RAISE_ARM_BUTTON = 0;
    private static final int LOWER_ARM_BUTTON = 1;
    private static final int RAISE_WINCH_BUTTON = 2;
    private static final int LOWER_WINCH_BUTTON = 3;

    private static Climber instance = new Climber();

    /**
     * The constructor for the Climber class. 
     */
    private Climber()
    {
        System.out.println(className + " : Constructor Started");

        System.out.println(className + ": Constructor Finished");
    }

    /**
     * The method to retrieve the instance of a Climber.
     * @return instance 
     */
    public static Climber getInstance()
    {
        return instance;
    }
    
    /**
     * The method to run the Arm and Winch subclasses under the Climber.
     */
    public void run()
    {
        if(driverController.getRawButton(7))
        {
            arm.setExtensionSpeed(-0.3);
        }
        if(driverController.getAction(DriverButtonAction.kLowerArms))
        {
            arm.setExtensionSpeed(-0.05);
        }
        else if(driverController.getAction(DriverButtonAction.kRaiseArms))
        {
            arm.setExtensionSpeed(0.65);
        }
        else if(driverController.getAction(DriverAxisAction.kUnspoolWinch) > 0.1)
        {
            winch.lowerWinch(driverController.getAction(DriverAxisAction.kUnspoolWinch));
        }
        else if(driverController.getAction(DriverAxisAction.kSpoolWinch) > 0.1)
        {
            winch.raiseWinch(driverController.getAction(DriverAxisAction.kSpoolWinch));
        }
        else
        {
            arm.holdArm();
            winch.stopWinch();
        }
    }

    public double getArmEncoderPosition()
    {
        return arm.getEncoderPosition();
    }

    public double getWinchEncoderPosition()
    {
        return winch.getEncoderPosition();
    }

    public String getArmData()
    {
        return arm.getArmData();
    }

    public String getWinchData()
    {
        return winch.getWinchData();
    }
}