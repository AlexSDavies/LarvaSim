import java.awt.Color;


public class Wall implements Drawable {

	Point centre;
	double radius;
	
	public Wall(Point centre, double radius)
	{
		this.centre = centre;
		this.radius = radius;
	}
	
	public void draw(SimViewer s)
	{
		s.setColor(Color.GRAY);
		s.setLineWidth(3);
		s.drawCircle(new Point(centre.x-radius, centre.y-radius),new Point(centre.x+radius, centre.y+radius));
	}

	// Check if a movement from p1 to p2 leads to a collision with this wall
	public boolean checkCollision(Point p1, Point p2)
	{
		double dist1 = p1.distance(centre);
		double dist2 = p2.distance(centre);
		if (dist1 < radius && dist2 > radius)
			{return true;}
		else
			{return false;}	
	}

}
