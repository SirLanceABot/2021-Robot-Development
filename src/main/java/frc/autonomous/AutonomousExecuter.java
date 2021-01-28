package frc.autonomous;

import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import frc.autonomous.commands.interfaces.*;

public class AutonomousExecuter
{
    private static boolean hasBeenInitialized = false;
    private static int currentCommandIndex = 0;
    private static ArrayList<Command> currentCommandList;
    private static AutonomousExecuter autoExecuter = new AutonomousExecuter();
    private static AutonomousBuilder autoBuilder = AutonomousBuilder.getInstance();

    private static ArrayList<ArrayList<Command>> commandList = autoBuilder.getCommandList();

    private AutonomousExecuter()
    {

    }

    public static AutonomousExecuter getAutonomousExecuter()
    {
        return autoExecuter;
    }

    public void executeAuto()
    {
        if(currentCommandIndex < commandList.size())
        {
            currentCommandList = commandList.get(currentCommandIndex);

            if(areCommandsDone(currentCommandList) == true)
            {
                System.out.println("Ending the Command number: " + currentCommandIndex);
                currentCommandIndex++;
                hasBeenInitialized = false;
                endCommands(currentCommandList);
            }
            else if(hasBeenInitialized == false)
            {
                System.out.println("Initializing the Command number: " + currentCommandIndex);
                initCommands(currentCommandList);
                hasBeenInitialized = true;
            }   
            else
            {
                System.out.println("Running the Command number: " + currentCommandIndex);
                executeCommands(currentCommandList);
            }
        }
        else
        {
            System.out.println("Done w auto");
        }
    }









    /**
     * checks to see if all the commands that are running in parrallel are done
     * might just be one command running but it is checked all the same
     * @param command
     * @return 
     */
    public boolean areCommandsDone(ArrayList<Command> commands)
    {
       for(Command command: commands)
        {
            if(command.isFinished() == false)
            {
                return false;
            }
        }
        //returns true if all the commands that are running from the node are completed
        return true;
    }

    public void initCommands(ArrayList<Command> commands)
    {
        for(Command command: commands)
        {
            command.init();
        }
    }

    public void executeCommands(ArrayList<Command> commands)
    {
        for(Command command: commands)
        {
            if(!command.isFinished())
            {
                command.execute();
            }
        }
    }

    public void endCommands(ArrayList<Command> commands)
    {
        for(Command command: commands)
        {
            command.end();
        }
    }

}