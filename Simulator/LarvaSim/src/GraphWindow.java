import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis.AxisTitle;
import info.monitorenter.gui.chart.axis.AAxis;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.ITrace2D;

/*
 * Deals with updating the graphs shown during the simulation
 * 
 * If you want to add new graphs, this is the place to do it:
 * 1. Add new Chart2D and one or more ITrace2D fields
 * 2. Add initialisation of graph and trace to initGraphs
 *   (Should mostly be able to copy paste from other graphs 
 * 3. Add a line to 'updateGraphs' to add the relevant data to the Trace
 *   (If this data is not in LarvaData, you probably want to add it there - see LarvaData)
 * 
 * (There are several graphs setup but not displayed - uncomment graphlist.add lines to see them
 * 
 */


public class GraphWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<Chart2D> graphList;
	
	// List of all graphs
	private Chart2D odourGraph;
	private Chart2D headAngleGraph;
	private Chart2D bearingGraph;
	private Chart2D perceptionGraph;
	private Chart2D turnProbGraph;
	private Chart2D headCastProbGraph;
	
	private ITrace2D headOdour;
	private ITrace2D midOdour;
	private ITrace2D headAngle;
	private ITrace2D bearing;
	private ITrace2D perception;
	private Trace2DLtd turnProb;
	private Trace2DLtd headCastProb;

		

	public GraphWindow()
	{
		
		initGraphs();
		
		initUI();
		
	}
	

	
	public void updateGraphs(LarvaData data){
		
		double t = data.getT();
				
		headOdour.addPoint(t, data.getOdourValueHead());
		midOdour.addPoint(t, data.getOdourValueMid());
		
		headAngle.addPoint(t,data.getHeadAngle());
		
		bearing.addPoint(t,data.getBearing());
		
		perception.addPoint(t,data.getPerception());
		
		turnProb.addPoint(t,data.getTurnProb());
		
		headCastProb.addPoint(t,data.getHeadCastProb());
		
	}
	


	private void initGraphs() {
		
		graphList = new ArrayList <Chart2D>();
		
		
		// Perception
		perception = new Trace2DLtd(100);
		perception.setName("Perception");
		perceptionGraph = new Chart2D();
		perceptionGraph.setPreferredSize(new Dimension(250, 200));
		perceptionGraph.addTrace(perception);	
		perceptionGraph.getAxisX(perception).setAxisTitle(new AxisTitle("Time"));	
		graphList.add(perceptionGraph);
		
		
		// Turn prob
		turnProbGraph = new Chart2D();
		turnProbGraph.setPreferredSize(new Dimension(250, 200));
		turnProb = new Trace2DLtd(100);
		turnProb.setName("Turn probability");
		turnProbGraph.addTrace(turnProb);
		graphList.add(turnProbGraph);
		
		
		// Head cast prob
		headCastProbGraph = new Chart2D();
		headCastProbGraph.setPreferredSize(new Dimension(250, 200));
		headCastProb = new Trace2DLtd(100);
		headCastProb.setName("Forward probability");
		headCastProbGraph.addTrace(headCastProb);
		graphList.add(headCastProbGraph);
		
		
		// Head angle
		headAngle = new Trace2DLtd(100);
		headAngle.setName("Head angle");
		headAngleGraph = new Chart2D();
		headAngleGraph.setPreferredSize(new Dimension(250, 200));
		headAngleGraph.addTrace(headAngle);
		//graphList.add(headAngleGraph);
		
		// Bearing
		bearingGraph = new Chart2D();
		bearingGraph.setPreferredSize(new Dimension(250, 200));
		bearing = new Trace2DLtd(100);
		bearingGraph.addTrace(bearing);
		//graphList.add(bearingGraph);
		
		// Odour
		headOdour = new Trace2DLtd(100);
		midOdour = new Trace2DLtd(100);
		headOdour.setColor(Color.RED);		
		odourGraph = new Chart2D();
		odourGraph.setPreferredSize(new Dimension(250, 200));
		odourGraph.addTrace(headOdour);
		odourGraph.addTrace(midOdour);
		//graphList.add(odourGraph);
		
		
	}


	private void initUI() {
		
		
		setSize(1100,250);
		setLocation(20, 600);
		setLayout(new FlowLayout());
		
		// Add all graphs
		for (Chart2D g : graphList)
		{
			getContentPane().add(g);
		}
		
	}
	
	
	
}
