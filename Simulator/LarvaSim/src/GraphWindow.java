import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.axis.AAxis;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.ITrace2D;


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
		
		// Odour
		headOdour = new Trace2DLtd(100);
		midOdour = new Trace2DLtd(100);
		headOdour.setColor(Color.RED);
				
		odourGraph = new Chart2D();
		odourGraph.setPreferredSize(new Dimension(250, 200));
			
		odourGraph.addTrace(headOdour);
		odourGraph.addTrace(midOdour);
		
		//graphList.add(odourGraph);
		
		// Perception
		perception = new Trace2DLtd(100);
		
		perceptionGraph = new Chart2D();
		perceptionGraph.setPreferredSize(new Dimension(250, 200));
		
		perceptionGraph.addTrace(perception);
		
		graphList.add(perceptionGraph);
		
		// Turn prob
		turnProbGraph = new Chart2D();
		turnProbGraph.setPreferredSize(new Dimension(250, 200));
		turnProb = new Trace2DLtd(100);
		turnProbGraph.addTrace(turnProb);
		graphList.add(turnProbGraph);
		
		// Head cast prob
		headCastProbGraph = new Chart2D();
		headCastProbGraph.setPreferredSize(new Dimension(250, 200));
		headCastProb = new Trace2DLtd(100);
		headCastProbGraph.addTrace(headCastProb);
		graphList.add(headCastProbGraph);
		
		// Head angle
		headAngle = new Trace2DLtd(100);
				
		headAngleGraph = new Chart2D();
		headAngleGraph.setPreferredSize(new Dimension(250, 200));
		
		headAngleGraph.addTrace(headAngle);
		
		graphList.add(headAngleGraph);
		
		// Bearing
		bearingGraph = new Chart2D();
		bearingGraph.setPreferredSize(new Dimension(250, 200));
		bearing = new Trace2DLtd(100);
		bearingGraph.addTrace(bearing);
		//graphList.add(bearingGraph);
		
		
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
