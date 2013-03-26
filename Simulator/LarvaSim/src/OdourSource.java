import java.awt.Color;

public abstract class OdourSource implements Drawable {


	public abstract double getValue(Point p);


	public void draw(SimViewer s)
	{
			
		int divSize = 5;
		int numXDivs = (s.maxX - s.minX)/divSize;
		int numYDivs = (s.maxY - s.minY)/divSize;
		
		for (int ix = 0; ix < numXDivs; ix++)
		{
			for (int iy = 0; iy < numYDivs; iy++)
			{
				int x = s.minX + ix*divSize;
				int y = s.minY + iy*divSize;
				float odourVal = (float) getValue(new Point(x,y));
				float hueVal = (1-odourVal)*240/360;
				s.setColor(Color.getHSBColor(hueVal, 1, 1));
				s.fillRect(new Point(x,y), new Point(x+divSize+1,y+divSize+1));
			}
		}
		
		s.setColor(Color.BLACK);
		
	}
	
	
}
