package simulation;
import java.awt.Color;
import java.util.ArrayList;

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

		// int[] concs = new int[]{5,50,500,5000};
		
		// double[] scaling = new double[]{0.3,0.2,0.1,0.0};
		
//		//for(int c = 0; c < concs.length; c++)
//		for(int s = 0; s < scaling.length; s++)
//		{
//
//			for (int rep = 1; rep <= 50; rep++)
//			{
//
//				//int conc = concs[c];
//				double scale = scaling[s]; 
//				
//				System.out.println("Trial: " + rep);
//
//				// String to use for simulation output
//				// (Files get saved to the 'Data' folder)
//				// String saveName = "FullStatLarva_1_" + Integer.toString(conc) + "_" + Integer.toString(rep);
//				String saveName = "ParameterLarva_" + Integer.toString(s)  + "_" + Integer.toString(rep);
//				
//
//				// Create default parameters for larva
//				//FullStatLarvaParameters parameters = new FullStatLarvaParameters(16,36);
//				//parameters.setTurnInitiationFromFile("D:/Uni/LarvaSim/Input Data/bearing_and_turning/Naive 1_"+ Integer.toString(conc) + "_TurnBearings.csv");
//				//parameters.setTurnAnglesFromFile("D:/Uni/LarvaSim/Input Data/bearing_and_turning/Naive 1_"+ Integer.toString(conc) + "_ByBearing.csv");
//
//				FullStatLarvaParameters parameters = new FullStatLarvaParameters(16,36);
//				parameters.setTurnInitiationFromFile("D:/Uni/LarvaSim/Input Data/bearing_and_turning/Naive 1_50_TurnBearings.csv");
//				parameters.setTurnAnglesFromFile("D:/Uni/LarvaSim/Input Data/bearing_and_turning/Naive 1_50_ByBearing.csv");
//				
//
//				// parameters.castDirProb = new double[] {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5};
//				// parameters.bearingTurnProbs = new double[] {0.125,0.125,0.125,0.125,0.125,0.125,0.125,0.125};
//
//				// Create the simulation
//				Simulation sim = new Simulation(parameters, saveName);
//				sim.getOdour().setDrawRadius(50);
//
//				// sim.setBoundry(32.25,50.65);
//
//				sim.setShowSimulation(true);
//
//				// Add desired objects to simulator
//				// SingleOdourSource od = new SingleOdourSource(new Point(0,0), 15,7,4,12);
//
//				// DataOdour od = new DataOdour("D:/Uni/LarvaSim/Input Data/BarcelonaOdourNorm.csv");
//
//				SingleOdourSource od = new SingleOdourSource(new Point(-40,0), 50,50,50,50);
//
//				sim.addOdour(od);
//
//				sim.addWall(new CircleWall(new Point(0,0), 50));
//
//				Point randPoint = new Point((Math.random()-0.5)*30*2,(Math.random()-0.5)*45*2);
//				Point zeroPoint = new Point(0,0);
//				Point midPoint = new Point((Math.random()-0.5)*2,(Math.random()-0.5)*2);
//
//				Point startPoint = midPoint;
//
//				double startDir = Math.random()*2*Math.PI;
//
//				startPoint = new Point((Math.random()-0.5)*1,(Math.random()-0.5)*1);
//				startDir = Math.random()*2*Math.PI;
//
//				// FullStatLarva l = new FullStatLarva(sim, parameters, startPoint, startDir);
//				ParameterisedLarva l = new ParameterisedLarva(sim, parameters, startPoint, startDir);
//				l.setHeadCastDirScaling(scale);
//				
//				sim.addLarva(l);
//
//				// Set runtime and speedup
//				double runtime = 600; // seconds
//				double speedup = 10; // 1 = realtime, 1000 = max
//				
//				sim.setShowSimulation(true);
//
//				// Run simulation
//				sim.runSimulation(runtime,speedup);
//
//			}
//
//		}

		
		
//		// Preference indices with different kernels
//		double[] kernelScaling = new double[]{1.0, 2.0/3.0, 1.0/3.0, 0.0, -1.0/3.0, -2.0/3.0, -1.0}; 
//		
//		for (int i = 0; i < kernelScaling.length; i++)
//		{
//			for (int j = 0; j < kernelScaling.length; j++)
//			{
//			
//			for (int rep = 1; rep <= 50; rep++)
//			{
//		
//				System.out.println("Trial: " + rep);
//
//				String saveName = "KernelPrefNorm_" + Integer.toString(i)  + "_" + Integer.toString(j)  + "_" + Integer.toString(rep);
//
//				MinForwardParameters parameters = new MinForwardParameters();
//
//				parameters.castKernelStartVal *= kernelScaling[i];
//				parameters.castKernelEndVal *= kernelScaling[i];
//				
//				parameters.turnKernelStartVal *= kernelScaling[j];
//				parameters.turnKernelEndVal *= kernelScaling[j];
//
//				// Create the simulation
//				Simulation sim = new Simulation(parameters, saveName);
//				sim.getOdour().setDrawRadius(50);
//
//				sim.setShowSimulation(true);
//
//				// Add desired objects to simulator
//				SingleOdourSource od = new SingleOdourSource(new Point(-40,0), 30,30,30,30);
//
//				sim.addOdour(od);
//
//				sim.addWall(new CircleWall(new Point(0,0), 50));
//
//				Point startPoint = new Point((Math.random()-0.5)*2,(Math.random()-0.5)*2);
//
//				double startDir = Math.random()*2*Math.PI;
//
//				startPoint = new Point((Math.random()-0.5)*1,(Math.random()-0.5)*1);
//				startDir = Math.random()*2*Math.PI;
//
//				Larva_MinForward l = new Larva_MinForward(sim, parameters, startPoint, startDir);
//				
//				sim.addLarva(l);
//
//				// Set runtime and speedup
//				double runtime = 300; // seconds
//				double speedup = 1; // 1 = realtime, 1000 = max
//				
//				sim.setShowSimulation(true);
//
//				// Run simulation
//				sim.runSimulation(runtime,speedup);
//
//			}
//			
//			}
//			
//		
//		}
//		
//		
		
		
		String[] odourDataNames = new String[]{"linShallNorm.csv","linSteepNorm.csv","expNorm.csv"};
		String[] saveNames = new String[]{"LinShallTest","LinSteepTest","ExpTest"};

		for(int f = 0; f < 3; f++)
		{
			for(int i = 1; i < 101; i++)
			{
			
				System.out.println(i);
				
				String saveName = saveNames[f] + Integer.toString(i);
		
				MinForwardParameters params = new MinForwardParameters();
				// WeathervaneParameters params = new WeathervaneParameters();
				
				Simulation sim = new Simulation(params, saveName);
				
				sim.setShowSimulation(false);
		
				DataOdour od = new DataOdour("D:/Uni/LarvaSim/Input Data/" + odourDataNames[f]);
		
				sim.addOdour(od);
		
				sim.setBoundry(50.2, 32);
				
				Point startPoint = new Point(-30,0);
				double startDir = Math.random()*2*Math.PI;
		
				Larva_MinForward l = new Larva_MinForward(sim, params, startPoint, startDir);
				// WeathervaneLarva l = new WeathervaneLarva(sim, params, startPoint, startDir);
				
				sim.addLarva(l);
				
				double runtime = 600; // seconds
				double speedup = 1; // 1 = realtime, 1000 = max
		
				sim.runSimulation(runtime,speedup);
			
			}
		}
		
		
		
//		for(int i = 1; i < 5; i++)
//		{
//
//		String saveName = "WeathervaneTest" + Integer.toString(i);
//
//		WeathervaneParameters params = new WeathervaneParameters();
//
//		Simulation sim = new Simulation(params, saveName);
//
//		sim.setShowSimulation(true);
//
//		SingleOdourSource od = new SingleOdourSource(new Point(0,0), 15,15,15,15);
//
//		sim.addOdour(od);
//
//		Point startPoint = new Point(-10,-20);
//		double startDir = 0;
//
//		WeathervaneLarva l = new WeathervaneLarva(sim, params, startPoint, startDir);
//
//		sim.addLarva(l);
//		
//		double runtime = 600; // seconds
//		double speedup = 1; // 1 = realtime, 1000 = max
//
//		sim.runSimulation(runtime,speedup);
//		
//		}
		
		
		
		
		/*
		// String to use for simulation output
		// (Files get saved to the 'Data' folder)
		String saveName = "DemoPath";


		// Create default parameters for larva
		MinForwardParameters params = new MinForwardParameters();

		// Create the simulation
		Simulation sim = new Simulation(params, saveName);

		sim.setShowSimulation(true);

		SingleOdourSource od = new SingleOdourSource(new Point(0,0), 15,15,15,15);

		sim.addOdour(od);

		Point startPoint = new Point(-15,-10);
		double startDir = 0;


		Larva_MinForward l = new Larva_MinForward(sim, params, startPoint, startDir);

		sim.addLarva(l);
		
		// Set runtime and speedup
		double runtime = 60000; // seconds
		double speedup = 1; // 1 = realtime, 1000 = max

		// Run simulation
		sim.runSimulation(runtime,speedup);
		*/
		
		//DemoSimulations demo = new DemoSimulations();
		//demo.noTurnBias.runSimulation(60000, 10);
		

	}

}
