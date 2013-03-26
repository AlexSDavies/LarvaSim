
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
		castAngle = 2*Math.PI/3;
		castSpeed = 1;
		
		turnKernelStartVal = 4;
		turnKernelEndVal = -4;
		turnKernelDuration = 10;
		
		turnProbBase = 0.01;
		turnProbMult = 1;
		
		castKernelStartVal = 0;
		castKernelEndVal = 3;
		castKernelDuration = 2;
		
		castProbBase = 0.0;
		castProbMult = 1;
		
		
		
	}
	

	
}
