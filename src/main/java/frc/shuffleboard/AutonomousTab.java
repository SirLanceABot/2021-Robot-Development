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


public class AutonomousTab
{
    private static final String className = new String("[AutonomousTab]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    // Create enumerated types for each Box
    //-------------------------------------------------------------------//
    public enum StartingLocation
    {
        kNone, kRight, kCenter, kLeft;
    }

    //-------------------------------------------------------------------//

    public enum OrderOfOperations
    {
        kShootThenMove, kMoveThenShoot;
    }

    //-------------------------------------------------------------------//

    public enum ShootPowerCell
    {
        kYes, kNo;
    }

    public enum ShootDelay
    {
        k0, k1, k2, k3, k4, k5;
    }

    //-------------------------------------------------------------------//

    public enum MoveOffLine
    {
        kYes, kNo;
    }

    public enum MoveDelay
    {
        k0, k1, k2, k3, k4, k5;
    }

    public enum DirectionToMove
    {
        kAwayFromPowerPort, kTowardPowerPort;
    }

    //-------------------------------------------------------------------//

    public enum PickUpPowerCell
    {
        kYes, kNo;
    }

    public enum PickUpLocation
    {
        kTrench, kRendezvousPoint;
    }

    public enum ShootNewPowerCells
    {
        kYes, kNo;
    }



    // Create a class to hold the data on the Shuffleboard tab
    protected static class AutonomousTabData
    {
        public StartingLocation startingLocation = StartingLocation.kNone;

        public OrderOfOperations orderOfOperations = OrderOfOperations.kShootThenMove;

        public ShootPowerCell shootPowerCell = ShootPowerCell.kYes;
        public ShootDelay shootDelay = ShootDelay.k0;

        public MoveOffLine moveOffLine = MoveOffLine.kYes;
        public MoveDelay moveDelay = MoveDelay.k0;
        public DirectionToMove directionToMove = DirectionToMove.kAwayFromPowerPort;

        public PickUpPowerCell pickUpPowerCell = PickUpPowerCell.kYes;
        public PickUpLocation pickUpLocation = PickUpLocation.kTrench;
        public ShootNewPowerCells shootNewPowerCells = ShootNewPowerCells.kYes;


        
        @Override
        public String toString()
        {
            String str = "";

            str += " \n";
            str += "*****  AUTONOMOUS SELECTION  *****\n";
            str += "Starting Location     : "  + startingLocation   + "\n";
            str += "Order of Operations   : "  + orderOfOperations  + "\n";
            str += "Shoot Power Cell      : "  + shootPowerCell     + "\n";
            str += "Shoot Delay           : "  + shootDelay         + "\n";
            str += "Move Off Line         : "  + moveOffLine        + "\n";
            str += "Move Delay            : "  + moveDelay          + "\n";
            str += "Direction to Move     : "  + directionToMove    + "\n";
            str += "Pick Up Power Cell    : "  + pickUpPowerCell    + "\n";
            str += "Pick Up Location      : "  + pickUpLocation     + "\n";    
            str += "Shoot New Power Cells : "  + shootNewPowerCells + "\n";

            return str;
        }
    }

    // Create a Shuffleboard Tab
    private ShuffleboardTab autonomousTab = Shuffleboard.getTab("Autonomous");

    // Create an object to store the data in the Boxes
    private AutonomousTabData autonomousTabData = new AutonomousTabData();
 
    // Create the Box objects
    private SendableChooser<StartingLocation> startingLocationBox = new SendableChooser<>();

    private SendableChooser<OrderOfOperations> orderOfOperationsBox = new SendableChooser<>();

    private SendableChooser<ShootPowerCell> shootPowerCellBox = new SendableChooser<>();
    private SendableChooser<ShootDelay> shootDelayBox = new SendableChooser<>();
    
    private SendableChooser<MoveOffLine> moveOffLineBox = new SendableChooser<>();
    private SendableChooser<MoveDelay> moveDelayBox = new SendableChooser<>();
    private SendableChooser<DirectionToMove> directionToMoveBox = new SendableChooser<>();

    private SendableChooser<PickUpPowerCell> pickUpPowerCellBox = new SendableChooser<>();
    private SendableChooser<PickUpLocation> pickUpLocationBox = new SendableChooser<>();
    private SendableChooser<ShootNewPowerCells> shootNewPowerCellBox = new SendableChooser<>();
    
    //private SendableChooser<Boolean> redLightGreenLightBox = new SendableChooser<>();
    private NetworkTableEntry goodToGo;


    // Create the Button object
    private SendableChooser<Boolean> sendDataButton = new SendableChooser<>();
    // private NetworkTableEntry sendDataButton;

    private boolean previousStateOfSendButton = false;

    private static AutonomousTab instance = new AutonomousTab();

    private AutonomousTab()
    {
        System.out.println(className + " : Constructor Started");

        createStartingLocationBox();

        createOrderOfOperationsBox();
        
        createShootPowerCellBox();
        createShootDelayBox();
        
        createMoveOffLineBox();
        createMoveDelayBox();
        createDirectionToMoveBox();

        createPickUpPowerCellBox();
        createPickUpLocationBox();
        createShootNewPowerCellBox();
        
        // sendDataButton = createSendDataButton();
        // sendDataButton.setBoolean(false);
        createSendDataButton();

        goodToGo = createRedLightGreenLightBox();
        goodToGo.setBoolean(false);

        System.out.println(className + ": Constructor Finished");
    }

    protected static AutonomousTab getInstance()
    {
        return instance;
    }



    /**
    * <b>Starting Location</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createStartingLocationBox()
    {
        //create and name the Box
        SendableRegistry.add(startingLocationBox, "Starting Location");
        SendableRegistry.setName(startingLocationBox, "Starting Location");
        
        //add options to  Box
        startingLocationBox.setDefaultOption("None", StartingLocation.kNone);
        startingLocationBox.addOption("Left", StartingLocation.kLeft);
        startingLocationBox.addOption("Center", StartingLocation.kCenter);
        startingLocationBox.addOption("Right", StartingLocation.kRight);

        //put the widget on the shuffleboard
        autonomousTab.add(startingLocationBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(0, 0)
            .withSize(8, 2);
    }



    /**
    * <b>Order of Operations</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createOrderOfOperationsBox()
    {
        //create and name the Box
        SendableRegistry.add(orderOfOperationsBox, "Order of Operations");
        SendableRegistry.setName(orderOfOperationsBox, "Order of Operations");

        //add options to box
        orderOfOperationsBox.setDefaultOption("Shoot Then Move", OrderOfOperations.kShootThenMove);
        orderOfOperationsBox.addOption("Move Then Shoot", OrderOfOperations.kMoveThenShoot);

        //put the widget on the Shuffleboard
        autonomousTab.add(orderOfOperationsBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(9,0)
            .withSize(8, 2);
    }



    /**
    * <b>Shoot Power Cell</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createShootPowerCellBox()
    {
        //create and name the Box
        SendableRegistry.add(shootPowerCellBox, "Shoot Power Cell");
        SendableRegistry.setName(shootPowerCellBox, "Shoot Power Cell");

        //add options to Box
        shootPowerCellBox.setDefaultOption("Yes", ShootPowerCell.kYes);
        shootPowerCellBox.addOption("No", ShootPowerCell.kNo);

        //put the widget on the shuffleboard
        autonomousTab.add(shootPowerCellBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(1, 3)
            .withSize(4, 2);
    }

    /**
    * <b>Shoot Delay</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createShootDelayBox()
    {
        //create and name the Box
        SendableRegistry.add(shootDelayBox, "Shoot Delay (Seconds)");
        SendableRegistry.setName(shootDelayBox, "Shoot Delay (Seconds)");

        //add options to Box
        shootDelayBox.setDefaultOption("0", ShootDelay.k0);
        shootDelayBox.addOption("1", ShootDelay.k1);
        shootDelayBox.addOption("2", ShootDelay.k2);
        shootDelayBox.addOption("3", ShootDelay.k3);
        shootDelayBox.addOption("4", ShootDelay.k4);
        shootDelayBox.addOption("5", ShootDelay.k5);

        //put the widget on the shuffleboard
        autonomousTab.add(shootDelayBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(6, 3)
            .withSize(6, 2);
    }



    /**
    * <b>Move</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createMoveOffLineBox()
    {
        //create and name the Box
        SendableRegistry.add(moveOffLineBox, "Move Off Line");
        SendableRegistry.setName(moveOffLineBox, "Move Off Line");

        //add options to Box
        moveOffLineBox.setDefaultOption("Yes", MoveOffLine.kYes);
        moveOffLineBox.addOption("No", MoveOffLine.kNo);

        //put the widget on the shuffleboard
        autonomousTab.add(moveOffLineBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(1, 6)
            .withSize(4, 2);
    }

    /**
    * <b>Move Delay</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createMoveDelayBox()
    {
        //create and name the Box
        SendableRegistry.add(moveDelayBox, "Move Delay (Seconds)");
        SendableRegistry.setName(moveDelayBox, "Move Delay (Seconds)");

        //add options to Box
        moveDelayBox.setDefaultOption("0", MoveDelay.k0);
        moveDelayBox.addOption("1", MoveDelay.k1);
        moveDelayBox.addOption("2", MoveDelay.k2);
        moveDelayBox.addOption("3", MoveDelay.k3);
        moveDelayBox.addOption("4", MoveDelay.k4);
        moveDelayBox.addOption("5", MoveDelay.k5);

        //put the widget on the shuffleboard
        autonomousTab.add(moveDelayBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(6, 6)
            .withSize(6, 2);
    }

    /**
    * <b>Move Direction</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createDirectionToMoveBox()
    {
        //create and name the Box
        SendableRegistry.add(directionToMoveBox, "Direction to Move");
        SendableRegistry.setName(directionToMoveBox, "Direction to Move");

        //add options to Box
        directionToMoveBox.setDefaultOption("Away From Power Port", DirectionToMove.kAwayFromPowerPort);
        directionToMoveBox.addOption("Toward Power Port", DirectionToMove.kTowardPowerPort);
        
        //put the widget on the shuffleboard
        autonomousTab.add(directionToMoveBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(13, 6)
            .withSize(8, 2);
    }

    /**
    * <b>Pick Up Power Cell</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createPickUpPowerCellBox()
    {
        //create and name the Box
        SendableRegistry.add(pickUpPowerCellBox, "Pick Up Power Cell");
        SendableRegistry.setName(pickUpPowerCellBox, "Pick Up Power Cell");

        //add options to Box
        pickUpPowerCellBox.setDefaultOption("Yes", PickUpPowerCell.kYes);
        pickUpPowerCellBox.addOption("No", PickUpPowerCell.kNo);

        //put the widget on the shuffleboard
        autonomousTab.add(pickUpPowerCellBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(1, 9)
            .withSize(4, 2);
    }

    /**
    * <b>Power Cell Pick Up Location</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createPickUpLocationBox()
    {
        //create and name the Box
        SendableRegistry.add(pickUpLocationBox, "Pick Up Location");
        SendableRegistry.setName(pickUpLocationBox, "Pick Up Location");

        //add options to Box
        pickUpLocationBox.setDefaultOption("Trench", PickUpLocation.kTrench);
        pickUpLocationBox.addOption("Rendezvous Point", PickUpLocation.kRendezvousPoint);

        //put the widget on the shuffleboard
        autonomousTab.add(pickUpLocationBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(6, 9)
            .withSize(6, 2);
    }

    private void createShootNewPowerCellBox()
    {
        //create and name the Box
        SendableRegistry.add(shootNewPowerCellBox, "Shoot New Power Cells");
        SendableRegistry.setName(shootNewPowerCellBox, "Shoot New Power Cells");

        //add options to Box
        shootNewPowerCellBox.setDefaultOption("Yes", ShootNewPowerCells.kYes);
        shootNewPowerCellBox.addOption("No", ShootNewPowerCells.kNo);

        //put the widget on the shuffleboard
        autonomousTab.add(shootNewPowerCellBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(13, 9)
            .withSize(8, 2);
    }

    /**
     * <b>Send Data</b> Button
     * <p>
     * Create an entry in the Network Table and add the Button to the Shuffleboard
     * Tab
     * 
     * @return
     */
    private void createSendDataButton()
    {
        SendableRegistry.add(sendDataButton, "Send Data");
        SendableRegistry.setName(sendDataButton, "Send Data");

        sendDataButton.setDefaultOption("No", false);
        sendDataButton.addOption("Yes", true);

        autonomousTab.add(sendDataButton)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(23, 1)
            .withSize(4, 2);
        
        // return autonomousTab.add("Send Data", false)
        //     .withWidget(BuiltInWidgets.kToggleSwitch)
        //     .withPosition(23, 1)
        //     .withSize(4, 2)
        //     .getEntry();
    }

    private NetworkTableEntry createRedLightGreenLightBox()
    {
        //SendableRegistry.add(redLightGreenLightBox, "Good to Go?");
        //SendableRegistry.setName(redLightGreenLightBox, "Good to Go?");

        // redLightGreenLightBox.setDefaultOption("No", false);
        // redLightGreenLightBox.addOption("Yes", true);

        Map<String, Object> booleanBoxProperties = new HashMap<>();
        booleanBoxProperties.put("Color when true", "Lime");
        booleanBoxProperties.put("Color when false", "Red");
        
        return autonomousTab.add("Good to Go?", false)
             .withWidget(BuiltInWidgets.kBooleanBox)
             .withPosition(23, 4)
             .withSize(4, 4)
             .withProperties(booleanBoxProperties)
             .getEntry();

        
    }

    private void updateAutonomousTabData()
    {
        autonomousTabData.startingLocation = startingLocationBox.getSelected();

        autonomousTabData.orderOfOperations = orderOfOperationsBox.getSelected();
        
        autonomousTabData.shootPowerCell = shootPowerCellBox.getSelected();
        autonomousTabData.shootDelay = shootDelayBox.getSelected();

        autonomousTabData.moveOffLine = moveOffLineBox.getSelected();
        autonomousTabData.moveDelay = moveDelayBox.getSelected();
        autonomousTabData.directionToMove = directionToMoveBox.getSelected();

        autonomousTabData.pickUpPowerCell = pickUpPowerCellBox.getSelected();
        autonomousTabData.pickUpLocation = pickUpLocationBox.getSelected();
        
        autonomousTabData.shootNewPowerCells = shootNewPowerCellBox.getSelected();
    }

    public void checkForNewAutonomousTabData()
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
    }

    public AutonomousTabData getAutonomousTabData()
    {
        return autonomousTabData;
    }

    private boolean isDataValid()
    {
        boolean isValid = true;

        boolean pickUpPowerCell = (autonomousTabData.pickUpPowerCell == PickUpPowerCell.kYes);

        boolean moveTowardPowerPort = (autonomousTabData.directionToMove == DirectionToMove.kTowardPowerPort);

        boolean shootNewPowerCells = (autonomousTabData.shootNewPowerCells == ShootNewPowerCells.kYes);

        boolean startingLocationLeft = (autonomousTabData.startingLocation == StartingLocation.kLeft);
        boolean startingLocationRight = (autonomousTabData.startingLocation == StartingLocation.kRight);

        boolean pickUpLocation = (autonomousTabData.pickUpLocation == PickUpLocation.kTrench);


        //if trying to pick up new cell and moving away from them
        if(pickUpPowerCell && moveTowardPowerPort)
        {
            isValid = false;
            
            DriverStation.reportWarning("Cannot Pick Up Power Cells and Move Toward Power Port", false);
        }

        //if not picking up new cell and trying to shoot it
        if(!pickUpPowerCell && shootNewPowerCells)
        {
            isValid = false;
            DriverStation.reportWarning("Must Pick Up Power Cell to Shoot New Power Cell", false);

        }

        //if starting left and going to trench
        if(startingLocationLeft && pickUpLocation)
        {
            isValid = false;
            DriverStation.reportWarning("Advised not to start Left with Pick Up Location: Trench.\n Potential crossover collision.", false);
        }

        //if starting right and going to rendezvous point
        if(startingLocationRight && !pickUpLocation)
        {
            isValid = false;
            DriverStation.reportWarning("Advised not to start Right with Pick Up Location: Rendezvous Point.\n Potential crossover collision", false);

        }

        return isValid;
    }
}
