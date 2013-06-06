


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
		castAngle = 2*Math.PI/3;
		castSpeed = 2*Math.PI/3;;
		
		turnKernelStartVal = 4;
		turnKernelEndVal = -4;
		turnKernelDuration = 10;
		
		turnProbBase = 3.0/60.0; // Matching Berni et al (2012) data for turns w/ no odour 
		turnProbMult = 1;
		
		castKernelStartVal = 0;
		castKernelEndVal = 6;
		castKernelDuration = 1;
		
		castProbBase = 0.1;
		castProbMult = 1;
		
		
	}
	

	
}
