
public class SimRunner {

	public static void main(String[] args){
		
		AlgoLarvaParameters parameters = new AlgoLarvaParameters();
		
		// Set default parameters 
		parameters.castAngle = 4*Math.PI/5;
		parameters.castSpeed = 1;
		
		parameters.turnKernalStartVal = 9;
		parameters.turnKernalEndVal = -9;
		parameters.turnKernalDuration = 10;
		
		parameters.turnProbBase = 0.2;
		parameters.turnProbMult = 1;
		
		parameters.castKernalStartVal = -50;
		parameters.castKernalEndVal = 50;
		parameters.castKernalDuration = 5;
		
		parameters.castProbBase = 0.0;
		parameters.castProbMult = 1;
		
		
		double runTime = 6000;
		
		new Simulation(parameters, runTime, "test");
		
//		for(int i = 1; i <= 10; i++){
//		
//			parameters.castAngle = i*Math.PI/10;
//			
//			String uniqueName = "castAngle_" + Integer.toString(i);
//			
//			new Simulation(parameters,runTime,uniqueName);
//		
//		}

		System.exit(0);
		
	}
	
}
