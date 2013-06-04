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
	int minX, maxX, minY, maxY;
	
	Graphics2D g;
	
	// SimViewer is given a list of objects to draw
	// Default arena size = 500x500
	public SimViewer(List <Drawable> drawObjects, int leftX, int rightX, int topY, int botY)
	{
		this.drawObjects = drawObjects;
		
		minX = leftX;
		maxX = rightX;
		minY = topY;
		maxY = botY;
		
		arenaSize = new Dimension(maxX-minX, maxY-minY);
		
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
		return new Point((p.x - minX)*xScale, (p.y-minY)*yScale);
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

	public void drawCircle(Point topLeft, Point botRight) {
		Point scaledTopLeft = scale(topLeft);
		Point scaledBotRight = scale(botRight);
		g.drawOval((int) scaledTopLeft.x, (int) scaledTopLeft.y, (int) (scaledBotRight.x - scaledTopLeft.x), (int) (scaledBotRight.y - scaledTopLeft.y));
	}
	

	public void fillRect(Point topLeft, Point botRight) {
		Point scaledTopLeft = scale(topLeft);
		Point scaledBotRight = scale(botRight);
		g.fillRect((int) Math.floor(scaledTopLeft.x), (int) Math.floor(scaledTopLeft.y), (int) Math.ceil(scaledBotRight.x - scaledTopLeft.x),(int) Math.ceil(scaledBotRight.y - scaledTopLeft.y));
	}


	public void drawPoint(Point p) {
		Point scaledPoint = scale(p);
		g.fillOval((int) scaledPoint.x, (int) scaledPoint.y, 1, 1);
	}
	
	
}
