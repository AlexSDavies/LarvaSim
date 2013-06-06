package simulation;
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
		
		// String to use for simulation output
		// (Files get saved to the 'Data' folder)
		String saveName = "DemoSave";

		// Create default parameters for larva
		AlgoLarvaParameters parameters = new AlgoLarvaParameters();
		
		// Create the simulation
		Simulation sim = new Simulation(parameters, saveName);

		// Add desired objects to simulator
		SingleOdourSource od = new SingleOdourSource(new Point(-20,0), 15,7,4,12);
		sim.addOdour(od);

		sim.addWall(new Wall(new Point(0,0),30));

		Larva l = new AlgoLarva(sim, parameters, new Point(10,10), Math.PI);
		sim.addLarva(l);

		// Set runtime and speedup
		double runtime = 600; // seconds
		double speedup = 10; // 1 = realtime, 1000 = max
		
		// Run simulation
		sim.runSimulation(runtime,speedup);

		// Exit once simulation is done
		System.exit(0);
		
	}
	
}
