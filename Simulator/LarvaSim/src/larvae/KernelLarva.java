package larvae;



import simulation.Point;
import simulation.Simulation;




// TODO: Update this to remove specific reliance on AlgoLarvaParams
// DONE
// AlgoLarva specific stuff should be moved to its own class
// The top-level larva class should only need a generic parameters object
// This also means moving the perception kernel stuff elsewhere too

public abstract class KernelLarva extends Larva {


	
	protected double perceptionHistoryTime;
	protected int perceptionHistoryLength;
	
	public enum LarvaState {FORWARD,CAST_LEFT,CAST_RIGHT,CAST_BACK_LEFT,CAST_BACK_RIGHT;}
	protected LarvaState state;
	
	protected double previousOdour;

	protected int perceptionPointer;
	protected double[] perceptionHistory;
	
	protected double[] turnStimulusKernel;
	protected double[] headCastStimulusKernel;

	
	public KernelLarvaParameters parameters;
	

	// This may be used in update step to do variable head cast max ranges
	protected double headCastRange;
	
	
	// Create larva
	// Larva must be passed parent simulator to access other objects in simulation
	public KernelLarva(Simulation sim, Parameters params, Point startPos, double dir)
	{
		
		super(sim,params,startPos,dir);
		
		// Initialise various things 
		
		perceptionHistoryTime = ((KernelLarvaParameters) params).turnKernelDuration;
		
		perceptionHistoryLength = (int) (perceptionHistoryTime/timestep);
		
		state = LarvaState.FORWARD;
		
		perceptionPointer = 0;
		perceptionHistory = new double[perceptionHistoryLength];
		
		previousOdour = getOdourValueHead();
		
		this.parameters = (KernelLarvaParameters) params;
		
		initialiseAllKernels();
		
		System.out.print("");
		
	}


	
	// Currently returns a head cast angle uniformly from 0 to params.castAngle
	// TODO: Consider different distributions
	protected double sampleHeadCastRange() {
		double range = Math.random() * parameters.castAngle;
		return range;
	}

	
	
	private void initialiseAllKernels()
	{
		
		// TODO: remove after checking 
//		// Turn stimulus kernel
//		turnStimulusKernel = new double[perceptionHistoryLength];
//		int turnKernelLength = (int) (parameters.turnKernelDuration/timestep);
//		int turnKernelStartPos = perceptionHistoryLength - turnKernelLength;
//		
//		for(int i = turnKernelStartPos; i < perceptionHistoryLength; i++)
//		{
//			turnStimulusKernel[i] = parameters.turnKernelStartVal + ((parameters.turnKernelEndVal - parameters.turnKernelStartVal)/turnKernelLength)*i;
//		}
//		
//		// Head cast stimulus kernel
//		headCastStimulusKernel= new double[perceptionHistoryLength];
//		
//		int castKernelLength = (int) (parameters.castKernelDuration/timestep);
//		int castKernelStartPos = perceptionHistoryLength - castKernelLength;
//		
//		for(int i = castKernelStartPos; i < perceptionHistoryLength; i++)
//		{
//			headCastStimulusKernel[i] = parameters.castKernelStartVal + ((parameters.castKernelEndVal - parameters.castKernelStartVal)/castKernelLength)*i;
//		}
		
		turnStimulusKernel = initialiseKernel(parameters.turnKernelStartVal,	parameters.turnKernelEndVal, parameters.turnKernelDuration);
		headCastStimulusKernel = initialiseKernel(parameters.castKernelStartVal,	parameters.castKernelEndVal, parameters.castKernelDuration);

		
	}

	protected double[] initialiseKernel(double kernelStartVal, double kernelEndVal, double kernelDuration)
	{
		
		double[] kernel = new double[perceptionHistoryLength];
		int kernelLength = (int) (kernelDuration/timestep);
		int kernelStartPos =  perceptionHistoryLength - kernelLength;
		
		for(int i = kernelStartPos; i < perceptionHistoryLength; i++)
		{
			kernel[i] = kernelStartVal + ((kernelEndVal - kernelStartVal)/kernelLength)*i;
		}
		
		return kernel;
		
	}
	
	
	// Returns the probability of initiating a turn based on perception history
	public double getTurnProbability()
	{
		
		double modifier = convolveWithPerception(turnStimulusKernel); 
		
		// Scale rate
		double rate = Math.max(parameters.turnProbBase + parameters.turnProbMult*modifier,0);
		
		// Calculate turn probability based on rate and timestep
		double p = timestep*rate;
		
		return p;
		
	}
	
	
//	public double getTurnModifier()
//	{
//		
//		double[] perception = getPerceptionHistory();
//		
//		// Multiply kernel by perception history
//		double modifier = 0;
//		for(int i = 0; i < perceptionHistoryLength; i++)
//		{
//			modifier += perception[i]*turnStimulusKernel[i];
//		}
//		
//		return modifier;
//		
//	}
	
	
	// Returns the probability of stopping head casting
	public double getHeadCastStopProbability() {
		
		double modifier = convolveWithPerception(headCastStimulusKernel);
		
		// Scale rate
		double rate = Math.max(parameters.castProbBase + parameters.castProbMult*modifier,0);
		
		// Calculate turn probability based on rate and timestep
		double p = timestep*rate;
		
		return p;

	}
	
	
	public double getHeadCastStopModifier()
	{
		return convolveWithPerception(headCastStimulusKernel);
	}
	
	public double getTurnModifier()
	{
		return convolveWithPerception(turnStimulusKernel);
	}
	
	
	
	public double convolveWithPerception(double[] kernel)
	{
		
		double[] perception = getPerceptionHistory();
		
		double result = 0;
		for(int i = 0; i < perceptionHistoryLength; i++)
		{
			result += perception[i]*kernel[i];
		}
		
		return result;
		
		
	}
	

	protected void addPerception(double odourValueHead)
	{
		// Calculate perception: 1/C * dC/dt
		double C = getOdourValueHead();
		double deltaC = C - previousOdour;
		double perception;
		
		if (C != 0)
			{perception = 1/C * deltaC;}
		else
			{perception = 0;}
		
		// TODO: Really shouln't cap this
		if (Double.isInfinite(perception))
		{
			perception = Double.MAX_VALUE;
		}
		
		// Add perception to history
		// Perception history is kept in a looping array, with perceptionPointer keeping the current value
		perceptionPointer = (perceptionPointer+1) % perceptionHistoryLength;
		perceptionHistory[perceptionPointer] = perception;
	}

	
	// As perception history is kept in a looping array, we use this method to return an array
	// with normal ordering
	// TODO: Check and fix this
	protected double[] getPerceptionHistory()
	{
		double[] outArray = new double[perceptionHistoryLength];
		int s1 = perceptionPointer+1;
		int l1 = perceptionHistoryLength - s1;
		int s2 = 0;
		int l2 = s1;
		
		System.arraycopy(perceptionHistory, s1, outArray, 0, l1);
		System.arraycopy(perceptionHistory, s2, outArray, l1, l2);

		return outArray;
	}

	
	public double getPerception()
	{
		return perceptionHistory[perceptionPointer];		
	}


//	Removed for time being - probably should always set parameters at start	
//	// TODO: Check if resetting parameters breaks anything
//	public void setParams(Parameters p)
//	{
//		this.parameters = (KernelLarvaParameters) p;
//		initialiseKernels();
//	}


	
}