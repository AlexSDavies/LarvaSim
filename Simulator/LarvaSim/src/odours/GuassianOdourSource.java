package odours;
import simulation.Point;


public class GuassianOdourSource extends OdourSource {

	Point odourPos;
	
	private double intensity, sigmaX, sigmaY;
	
	public GuassianOdourSource(Point pos, double sigmaX, double sigmaY)
	{
		odourPos = pos;
		this.sigmaX = sigmaX;
		this.sigmaY = sigmaY;
		
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
				
		double xScaledDist = xDist/sigmaX;
		double yScaledDist = yDist/sigmaY;
		
		double val = Math.exp(-(xScaledDist*xScaledDist)-(yScaledDist*yScaledDist));
		
		return val*intensity;
		
	}
	
}
