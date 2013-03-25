import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class SimViewer extends JPanel
{

	private static final long serialVersionUID = 1L;

	List <Drawable> drawObjects;
	
	Dimension arenaSize;
	
	Graphics2D g;
	
	// SimViewer is given a list of objects to draw
	// Default arena size = 500x500
	public SimViewer(List <Drawable> drawObjects)
	{
		this.drawObjects = drawObjects;
		arenaSize = new Dimension(500,500);
	}
	
	
	public void paintComponent(Graphics g) {
		
		this.g = (Graphics2D) g;
		
		super.paintComponent(g);
				
		for (Drawable d : drawObjects) {
			d.draw(this);
		}

	}

	public void setArenaSize(Dimension d){
		arenaSize = d;
	}
	
	
	// Scales a point so that arena size fits in simViewer
	public Point scale(Point p){
		Dimension dim = getSize();
		double xScale = dim.getWidth() / arenaSize.getWidth();
		double yScale = dim.getHeight() / arenaSize.getHeight();
		return new Point(p.x*xScale, p.y*yScale);
		
	}


	public void setLineWidth(int w) {
		g.setStroke(new BasicStroke(w));
	}

	public void setColor(Color c) {
		g.setColor(c);
	}
	
	public void setTransparecy(float val)
	{
		int rule = AlphaComposite.SRC_OVER;
	    Composite comp = AlphaComposite.getInstance(rule, val);
        g.setComposite(comp);
	}
	
	
	public void drawLine(Point p1, Point p2) {
		Point scaledP1 = scale(p1);
		Point scaledP2 = scale(p2);
		g.drawLine((int) scaledP1.x,(int) scaledP1.y,(int) scaledP2.x,(int) scaledP2.y);
	}

	public void drawCircle(Point c, double radius) {
		Point scaledC = scale(c);
		Point scaledR = scale(new Point(radius,radius));
		g.drawOval((int) (scaledC.x - scaledR.x), (int) (scaledC.y - scaledR.y), (int) (2*scaledR.x), (int) (2*scaledR.y));
	}
	

	public void fillRect(int x, int y, int i, int j) {
		Point topLeft = scale(new Point(x,y));
		Point size = scale(new Point(i,j));
		g.fillRect((int) topLeft.x,(int) topLeft.y, (int) Math.ceil(size.x),(int) Math.ceil(size.y));
	}


	public void drawPoint(Point p) {
		Point scaledPoint = scale(p);
		g.fillOval((int) scaledPoint.x, (int) scaledPoint.y, 2, 2);
	}
	
	
}
