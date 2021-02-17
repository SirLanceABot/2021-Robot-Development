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



    // Create a class to hold the data on the Shuffleboard tab
    protected static class SkillsCompetitionTabData
    {
        public Competition competition = Competition.kNone;


        
        @Override
        public String toString()
        {
            String str = "";

            str += " \n";
            str += "*****  Skills Competition  *****\n";
            str += "Competition           : "  + competition   + "\n";

            return str;
        }
    }

    // Create a Shuffleboard Tab
    private ShuffleboardTab skillsCompetitionTab = Shuffleboard.getTab("Skills Competition");

    // Create an object to store the data in the Boxes
    private SkillsCompetitionTabData skillsCompetitionTabData = new SkillsCompetitionTabData();

    // Create the Box objects
    private SendableChooser<Competition> competitionBox = new SendableChooser<>();

    private static SkillsCompetitionTab instance = new SkillsCompetitionTab();

    private SkillsCompetitionTab()
    {
        System.out.println(className + " : Constructor Started");

        createCompetitionBox();

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
        SendableRegistry.add(competitionBox, "Starting Location");
        SendableRegistry.setName(competitionBox, "Starting Location");
        
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

    private void updateSkillsCompetitionTabData()
    {
        skillsCompetitionTabData.competition = competitionBox.getSelected();
    }

    public SkillsCompetitionTabData getSkillsCompetitionTabData()
    {
        return skillsCompetitionTabData;
    }
}