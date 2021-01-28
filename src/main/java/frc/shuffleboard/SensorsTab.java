package frc.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.components.climber.Climber;
import frc.components.drivetrain.Drivetrain;
import frc.components.powercellsupervisor.PowerCellSupervisor;

/**
 * @author Elliot Measel
 * ShuffleBoard display for all sensors and encoders
 */
public class SensorsTab
{
    private static final String className = new String("[SensorsTab]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    // Create a Shuffleboard Tab
    private ShuffleboardTab sensorsTab = Shuffleboard.getTab("Sensors");

    private static Drivetrain drivetrain = Drivetrain.getInstance();
    private static PowerCellSupervisor powerCellSupervisor = PowerCellSupervisor.getInstance();
    private static Climber climber = Climber.getInstance();

    private static NetworkTableEntry frontLeftDriveEntry;
    private static NetworkTableEntry frontRightDriveEntry;
    private static NetworkTableEntry backLeftDriveEntry;
    private static NetworkTableEntry backRightDriveEntry;

    private static NetworkTableEntry centerIntakeEntry;
    private static NetworkTableEntry leftIntakeEntry;
    private static NetworkTableEntry rightIntakeEntry;

    private static NetworkTableEntry shuttleMotorEntry;
    
    private static NetworkTableEntry shooterEntry;

    private static NetworkTableEntry shroudEntry;

    private static NetworkTableEntry turretEntry;

    private static NetworkTableEntry climberArmEntry;
    private static NetworkTableEntry climberWinchEntry;


    private static NetworkTableEntry wristExtendedEntry; 
    private static NetworkTableEntry wristRetractedEntry; 

    private static NetworkTableEntry shuttleSensorEntry;
    
    private static SensorsTab instance = new SensorsTab();

    private SensorsTab()
    {
        System.out.println(className + " : Constructor Started");

        // Create the text boxes for the Drivetrain encoders
        frontLeftDriveEntry = createTextBox("Front Left Drive",   "0, 0, 0, 0", 0, 0, 4, 2);
        frontRightDriveEntry = createTextBox("Front Right Drive", "0, 0, 0, 0", 4, 0, 4, 2);
        backLeftDriveEntry = createTextBox("Back Left Drive",     "0, 0, 0, 0", 0, 2, 4, 2);
        backRightDriveEntry = createTextBox("Back Right Drive",   "0, 0, 0, 0", 4, 2, 4, 2);

        centerIntakeEntry = createTextBox("Center Intake",        "0, 0, 0, 0, 0", 13, 0, 4, 2);
        leftIntakeEntry = createTextBox("Left Intake",            "0, 0, 0, 0, 0", 9, 0, 4, 2);
        rightIntakeEntry = createTextBox("Right Intake",          "0, 0, 0, 0, 0", 17, 0, 4, 2);

        shuttleMotorEntry = createTextBox("Shuttle",              "0, 0, 0, 0, 0", 0, 5, 4, 2);

        shroudEntry = createTextBox("Shroud",                     "0, 0, 0, 0", 4, 5, 4, 2);

        turretEntry = createTextBox("Turret",                     "0, 0, 0, 0", 8, 5, 4, 2);

        shooterEntry = createTextBox("Shooter",                   "0, 0, 0, 0, 0", 12, 5, 4, 2);

        climberArmEntry = createTextBox("Climber Arm",            "0, 0, 0, 0, 0", 22, 0, 4, 2);
        climberWinchEntry = createTextBox("Climber Winch",        "0, 0, 0, 0, 0", 22, 2, 4, 2);



        wristExtendedEntry = createTextBox("Wrist Sensor 1",      false, 18, 5, 4, 2);
        wristRetractedEntry = createTextBox("Wrist Sensor 2",     false, 23, 5, 4, 2);

        shuttleSensorEntry = createTextBox("Shuttle Sensors",     "0, 0, 0, 0, 0, 0", 0, 8, 4, 2);


        System.out.println(className + ": Constructor Finished");
    }

    public static SensorsTab getInstance()
    {
        return instance;
    }

    /**
    * Create a <b>Text Box</b>
    * <p>Create an entry in the Network Table and add the Text Box to the Shuffleboard Tab
    */
    private NetworkTableEntry createTextBox(String title, Integer defaultValue, int column, int row, int width, int height)
    {
        return sensorsTab.add(title, defaultValue)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(column, row)
            .withSize(width, height)
            .getEntry();
    }

    private NetworkTableEntry createTextBox(String title, Boolean defaultValue, int column, int row, int width, int height)
    {
        return sensorsTab.add(title, defaultValue)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(column, row)
            .withSize(width, height)
            .getEntry();
    }

    private NetworkTableEntry createTextBox(String title, String defaultValue, int column, int row, int width, int height)
    {
        return sensorsTab.add(title, defaultValue)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(column, row)
            .withSize(width, height)
            .getEntry();
    }

    public void updateEncoderValues()
    {
        frontLeftDriveEntry.setString(drivetrain.getFrontLeftData());
        frontRightDriveEntry.setString(drivetrain.getFrontRightData());
        backLeftDriveEntry.setString(drivetrain.getBackLeftData());
        backRightDriveEntry.setString(drivetrain.getBackRightData());

        centerIntakeEntry.setString(powerCellSupervisor.getCenterRollerData());
        leftIntakeEntry.setString(powerCellSupervisor.getLeftRollerData());
        rightIntakeEntry.setString(powerCellSupervisor.getRightRollerData());

        shuttleMotorEntry.setString(powerCellSupervisor.getShuttleMotorData());

        shroudEntry.setString(powerCellSupervisor.getShroudData());

        turretEntry.setString(powerCellSupervisor.getTurretData());

        shooterEntry.setString(powerCellSupervisor.getFlywheelData());

        climberArmEntry.setString(climber.getArmData());
        climberWinchEntry.setString(climber.getWinchData());



        wristExtendedEntry.setBoolean(powerCellSupervisor.getWristSensorExtended());
        wristRetractedEntry.setBoolean(powerCellSupervisor.getWristSensorRetracted());

        shuttleSensorEntry.setString(powerCellSupervisor.getShuttleSensorData());
    }
}