package simulation;
import java.awt.Color;
import odours.*;
import larvae.*;

public class RunIntensities {



	public static void main(String[] args){

		// This is a standard way of setting up and running a simulation
		// If you want to run multiple simulations, can enclose the whole thing in a loop
		// (making sure to change the saveName on each iteration)

		double[] intensities = new double[]{0.0, 0.1, 0.5, 0.6, 0.7, 0.8, 0.9, 1, 2, 5, 10}; 

		for(int d = 7; d < intensities.length; d++)
		{

			for (int rep = 1; rep <= 50; rep++)
			{

				System.out.println("Trial: " + rep);

				// String to use for simulation output
				// (Files get saved to the 'Data' folder)
				String saveName = "PI_Intensity_" + Double.toString(intensities[d]) + "_" + Integer.toString(rep);


				// Create default parameters for larva
				KernelLarvaParameters parameters = new MinForwardParameters();

				// Create the simulation
				Simulation sim = new Simulation(parameters, saveName);

				sim.setShowSimulation(true);

				// Add desired objects to simulator
				// SingleOdourSource od = new SingleOdourSource(new Point(0,0), 15,7,4,12);

				// DataOdour od = new DataOdour("D:/Uni/LarvaSim/Odour Data/BarcelonaOdourNorm.csv");

				double var = 25; 
				SingleOdourSource od = new SingleOdourSource(new Point(-40,0), var,var,var,var);
				od.setIntensity(intensities[d]);

				// SingleOdourSource od = new SingleOdourSource(new Point(0,0), 15,7,4,12);

				sim.addOdour(od);


				sim.addWall(new CircleWall(new Point(0,0),50));


				Point randPoint = new Point((Math.random()-0.5)*30*2,(Math.random()-0.5)*45*2);
				Point zeroPoint = new Point(0,0);
				Point midPoint = new Point(0,(Math.random()-0.5)*100);

				Point startPoint = midPoint;

				double startDir = Math.random()*2*Math.PI;

				Larva_MinForward l = new Larva_NoNormalisation(sim, parameters, startPoint, startDir);


				sim.addLarva(l);

				// Set runtime and speedup
				double runtime = 300; // seconds
				double speedup = 50; // 1 = realtime, 1000 = max

				// Run simulation
				sim.runSimulation(runtime,speedup);

			}

		}


	}

}


