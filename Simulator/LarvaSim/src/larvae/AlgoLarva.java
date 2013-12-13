package larvae;
import simulation.Point;
import simulation.Simulation;





public class AlgoLarva extends KernelLarva
{

	public AlgoLarva(Simulation sim, KernelLarvaParameters params, Point startPos, double dir)
	{
		super(sim,params,startPos, dir);
	}
	
	
	@SuppressWarnings("incomplete-switch")
	public void update(){
		
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
			if (Math.random() < getHeadCastStopProbability())
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
	

}
