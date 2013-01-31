import java.awt.Color;
import java.awt.Graphics;


public class OdourSource implements Drawable {

	
	Point odourPos;
	
	public OdourSource(Point point) {
		odourPos = point;
	}

	public double getValue(Point p)
	{
		
//		double dist = odourPos.distance(p);
//		double d = dist/200;
//		double val = Math.exp(-(d*d));
		
		double xDist = Math.abs(odourPos.x - p.x);
		double yDist = Math.abs(odourPos.y - p.y);
				
		double xScaledDist = xDist/100;
		double yScaledDist = yDist/70;
		
		double xScaledDist2 = xDist/40;
		double yScaledDist2 = yDist/120;
		
		double val = 0.5 * Math.exp(-(xScaledDist*xScaledDist)-(yScaledDist*yScaledDist))
			+ 0.5 * Math.exp(-(xScaledDist2*xScaledDist2)-(yScaledDist2*yScaledDist2));
		
		return val;
		
	}

	public void draw(SimViewer s)
	{
		
		int divSize = 5;
		
		for (int x = 1; x <= 500; x+=divSize)
		{
			for (int y = 1; y <= 500; y+=divSize)
			{
				float odourVal = (float) getValue(new Point(x,y));
				float hueVal = (1-odourVal)*240/360;
				s.setColor(Color.getHSBColor(hueVal, 1, 1));
				s.fillRect(x,y, divSize, divSize);
			}
		}
		
		s.setColor(Color.BLACK);
		//s.fillRect((int) odourPos.x, (int) odourPos.y, 5, 5);
		
	}
	
	
}
