package simulation;

import gui.SimViewer;
import gui.SimWindow;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import larvae.LarvaData;
import larvae.Parameters;



public class DataProcessing {

	
	// Runs through all data processing and saves data
	public static void doAll(List<LarvaData> larvaData, Parameters parameters, SimWindow simWindow, String uniqueName) {
		
		String path = "../../Data/";
		
		// Save all data
		saveData(larvaData,path+"data_"+uniqueName);
		
		// Get list of turn start / end indices
		List<int[]> turnPoints = DataProcessing.getTurnPoints(larvaData);
		
		// Save turn indices
		// saveTurnTimes(turnPoints,path+"turns_"+uniqueName);
		
		// Save Parameters
		saveParameters(parameters,path+"parameters_"+uniqueName);
		
		// Save image of path
		if(simWindow != null)
		{
		saveImage(larvaData,simWindow.simViewer,path+"path_"+uniqueName);
		}
		
	}
	
	
	// Saves an image of the larva's path over the whole simulation
	private static void saveImage(List<LarvaData> larvaData, SimViewer simViewer, String saveFile) {
		
		// Short pause to make sure Swing is happy
		try
			{Thread.sleep((int) 100);}
		catch (InterruptedException e)
			{e.printStackTrace();}
		
		// Add all points on path to draw
		for (LarvaData l : larvaData)
			{
			simViewer.addDrawObject(l.getTailPos());
			// simViewer.addDrawObject(l.getHeadPos());
			}
		
		// Copy to buffered image and save
		BufferedImage bImg = new BufferedImage(simViewer.getWidth(), simViewer.getWidth(), BufferedImage.TYPE_INT_RGB);
		Graphics2D saveGraphics = bImg.createGraphics();
		simViewer.paintComponent(saveGraphics);
		try
			{ImageIO.write(bImg, "png", new File(saveFile + ".png"));}
		catch (IOException e)
			{e.printStackTrace();}
		
		
	}



	// Returns the LarvaData objects at the start times of all turns in a list of LarvaData
	public static List<int[]> getTurnPoints(List<LarvaData> larvaData) {
		
		List<LarvaData> turnTimes = new ArrayList<LarvaData>();
		
		List<int[]> turnPositions = new ArrayList<int[]>(); 
		
		LarvaData turnStartPoint = null;
		boolean turning = false;
		
		for (LarvaData dataPoint : larvaData)
		{
			
			// Register start of turn
			if (Math.abs(dataPoint.getDeltaAngle()) > Math.toRadians(10) && !turning)
			{
				turnStartPoint = dataPoint;
				turning = true;
			}
			
			// Register end of turn
			if (Math.abs(dataPoint.getDeltaAngle()) < Math.toRadians(10) && turning)
			{
				turning = false;
				// Save turns of duration > 1 second
				if ((dataPoint.getT() - turnStartPoint.getT()) >= 1)
				{
					turnTimes.add(turnStartPoint);
					int[] thisTurnStartEnd = {larvaData.indexOf(turnStartPoint), larvaData.indexOf(dataPoint)};
					turnPositions.add(thisTurnStartEnd);
				}
				
			}
			
		}
	

		return turnPositions;
		
	}

	// Writes data contained in larvaData to a file
	// Each row is from one timestep, and contains various bits of data
	// as defined by the Larva objects toString method 
	public static void saveData(List<LarvaData> larvaData, String name)
	{
		
		try
		{
			FileWriter fstream = new FileWriter(name);
			BufferedWriter out =  new BufferedWriter(fstream);
			
			out.write(LarvaData.getOutputHeadings() + "\n");
			
			for (LarvaData l : larvaData)
			{
				String output = l.toString();
				out.write(output + "\n");
			}
			
			
			out.close();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Writes turn times corresponding to the indexes of the list of larva data
	public static void saveTurnTimes(List<int[]> turnPoints, String name)
	{
		
		try
		{
			FileWriter fstream = new FileWriter(name);
			BufferedWriter out =  new BufferedWriter(fstream);
			
			for (int[] turns : turnPoints)
			{
				String output = turns[0] + " " + turns[1];
				out.write(output + "\n");
			}
			
			
			out.close();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Writes out parameters to file
	private static void saveParameters(Parameters parameters, String name)
	{
		
		try
		{
			FileWriter fstream = new FileWriter(name);
			BufferedWriter out =  new BufferedWriter(fstream);
			
			out.write(parameters.getParamString());			
			
			out.close();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	
	

}
