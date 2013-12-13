package simulation;

import gui.SimViewer;

import java.awt.Color;
import java.awt.geom.Line2D;

public class StraightWall extends Wall 
{

	private Point w1, w2;

	public StraightWall(Point w1, Point w2)
	{
		this.w1 = w1;
		this.w2 = w2;
	}
		
		
	public void draw(SimViewer s)
	{
		s.setColor(Color.WHITE);
		s.setLineWidth(1);
		s.drawLine(new Point(w1.x,w1.y), new Point(w2.x,w2.y));
	}

	
	public boolean checkCollision(Point p1, Point p2)
	{
		boolean intersect = Line2D.linesIntersect(w1.x, w1.y, w2.x, w2.y, p1.x, p1.y, p2.x, p2.y);
		return intersect;
	}

	public double getAwayAngle(Point p1, Point p2) {

		double testAngle = Geometry.lineAngle(p1, p2);
		double wallAngle = Geometry.lineAngle(w1, w2);
		double angleBetween = Geometry.normaliseAngle(testAngle - wallAngle);
		
		double turnDir;
		
		if((angleBetween > 0 && angleBetween < Math.PI) || angleBetween < -Math.PI)
			{turnDir = -1;}
		else
			{turnDir = 1;}
		
		return turnDir;
		
	}
	
}

