import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class Larva implements Drawable, Updateable {

	// Parameters
	private final double headLength = 10;
	private final double tailLength = 10;
	
	private final double castSpeed = 2;
	
	private final double forwardSpeed = 10;
	
	// TODO: Change this to time rather than steps
	private final int perceptionHistoryLength = 100;

	
	
	// Fields
	private LarvaPosition pos;	
	private OdourSource odour;
	
	private double timestep;
	
	public enum LarvaState {FORWARD,CAST_LEFT,CAST_RIGHT;}
	private LarvaState state;
	
	private double previousOdour;

	private int perceptionPointer;
	private double[] perceptionHistory;
	
	public double[] turnStimulusKernal;
	private double[] headCastStimulusKernal;

	
	private AlgoLarvaParameters params;
	
	
	
	// Create larva (must be passed an odour source)
	public Larva(double timestep, OdourSource o, Parameters parameters)
	{
		pos = new LarvaPosition(new Point(50,450), new Point(50,440), new Point(50,430));
		odour = o;
		
		this.timestep = timestep;
		
		state = LarvaState.FORWARD;
		
		perceptionPointer = 0;
		perceptionHistory = new double[perceptionHistoryLength];
		
		previousOdour = getOdourValueHead();
		
		this.params = (AlgoLarvaParameters) parameters;
		
		initialiseKernals();
						
	}
	
	
	private void initialiseKernals()
	{
		
		// Turn stimulus kernal
		turnStimulusKernal = new double[perceptionHistoryLength];
		for(int i = 0; i < perceptionHistoryLength; i++)
		{
			turnStimulusKernal[i] = params.turnKernalStartVal + ((params.turnKernalEndVal - params.turnKernalStartVal)/perceptionHistoryLength)*i;
		}
		
		// Head cast stimulus kernal
		headCastStimulusKernal= new double[perceptionHistoryLength];
		int kernalStartPos = (int) (params.castKernalStartPos*perceptionHistoryLength);
		int kernalLength = perceptionHistoryLength - kernalStartPos;
		for(int i = kernalStartPos; i < perceptionHistoryLength; i++)
		{
			headCastStimulusKernal[i] = params.castKernalStartVal + ((params.castKernalEndVal - params.castKernalStartVal)/kernalLength)*i;
		}
		
		
	}


	// Update method, called from simulation every step
	public void update(){
	
		// System.out.println(getHeadAngle() + " : " + getTailAngle());
		
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

	
	// Probabilities are based on a poisson type system:
	// We calculate a 'rate' which determines the expected time to turning
	// and then calculate a probability based on this and the timestep
	

	// Returns the probability of initiating a turn based on perception history
	// TODO: Make this more principled
	public double getTurnProbability()
	{
		
		double[] perception = getPerceptionHistory();
		
		// Multiply kernel by perception history
		double rate = 0;
		for(int i = 0; i < perceptionHistoryLength; i++)
		{
			rate += perception[i]*turnStimulusKernal[i];
		}
		
		// Scale rate
		rate = params.turnProbBase + Math.max(params.turnProbMult*rate,0);
		
		// Calculate turn probability based on rate and timestep
		double p = timestep*rate;
		
		return p;
		
	}
	
	
	// Returns the probability of stopping head casting
	// TODO: Make this more principled
	public double getHeadCastStopProbability() {
		
		double[] perception = getPerceptionHistory();
		
		// Multiply kernel by perception history
		double rate = 0;
		for(int i = 0; i < perceptionHistoryLength; i++)
		{
			rate += perception[i]*headCastStimulusKernal[i];
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
		perceptionPointer = (perceptionPointer+1) % perceptionHistoryLength;
		perceptionHistory[perceptionPointer] = perception;
	}

	// TODO: Check this
	private double[] getPerceptionHistory()
	{
		double[] outArray = new double[perceptionHistoryLength];
		int s1 = perceptionPointer+1;
		int l1 = perceptionHistoryLength - s1;
		int s2 = 0;
		int l2 = s1;
		
		System.arraycopy(perceptionHistory, s1, outArray, 0, l1);
		System.arraycopy(perceptionHistory, s2, outArray, l1, l2);
//		System.out.println(Arrays.toString(outArray));
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