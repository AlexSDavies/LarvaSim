package simulation;

import java.awt.Color;

import gui.SimViewer;



public class CircleWall extends Wall{

	public Point centre;
	double radius;
	
	public CircleWall(Point centre, double radius)
	{
		this.centre = centre;
		this.radius = radius;
	}
	
	public void draw(SimViewer s) {
		s.setColor(Color.GRAY);
		s.setLineWidth(2);
		s.drawLine(new Point(centre.x,centre.y-radius), new Point(centre.x,centre.y+radius));
		
		s.setColor(Color.GRAY);
		s.setLineWidth(5);
		s.drawCircle(new Point(centre.x-radius, centre.y-radius),new Point(centre.x+radius, centre.y+radius));
	}

	public boolean checkCollision(Point p1, Point p2) {
		double dist1 = p1.distance(centre);
		double dist2 = p2.distance(centre);
		if (dist1 < radius && dist2 > radius)
			{return true;}
		else
			{return false;}	
	}

	public double getAwayAngle(Point p1, Point p2)
	{
		double wallAngle = Geometry.normaliseAngle(Geometry.lineAngle(p1,p2) - Geometry.lineAngle(centre,p2));
		double turnDir = Math.signum(wallAngle);
		return turnDir;
	}

}
