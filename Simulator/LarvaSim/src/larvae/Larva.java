package larvae;



import gui.Drawable;
import gui.SimViewer;

import java.awt.Color;

import simulation.Geometry;
import simulation.Point;
import simulation.Simulation;
import simulation.Updateable;
import simulation.Wall;

/*
 * This class defines the behaviour of the simulated larva
 * 
 * The 'update' function gets called on every step of the simulation,
 * and is where any behaviour should be encoded
 * 
 */

public abstract class Larva implements Drawable, Updateable {

	// Parameters
	protected final double headLength = 1;
	protected final double tailLength = 1;
	
	// Speed in mm/s
	protected final double forwardSpeed = 1;
	
	protected final int perceptionHistoryTime = 10;
	protected int perceptionHistoryLength;
	
	
	// Fields
	protected LarvaPosition pos;	
	
	protected double timestep;
	
	public enum LarvaState {FORWARD,CAST_LEFT,CAST_RIGHT,CAST_BACK_LEFT,CAST_BACK_RIGHT;}
	protected LarvaState state;
	
	protected double previousOdour;

	protected int perceptionPointer;
	protected double[] perceptionHistory;
	
	protected double[] turnStimulusKernel;
	protected double[] headCastStimulusKernel;

	
	protected AlgoLarvaParameters params;
	

	// This may be used in update step to do variable head cast max ranges
	protected double headCastRange;
	
	protected Simulation sim;
	
	Color col;
	
	
	// Create larva
	// Larva must be passed parent simulator to access other objects in simulation
	public Larva(Simulation sim, Point startPos, double dir)
	{
		
		this.sim = sim;
		
		// Set initial position
		Point midPos = startPos;
		Point headPos = new Point(midPos.x + headLength*Math.cos(dir), midPos.y + headLength*Math.sin(dir));
		Point tailPos = new Point(midPos.x - headLength*Math.cos(dir), midPos.y - headLength*Math.sin(dir));
		pos = new LarvaPosition(headPos, midPos, tailPos);
		
		// Initialise various things 
		timestep = sim.timestep;
		perceptionHistoryLength = (int) (perceptionHistoryTime/timestep);
		
		state = LarvaState.FORWARD;
		
		perceptionPointer = 0;
		perceptionHistory = new double[perceptionHistoryLength];
		
		previousOdour = getOdourValueHead();
		
		params = (AlgoLarvaParameters) sim.getParameters();
		
		initialiseKernels();
						
		// Switch for exciting multicoloured larvae
		// col = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
		col = Color.BLACK;
		
	}


	// Update method, called from simulation every step
	public abstract void update();

	
	// Currently returns a head cast angle uniformly from 0 to params.castAngle
	// TODO: Consider different distributions
	protected double sampleHeadCastRange() {
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
		rate = params.turnProbBase + Math.max(params.turnProbMult*rate,0.0);
		
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
		rate = params.castProbBase + Math.max(params.castProbMult*rate,0);
		
		// Calculate turn probability based on rate and timestep
		double p = timestep*rate;
		
		return p;

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

	// Moves forward by the specified distance
	// (Tail section follows head)
	protected boolean moveForward(double dist){
		
		double angle = Geometry.lineAngle(pos.mid, pos.head);
			
		Point movement = new Point(Math.cos(angle)*dist,Math.sin(angle)*dist);
		
		// Check for collision with any walls
		// If collision would happen, don't move, and return false
		Point newHeadPoint = new Point(pos.head.x + movement.x, pos.head.y + movement.y);
		for(Wall w : sim.getWalls())
		{
			if (w.checkCollision(pos.head,newHeadPoint))
			{
				double wallAngle = Geometry.normaliseAngle(Geometry.lineAngle(pos.mid,pos.head) - Geometry.lineAngle(w.centre,pos.head));
				double turnDir = Math.signum(wallAngle);
				turnHead(turnDir*params.castSpeed*timestep);
				return true;
			}
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
	protected boolean turnHead(double angle)
	{
		
		double currentAngle = getHeadAngle();
		double newAngle = currentAngle + angle;
				
		// Check for collision with any walls
		// If collision would happen, don't move, and return false
		Point newHeadPoint = new Point(pos.mid.x + headLength*Math.cos(newAngle), pos.mid.y + headLength*Math.sin(newAngle));
		for(Wall w : sim.getWalls())
		{
			if (w.checkCollision(pos.head,newHeadPoint))
				{return false;}
		}
		
		pos.head.x = newHeadPoint.x;
		pos.head.y = newHeadPoint.y;
		
		return true;
		
	}
	
	
	// TODO: Check if resetting parameters breaks anything
	public void setParams(Parameters p)
	{
		this.params = (AlgoLarvaParameters) p;
		initialiseKernels();
	}

	
	// Draw method, called from Simulation every step
	public void draw(SimViewer s)
	{
	
		s.setColor(col);
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
		double deltaXVal = sim.getOdour().getValue(new Point(pos.mid.x + 1, pos.mid.y)) - curVal;
		double deltaYVal = sim.getOdour().getValue(new Point(pos.mid.x, pos.mid.y + 1)) - curVal;
		
		double odourAngle = Math.atan2(deltaYVal, deltaXVal);
		
		double headAngle = getBodyAngle() - odourAngle;
		
		double returnVal = Geometry.normaliseAngle(headAngle);
		
		return returnVal;
		
	}
	
	
	
	public LarvaPosition getPos() {
		return pos;
		}
	
	public double getOdourValueHead()
	{
		return sim.getOdour().getValue(pos.head);
	}
	
	public double getOdourValueMid()
	{
		return sim.getOdour().getValue(pos.mid);
	}

	
	public void setColour(Color c)
	{
		this.col = c;
	}
	
}