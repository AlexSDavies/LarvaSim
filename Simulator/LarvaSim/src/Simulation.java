
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;


public class Simulation {

	private List <Drawable> drawObjects;
	private List <Updateable> updateObjects;

	private Larva larva;
	
	private List <LarvaData> larvaData;
	
	private SimWindow simWindow;
	private GraphWindow graphWindow;
		
	private ITrace2D trace1_1, trace1_2, trace2;
	private OdourSource odour;
	
	final double speed = 1000;
	
	final double timestep = 0.1;
	
	public Simulation(Parameters parameters,double runTime,String uniqueName)
	{
	
		initObjects(parameters);
		
		initWindows();

		// Sleep
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		runSimulation(runTime,uniqueName);
		
	}
	
	// *---------------------------*
	// Main simulation code
	private void runSimulation(double runTime,String uniqueName)
	{
	
		
		
		// -------------- Run simulation -----------------
		
		double t = 0;
		
		
		// Setup first data point so we can get rates of change in first step 
		LarvaData prevData = new LarvaData(t,larva); 
		
		while(simWindow.isShowing() && t < runTime){
			
			// Update all objects
			for (Updateable u : updateObjects){
				u.update();
			}

			// Point p = new Point(larva.getPos().mid.x,larva.getPos().mid.y);
			// drawObjects.add(p);
			
			// Store data
			LarvaData data = new LarvaData((double) t,larva,prevData);
			larvaData.add(data);
			prevData = data;
			
			// Update graphs, graphics
			graphWindow.updateGraphs(data);
			simWindow.simViewer.repaint();
			
			
			// Sleep
			try {
				Thread.sleep((int) (timestep*1000/speed));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Increment time
			t += timestep;
			
		}
		
		
		// -------------- Process data -----------------
		
		DataProcessing.doAll(larvaData,uniqueName);
		
		simWindow.dispose();
		graphWindow.dispose();
		
	}


	private void initWindows() {

		Runnable runSimWindow = new Runnable()
		{
			public void run()
			{
				simWindow = new SimWindow(drawObjects);
				// simWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				simWindow.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(runSimWindow);
		
		Runnable runGraphWindow= new Runnable()
		{
			public void run()
			{
				graphWindow = new GraphWindow();
				graphWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				graphWindow.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(runGraphWindow);
		
		
	}


	// Set up objects (larva, graphs etc.)
	private void initObjects(Parameters parameters)
	{
		
		// Create objects
		odour = new OdourSource(new Point(200,200));
		
		larva = new Larva(timestep,odour,parameters);
		
		larvaData = new ArrayList<LarvaData>();
		
		// Add objects to be drawn onto simViewer
		// NOTE: Order added is order drawn
		drawObjects = new ArrayList<Drawable>();
		drawObjects.add(odour);
		drawObjects.add(larva);
		
		// Add objects to list of items which need updated every cycle
		updateObjects = new ArrayList<Updateable>();
		updateObjects.add(larva);
	
		
		
	}

}
