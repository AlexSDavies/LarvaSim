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
public class RunPrefIndex {

	public static void main(String[] args){


		double[] intensities = new double[] {0.2,0.4,0.6,0.8,1.0};
		double[] variances = new double[] {10,20,30,40,50};
		
		for(int i = 0; i < intensities.length; i++)
		{
			for(int v = 0; v < variances.length; v++)
			{

				for (int rep = 1; rep <= 201; rep++)
				{

					double intensity = intensities[i];
					double variance = variances[v];

					// System.out.println("Trial: " + rep);
					System.out.println(v + i*variances.length +  "/" + intensities.length * variances.length + ": " + rep);

					// String to use for simulation output
					// (Files get saved to the 'Data' folder)
					String saveName = "Pref_BasicModel_Intensity" + Double.toString(intensity) + "_Variance" + Double.toString(variance) + "_" + Integer.toString(rep);


					// Create default parameters for larva
					MinForwardParameters parameters = new MinForwardParameters();

					// Create the simulation
					Simulation sim = new Simulation(parameters, saveName);

					sim.setShowSimulation(false);

					// Add desired objects to simulator
					SingleOdourSource od = new SingleOdourSource(new Point(-40,0), variance,variance,variance,variance);
					od.setIntensity(intensity);
										
					sim.addOdour(od);

					sim.addWall(new CircleWall(new Point(0,0), 50));

					Point startPoint = new Point(0,(Math.random()-0.5)*8);
					double startDir = Math.random()*2*Math.PI;

					Larva_MinForward l = new Larva_MinForward(sim, parameters, startPoint, startDir);

					sim.addLarva(l);

					// Set runtime and speedup
					double runtime = 300; // seconds
					double speedup = 50; // 1 = realtime, 1000 = max

					if(rep == 200)
					{
						runtime = 10;
						sim.getOdour().setDrawRadius(50);
						sim.setShowSimulation(true);
					}
					
					// Run simulation
					sim.runSimulation(runtime,speedup);

				}

			}
			

		}

	}

}
