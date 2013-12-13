package larvae;

import gui.Drawable;
import gui.SimViewer;

import java.awt.Color;

import simulation.Geometry;
import simulation.Point;
import simulation.Simulation;
import simulation.Updateable;
import simulation.CircleWall;
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
	
	// Fields
	protected LarvaPosition pos;	
	
	protected double timestep;
	
	protected Simulation sim;
	
	private Parameters parameters;
	
	Color col;
	
	
	public Larva(Simulation sim, Parameters params, Point startPos, double dir)
	{
		
		if (sim != null)
		{
			this.sim = sim;
			timestep = sim.timestep;
		}
		
		// Set initial position
		Point midPos = startPos;
		Point headPos = new Point(midPos.x + headLength*Math.cos(dir), midPos.y + headLength*Math.sin(dir));
		Point tailPos = new Point(midPos.x - headLength*Math.cos(dir), midPos.y - headLength*Math.sin(dir));
		pos = new LarvaPosition(headPos, midPos, tailPos);
		
		this.setParameters(params);
		
		col = Color.BLACK;
		
	}
	
	public void setSim(Simulation sim)
	{
		this.sim = sim;
		timestep = sim.timestep;
	}
	
	// This shouldn't be used apart from in setting up initial position
	public void setPos(Point startPos, double dir)
	{
		Point midPos = startPos;
		Point headPos = new Point(midPos.x + headLength*Math.cos(dir), midPos.y + headLength*Math.sin(dir));
		Point tailPos = new Point(midPos.x - headLength*Math.cos(dir), midPos.y - headLength*Math.sin(dir));
		pos = new LarvaPosition(headPos, midPos, tailPos);
	}
	
	// Update method, called from simulation every step
	public abstract void update();

	
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
				double turnDir = w.getAwayAngle(pos.mid,pos.head);
				// TODO: maybe make this not rely on parameters
				// turnHead(turnDir*getParameters().castSpeed*timestep);
				double currentAngle = getHeadAngle();
				double newAngle = currentAngle + turnDir*getParameters().castSpeed*timestep;
				Point newHeadPoint2 = new Point(pos.mid.x + headLength*Math.cos(newAngle), pos.mid.y + headLength*Math.sin(newAngle));
				pos.head.x = newHeadPoint2.x;
				pos.head.y = newHeadPoint2.y;
				
				return false;
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

	public Parameters getParameters() {
		return parameters;
	}

	private void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	
	
	

}
