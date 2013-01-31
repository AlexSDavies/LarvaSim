import java.awt.Color;
import java.awt.geom.Point2D.Double;


public class Point extends Double implements Drawable {

	
	public Point(double x, double y) {
		super(x,y);
	}

	public void translate(Point translation){
		
		x = x+translation.x;
		y = y+translation.y;
		
	}

	public void draw(SimViewer s) {
		s.setColor(Color.GRAY);
		s.drawPoint(this);
	}
	

	
}
