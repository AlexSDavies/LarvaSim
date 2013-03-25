import java.awt.Color;

public abstract class OdourSource implements Drawable {


	public abstract double getValue(Point p);


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
		
	}
	
	
}
