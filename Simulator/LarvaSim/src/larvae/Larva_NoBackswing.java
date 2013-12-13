package larvae;
import simulation.Point;
import simulation.Simulation;




public class Larva_NoBackswing extends KernelLarva {

	private double lastHeadCastTime;
	
	
	public Larva_NoBackswing(Simulation sim, KernelLarvaParameters params, Point startPos, double dir)
	{
		super(sim, params, startPos, dir);
		lastHeadCastTime = 0;
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

			break;
		
		case CAST_LEFT:
			moveSuccess = turnHead(parameters.castSpeed*timestep);
			if (getRelativeHeadAngle() > parameters.castAngle || !moveSuccess)
			{
				state = LarvaState.CAST_RIGHT;
			}
			if (getRelativeHeadAngle() > Math.PI/4.0 && Math.random() < getHeadCastStopProbability())
			{
				state = LarvaState.FORWARD;
				lastHeadCastTime = sim.getTime();
			}
			
			break;
		
		case CAST_RIGHT:
			moveSuccess = turnHead(-parameters.castSpeed*timestep);
			if (getRelativeHeadAngle() < -parameters.castAngle || !moveSuccess)
			{
				state = LarvaState.CAST_LEFT;
			}
			if (getRelativeHeadAngle() < -Math.PI/4.0 && Math.random() < getHeadCastStopProbability())
			{
				state = LarvaState.FORWARD;
				lastHeadCastTime = sim.getTime();
			}
			
			break;
			
		}
		
		// Update perception
		addPerception(getOdourValueHead());		
		previousOdour = getOdourValueHead();
		
		
	}



}
