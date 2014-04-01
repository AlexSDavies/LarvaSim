package larvae;

public class StatLarvaParameters extends Parameters {

	public double
		castAngle,
		turnRate;
	
	public double[]
		turnBearingDivs,
		bearingTurnProbs,
		castBearingDivs,
		castDirProb;
	
	
	public StatLarvaParameters()
	{
		
		// Set default parameter values
		castAngle = 2*Math.PI/3;
		castSpeed = 4*Math.PI/3;;
		
		turnBearingDivs = new double[] {0,  0.3927, 0.7854, 1.1781, 1.5708, 1.9635, 2.3562, 2.7489};
		bearingTurnProbs = new double[] {0.0100, 0.0200, 0.0500, 0.0900, 0.1600, 0.1900, 0.2300, 0.2500};
		turnRate = 0.148; // Average turns per second
		
		castBearingDivs = new double[] { -3.1416, -2.6180, -2.0944, -1.5708, -1.0472, -0.5236, 0, 0.5236, 1.0472, 1.5708, 2.0944, 2.6180};
		castDirProb = new double[] {0.38, 0.23, 0.14, 0.14, 0.11, 0.53, 0.56, 0.85, 0.95, 0.86, 0.70, 0.58};
		
	}

}
