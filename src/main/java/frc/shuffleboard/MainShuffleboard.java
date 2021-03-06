package frc.shuffleboard;

import frc.shuffleboard.AutonomousTab.AutonomousTabData;
import frc.shuffleboard.SkillsCompetitionTab.SkillsCompetitionTabData;

/**
 * The MainShuffleboard class will be the main interface to access the other
 * tabs on the Shuffleboard.
 * 
 * <p>
 * <b>Shuffleboard Settings</b>
 * <ul>
 * <li>Open the Shuffleboard</li>
 * <li>Open the File menu and select Preferences</li>
 * <li>Select App Settings on the left (if not already selected)</li>
 * <li>Under Tab Setting on the right, set the Default Tile Size to 32</li>
 * <li>Select a tab under Tabs on the left</li>
 * <li>Under Layout on the right, set Tile Size to 32, set Horizontal Spacing to
 * 16, set Vertical Spacing to 16</li>
 * </ul>
 */
public class MainShuffleboard
{
    private static final String className = new String("[MainShuffleboard]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private AutonomousTab autonomousTab = AutonomousTab.getInstance();
    private DriverControllerTab driverControllerTab = DriverControllerTab.getInstance();
    private OperatorControllerTab operatorControllerTab = OperatorControllerTab.getInstance();
    private CameraTab cameraTab = CameraTab.getInstance();
    private SensorsTab sensorsTab = SensorsTab.getInstance();
    private SkillsCompetitionTab skillsCompetitionTab = SkillsCompetitionTab.getInstance();

    private static MainShuffleboard instance = new MainShuffleboard();

    private MainShuffleboard()
    {
        System.out.println(className + " : Constructor Started");

        System.out.println(className + ": Constructor Finished");
    }

    public static MainShuffleboard getInstance()
    {
        return instance;
    }

    // ----------------------------------------------------------------------------------
    // AUTONOMOUS TAB
    public AutonomousTabData getAutonomousTabData()
    {
        return autonomousTab.getAutonomousTabData();
    }

    public void checkForNewAutonomousTabData()
    {
        autonomousTab.checkForNewAutonomousTabData();
    }

    // ------------------------------------------------------------------------------------
    // SKILLS COMPETITION TAB
    public SkillsCompetitionTabData getSkillsCompetitionTabData()
    {
        return skillsCompetitionTab.getSkillsCompetitionTabData();
    }

    // ------------------------------------------------------------------------------------
    // DRIVER CONTROLLER TAB
    public void setDriverControllerSettings()
    {
       driverControllerTab.setDriverControllerAxisSettings();
    }

    // ------------------------------------------------------------------------------------
    // OPERATOR CONTROLLER TAB
    public void setOperatorControllerSettings()
    {
       operatorControllerTab.setOperatorControllerAxisSettings();
    }

    // ------------------------------------------------------------------------------------
    // CAMERA TAB
    public void updateMatchTime()
    {
       cameraTab.updateMatchTime();
    }

    public void updateMatchInfo()
    {
       cameraTab.updateMatchInfo();
    }

    // ------------------------------------------------------------------------------------
    // SENSORS TAB
    public void updateSensors()
    {
        sensorsTab.updateEncoderValues();
    }
}
