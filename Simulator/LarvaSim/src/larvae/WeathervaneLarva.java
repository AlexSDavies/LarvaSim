package larvae;


import simulation.Point;
import simulation.Simulation;

public class WeathervaneLarva extends Larva_MinForward {

	private enum wvState{LEFT,RIGHT,PAUSE;}
	private wvState wvDir, wvNextDir;
	private double[] wvKernel;
	private double[] avKernel;
	
	public WeathervaneLarva(Simulation sim, Parameters params, Point startPos, double dir)
	{
		super(sim, params, startPos, dir);
		
		this.wvDir = wvState.LEFT;
		
		WeathervaneParameters p = (WeathervaneParameters) params;
		
		wvKernel = initialiseKernel(p.wvKernelStartVal, p.wvKernelEndVal, p.wvKernelDuration);
		avKernel = initialiseKernel(p.wvKernelStartVal/10, p.wvKernelEndVal/10, p.wvKernelDuration*10);
		
	}

	
	@SuppressWarnings("incomplete-switch")
	public void update(){
		
		boolean moveSuccess;
		
	
		// Do movement	
		switch(state){
		
		case FORWARD:
			
			if(sim.getTime() - lastHeadCastTime > 2)
			{
				weathervane();
			}
			
			
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


	private void weathervane()
	{
		
		double wvParam = convolveWithPerception(wvKernel);
		double avParam = convolveWithPerception(avKernel);
		double turnParam = convolveWithPerception(turnStimulusKernel);
		
		double p = (wvParam-avParam)*30*timestep + 0.2;
		
		// System.out.printf("%.2f \n%.2f \n%.2f \n\n",wvParam,avParam,wvParam-avParam);
		// System.out.printf("%.2f \n",p);
		
		if (Math.random() < p && wvDir != wvState.PAUSE)
		{
			wvNextDir = wvDir;
			wvDir = wvState.PAUSE;
		}
		
		
		if(wvDir == wvState.LEFT)
		{
			//System.out.printf("Left: %.2f \n",p);
			turnHead(((WeathervaneParameters) parameters).wvSpeed*timestep);	
			if (getRelativeHeadAngle() > ((WeathervaneParameters) parameters).wvAngle)
			{			
				wvDir = wvState.RIGHT;
			}
		}
		else if(wvDir == wvState.RIGHT)
		{
			//System.out.printf("Right: %.2f \n",p);
			turnHead(-((WeathervaneParameters) parameters).wvSpeed*timestep);
			if (getRelativeHeadAngle() < -((WeathervaneParameters) parameters).wvAngle)
			{
				wvDir = wvState.LEFT;
			}
		}
		else if(wvDir == wvState.PAUSE)
		{
			//System.out.println("Pause");
			if(Math.random() > 0.9)
			{
				wvDir = wvNextDir;
				//System.out.println("UnPause");
			}
		}
		
		
		
	}
	
	
	
	
}
