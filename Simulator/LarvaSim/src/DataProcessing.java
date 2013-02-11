import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DataProcessing {

	
	// Runs through all data processing and saves data
	public static void doAll(List<LarvaData> larvaData, Parameters parameters, String uniqueName) {
		
		String path = "/afs/inf.ed.ac.uk/user/s07/s0784670/LarvaSim/Data/";
		
		// Save all data
		saveData(larvaData,path+"data_"+uniqueName);
		
		// Get list of turn start / end indices
		List<int[]> turnPoints = DataProcessing.getTurnPoints(larvaData);
		
		// Save turn indices
		saveTurnTimes(turnPoints,path+"turns_"+uniqueName);
		
		// Save Parameters
		saveParameters(parameters,path+"parameters_"+uniqueName);
		
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
