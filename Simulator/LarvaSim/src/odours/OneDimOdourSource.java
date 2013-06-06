package odours;
import simulation.Point;


public class OneDimOdourSource extends OdourSource {


	double maxX;
	double gradient;
	double intensity;
	
	public OneDimOdourSource(double x, double gradient)
	{
		this.maxX = x;
		this.gradient = gradient;
		intensity = 1;
	}
	
	public void setIntensity(double val)
	{
		intensity = val;
	}
	
	
	public double getValue(Point p) {
		
		double val = intensity - gradient * Math.abs(p.x - maxX);
		val = Math.max(val, 0);
		return val;
	}

}
