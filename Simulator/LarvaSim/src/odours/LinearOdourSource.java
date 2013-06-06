package odours;
import simulation.Point;


public class LinearOdourSource extends OdourSource
{

	private Point pos;
	private double gradient;
	private double intensity;


	public LinearOdourSource(Point pos, double gradient)
	{
		this.pos = pos;
		this.gradient = gradient;
		intensity = 1;
	}
	
	public void setIntensity(double val)
	{
		intensity = val;
	}
	
	public double getValue(Point p) {

		double val = Math.max(intensity - (gradient * pos.distance(p)),0) + 0.01;
		
		return val;
	}

	
	
}
