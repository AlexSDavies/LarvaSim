package larvae;




public class KernelLarvaParameters extends Parameters {

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
		castAngle;

	public KernelLarvaParameters()
	{
		
		// Set default parameter values
		castAngle = 120.0/180.0*Math.PI;
		castSpeed = 2*castAngle;
		
		turnKernelStartVal = 1;
		turnKernelEndVal = -1;
		turnKernelDuration = 20;
		
		turnProbBase = 0.148;
		turnProbMult = 1;
		
		castKernelStartVal = 0;
		castKernelEndVal = 1.5;
		castKernelDuration = 0.5;
		
		castProbBase = 2;
		castProbMult = 1;
		
		
	}
	

	
}
