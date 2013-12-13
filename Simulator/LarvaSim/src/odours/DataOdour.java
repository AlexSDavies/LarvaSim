package odours;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import simulation.Point;

public class DataOdour extends OdourSource {

	
	double[][] odourValues;
	int width;
	int height;
	double divScale;
	
	double intensity;
	
	// Requires a CSV file of odour values
	// In 0.1 mm intervals
	// Currently odour values are not any 'real world' quantity, generally 0-1
	public DataOdour(String filename)
	{
		
		// Scale hardcoded for now
		divScale = 0.1;
		
		// Odour is multiplied by intensity; default is no scaling
		intensity = 1;
		
		// Read file and convert to 2D array of odour values
		
		List<double[]> odourList = new ArrayList<double[]>();
		
		try(BufferedReader fileReader = new BufferedReader(new FileReader(filename));)
		{
			String currentLine;
			
			
			while ((currentLine = fileReader.readLine()) != null)
			{
				String[] stringValues = currentLine.split(",");
				
				double[] doubleValues = new double[stringValues.length];
				
				for (int i = 0; i < stringValues.length; i++)
				{
					doubleValues[i] = Double.valueOf(stringValues[i]);
				}
				
				odourList.add(doubleValues);
				
			}
			
			fileReader.close();
			
		}
		catch (IOException e)
		{
			System.out.println("Problem reading file " + filename + " - exiting.");
			System.out.println(e.toString());
		}
		
		
		odourValues = odourList.toArray(new double[odourList.size()][]);
		
		
		width = (int) (odourValues[0].length*divScale);
		height = (int) (odourValues.length*divScale);
		
	}

	private Index posToIndex(Point p)
	{
		
		int xInd = (int) ((p.getX() + (width/2))/divScale);
		int yInd = (int) ((p.getY() + (height/2))/divScale);
		
		return(new Index(xInd,yInd));
		
	}
	
	public void setIntensity(double intensity)
	{
		this.intensity = intensity;
	}
	
	
	public double getValue(Point p)
	{
		
		Index ind = posToIndex(p);
	
		// System.out.println(ind.x + ", " + ind.y);
		
		double odourVal;
				
		if(ind.x >= 0 && ind.x < odourValues[0].length && ind.y >= 0 && ind.y < odourValues.length)
		{
			odourVal = odourValues[ind.y][ind.x];
		}
		else
		{
			odourVal = 0;
		}
		
		
		return odourVal;
		
	}
	
	
	class Index
	{
		int x;
		int y;
		
		Index(int x, int y)
		{
			this.x = x; this.y = y;
		}
		
	}

}
