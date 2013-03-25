import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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
	
	private double headCastRange;
	
	private List<Wall> walls;
	
	
	// Create larva
	public Larva(double timestep, OdourSource o, Parameters parameters)
	{
		
		// Set initial position
		// TODO: Alter to take position as an input
		pos = new LarvaPosition(new Point(200,250), new Point(210,250), new Point(220,250));
		odour = o;
		
		// Initialise various things 
		this.timestep = timestep;
		perceptionHistoryLength = (int) (perceptionHistoryTime/timestep);
		
		state = LarvaState.FORWARD;
		
		perceptionPointer = 0;
		perceptionHistory = new double[perceptionHistoryLength];
		
		previousOdour = getOdourValueHead();
		
		walls = new ArrayList<Wall>();
		
		this.params = (AlgoLarvaParameters) parameters;
		
		initialiseKernels();
						
	}
	
	
	// Update method, called from simulation every step
	public void update(){
	
		boolean moveSuccess;
		
		// Do movement	
		switch(state){
		
		case FORWARD:
			moveSuccess = moveForward(forwardSpeed*timestep);
			if (Math.random() < getTurnProbability() || !moveSuccess)
			{
				if(Math.random() < 0.5)
					{state = LarvaState.CAST_LEFT;}
				else
					{state = LarvaState.CAST_RIGHT;}
			}
			headCastRange = sampleHeadCastRange();
			break;
		
		case CAST_LEFT:
			moveSuccess = turnHead(params.castSpeed*timestep);
			if (getRelativeHeadAngle() > params.castAngle || !moveSuccess)
			{
				state = LarvaState.CAST_RIGHT;
				headCastRange = sampleHeadCastRange();
			}
			if (Math.random() < getHeadCastStopProbability())
			{
				state = LarvaState.FORWARD;
			}
			
			break;
		
		case CAST_RIGHT:
			moveSuccess = turnHead(-params.castSpeed*timestep);
			if (getRelativeHeadAngle() < -params.castAngle || !moveSuccess)
			{
				state = LarvaState.CAST_LEFT;
				headCastRange = sampleHeadCastRange();
			}
			if (Math.random() < getHeadCastStopProbability())
			{
				state = LarvaState.FORWARD;
			}
			
			break;
			
		}
		
		// Update perception
		addPerception(getOdourValueHead());		
		previousOdour = getOdourValueHead();
		
		
	}

	// Larva must be informed of any walls
	// so that movement functions can check for collisions 
	public void addWall(Wall w)
	{
		walls.add(w);
	}
	
	
	
	
	// Currently returns a head cast angle uniformly from 0 to params.castAngle
	// TODO: Consider different distributions
	private double sampleHeadCastRange() {
		double range = Math.random() * params.castAngle;
		return range;
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
	private boolean moveForward(double dist){
		
		double angle = Geometry.lineAngle(pos.mid, pos.head);
			
		Point movement = new Point(Math.cos(angle)*dist,Math.sin(angle)*dist);
		
		// Check for collision with any walls
		// If collision would happen, don't move, and return false
		Point newHeadPoint = new Point(pos.head.x + movement.x, pos.head.y + movement.y);
		for(Wall w : walls)
		{
			if (w.checkCollision(pos.head,newHeadPoint))
				{return false;}
		}
		
		pos.head.translate(movement);
		pos.mid.translate(movement);

		// Tail moves separately
		double newTailAngle = getTailAngle(); 
		pos.tail.x = pos.mid.x - tailLength*Math.cos(newTailAngle);
		pos.tail.y = pos.mid.y - tailLength*Math.sin(newTailAngle);

		return true;
		
	}
	
	// Turns the head section by the specified angle (radians)
	// (+ve = CCW, -ve = CW)
	private boolean turnHead(double angle)
	{
		
		double currentAngle = getHeadAngle();
		double newAngle = currentAngle + angle;
				
		// Check for collision with any walls
		// If collision would happen, don't move, and return false
		Point newHeadPoint = new Point(pos.mid.x + headLength*Math.cos(newAngle), pos.mid.y + headLength*Math.sin(newAngle));		for(Wall w : walls)
		{
			if (w.checkCollision(pos.head,newHeadPoint))
				{return false;}
		}
		
		pos.head.x = newHeadPoint.x;
		pos.head.y = newHeadPoint.y;
		
		return true;
		
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