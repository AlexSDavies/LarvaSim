package simulation;
import java.awt.Color;

import odours.*;
import larvae.*;



/*
 * This class is a essentially a wrapper to setup and launch the simulation
 * 
 * If you want to change parameters, here is the place to do it
 * 
 */
public class SimRunner {

	public static void main(String[] args){

		// This is a standard way of setting up and running a simulation
		// If you want to run multiple simulations, can enclose the whole thing in a loop
		// (making sure to change the saveName on each iteration)

		int[] concs = new int[]{5,50,500,5000};

		for(int c = 0; c < concs.length; c++)
		{

			for (int rep = 1; rep <= 50; rep++)
			{

				int conc = concs[c];

				System.out.println("Trial: " + rep);

				// String to use for simulation output
				// (Files get saved to the 'Data' folder)
				String saveName = "FullStatLarva_1_" + Integer.toString(conc) + "_" + Integer.toString(rep);


				// Create default parameters for larva
				FullStatLarvaParameters parameters = new FullStatLarvaParameters(16,36);
				parameters.setTurnInitiationFromFile("D:/Uni/LarvaSim/Input Data/bearing_and_turning/Naive 1_"+ Integer.toString(conc) + "_TurnBearings.csv");
				parameters.setTurnAnglesFromFile("D:/Uni/LarvaSim/Input Data/bearing_and_turning/Naive 1_"+ Integer.toString(conc) + "_ByBearing.csv");


				// parameters.castDirProb = new double[] {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5};
				// parameters.bearingTurnProbs = new double[] {0.125,0.125,0.125,0.125,0.125,0.125,0.125,0.125};

				// Create the simulation
				Simulation sim = new Simulation(parameters, saveName);

				// sim.setBoundry(32.25,50.65);

				sim.setShowSimulation(false);

				// Add desired objects to simulator
				// SingleOdourSource od = new SingleOdourSource(new Point(0,0), 15,7,4,12);

				// DataOdour od = new DataOdour("D:/Uni/LarvaSim/Input Data/BarcelonaOdourNorm.csv");

				SingleOdourSource od = new SingleOdourSource(new Point(0,35), 10,10,10,10);

				sim.addOdour(od);

				sim.addWall(new CircleWall(new Point(0,0), 50));

				Point randPoint = new Point((Math.random()-0.5)*30*2,(Math.random()-0.5)*45*2);
				Point zeroPoint = new Point(0,0);
				Point midPoint = new Point((Math.random()-0.5)*2,(Math.random()-0.5)*2);

				Point startPoint = midPoint;

				double startDir = Math.random()*2*Math.PI;

				startPoint = new Point((Math.random()-0.5)*1,(Math.random()-0.5)*1);
				startDir = Math.random()*2*Math.PI;

				FullStatLarva l = new FullStatLarva(sim, parameters, startPoint, startDir);

				sim.addLarva(l);

				// Set runtime and speedup
				double runtime = 600; // seconds
				double speedup = 50; // 1 = realtime, 1000 = max

				// Run simulation
				sim.runSimulation(runtime,speedup);

			}

		}


	}

}
