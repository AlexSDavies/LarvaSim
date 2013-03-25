
/*
 * This class is a essentially a wrapper to setup and launch the simulation
 * 
 * If you want to change parameters, here is the place to do it
 * 
 */
public class SimRunner {

	public static void main(String[] args){
		
		AlgoLarvaParameters parameters = new AlgoLarvaParameters();
		
		// Set default parameters 

		parameters.castAngle = 2*Math.PI/3;
		parameters.castSpeed = 1;
		
		parameters.turnKernelStartVal = 4;
		parameters.turnKernelEndVal = -4;
		parameters.turnKernelDuration = 10;
		
		parameters.turnProbBase = 0.01;
		parameters.turnProbMult = 1;
		
		parameters.castKernelStartVal = 0;
		parameters.castKernelEndVal = 3;
		parameters.castKernelDuration = 2;
		
		parameters.castProbBase = 0.0;
		parameters.castProbMult = 1;
		
		// Set runtime in seconds
		double runTime = 6000;
		
		// Set 'speedup' factor
		// 1 = real-time
		// 1000 is the max, which I use when I'm not watching it!
		double speedup = 1000;

		// Work in progress - GUI parameter setting
		// ParameterPicker.getParameters();
		
		// String to use for simulation output
		// (Files get saved to the 'Data' folder)
		String saveName = "wall";
		
				
		// Create and run the simulation
		new Simulation(parameters, runTime, speedup, saveName);
	
		
		// If you want to run multiple simulations with different parameters,
		// you can do something like this:
//		for(int i = -2; i <= 4; i++){
//		
//			double odourIntensity = Math.pow(10, i);
//			String uniqueName = "odourIntensity" + Double.toString(odourIntensity);
//			
//			new Simulation(parameters,runTime,speedup,uniqueName);
//		
//		}

		System.exit(0);
		
	}
	
}
