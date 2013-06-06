package larvae;
import simulation.Point;
import simulation.Simulation;

public class OneShotLarva extends Larva {

	
	double currentCastAngle;
	
	public OneShotLarva(Simulation sim, Point startPos, double dir)
	{
		super(sim, startPos, dir);
		
		currentCastAngle = getNewCastAngle();
							
	}

	private double getNewCastAngle() {

		double angle = Math.PI/6 + Math.random()*(4*Math.PI/6);
		return angle;
	}

	@Override
	public void update()
	{
		
		boolean moveSuccess;
		
		// Do movement	
		switch(state){
		
		// Same as AlgoLarva
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
			moveSuccess = turnHead(currentCastAngle*timestep);
			if (getRelativeHeadAngle() > currentCastAngle || !moveSuccess)
			{
				state = LarvaState.CAST_BACK_RIGHT;
				headCastRange = sampleHeadCastRange();
				
				if (Math.random() < getHeadCastStopProbability())
				{
					state = LarvaState.FORWARD;
				}
				
			}
			
			break;
		
		case CAST_RIGHT:
			moveSuccess = turnHead(-currentCastAngle*timestep);
			if (getRelativeHeadAngle() < -currentCastAngle || !moveSuccess)
			{
				state = LarvaState.CAST_BACK_LEFT;
				headCastRange = sampleHeadCastRange();
				
				if (Math.random() < getHeadCastStopProbability())
				{
					state = LarvaState.FORWARD;
				}
			}
			
			break;
			
		case CAST_BACK_RIGHT:
			moveSuccess = turnHead(-currentCastAngle*timestep);
			if (getRelativeHeadAngle() < 0 || !moveSuccess)
			{
				currentCastAngle = getNewCastAngle();
				
				double r = Math.random();
				
				if(r > 0.1)
					{state = LarvaState.CAST_RIGHT;}
				else if (r > 0.2)
					{state = LarvaState.FORWARD;}
				else
					{state = LarvaState.CAST_LEFT;}
			}
			
			break;
			
		case CAST_BACK_LEFT:
			moveSuccess = turnHead(currentCastAngle*timestep);
			if (getRelativeHeadAngle() > 0 || !moveSuccess)
			{
				currentCastAngle = getNewCastAngle();
				
				double r = Math.random();
				
				if(r > 0.1)
					{state = LarvaState.CAST_LEFT;}
				else if (r > 0.2)
					{state = LarvaState.FORWARD;}
				else
					{state = LarvaState.CAST_RIGHT;}
			}
			
			break;
			
			
		}
		
		// Update perception
		addPerception(getOdourValueHead());		
		previousOdour = getOdourValueHead();
		
	}

}
