
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


/*
 * 
 * Creating a new object of this class starts a new simulation
 * - Creates all the necessary windows for display etc.
 * - Creates objects (larva, odour source etc.)
 * - Starts simulation and calls the necessary objects every timestep
 * 
 */

public class Simulation {

	private List <Drawable> drawObjects;
	private List <Updateable> updateObjects;

	private Parameters parameters;
	
	private Larva larva;
	
	private List <LarvaData> larvaData;
	
	private SimWindow simWindow;
	private GraphWindow graphWindow;
	
	private OdourSource odour;
	
	double speedup;
	
	final double timestep = 0.1;
	
	public Simulation(Parameters parameters, double runTime, double speedup, String uniqueName)
	{
	
		
		this.parameters = parameters;
		this.speedup = speedup;
		
		initObjects(parameters);
		
		initWindows();


		
		// Have to wait a second before running so the window has time to appear
		// (TODO: There should be a better way to do this)
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

			// These lines can be added to draw the larva's 'trail'
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
				Thread.sleep((int) (timestep*1000/speedup));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Increment time
			t += timestep;
			
		}
		
		
		// -------------- Process data -----------------
		
		// Don't process data if window closed by user
		if (simWindow.isShowing())
		{
			DataProcessing.doAll(larvaData,parameters,simWindow.simViewer,uniqueName);
		}
		
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
		
		ArrayList<OdourSource> odourList = new ArrayList<OdourSource>();
		
		SingleOdourSource odour1 = new SingleOdourSource(new Point(-200,0),100,70,40,120);
		odour1.setIntensity(1);
		odourList.add(odour1);
		
		//SingleOdourSource odour2 = new SingleOdourSource(new Point(400,300),50,100,50,50);
		//odour2.setIntensity(0.5);
		//odourList.add(odour2);
		
		//OneDimOdourSource linOdour = new OneDimOdourSource(0, 1.0/300);
		//odourList.add(linOdour);
		
		//LinearOdourSource linSource = new LinearOdourSource(new Point(200,200), 1.0/200);
		//odourList.add(linSource);
		
		odour = new MultiOdourSource(odourList);
		
		Wall wall = new Wall(new Point(0,0),200);
		
		larva = new Larva(timestep,odour,parameters);
		larva.addWall(wall);
		
		larvaData = new ArrayList<LarvaData>();
		
		// Add objects to be drawn onto simViewer
		// NOTE: Order added is order drawn
		drawObjects = new ArrayList<Drawable>();
		drawObjects.add(odour);
		drawObjects.add(wall);
		drawObjects.add(larva);
		
		
		// Add objects to list of items which need updated every cycle
		updateObjects = new ArrayList<Updateable>();
		updateObjects.add(larva);
	
		
	}

}
