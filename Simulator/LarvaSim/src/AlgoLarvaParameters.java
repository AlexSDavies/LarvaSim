
public class AlgoLarvaParameters extends Parameters {

	public double
		turnKernelStartVal,
		turnKernelEndVal,
		turnKernelDuration,
		castKernelStartVal,
		castKernelEndVal,
		castKernelDuration,
		turnProbMult,
		turnProbBase,
		castProbMult,
		castProbBase,
		castSpeed,
		castAngle;

	public AlgoLarvaParameters()
	{
		// Set default parameter values
		// TODO: Change to match SimRunner
		castAngle = Math.PI/2;
		castSpeed = 1;
		
		turnKernelStartVal = 0.02;
		turnKernelEndVal = -0.02;
		turnKernelDuration = 10;
		
		castKernelStartVal = 0;
		castKernelEndVal = 0.1;
		castKernelDuration = 2;
		
		turnProbBase = 0.1;
		turnProbMult = 450;
		
		castProbBase = 0.1;
		castProbMult = 50;
		
	}
	

	
}
