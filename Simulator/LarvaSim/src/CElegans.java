


public class CElegans extends Larva {

	public CElegans(Simulation sim, Point startPos, double dir)
	{
		super(sim, startPos, dir);
		
		AlgoLarvaParameters params = (AlgoLarvaParameters) sim.getParameters();
		
		if (params.castProbBase == 0)
		{
			System.out.println("Warning: cast termination base probability should not be zero!");
			System.exit(1);
		}
		
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

			break;
		
		case CAST_LEFT:
			moveSuccess = turnHead(params.castSpeed*timestep);
			if (getRelativeHeadAngle() > params.castAngle || !moveSuccess)
			{
				state = LarvaState.CAST_RIGHT;
			}
			if (Math.random() < getHeadCastStopProbability())
			{
				state = LarvaState.FORWARD;
			}
			
			break;
		
		case CAST_RIGHT:
			moveSuccess = turnHead(-params.castSpeed*timestep);
			if (getRelativeHeadAngle() < -params.castAngle || !moveSuccess)
			{
				state = LarvaState.CAST_LEFT;
			}
			if (Math.random() < getHeadCastStopProbability())
			{
				state = LarvaState.FORWARD;
			}
			
			break;
			
		}
		
		// Update perception
		addPerception(getOdourValueHead());		
		previousOdour = getOdourValueHead();
	}
	
	
	// Returns the probability of stopping head casting
	// (Doesn't depend on perception for CElegans)
	public double getHeadCastStopProbability() {
		
		// Scale rate
		double rate = params.castProbBase;
		
		// Calculate turn probability based on rate and timestep
		double p = timestep*rate;
		
		return p;

	}

}
