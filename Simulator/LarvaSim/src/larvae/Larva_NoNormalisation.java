package larvae;

import simulation.Point;
import simulation.Simulation;

public class Larva_NoNormalisation extends Larva_MinForward {

		
		
		public Larva_NoNormalisation(Simulation sim, Parameters params, Point startPos, double dir)
		{
			super(sim, params, startPos, dir);
		}

		protected void addPerception(double odourValueHead)
		{
						
			// Calculate perception: 1/C * dC/dt
			double C = getOdourValueHead();
			double deltaC = C - previousOdour;
			
			double perception = deltaC;
			
			// Add perception to history
			// Perception history is kept in a looping array, with perceptionPointer keeping the current value
			perceptionPointer = (perceptionPointer+1) % perceptionHistoryLength;
			perceptionHistory[perceptionPointer] = perception;
		}



}
