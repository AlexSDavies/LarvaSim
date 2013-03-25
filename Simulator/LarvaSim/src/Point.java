import java.awt.Color;
import java.awt.geom.Point2D.Double;


public class Point extends Double implements Drawable {

	private static final long serialVersionUID = 1L;

	public Point(double x, double y) {
		super(x,y);
	}

	public void translate(Point translation){
		
		x = x+translation.x;
		y = y+translation.y;
		
	}

	public void draw(SimViewer s) {
		s.setTransparecy((float) 0.5);
		s.setColor(Color.GRAY);
		s.drawPoint(this);
		s.setTransparecy(1);
	}
	

	
}
