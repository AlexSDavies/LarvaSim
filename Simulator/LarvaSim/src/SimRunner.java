

/*
 * This class is a essentially a wrapper to setup and launch the simulation
 * 
 * If you want to change parameters, here is the place to do it
 * 
 */
public class SimRunner {

	public static void main(String[] args){
		
		DemoSimulations demo = new DemoSimulations();
		
		// demo.odourtaxis.runSimulation(6000, 1000);
		
		demo.pref.runSimulation(6000, 10);
		
		// demo.multiOdourMany.runSimulation(6000, 10);
		
		
		
		
//		double runTime = 30000;
//		double speedup = 100;
//				
//		String saveName = "demo";
//		
//		Parameters params = new AlgoLarvaParameters();
//		
//		// Create the simulation
//		Simulation sim = new Simulation(params, saveName);
//
//		// Add desired objects to simulator
//		SingleOdourSource od = new SingleOdourSource(new Point(0,0), 10,7,4,12);
//		sim.addOdour(od);
//
//		Larva_NoBackswing l = new Larva_NoBackswing(sim, new Point(35,15),Math.PI/2);
//		
//		sim.addLarva(l);
//		
//		// Run simulation
//		sim.runSimulation(runTime,speedup);
//		
		
		
//		DemoSimulations demo = new DemoSimulations();
//		demo.pref.runSimulation(600, 10);
//
//		
//		
//		// Create new set of (default) parameters
//		AlgoLarvaParameters parameters = new AlgoLarvaParameters();
//		
//		// Set runtime in seconds
//		double runTime = 900;
//		
//		// Set 'speedup' factor
//		// (1 is real-time, 1000 is the max)
//		double speedup = 1000;
//		
//		double[] sds = {10, 15, 20, 25};
//		
//		for(int i = 1; i <= 4; i++)
//		{
//			for(int larvaNum = 1; larvaNum <= 10; larvaNum++)
//			{
//				
//			
//			String saveName = "multiSourceSD_" + Integer.toString(i) + "_Larva_" + Integer.toString(larvaNum);
//			
//			// Create the simulation
//			Simulation sim = new Simulation(parameters, saveName);
//	
//			// Add desired objects to simulator
//			double sd = sds[i-1];
//			GuassianOdourSource od1 = new GuassianOdourSource(new Point(0,34),sd,sd);
//			GuassianOdourSource od2 = new GuassianOdourSource(new Point(-30,-16),sd,sd);
//			GuassianOdourSource od3 = new GuassianOdourSource(new Point(30,-16),sd,sd);
//			sim.addOdour(od1);
//			sim.addOdour(od2);
//			sim.addOdour(od3);
//			
//			// sim.addWall(new Wall(new Point(0,0),50));
//	
//			AlgoLarva l = new AlgoLarva(sim, new Point(20,20),Math.PI);
//			
//			sim.addLarva(l);
//			
//			// Run simulation
//			sim.runSimulation(runTime,speedup);
//		
//			}
//		}
		
//		
//		for(int i = 1; i <=2; i++)
//		{
//			for(int larvaNum = 1; larvaNum <= 10; larvaNum++)
//			{
//					
//				
//				String saveName = "multiSourceDiff_" + Integer.toString(i) + "_Larva_" + Integer.toString(larvaNum);
//				
//				// Create the simulation
//				Simulation sim = new Simulation(parameters, saveName);
//		
//				// Add desired objects to simulator
//				double sd = 15;
//				GuassianOdourSource od1 = new GuassianOdourSource(new Point(0,17.3),sd,sd);
//				GuassianOdourSource od2 = new GuassianOdourSource(new Point(-20,-17.3),sd,sd);
//				GuassianOdourSource od3 = new GuassianOdourSource(new Point(20,-17.3),sd,sd);
//				
//				if(i==2){
//				od2.setIntensity(0.56);
//				od3.setIntensity(0.32);
//				}
//				
//				sim.addOdour(od1);
//				sim.addOdour(od2);
//				sim.addOdour(od3);
//				
//				// sim.addWall(new Wall(new Point(0,0),50));
//		
//				AlgoLarva l = new AlgoLarva(sim, new Point(0,0),2*Math.PI*Math.random()-Math.PI);
//				
//				sim.addLarva(l);
//				
//				// Run simulation
//				sim.runSimulation(runTime,speedup);
//			
//			}
//		}
//		
		
		
//		int numLarvae = 10;
//		
//		for(int i = 1; i <= 10; i++)
//		{
//			for(int l = 1; l <= numLarvae; l++)
//			{
//
//				parameters.castAngle = i*Math.PI/10;
//				
//				// String to use for simulation output
//				// (Files get saved to the 'Data' folder)
//				String saveName = "CastAngle_" + Integer.toString(i) + "_Larva_" + Integer.toString(l);
//
//				// Create the simulation
//				Simulation sim = new Simulation(parameters, saveName);
//
//				// Add desired objects to simulator
//				SingleOdourSource od = new SingleOdourSource(new Point(-150,0), 100,70,40,120);
//				sim.addOdour(od);
//
//				sim.addWall(new Wall(new Point(0,0),200));
//
//				sim.addLarva(new Point(0,300*Math.random()-150),2*Math.PI*Math.random()-Math.PI);
//
//				// Run simulation
//				sim.runSimulation(runTime,speedup);
//
//			}
//		}
		
		System.exit(0);
		
	}
	
}
