import java.awt.Color;

public abstract class OdourSource implements Drawable {


	double radius;
	
	public abstract double getValue(Point p);
	
	public void setRadius(double r)
	{
		radius = r;
	}
	
	public void draw(SimViewer s)
	{
			
		int divSize = 5;
		int numXDivs = (s.maxX - s.minX)/divSize;
		int numYDivs = (s.maxY - s.minY)/divSize;
		
		for (int ix = 0; ix <= numXDivs; ix++)
		{
			for (int iy = 0; iy <= numYDivs; iy++)
			{
				int x = s.minX + ix*divSize;
				int y = s.minY + iy*divSize;
				Point p = new Point(x+divSize/2,y+divSize/2);
				float odourVal = (float) getValue(p);
				float hueVal = (1-odourVal)*240/360;
				
				if (p.distance(new Point(0,0)) < radius || radius == 0)
					{s.setColor(Color.getHSBColor(hueVal, 1, 1));}
				else
					{s.setColor(Color.WHITE);}
				
				s.fillRect(new Point(x-0.5,y-0.5), new Point(x+divSize+0.5,y+divSize+0.5));
			}
		}
		
		s.setColor(Color.BLACK);
		
	}
	
	
}
