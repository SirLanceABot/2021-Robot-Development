package frc.autonomous.commands.motionprofile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
//import java.util.logging.Level;

import edu.wpi.first.wpilibj.Filesystem;

public class CSVReader 
{
	String filename;
    double vmax;
    List<String> commands = new ArrayList<>();
	
	public CSVReader(String filename)
	{
		this.filename = Filesystem.getDeployDirectory() +"/" + filename;
    }

	public double[][] parseCSV() 
	{
		File file= new File(filename);
		
		List<List<String>> lines = new ArrayList<>();
		Scanner inputStream;
		
		try
		{
			inputStream = new Scanner(file);
			vmax = Double.parseDouble(inputStream.nextLine().trim());
			System.out.println("vmax equals: " + vmax);
			while(inputStream.hasNextLine()){
				String line= inputStream.nextLine();
				String[] values = line.split(",");
				lines.add(Arrays.asList(values));
			}
			
			inputStream.close();
			
		} catch (FileNotFoundException e)
		{
			System.out.println("*** ERROR ***");
			System.out.println("THE FILE WAS NOT FOUND");
			e.printStackTrace();
		}

		//	System.out.println(lines.size());

		// the following code iterates through the 2 dimensional array
		double [][] profile = new double[lines.size()][5];
		for(int counter1 = 0; counter1 < lines.size(); counter1++) 
		{
			List<String> line = lines.get(counter1);
			for (int counter2 = 0; counter2 < 5; counter2++) 
			{
				profile[counter1][counter2] = Double.parseDouble(line.get(counter2));
			}
			commands.add(line.get(4)); //#TODO: USED TO BE 5
		}
		return profile;
    }
    
	public List<String> getCommands()
	{
    	return commands;
    }

	public double getVmax()
	{
		return vmax;
    }

	public static void main(String[] args) 
	{
		String filename = args[0];
		CSVReader reader = new CSVReader(filename);
		reader.parseCSV();
    }
}
