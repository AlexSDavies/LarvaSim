package odours;

import gui.Drawable;
import gui.SimViewer;

import java.awt.Color;

import simulation.Point;

public abstract class OdourSource implements Drawable {


	private double radius;

	public abstract double getValue(Point p);
	
	// This can be set if you only want to draw the odour within a certain circular boundry
	// (Doesn't change the functionality)
	public void setDrawRadius(double r)
	{
		radius = r;
	}

	
	public void draw(SimViewer s)
	{
			
		double divSize = 0.5;
		int numXDivs = (int) ((s.getMaxX() - s.getMinX())/divSize);
		int numYDivs = (int) ((s.getMaxY() - s.getMinY())/divSize);
		
		for (int ix = 0; ix <= numXDivs; ix++)
		{
			for (int iy = 0; iy <= numYDivs; iy++)
			{
				double x = (s.getMinX() + ix*divSize);
				double y = (s.getMinY() + iy*divSize);
				Point p = new Point(x+divSize/2,y+divSize/2);
				float odourVal = (float) getValue(p);
				float hueVal = (1-odourVal)*240/360;
				
				if (p.distance(new Point(0,0)) < radius || radius == 0)
					{s.setColor(Color.getHSBColor(hueVal, 1, 1));}
				else
					{s.setColor(Color.WHITE);}
				
				s.fillRect(new Point(x,y), new Point(x+divSize,y+divSize));
			}
		}
		
		s.setColor(Color.BLACK);
		
	}
	
	
}
