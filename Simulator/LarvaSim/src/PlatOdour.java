
public class PlatOdour extends OdourSource {

	private Point c;
	private double max;
	private double rate;
	private double platRadius;

	public PlatOdour(Point c)
	{
		this.c = c;
		max = 1;
		rate = 0.4;
		platRadius = 5;
	}

	@Override
	public double getValue(Point p) {

		double xDist = Math.abs(c.x - p.x);
		double yDist = Math.abs(c.y - p.y);
		double dist = Math.sqrt(xDist*xDist + yDist*yDist);
		
		dist = dist - platRadius;
		
		double val;
		
		if(dist < 0)
			{val = max;}
		else
			{val = max*rate*Math.pow(Math.E,-rate*dist);}
		
		return val;
	}

}
