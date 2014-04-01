package larvae;

import larvae.StatLarva.LarvaState;
import simulation.Point;
import simulation.Simulation;

public class FullStatLarva extends Larva {


	public enum LarvaState {FORWARD,CAST_LEFT,CAST_RIGHT;}

	private LarvaState state;
	
	protected FullStatLarvaParameters parameters;
	
	private double currentMaxCastAngle;
	
	
	public FullStatLarva(Simulation sim, Parameters params, Point startPos, double dir)
	{
		super(sim, params, startPos, dir);

		state = LarvaState.FORWARD;
		
		this.parameters = (FullStatLarvaParameters) params;
		
		currentMaxCastAngle = 0;
		
	}

	@Override
	public void update()
	{

		boolean moveSuccess;
	
		double bearing = getBearing();
		
		double prevAngle;

		// Do movement	
		switch(state){
		
		case FORWARD:
			moveSuccess = moveForward(forwardSpeed*timestep);
			if (Math.random() < getTurnProbability(bearing) && moveSuccess)
			{
				
				currentMaxCastAngle = getCastAngle(bearing);
				
				if(currentMaxCastAngle < 0)
					{state = LarvaState.CAST_RIGHT;}
				else
					{state = LarvaState.CAST_LEFT;}
			}
			break;
		
		case CAST_LEFT:
			prevAngle = getRelativeHeadAngle();
			moveSuccess = turnHead(parameters.castSpeed*timestep);
			if(!moveSuccess)
			{
				state = LarvaState.CAST_RIGHT;
				currentMaxCastAngle = -Math.PI/2;
			}
			
			
			if(this.getRelativeHeadAngle() > currentMaxCastAngle || (prevAngle > 0 && getRelativeHeadAngle() < 0))
				{state = LarvaState.FORWARD;}
			
			break;
		
		case CAST_RIGHT:
			prevAngle = getRelativeHeadAngle();
			moveSuccess = turnHead(-parameters.castSpeed*timestep);
			
			if(!moveSuccess)
			{
				state = LarvaState.CAST_LEFT;
				currentMaxCastAngle = Math.PI/2;
			}
			
			
			if(this.getRelativeHeadAngle() < currentMaxCastAngle || (prevAngle < 0 && getRelativeHeadAngle() > 0))
				{state = LarvaState.FORWARD;}
			
			break;
			
		}
		
		// TODO: add this so we can collect data
		// addPerception(getOdourValueHead());		

	}

	
	private double getCastAngle(double bearing)
	{
		
		double minAngle = -Math.PI;
		double maxAngle = Math.PI;
		
		int div = (int) Math.floor((bearing - minAngle) / ((maxAngle - minAngle) / parameters.numPreTurnBearings));
		if (div == parameters.numPreTurnBearings){div--;} // catch edge case for exactly 180 degree bearing
		
		double[] angleProbs = parameters.turnAngles[div]; 
		
		double p = Math.random();
		
		double cumProb = angleProbs[0];
		int turnNum = 0;
		
		while(p > cumProb)
		{
			turnNum++;
			cumProb += angleProbs[turnNum];
		}
		
		double turnDiv = (maxAngle - minAngle)/parameters.numTurnAngles;
		double angle = minAngle + (turnNum+0.5)*turnDiv;
		
		// System.out.println(angle/Math.PI*180);
		
		return angle;
		
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
