package frc.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class CameraTab
{
    private static final String className = new String("[CameraTab]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    // Camera Tab
    private ShuffleboardTab cameraTab = Shuffleboard.getTab("Camera");

    private NetworkTableEntry timeRemainingEntry;
    private NetworkTableEntry matchEntry;
    private NetworkTableEntry teamStationEntry;

    private int oldTime = 0;

    private static DriverStation dStation = DriverStation.getInstance();;
    
    private static CameraTab instance = new CameraTab();

    private CameraTab()
    {
        System.out.println(className + " : Constructor Started");

        createCameraTab();
        timeRemainingEntry.setString("0:00");
        updateMatchInfo();

        System.out.println(className + ": Constructor Finished");
    }

    protected static CameraTab getInstance()
    {
        return instance;
    }

    private void createCameraTab()
    {
        // The Camera widgets are created on Rasp Pi
        // These are the Time and Match Information boxes
        timeRemainingEntry = 
            cameraTab.add("Time", "NA")
                .withWidget(BuiltInWidgets.kTextView)
                .withPosition(20, 13)
                .withSize(4, 2)
                .getEntry();

        matchEntry = 
            cameraTab.add("Match", "NA")
                .withWidget(BuiltInWidgets.kTextView)
                .withPosition(24, 13)
                .withSize(4, 2)
                .getEntry();

        teamStationEntry =
            cameraTab.add("Alliance", "NA")
                .withWidget(BuiltInWidgets.kTextView)
                .withPosition(28, 13)
                .withSize(4, 2)
                .getEntry();
    }
    
    public void updateMatchTime()
    {
        int matchTime;
        int minutes;
        int seconds;
        String timeRemainingFormatted;

        matchTime = (int) Math.round(dStation.getMatchTime() + 0.5);
        matchTime = (matchTime < 0 ? 0 : matchTime);

        if (matchTime != oldTime)
        {
            oldTime = matchTime;
            minutes = matchTime / 60;
            seconds = matchTime % 60;
            timeRemainingFormatted = Integer.toString(minutes) + (seconds < 10 ? ":0" : ":") + Integer.toString(seconds);

            timeRemainingEntry.setString(timeRemainingFormatted);
        }
    }

    public void updateMatchInfo()
    {
        String matchString;
        String teamStationString;

        matchString = dStation.getMatchType() + " " + dStation.getMatchNumber();
        teamStationString = dStation.getAlliance() + " " + dStation.getLocation();

        matchEntry.setString(matchString);
        teamStationEntry.setString(teamStationString);
    }
}