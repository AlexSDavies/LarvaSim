package larvae;
import simulation.Point;
import simulation.Simulation;




public class Larva_MinForward extends KernelLarva {

	private double lastHeadCastTime;
	
	private double minForwardInterval;
	
	private double previousAngle;
	
	public Larva_MinForward(Simulation sim, Parameters params, Point startPos, double dir)
	{
		super(sim, params, startPos, dir);
		lastHeadCastTime = 0;
		minForwardInterval = ((MinForwardParameters) params).minForwardInterval;
		previousAngle = getHeadAngle();
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
				if(sim.getTime() - lastHeadCastTime > minForwardInterval)
				{
					if(Math.random() < 0.5)
						{state = LarvaState.CAST_LEFT;}
					else
						{state = LarvaState.CAST_RIGHT;}
				}
			}

			break;
		
		case CAST_LEFT:
			
			moveSuccess = turnHead(parameters.castSpeed*timestep);
			
			if (getRelativeHeadAngle() > parameters.castAngle || !moveSuccess)
			{
				state = LarvaState.CAST_RIGHT;
			}
			
			if (getRelativeHeadAngle() > 0.65 && Math.random() < getHeadCastStopProbability())
			{
				state = LarvaState.FORWARD;
				lastHeadCastTime = sim.getTime();
			}
			
			// Sometimes switch back to other cast direction
			if (previousAngle < 0 && getRelativeHeadAngle() > 0)
			{
				if (Math.random() < 0.5)
					{turnHead(-parameters.castSpeed*timestep); state = LarvaState.CAST_RIGHT;}
			}
			
			break;
		
		case CAST_RIGHT:
			
			moveSuccess = turnHead(-parameters.castSpeed*timestep);
			
			if (getRelativeHeadAngle() < -parameters.castAngle || !moveSuccess)
			{
				state = LarvaState.CAST_LEFT;
			}
			
			if (getRelativeHeadAngle() < -0.65 && Math.random() < getHeadCastStopProbability())
			{
				state = LarvaState.FORWARD;
				lastHeadCastTime = sim.getTime();
			}
			
			// Sometimes switch back to other cast direction
			if (previousAngle > 0 && getRelativeHeadAngle() < 0)
			{
				if (Math.random() < 0.5)
					{turnHead(parameters.castSpeed*timestep); state = LarvaState.CAST_LEFT;}
			}
			
			break;
			
		}
		
		// Update perception
		addPerception(getOdourValueHead());		
		previousOdour = getOdourValueHead();
		
		previousAngle = getRelativeHeadAngle();
		
	}

	
	public void setMinForwardInterval(double interval)
	{
		minForwardInterval = interval;
	}
	

}
