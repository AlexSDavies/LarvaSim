
public class AlgoLarvaParameters extends Parameters {

	public double
		turnKernalStartVal,
		turnKernalEndVal,
		castKernalStartVal,
		castKernalEndVal,
		castKernalStartPos,
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
		
		turnKernalStartVal = 0.02;
		turnKernalEndVal = -0.02;
		
		castKernalStartVal = 0;
		castKernalEndVal = 0.1;
		castKernalStartPos = 0.75;
		
		turnProbBase = 0.1;
		turnProbMult = 450;
		
		castProbBase = 0.1;
		castProbMult = 50;
		
	}
	

	
}
