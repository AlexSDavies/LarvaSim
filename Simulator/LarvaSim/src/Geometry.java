import java.awt.geom.Point2D.Double;

public class Geometry {	
	
	
	static double angleBetween(Double p1, Double p2, Double p3){
		
		double x1 = p2.x - p1.x;
		double y1 = p2.y - p1.y;
		
		double x2 = p3.x - p2.x;
		double y2 = p3.y - p2.y;
		
		double a1 = Math.atan2(y1,x1);
		double a2 = Math.atan2(y2,x2);
		
		return a1 - a2;
		
	}
	
	static double lineAngle(Double p1, Double p2){
		
		return Math.atan2(p2.y-p1.y, p2.x-p1.x);
		
	}
	
	// Normalises an angle to between -pi and pi 
	static double normaliseAngle(double angle)
	{
		
		angle = angle % (2*Math.PI);
		
		if (angle > Math.PI)
			{angle = -Math.PI + (angle%Math.PI);}
		if (angle < -Math.PI)
			{angle = Math.PI + (angle%Math.PI);}
		
		return angle;
		
	}
	
}
