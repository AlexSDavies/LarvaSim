
public class SimRunner {

	public static void main(String[] args){
		
		AlgoLarvaParameters parameters = new AlgoLarvaParameters();
		
		// Set default parameters 
		parameters.castAngle = Math.PI/2;
		parameters.castSpeed = 1;
		
		parameters.turnKernalStartVal = 0.02;
		parameters.turnKernalEndVal = -0.02;
		
		parameters.castKernalStartVal = 0;
		parameters.castKernalEndVal = 0.1;
		parameters.castKernalStartPos = 0.75;
		
		parameters.turnProbBase = 0.1;
		parameters.turnProbMult = 450;
		
		parameters.castProbBase = 0.1;
		parameters.castProbMult = 50;
		
		
		ParameterPicker.getParameters();
		
		
//		double runTime = 6000;
//		
//		
//		for(int i = 1; i <= 6; i++){
//		
//			parameters.castAngle = i*Math.PI/6;
//			
//			String uniqueName = "castAngle_lowCastMult_" + Integer.toString(i);
//			
//			new Simulation(parameters,runTime,uniqueName);
//		
//		}

		// System.exit(0);
		
	}
	
}
