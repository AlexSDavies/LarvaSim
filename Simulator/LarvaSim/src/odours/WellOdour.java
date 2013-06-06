package odours;
import simulation.Point;


public class WellOdour extends OdourSource {

	private Point c;
	private double max;
	private double rate;
	private double wellRadius;

	public WellOdour(Point c)
	{
		this.c = c;
		max = 1;
		rate = 0.4;
		wellRadius = 5;
	}

	@Override
	public double getValue(Point p) {

		double xDist = Math.abs(c.x - p.x);
		double yDist = Math.abs(c.y - p.y);
		double dist = Math.sqrt(xDist*xDist + yDist*yDist);
		
		dist = dist - wellRadius;
		
		double val;
		
		if(dist < 0)
			{val = 0;}
		else
			{val = max*rate*Math.exp(-rate*dist);}
		
		return val;
	}

}
