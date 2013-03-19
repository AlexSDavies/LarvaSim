import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

/*
 * This class defines the behaviour of the simulated larva
 * 
 * The 'update' function gets called on every step of the simulation,
 * and is where any behaviour should be encoded
 * 
 */

public class Larva implements Drawable, Updateable {

	// Parameters
	private final double headLength = 10;
	private final double tailLength = 10;
	
	private final double forwardSpeed = 10;
	
	private final int perceptionHistoryTime = 10;
	private int perceptionHistoryLength;
	
	
	// Fields
	private LarvaPosition pos;	
	private OdourSource odour;
	
	private double timestep;
	
	public enum LarvaState {FORWARD,CAST_LEFT,CAST_RIGHT;}
	private LarvaState state;
	
	private double previousOdour;

	private int perceptionPointer;
	private double[] perceptionHistory;
	
	public double[] turnStimulusKernel;
	private double[] headCastStimulusKernel;

	
	private AlgoLarvaParameters params;
	
	
	
	// Create larva (must be passed an odour source)
	public Larva(double timestep, OdourSource o, Parameters parameters)
	{
		
		// Set initial position
		// TODO: Alter to take position as an input
		pos = new LarvaPosition(new Point(200,420), new Point(200,410), new Point(200,400));
		odour = o;
		
		// Initialise various things 
		this.timestep = timestep;
		perceptionHistoryLength = (int) (perceptionHistoryTime/timestep);
		
		state = LarvaState.FORWARD;
		
		perceptionPointer = 0;
		perceptionHistory = new double[perceptionHistoryLength];
		
		previousOdour = getOdourValueHead();
		
		this.params = (AlgoLarvaParameters) parameters;
		
		initialiseKernels();
						
	}
	
	
	// Update method, called from simulation every step
	public void update(){
	
		// Do movement	
		switch(state){
		
		case FORWARD:
			moveForward(forwardSpeed*timestep);
			if (Math.random() < getTurnProbability())
			{
				if(Math.random() < 0.5)
					{state = LarvaState.CAST_LEFT;}
				else
					{state = LarvaState.CAST_RIGHT;}
			}
			break;
		
		case CAST_LEFT:
			turnHead(params.castSpeed*timestep);
			if (getRelativeHeadAngle() > params.castAngle)
				{state = LarvaState.CAST_RIGHT;}
			if (Math.random() < getHeadCastStopProbability())
				{state = LarvaState.FORWARD;}
			
			break;
		
		case CAST_RIGHT:
			turnHead(-params.castSpeed*timestep);
			if (getRelativeHeadAngle() < -params.castAngle)
				{state = LarvaState.CAST_LEFT;}
			if (Math.random() < getHeadCastStopProbability())
				{state = LarvaState.FORWARD;}
			
			break;
			
		}
		
		// Update perception
		addPerception(getOdourValueHead());		
		previousOdour = getOdourValueHead();
		
		
	}

	
	private void initialiseKernels()
	{
		
		// Turn stimulus kernel
		turnStimulusKernel = new double[perceptionHistoryLength];
		int turnKernelLength = (int) (params.turnKernelDuration/timestep);
		int turnKernelStartPos = perceptionHistoryLength - turnKernelLength;
		
		for(int i = turnKernelStartPos; i < perceptionHistoryLength; i++)
		{
			turnStimulusKernel[i] = params.turnKernelStartVal + ((params.turnKernelEndVal - params.turnKernelStartVal)/turnKernelLength)*i;
		}
		
		// Head cast stimulus kernel
		headCastStimulusKernel= new double[perceptionHistoryLength];
		
		int castKernelLength = (int) (params.castKernelDuration/timestep);
		int castKernelStartPos = perceptionHistoryLength - castKernelLength;
		
		for(int i = castKernelStartPos; i < perceptionHistoryLength; i++)
		{
			headCastStimulusKernel[i] = params.castKernelStartVal + ((params.castKernelEndVal - params.castKernelStartVal)/castKernelLength)*i;
		}
		
		
	}

	
	// Returns the probability of initiating a turn based on perception history
	public double getTurnProbability()
	{
		
		double[] perception = getPerceptionHistory();
		
		// Multiply kernel by perception history
		double rate = 0;
		for(int i = 0; i < perceptionHistoryLength; i++)
		{
			rate += perception[i]*turnStimulusKernel[i];
		}
		
		// Scale rate
		rate = params.turnProbBase + Math.max(params.turnProbMult*rate,0);
		
		// Calculate turn probability based on rate and timestep
		double p = timestep*rate;
		
		return p;
		
	}
	
	
	// Returns the probability of stopping head casting
	public double getHeadCastStopProbability() {
		
		double[] perception = getPerceptionHistory();
		
		// Multiply kernel by perception history
		double rate = 0;
		for(int i = 0; i < perceptionHistoryLength; i++)
		{
			rate += perception[i]*headCastStimulusKernel[i];
		}
		
		// Scale rate
		rate = params.castProbBase+ Math.max(params.castProbMult*rate,0);
		
		// Calculate turn probability based on rate and timestep
		double p = timestep*rate;
		
		return p;

	}
	

	private void addPerception(double odourValueHead)
	{
		// Calculate perception: 1/C * dC/dt
		double C = getOdourValueHead();
		double deltaC = C - previousOdour;
		double perception = 1/C * deltaC;
		
		// Add perception to history
		// Perception history is kept in a looping array, with perceptionPointer keeping the current value
		perceptionPointer = (perceptionPointer+1) % perceptionHistoryLength;
		perceptionHistory[perceptionPointer] = perception;
	}

	
	// As perception history is kept in a looping array, we use this method to return an array
	// with normal ordering
	private double[] getPerceptionHistory()
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

	// Moves forward by the specified distance
	// (Tail section follows head)
	private void moveForward(double dist){
		
		
		double angle = Geometry.lineAngle(pos.mid, pos.head);
			
		Point move = new Point(Math.cos(angle)*dist,Math.sin(angle)*dist);
		
		pos.head.translate(move);
		pos.mid.translate(move);
		
		// Tail moves separately
		double newTailAngle = getTailAngle(); 
		pos.tail.x = pos.mid.x - tailLength*Math.cos(newTailAngle);
		pos.tail.y = pos.mid.y - tailLength*Math.sin(newTailAngle);
		
	}
	
	// Turns the head section by the specified angle (radians)
	// (+ve = CCW, -ve = CW)
	private void turnHead(double angle)
	{
		
		double currentAngle = getHeadAngle();
		double newAngle = currentAngle + angle;
		
		pos.head.x = pos.mid.x + headLength*Math.cos(newAngle);
		pos.head.y = pos.mid.y + headLength*Math.sin(newAngle);
		
	}
	
	
	
	// Draw method, called from Simulation every step
	public void draw(SimViewer s)
	{
	
		s.setColor(Color.BLACK);
		s.setLineWidth(3);
		s.drawLine(pos.mid,pos.head);
		s.drawLine(pos.tail,pos.mid);
		
	}
	
	
	
	
	
	// Returns angle of head compared to body
	// (+ve angle indicated left turn, -ve angle indicates right turn)
	public double getRelativeHeadAngle(){
		double angle = getHeadAngle() - getTailAngle();
		return Geometry.normaliseAngle(angle);
	}
	
	// Returns tail angle CCW from x-axis
	public double getTailAngle(){
		return Geometry.lineAngle(pos.tail,pos.mid);
	}
	
	// Returns head angle CCW from x-axis
	public double getHeadAngle(){
		return Geometry.lineAngle(pos.mid,pos.head);
	}
	
	// Gets the body angle (tail to mid) relative to x-axis
	public double getBodyAngle(){
		return Geometry.lineAngle(pos.tail,pos.mid);
	}
	
	// Gets the angle between the body angle and the odour
	// i.e. 0 if heading towards odour, 180 if heading away
	public double getBearing()
	{
		
		// Calculate angle of maximum odour increase
		double curVal = getOdourValueMid();
		double deltaXVal = odour.getValue(new Point(pos.mid.x + 1, pos.mid.y)) - curVal;
		double deltaYVal = odour.getValue(new Point(pos.mid.x, pos.mid.y + 1)) - curVal;
		
		double odourAngle = Math.atan2(deltaYVal, deltaXVal);
		
		double headAngle = getBodyAngle() - odourAngle;

		return Geometry.normaliseAngle(headAngle);
		
	}
	
	
	
	public LarvaPosition getPos() {
		return pos;
		}
	
	public double getOdourValueHead()
	{
		return odour.getValue(pos.head);
	}
	
	public double getOdourValueMid()
	{
		return odour.getValue(pos.mid);
	}

	
	
	
}