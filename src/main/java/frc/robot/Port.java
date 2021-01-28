package frc.robot;

/**
 * @author Elliot Measel
 * Class to organize the ports used
 */
public class Port
{
    private static final String className = new String("[Port]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private Port()
    {
        System.out.println(className + " : Constructor Started");

        System.out.println(className + ": Constructor Finished");
    }

    public class Motor
    {
        //TODO: determine CAN ID's on all motors
        public static final int CAN_DRIVETRAIN_FRONT_LEFT  =  1;
        public static final int CAN_DRIVETRAIN_BACK_LEFT   =  2;
        public static final int CAN_DRIVETRAIN_BACK_RIGHT  =  3;
        public static final int CAN_DRIVETRAIN_FRONT_RIGHT =  4;

        public static final int CAN_INTAKE_CENTER          =  5;
        public static final int CAN_INTAKE_LEFT            =  6;
        public static final int CAN_INTAKE_RIGHT           =  7;

        public static final int CAN_SHUTTLE                =  8;

        public static final int CAN_SHOOTER_MASTER         =  9;
        public static final int CAN_SHOOTER_SLAVE          = 10;

        public static final int CAN_SHROUD                 = 11;

        public static final int CAN_TURRET                 = 12;

        public static final int CAN_CLIMBER_ARM            = 13;
        public static final int CAN_CLIMBER_WINCH          = 14;

        public static final int CAN_CONTROL_PANEL          = 15; //TODO: declare control panel motor
    }

    public class Relay
    {
        public static final int LED = 0;
    }

    public class Sensor
    {
        //TODO: bring in LIDAR constant under sensors.LidarLite.Constants.java
        
        public static final int WRIST_EXTEND_LIMIT_SWITCH  = 0;
        public static final int WRIST_RETRACT_LIMIT_SWITCH = 1;

        //SHUTTLE_1 is the spot closest to the intake
        //SHUTTLE_5 is the spot closest to the shooter
        public static final int SHUTTLE_1                  = 2;
        public static final int SHUTTLE_2                  = 3;
        public static final int SHUTTLE_3                  = 4;
        public static final int SHUTTLE_4                  = 5;
        public static final int SHUTTLE_5                  = 6;
        public static final int SHUTTLE_6                  = 7; //TODO: declare 6th shuttle sensor
    }

    public class Controller
    {
        public static final int DRIVER   = 0;
        public static final int OPERATOR = 1;
    }

    public class Pneumatic
    {
        public static final int SHIFTER_EXTEND        = 4;
        public static final int SHIFTER_RETRACT       = 5;

        public static final int INTAKE_EXTEND         = 2;
        public static final int INTAKE_RETRACT        = 3;

        //TODO: declare control panel pneumatics
        public static final int CONTROL_PANEL_EXTEND  = 6;
        public static final int CONTROL_PANEL_RETRACT = 7;

        //TODO: declare cooling pneumatics
        public static final int DRIVERTRAIN_COOLING_ON   = 0;
        public static final int DRIVERTRAIN_COOLING_OFF   = 1;

    }

    public class PDP
    {
        public static final int PDP_DRIVETRAIN_FRONT_RIGHT =  2;
        public static final int PDP_DRIVETRAIN_BACK_RIGHT  =  3;
        public static final int PDP_DRIVETRAIN_BACK_LEFT   = 15;
        public static final int PDP_DRIVETRAIN_FRONT_LEFT  = 14;

        public static final int PDP_INTAKE_CENTER           = 6;
        public static final int PDP_INTAKE_LEFT             = 9;
        public static final int PDP_INTAKE_RIGHT            = 5;

        public static final int PDP_SHUTTLE                = 10;

        public static final int PDP_SHOOTER_MASTER         = 13;
        public static final int PDP_SHOOTER_SLAVE          = 12;

        public static final int PDP_SHROUD                 = 11;

        public static final int PDP_TURRET                 =  1;

        public static final int PDP_CLIMBER_ARM            =  8;
        public static final int PDP_CLIMBER_WINCH          =  0;

        public static final int PDP_CONTROL_PANEL          =  4;
        
        public static final int PDP_VRM                    =  7;
    }
}