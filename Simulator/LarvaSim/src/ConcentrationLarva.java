


public class ConcentrationLarva extends Larva {

	private double maxOdourPref;

	public ConcentrationLarva(Simulation sim, Point startPos, double dir)
	{
		super(sim, startPos, dir);
		
		this.maxOdourPref = 0.5;
		
	}
	
	
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
			moveSuccess = turnHead(params.castSpeed*timestep);
			if (getRelativeHeadAngle() > params.castAngle || !moveSuccess)
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
			moveSuccess = turnHead(-params.castSpeed*timestep);
			if (getRelativeHeadAngle() < -params.castAngle || !moveSuccess)
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
		
		// Invert rate if odour concentration is too high
		if (getOdourValueHead() > maxOdourPref)
		{
			rate = -rate;
		}
		
		// Scale rate and get rid of negatives
		rate = Math.max(params.turnProbMult*rate,0.0);
		
		// Calculate turn probability based on rate and timestep
		double p = timestep * (rate + params.turnProbBase);
		
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
		
		if (getOdourValueHead() > maxOdourPref)
		{
			rate = -rate;
		}
		
		// Scale rate and get rid of negatives
		rate = Math.max(params.castProbMult*rate,0);
		
		// Calculate turn probability based on rate and timestep
		double p = timestep * (rate + params.castProbBase);
		
		return p;
		
	}


	public void setMaxOdourPref(double pref)
	{
		maxOdourPref = pref;
	}

}