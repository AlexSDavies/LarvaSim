package larvae;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FullStatLarvaParameters extends StatLarvaParameters{

	public double[][] turnAngles;
	public int numPreTurnBearings;
	public int numTurnAngles;
	
	public FullStatLarvaParameters(int numPreTurnBearings, int numTurnAngles)
	{
		super();
		
		this.numPreTurnBearings = numPreTurnBearings;
		this.numTurnAngles = numTurnAngles;
		
		turnAngles = null;
		
	}

	public void setTurnAnglesFromFile(String fileName)
	{
		
		turnAngles = new double[numPreTurnBearings][numTurnAngles];
		
		try(BufferedReader fileReader = new BufferedReader(new FileReader(fileName));)
		{
			
			String currentLine;
			
			int l = 0;
			while ((currentLine = fileReader.readLine()) != null)
			{
				String[] stringValues = currentLine.split(",");
				
				for (int i = 0; i < stringValues.length; i++)
				{
					turnAngles[l][i] = Double.valueOf(stringValues[i]);
				}
				
				l++;
				
			}
			
			fileReader.close();
			
		}
		catch (IOException e)
		{
			System.out.println("Problem reading file " + fileName + " - exiting.");
			System.out.println(e.toString());
		}
		
		
	}
	

	public void setTurnInitiationFromFile(String fileName)
	{
		
		
		try(BufferedReader fileReader = new BufferedReader(new FileReader(fileName));)
		{
			
			String currentLine;
			
			currentLine = fileReader.readLine();
			String[] stringValues = currentLine.split(",");
				
			for (int i = 0; i < stringValues.length; i++)
			{
				this.bearingTurnProbs[i] = Double.valueOf(stringValues[i]);
			}
				
			fileReader.close();
			
		}
		catch (IOException e)
		{
			System.out.println("Problem reading file " + fileName + " - exiting.");
			System.out.println(e.toString());
		}

		
	}
	
	
}
