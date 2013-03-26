
/*
 * This class is a essentially a wrapper to setup and launch the simulation
 * 
 * If you want to change parameters, here is the place to do it
 * 
 */
public class SimRunner {

	public static void main(String[] args){
		
		// Create new set of (default) parameters
		AlgoLarvaParameters parameters = new AlgoLarvaParameters();
				
		// Set runtime in seconds
		double runTime = 6000;
		
		// Set 'speedup' factor
		// (1 is real-time, 1000 is the max)
		double speedup = 10;
		
		// String to use for simulation output
		// (Files get saved to the 'Data' folder)
		String saveName = "PITest";
				
		// Create the simulation
		Simulation sim = new Simulation(parameters, saveName);
	
		// Add desired objects to simulator
		sim.addOdour(new SingleOdourSource(new Point(-100,0), 100, 70, 40, 120));
		sim.addWall(new Wall(new Point(0,0),200));
		for (int i = 1; i<10; i++)
		{
			sim.addLarva(new Point(100*Math.random(),100*Math.random()), 0);
		}
		
		
		// Run simulation
		sim.runSimulation(runTime,speedup);
		
		

		System.exit(0);
		
	}
	
}
