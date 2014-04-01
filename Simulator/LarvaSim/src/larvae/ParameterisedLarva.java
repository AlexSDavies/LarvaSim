package larvae;

import java.util.Random;

import simulation.Point;
import simulation.Simulation;

public class ParameterisedLarva extends FullStatLarva {

	private Random rng;
	
	
	private double headCastDirScaling;
	
	public ParameterisedLarva(Simulation sim, Parameters params, Point startPos, double dir)
	{
		super(sim, params, startPos, dir);
		
		rng = new Random();
		
		headCastDirScaling = 0.3;
		
	}
	
	
	public void setHeadCastDirScaling(double scaling)
	{
		this.headCastDirScaling = scaling;		
	}


	private double getCastAngle(double bearing)
	{
		
		
		double mixProb = 0.5+headCastDirScaling*Math.sin(bearing);
		
		double dir = 0;
		
		if (Math.random() < mixProb)
			{dir = -1;}
		else
			{dir = 1;}
		
		
		double mean = 0.61;
		double var = 0.35;
		
		
		double randomAngle = rng.nextGaussian()*Math.sqrt(var) + mean;
		
		double angle = randomAngle*dir;
		
		return angle;
		
	}
	

	
	/* TODO: overwrite 
	 * 
	private double getTurnProbability(double bearing){
		
	}
	*/

}
