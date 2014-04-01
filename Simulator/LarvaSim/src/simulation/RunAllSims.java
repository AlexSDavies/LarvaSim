package simulation;

import java.lang.reflect.Constructor;

import odours.DataOdour;
import larvae.*;

public class RunAllSims {

	public static void main(String[] args)
	{
		
		String name;
		Parameters params;
		
//		// Basic larva
//		name = "basicModel";
//		params = new MinForwardParameters();
//		runStandardSim("larvae.Larva_MinForward",params,name);
//		
//		// Low cast base
//		name = "lowCastBase";
//		params = new MinForwardParameters();
//		((KernelLarvaParameters) params).castProbBase *= 0.1;
//		runStandardSim("larvae.Larva_MinForward",params,name);
//		
//		// Larva with different parameters
//		// High cast
//		name = "highCastKernel";
//		params = new MinForwardParameters();
//		((KernelLarvaParameters) params).castKernelEndVal *= 10;
//		runStandardSim("larvae.Larva_MinForward",params,name);
//		
//		name = "midHighCastKernel";
//		params = new MinForwardParameters();
//		((KernelLarvaParameters) params).castKernelEndVal *= 3;
//		runStandardSim("larvae.Larva_MinForward",params,name);
//		
//		// Low cast
//		name = "lowCastKernel";
//		params = new MinForwardParameters();
//		((KernelLarvaParameters) params).castKernelEndVal *= 0.1;
//		runStandardSim("larvae.Larva_MinForward",params,name);
//		
//		name = "midLowCastKernel";
//		params = new MinForwardParameters();
//		((KernelLarvaParameters) params).castKernelEndVal *= 0.33;
//		runStandardSim("larvae.Larva_MinForward",params,name);
//		
//		
//		// High turn
//		name = "highTurnKernel";
//		params = new MinForwardParameters();
//		((KernelLarvaParameters) params).turnKernelStartVal *= 10;
//		((KernelLarvaParameters) params).turnKernelEndVal *= 10;
//		runStandardSim("larvae.Larva_MinForward",params,name);
//		
//		name = "midHighTurnKernel";
//		params = new MinForwardParameters();
//		((KernelLarvaParameters) params).turnKernelStartVal *= 3;
//		((KernelLarvaParameters) params).turnKernelEndVal *= 3;
//		runStandardSim("larvae.Larva_MinForward",params,name);
//		
//		// Low turn
//		name = "lowTurnKernel";
//		params = new MinForwardParameters();
//		((KernelLarvaParameters) params).turnKernelStartVal *= 0.1;
//		((KernelLarvaParameters) params).turnKernelEndVal *= 0.1;
//		runStandardSim("larvae.Larva_MinForward",params,name);
//		
//		
//		name = "midLowTurnKernel";
//		params = new MinForwardParameters();
//		((KernelLarvaParameters) params).turnKernelStartVal *= 0.33;
//		((KernelLarvaParameters) params).turnKernelEndVal *= 0.33;
//		runStandardSim("larvae.Larva_MinForward",params,name);
//		
//		
//		// No head cast bias
//		name = "noCastBias";
//		params = new MinForwardParameters();
//		((KernelLarvaParameters) params).castKernelEndVal = 0;
//		((KernelLarvaParameters) params).castProbBase = 2.3;
//		runStandardSim("larvae.Larva_MinForward",params,name);
//		
//		name = "noCastBiasForward5";
//		params = new MinForwardParameters();
//		((MinForwardParameters) params).minForwardInterval = 5;
//		((KernelLarvaParameters) params).castKernelEndVal = 0;
//		runStandardSim("larvae.Larva_MinForward",params,name);
//		
//		
//		
//		// No turn bias
//		name = "noTurnBias";
//		params = new MinForwardParameters();
//		((KernelLarvaParameters) params).turnProbBase = 0.2;
//		((KernelLarvaParameters) params).turnKernelStartVal = 0;
//		((KernelLarvaParameters) params).turnKernelEndVal = 0;
//		runStandardSim("larvae.Larva_MinForward",params,name);
//		
//		
//		// No normalisation
//		name = "noNormalisation";
//		params = new MinForwardParameters();
//		runStandardSim("larvae.Larva_NoNormalisation",params,name);

//		
//		// Stat larva
//		name = "StatLarva";
//		params = new StatLarvaParameters();
//		runStandardSim("larvae.StatLarva", params, name);
//		
//		// Stat no turn bias
//		name = "StatLarvaNoTurnBias";
//		params = new StatLarvaParameters();
//		((StatLarvaParameters) params).bearingTurnProbs = new double[] {0.125,0.125,0.125,0.125,0.125,0.125,0.125,0.125};
//		runStandardSim("larvae.StatLarva", params, name);
//		
//		
//		// Stat no cast bias
//		name = "StatLarvaNoCastBias";
//		params = new StatLarvaParameters();
//		((StatLarvaParameters) params).castDirProb = new double[] {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5};
//		runStandardSim("larvae.StatLarva", params, name);
//		
//		
//		// Stat no bias
//		name = "StatLarvaNoBias";
//		params = new StatLarvaParameters();
//		((StatLarvaParameters) params).bearingTurnProbs = new double[] {0.125,0.125,0.125,0.125,0.125,0.125,0.125,0.125};
//		((StatLarvaParameters) params).castDirProb = new double[] {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5};
//		runStandardSim("larvae.StatLarva", params, name);
//		
		
		
	}

	
	private static void runStandardSim(String className, Parameters params, String name)
	{
		
		for (int rep = 1; rep <= 50; rep++)
		{
		
			System.out.println(name + ": " + rep);
			
			// String to use for simulation output
			// (Files get saved to the 'Data' folder)
			String saveName = name + Integer.toString(rep);
			
			// Create the simulation
			Simulation sim = new Simulation(params, saveName);
	
			sim.setShowSimulation(true);
			
			DataOdour od = new DataOdour("D:/Uni/LarvaSim/Input Data/BarcelonaOdourNorm.csv");
			sim.addOdour(od);
			
			sim.setBoundry(32.25,50.65);
			
			// TODO: choose start point
			Point startPoint = new Point((Math.random()-0.5)*30*2,(Math.random()-0.5)*45*2);;
			double startDir = Math.random()*2*Math.PI;
			
			
			Larva l = null;
			try {
				Class larvaClass = Class.forName(className);
				Class[] types = {Simulation.class, Parameters.class, Point.class, Double.TYPE};
				Constructor larvaConstructor;
				larvaConstructor = larvaClass.getConstructor(types);
				l = (Larva) larvaConstructor.newInstance(sim,params,startPoint,startDir); 
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			sim.addLarva(l);
			
			// Set runtime and speedup
			double runtime = 600; // seconds
			double speedup = 1; // 1 = realtime, 1000 = max
			
			// Run simulation
			sim.runSimulation(runtime,speedup);

		}
		
		
	}
	
	
}
