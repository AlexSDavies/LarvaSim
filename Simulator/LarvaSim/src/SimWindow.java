import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;


public class SimWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	List <Drawable> drawObjects;
	
	public SimViewer simViewer;
	
	public Chart2D chart;
	
	
	// Constructor
	public SimWindow(List <Drawable> drawObjects)
	{
		this.drawObjects = drawObjects;
		initUI();
	}
	

	
	// Set up UI elements
	private void initUI()
	{
		
		setSize(500,500);
		setLocation(20, 20);
		
		simViewer = new SimViewer(drawObjects);
		getContentPane().add(simViewer,BorderLayout.CENTER);
		simViewer.setPreferredSize(new Dimension(100,100));

		
	}
	
	
	
}
