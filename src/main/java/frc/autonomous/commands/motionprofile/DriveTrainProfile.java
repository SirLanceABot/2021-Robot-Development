package frc.autonomous.commands.motionprofile;

import java.util.ArrayList;
import java.util.List;

import frc.autonomous.commands.motionprofile.CSVReader;

public class DriveTrainProfile 
{
	
	private String profileFile;
	private ArrayList<Checkpoint> leftCheckpoints;
	private ArrayList<Checkpoint> rightCheckpoints;
	private List<String> commands;
	
	public DriveTrainProfile(String profileFile) {
		this.profileFile = profileFile;
		// construct the array lists to store the checkpoints for the left and right motors
		leftCheckpoints = new ArrayList<Checkpoint>();
		rightCheckpoints = new ArrayList<Checkpoint>();
		// construct the csv reader
		CSVReader reader = new CSVReader(this.profileFile);
		// store the csv data to double array
		double[][] profilePoints = reader.parseCSV();
		// store the commands into a local string list
		commands = reader.getCommands();
		
		// iterates through the 2d array of profile points
		for (double[] point : profilePoints) {
			// make new checkpoint variables
			Checkpoint leftCheckpoint = new Checkpoint();
			Checkpoint rightCheckpoint = new Checkpoint();
			
			// store the values from the csv file (which are in the double array) and store to local variabels
			double leftPos = point[0];
	    	double leftVel = point[1];
	    	double rightPos = point[2];
	    	double rightVel = point[3];
			double heading = point[4];
			// add the values to the checkpoints
	    	leftCheckpoint.position = leftPos;
	    	leftCheckpoint.velocity = leftVel;
	    	leftCheckpoint.angle = heading;
	    	rightCheckpoint.position = rightPos;
	    	rightCheckpoint.velocity = rightVel;
	    	rightCheckpoint.angle = heading;
	    	leftCheckpoints.add(leftCheckpoint);
	    	rightCheckpoints.add(rightCheckpoint);
		}
	}
	
	/**
	 * Getter function to get the command that is associated with the index specified.
	 * TODO: Need to say what the indexes are for each command
	 * @param index
	 * @return A string of the specified command
	 */
	public String getCommand(int index) {
		return commands.get(index);
	}
	

	/**
	 * Getter function for the Checkpoint array of leftCheckpoints
	 * @return Left Checkpoints Array	
	 */
	public ArrayList<Checkpoint> getLeftCheckpoints() {
		return leftCheckpoints;
	}

	
	/**
	 * Getter function for the Checkpoint array of rightCheckpoints
	 * @return Right Checkpoints Array	
	 */
	public ArrayList<Checkpoint> getRightCheckpoints() {
		return rightCheckpoints;
	}

	/**
	 * Returns the the filename of the csv as a string
	 * @return Name of csv as a string
	 */
	public String getProfileFile() {
		return this.profileFile;
	}
	
	/**
	 * Setter function to set the path of the file:
	 * #TODO: Specify whether it is a local path or the system path
	 * UPDATE: I used the system path but will validate if the local path works later
	 * @param filePath
	 */
	public void setProfileFile(String filePath) {
		this.profileFile = filePath;
	}
	
}
