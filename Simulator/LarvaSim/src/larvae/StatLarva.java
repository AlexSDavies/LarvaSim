package larvae;

import simulation.Point;
import simulation.Simulation;

public class StatLarva extends Larva {

	public enum LarvaState {FORWARD,CAST_LEFT,CAST_RIGHT;}

	private LarvaState state;
	
	private StatLarvaParameters parameters;
	
	private double currentMaxCastAngle;
	
	public StatLarva(Simulation sim, Parameters params, Point startPos, double dir)
	{
		super(sim, params, startPos, dir);

		state = LarvaState.FORWARD;
		
		this.parameters = (StatLarvaParameters) params;
		
		currentMaxCastAngle = 0;
		
	}

	@Override
	public void update()
	{

		boolean moveSuccess;
	
		double bearing = getBearing();
		
		// Do movement	
		switch(state){
		
		case FORWARD:
			moveSuccess = moveForward(forwardSpeed*timestep);
			if (Math.random() < getTurnProbability(bearing) || !moveSuccess)
			{
				
				currentMaxCastAngle = Math.random() * (2*Math.PI/4) + Math.PI/4;
				
				if(Math.random() > getLeftTurnProb(bearing))
					{state = LarvaState.CAST_LEFT;}
				else
					{state = LarvaState.CAST_RIGHT;}
			}
			break;
		
		case CAST_LEFT:
			moveSuccess = turnHead(parameters.castSpeed*timestep);
			if(this.getRelativeHeadAngle() > currentMaxCastAngle || !moveSuccess)
				{state = LarvaState.FORWARD;}
			
			break;
		
		case CAST_RIGHT:
			moveSuccess = turnHead(-parameters.castSpeed*timestep);
			if(this.getRelativeHeadAngle() < -currentMaxCastAngle || !moveSuccess)
				{state = LarvaState.FORWARD;}
			
			break;
			
		}
		
		// TODO: add this so we can collect data
		// addPerception(getOdourValueHead());		

	}

	
	private double getLeftTurnProb(double bearing)
	{
		double bearingProb = 0;
		int i = 0;
		while (i < parameters.castBearingDivs.length && parameters.castBearingDivs[i] <= bearing)
		{
			bearingProb = parameters.castDirProb[i];
			i++;
		}
		
		return bearingProb;
	}

	private double getTurnProbability(double bearing){
		
		double absBearing = Math.abs(bearing);
		
		double bearingProb = 0;
		
		
		int i = 0;
		while (i < parameters.turnBearingDivs.length && parameters.turnBearingDivs[i] <= absBearing)
		{
			bearingProb = parameters.bearingTurnProbs[i];
			i++;
		}
		
		// Expected #turns/s
		//	= turnRate * probAtBearing * numberOfBearings
		double expTurns = parameters.turnRate * bearingProb * parameters.turnBearingDivs.length;
		
		// Probability turn this timestep = expected turns/s * timestep
		double pTurn = expTurns * timestep;
		
		return pTurn;
		
	}

}
