package frc.shuffleboard;

import frc.controls.OperatorController;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;


public class OperatorControllerTab
{
    private static final String className = new String("[OperatorControllerTab]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    /**
     * The AxisObjects class is used to create the objects needed on the Shuffleboard and Network Table.
     */
    private class AxisObjects
    {
        private NetworkTableEntry deadzoneEntry;
        private NetworkTableEntry minOutputEntry;
        private NetworkTableEntry maxOutputEntry;
        private SendableChooser<Boolean> isFlipped = new SendableChooser<>();
        private SendableChooser<OperatorController.AxisScale> axisScaleComboBox = new SendableChooser<>();
    }

    private OperatorController operatorController = OperatorController.getInstance();

    // Create a Shuffleboard Tab
    private ShuffleboardTab operatorControllerTab = Shuffleboard.getTab("Operator Controller");

    private AxisObjects xAxisObjects = new AxisObjects();
    private AxisObjects yAxisObjects = new AxisObjects();
    private AxisObjects zAxisObjects = new AxisObjects();
    private AxisObjects sliderAxisObjects = new AxisObjects();


    private static OperatorControllerTab instance = new OperatorControllerTab();

    private OperatorControllerTab()
    {
        System.out.println(className + " : Constructor Started");

        createAxisWidgets(OperatorController.Axis.kXAxis, "X Axis", xAxisObjects, 0);
        createAxisWidgets(OperatorController.Axis.kYAxis, "Y Axis", yAxisObjects, 5);
        createAxisWidgets(OperatorController.Axis.kZAxis, "Z Axis", zAxisObjects, 10);
        createAxisWidgets(OperatorController.Axis.kSlider, "Slider", sliderAxisObjects, 15);

        // TODO: create the other widgets for the other axes

        System.out.println(className + ": Constructor Finished");
    }

    protected static OperatorControllerTab getInstance()
    {
        return instance;
    }
    
    private void createAxisWidgets(OperatorController.Axis axis, String name, AxisObjects axisObjects, int column)
    {
        int row = 0;
        int width = 4;
        int height = 2;

        // Get the current axis settings on the Driver Controller for the given axis
        OperatorController.AxisSettings axisSettings = operatorController.new AxisSettings();
        axisSettings = operatorController.getAxisSettings(axis);

        // Create the text box to set the deadzone of the axis
        axisObjects.deadzoneEntry = createTextBox(name + " Deadzone", Double.toString(axisSettings.axisDeadzone), column, row, width, height);
        
        //Create the text box to set the min output of the axis
        row += 2;
        axisObjects.minOutputEntry = createTextBox(name + " Min Output", Double.toString(axisSettings.axisMinOutput), column, row, width, height);

        // Create the text box to set the max output of the axis
        row += 2;
        axisObjects.maxOutputEntry = createTextBox(name + " Max Output", Double.toString(axisSettings.axisMaxOutput), column, row, width, height);

        // Create the button to flip the axis (swap negative and positive)
        row += 2;
        createSplitButtonChooser(axisObjects.isFlipped, name + " Is Flipped", axisSettings.axisIsFlipped, column, row, width, height);

        // Create the combo box to set the axis scale
        row += 3;
        createComboBox(axisObjects.axisScaleComboBox, name + " Axis Scale", axisSettings.axisScale, column, row, width, height);
    }

     /**
    * Create a <b>Text Box</b>
    * <p>Create an entry in the Network Table and add the Text Box to the Shuffleboard Tab
    */
    private NetworkTableEntry createTextBox(String title, String defaultValue, int column, int row, int width, int height)
    {
        return operatorControllerTab.add(title, defaultValue)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(column, row)
            .withSize(width, height)
            .getEntry();
    }

    /**
    * Create a <b>Combo Box</b>
    * <p>Create an entry in the Network Table and add the Combo Box to the Shuffleboard Tab
    */
    private void createComboBox(SendableChooser<OperatorController.AxisScale> comboBox, String title, OperatorController.AxisScale defaultValue, int column, int row, int width, int height)
    {
        SendableRegistry.add(comboBox, title);
        SendableRegistry.setName(comboBox, title);
        
        for(OperatorController.AxisScale axisScale: OperatorController.AxisScale.values())
        {
            if(axisScale == defaultValue)
            {
                comboBox.setDefaultOption(axisScale.toString(), axisScale);
            }
            else
            {
                comboBox.addOption(axisScale.toString(), axisScale);
            }
        }

        operatorControllerTab.add(comboBox)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withPosition(column, row)
            .withSize(width, height);
    }

    /**
    * Create a <b>Split Button Chooser</b>
    * <p>Create an entry in the Network Table and add the Split Button Chooser to the Shuffleboard Tab
    */
    private void createSplitButtonChooser(SendableChooser<Boolean> splitButtonChooser, String title, boolean defaultValue, int column, int row, int width, int height)
    {
        SendableRegistry.add(splitButtonChooser, title);
        SendableRegistry.setName(splitButtonChooser, title);

        splitButtonChooser.setDefaultOption((defaultValue ? "Yes" : "No"), defaultValue);
        splitButtonChooser.addOption((!defaultValue ? "Yes" : "No"), !defaultValue);

        operatorControllerTab.add(splitButtonChooser)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(column, row)
            .withSize(width, height);
    }

    private OperatorController.AxisSettings getAxisSettingsFromShuffleboard(AxisObjects axisObjects)
    {
        OperatorController.AxisSettings axisSettings = operatorController.new AxisSettings();

        axisSettings.axisDeadzone = Double.valueOf(axisObjects.deadzoneEntry.getString("0.1"));
        axisSettings.axisMinOutput = Double.valueOf(axisObjects.minOutputEntry.getString("0.0"));
        axisSettings.axisMaxOutput = Double.valueOf(axisObjects.maxOutputEntry.getString("1.0"));
        axisSettings.axisIsFlipped = axisObjects.isFlipped.getSelected();
        axisSettings.axisScale = axisObjects.axisScaleComboBox.getSelected();

        return axisSettings;
    }

    public void setOperatorControllerAxisSettings()
    {
        OperatorController.AxisSettings axisSettings = operatorController.new AxisSettings();

        axisSettings = getAxisSettingsFromShuffleboard(xAxisObjects);
        operatorController.setAxisSettings(OperatorController.Axis.kXAxis, axisSettings);

        axisSettings = getAxisSettingsFromShuffleboard(yAxisObjects);
        operatorController.setAxisSettings(OperatorController.Axis.kYAxis, axisSettings);

        axisSettings = getAxisSettingsFromShuffleboard(zAxisObjects);
        operatorController.setAxisSettings(OperatorController.Axis.kZAxis, axisSettings);

        axisSettings = getAxisSettingsFromShuffleboard(sliderAxisObjects);
        operatorController.setAxisSettings(OperatorController.Axis.kSlider, axisSettings);
    }
}
