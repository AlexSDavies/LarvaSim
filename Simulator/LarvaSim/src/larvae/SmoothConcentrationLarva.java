package larvae;
import simulation.Point;
import simulation.Simulation;




public class SmoothConcentrationLarva extends Larva {

	private double maxOdourPref;
	private double dislikeSlope;

	public SmoothConcentrationLarva(Simulation sim, AlgoLarvaParameters params, Point startPos, double dir)
	{
		super(sim, params, startPos, dir);
		
		this.maxOdourPref = 0.5;
		
		this.dislikeSlope = 5;
		
	}
	
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void update()
	{
		boolean moveSuccess;
		
		// Do movement	
		switch(state){
		
		case FORWARD:
			moveSuccess = moveForward(forwardSpeed*timestep);
			if (Math.random() < getTurnProbability() || !moveSuccess)
			{
				if(Math.random() < 0.5)
					{state = LarvaState.CAST_LEFT;}
				else
					{state = LarvaState.CAST_RIGHT;}
			}
			headCastRange = sampleHeadCastRange();
			break;
		
		case CAST_LEFT:
			moveSuccess = turnHead(parameters.castSpeed*timestep);
			if (getRelativeHeadAngle() > parameters.castAngle || !moveSuccess)
			{
				state = LarvaState.CAST_RIGHT;
				headCastRange = sampleHeadCastRange();
			}
			if (getRelativeHeadAngle() > 0 &&  Math.random() < getHeadCastStopProbability())
			{
				state = LarvaState.FORWARD;
			}
			
			break;
		
		case CAST_RIGHT:
			moveSuccess = turnHead(-parameters.castSpeed*timestep);
			if (getRelativeHeadAngle() < -parameters.castAngle || !moveSuccess)
			{
				state = LarvaState.CAST_LEFT;
				headCastRange = sampleHeadCastRange();
			}
			if (getRelativeHeadAngle() < 0 && Math.random() < getHeadCastStopProbability())
			{
				state = LarvaState.FORWARD;
			}
			
			break;

		}
		
		// Update perception
		addPerception(getOdourValueHead());		
		previousOdour = getOdourValueHead();

	}
	
	public double getTurnProbability()
	{
		
		double[] perception = getPerceptionHistory();
		
		// Multiply kernel by perception history
		double rate = 0;
		for(int i = 0; i < perceptionHistoryLength; i++)
		{
			rate += perception[i]*turnStimulusKernel[i];
		}
		
		// Reduce rate based on dislike for odour
		rate = rate * (1 - 2*getSigmoidDislike());
		
		// Scale rate and get rid of negatives
		rate = Math.max(parameters.turnProbMult*rate,0.0);
		
		// Calculate turn probability based on rate and timestep
		double p = timestep * (rate + parameters.turnProbBase);
		
		return p;
		
		
	}
	
	
	public double getHeadCastStopProbability()
	{
		
		double[] perception = getPerceptionHistory();
		
		// Multiply kernel by perception history
		double rate = 0;
		for(int i = 0; i < perceptionHistoryLength; i++)
		{
			rate += perception[i]*headCastStimulusKernel[i];
		}
		
		// Reduce rate based on dislike for odour
		rate = rate * (1 - 2*getSigmoidDislike());
		
	
		// Scale rate and get rid of negatives
		rate = Math.max(parameters.castProbMult*rate,0);
		
		// Calculate turn probability based on rate and timestep
		double p = timestep * (rate + parameters.castProbBase);
		
		return p;
		
	}

	
	@SuppressWarnings("unused")
	private double getDislike()
	{
		
		double odourVal = getOdourValueMid();
		
		double dislike = odourVal / maxOdourPref;
		
		return dislike;
		
	}
	
	private double getSigmoidDislike()
	{
		
		double odourVal = getOdourValueMid();

		double dislike = 1/(1 + Math.exp(-dislikeSlope*(odourVal-maxOdourPref)));
		
		return dislike;
		
	}
	

	public void setMaxOdourPref(double pref)
	{
		maxOdourPref = pref;
	}

	public void setDislikeSlope(double slope)
	{
		dislikeSlope = slope;
	}
	
}