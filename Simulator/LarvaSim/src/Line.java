import java.awt.Color;


public class Line implements Drawable {

	private Point p1, p2;


	public Line(Point p1, Point p2)
	{
		this.p1 = p1;
		this.p2 = p2;
	}
	
	
	public void draw(SimViewer s)
	{
		s.setColor(Color.BLACK);
		s.drawLine(p1, p2);
	}

}
