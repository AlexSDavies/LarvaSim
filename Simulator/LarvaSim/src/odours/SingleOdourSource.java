package odours;
import simulation.Point;


public class SingleOdourSource extends OdourSource {

	Point odourPos;
	
	private double intensity, sigmaX1, sigmaX2, sigmaY1, sigmaY2;
	
	public SingleOdourSource(Point pos, double sigmaX1, double sigmaY1, double sigmaX2, double sigmaY2)
	{
		odourPos = pos;
		this.sigmaX1 = sigmaX1;
		this.sigmaY1 = sigmaY1;
		this.sigmaX2 = sigmaX2;
		this.sigmaY2  = sigmaY2;
		
		intensity = 1;
		
	}
	
	public void setIntensity(double val)
	{
		intensity = val;
	}
	
	public double getValue(Point p)
	{
		
		double xDist = Math.abs(odourPos.x - p.x);
		double yDist = Math.abs(odourPos.y - p.y);
				
		double xScaledDist = xDist/sigmaX1;
		double yScaledDist = yDist/sigmaY1;
		
		double xScaledDist2 = xDist/sigmaX2;
		double yScaledDist2 = yDist/sigmaY2;
		
		double val = 0.5 * Math.exp(-(xScaledDist*xScaledDist)-(yScaledDist*yScaledDist))
			+ 0.5 * Math.exp(-(xScaledDist2*xScaledDist2)-(yScaledDist2*yScaledDist2));
		
		return val*intensity;
		
	}
	
}
