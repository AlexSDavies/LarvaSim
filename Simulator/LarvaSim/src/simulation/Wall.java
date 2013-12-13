package simulation;

import gui.Drawable;

public abstract class Wall implements Drawable
{

	public abstract boolean checkCollision(Point p1, Point p2);

	public abstract double getAwayAngle(Point p1, Point p2);
	
}