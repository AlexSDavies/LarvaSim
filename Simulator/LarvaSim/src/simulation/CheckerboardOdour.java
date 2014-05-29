package simulation;

import odours.OdourSource;

public class CheckerboardOdour extends OdourSource {

	private double width = 20;
	
	public CheckerboardOdour()
	{
	}
	
	public double getValue(Point p) {
		
		double xDist = p.x;
		double yDist = p.y;
		
		if(xDist < 0)
			xDist = Math.abs(xDist - width);
		if(yDist < 0)
			yDist = Math.abs(yDist - width);
		
		
		int xSquare = (int) ((xDist/width) % 2);
		int ySquare = (int) ((yDist/width) % 2);
		
		double odourVal;
		
		if (xSquare == ySquare)
			{odourVal = 1;}
		else
			{odourVal = 0;}
		
		return odourVal;
		
	}

}
