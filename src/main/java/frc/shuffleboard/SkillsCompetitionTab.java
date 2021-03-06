package frc.shuffleboard;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;


public class SkillsCompetitionTab
{
    private static final String className = new String("[SkillsCompetitionTab]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    // Create enumerated types for each Box
    //-------------------------------------------------------------------//
    public enum Competition
    {
        kNone, kGalacticSearch, kAutoNav;
    }

    public enum GalacticSearchPath
    {
        kNone, kA, kB;
    }

    public enum GalacticSearchColor
    {
        kNone, kRed, kBlue;
    }

    public enum AutoNavPath
    {
        kNone, kBarrelRacing, kSlalom, kBounce;
    }



    // Create a class to hold the data on the Shuffleboard tab
    public static class SkillsCompetitionTabData
    {
        public Competition competition = Competition.kNone;
        public GalacticSearchPath galacticSearchPath = GalacticSearchPath.kNone;
        public GalacticSearchColor galacticSearchColor = GalacticSearchColor.kNone;
        public AutoNavPath autoNavPath = AutoNavPath.kNone;


        
        @Override
        public String toString()
        {
            String str = "";

            str += " \n";
            str += "*****  Skills Competition  *****\n";
            str += "Competition           : "  + competition   + "\n";
            str += "GalacticSearchPath    : "  + galacticSearchPath   + "\n";
            str += "GalacticSearchColor   : "  + galacticSearchColor   + "\n";
            str += "AutoNavPath           : "  + autoNavPath   + "\n";

            return str;
        }
    }

    // Create a Shuffleboard Tab
    private ShuffleboardTab skillsCompetitionTab = Shuffleboard.getTab("Skills Competition");

    // Create an object to store the data in the Boxes
    private SkillsCompetitionTabData skillsCompetitionTabData = new SkillsCompetitionTabData();

    // Create the Box objects
    private SendableChooser<Competition> competitionBox = new SendableChooser<>();
    private SendableChooser<GalacticSearchPath> galacticSearchPathBox = new SendableChooser<>();
    private SendableChooser<GalacticSearchColor> galacticSearchColorBox = new SendableChooser<>();
    private SendableChooser<AutoNavPath> autoNavPathBox = new SendableChooser<>();

    private static SkillsCompetitionTab instance = new SkillsCompetitionTab();

    private SkillsCompetitionTab()
    {
        System.out.println(className + " : Constructor Started");

        createCompetitionBox();
        createGalacticSearchPathBox();
        createGalacticSearchColorBox();
        createAutoNavPathBox();

        System.out.println(className + ": Constructor Finished");
    }

    protected static SkillsCompetitionTab getInstance()
    {
        return instance;
    }



    /**
    * <b>Starting Location</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createCompetitionBox()
    {
        //create and name the Box
        SendableRegistry.add(competitionBox, "Competition Type");
        SendableRegistry.setName(competitionBox, "Competition Type");
        
        //add options to  Box
        competitionBox.setDefaultOption("None", Competition.kNone);
        competitionBox.addOption("Galactic Search", Competition.kGalacticSearch);
        competitionBox.addOption("Auto Nav", Competition.kAutoNav);

        //put the widget on the shuffleboard
        skillsCompetitionTab.add(competitionBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(0, 0)
            .withSize(8, 2);
    }

    private void createGalacticSearchPathBox()
    {
        //create and name the Box
        SendableRegistry.add(galacticSearchPathBox, "Galactic Search Path");
        SendableRegistry.setName(galacticSearchPathBox, "Galactic Search Path");
        
        //add options to  Box
        galacticSearchPathBox.setDefaultOption("None", GalacticSearchPath.kNone);
        galacticSearchPathBox.addOption("A", GalacticSearchPath.kA);
        galacticSearchPathBox.addOption("B", GalacticSearchPath.kB);

        //put the widget on the shuffleboard
        skillsCompetitionTab.add(galacticSearchPathBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(9, 3)
            .withSize(5, 2);
    }

    private void createGalacticSearchColorBox()
    {
        //create and name the Box
        SendableRegistry.add(galacticSearchColorBox, "Galactic Search Color");
        SendableRegistry.setName(galacticSearchColorBox, "Galactic Search Color");
        
        //add options to  Box
        galacticSearchColorBox.setDefaultOption("None", GalacticSearchColor.kNone);
        galacticSearchColorBox.addOption("Red", GalacticSearchColor.kRed);
        galacticSearchColorBox.addOption("Blue", GalacticSearchColor.kBlue);

        //put the widget on the shuffleboard
        skillsCompetitionTab.add(galacticSearchColorBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(9, 0)
            .withSize(5, 2);
    }

    private void createAutoNavPathBox()
    {
        //create and name the Box
        SendableRegistry.add(autoNavPathBox, "AutoNav Path");
        SendableRegistry.setName(autoNavPathBox, "AutoNav Path");
        
        //add options to  Box
        autoNavPathBox.setDefaultOption("None", AutoNavPath.kNone);
        autoNavPathBox.addOption("Barrel Racing", AutoNavPath.kBarrelRacing);
        autoNavPathBox.addOption("Slalom", AutoNavPath.kSlalom);
        autoNavPathBox.addOption("Bounce", AutoNavPath.kBounce);

        //put the widget on the shuffleboard
        skillsCompetitionTab.add(autoNavPathBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(0, 3)
            .withSize(8, 2);
    }

    private void updateSkillsCompetitionTabData()
    {
        skillsCompetitionTabData.competition = competitionBox.getSelected();
        skillsCompetitionTabData.galacticSearchPath = galacticSearchPathBox.getSelected();
        skillsCompetitionTabData.galacticSearchColor = galacticSearchColorBox.getSelected();
        skillsCompetitionTabData.autoNavPath = autoNavPathBox.getSelected();
    }
/*
    public void checkForNewSkillsCompetitionTabData()
    {
        boolean isSendDataButtonPressed = sendDataButton.getSelected();

        if(isSendDataButtonPressed && !previousStateOfSendButton)
        {
            previousStateOfSendButton = true;

            // Get values from the Boxes
            updateAutonomousTabData();

            System.out.println(autonomousTabData);
            
            if(isDataValid())
            {
                goodToGo.setBoolean(true);   
            }
            else
            {
                goodToGo.setBoolean(false);
            }
        }
        
        if (!isSendDataButtonPressed && previousStateOfSendButton)
        {
            previousStateOfSendButton = false;
        }
    }*/

    public SkillsCompetitionTabData getSkillsCompetitionTabData()
    {
        updateSkillsCompetitionTabData();
        return skillsCompetitionTabData;
    }
}