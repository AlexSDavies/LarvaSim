
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
	private List <Wall> walls;
	
	double speedup;

	private String saveName;
	
	public final double timestep = 0.1;
	
	private List<OdourSource> odourList;

	
	public Simulation(Parameters parameters, String uniqueName)
	{
		
		this.parameters = parameters;
		this.saveName = uniqueName;
		
		initObjects(parameters);
		
		initWindows();

		
		// Have to wait a second before running so the window has time to appear
		// (TODO: There should be a better way to do this)
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}
	
	
	// *---------------------------*
	// Main simulation code
	public void runSimulation(double runTime, double speedup)
	{
				
		// Check we have at least one larva
		if (larva == null)
		{
			System.out.println("No larva added to simulation! Exiting.");
			cleanup();
			return;
		}
		
		
		// -------------- Run simulation -----------------
		
		double t = 0;
		
		// Setup first data point so we can get rates of change in first step 
		LarvaData prevData = new LarvaData(0,larva); 
		
		System.out.println(simWindow);
		
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

			// Update graphs
			graphWindow.updateGraphs(data);
			
			// Update graphics
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
			if (larva != null)
				{DataProcessing.doAll(larvaData,parameters,simWindow.simViewer,saveName);}
		}
		
		cleanup();

	}

	private void cleanup()
	{
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
		
		try
			{SwingUtilities.invokeAndWait(runSimWindow);}
		catch (Exception e)
			{e.printStackTrace();}
		
		Runnable runGraphWindow= new Runnable()
		{
			public void run()
			{
				graphWindow = new GraphWindow();
				graphWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				graphWindow.setVisible(true);
			}
		};

		try
			{SwingUtilities.invokeAndWait(runGraphWindow);}
		catch (Exception e)
			{e.printStackTrace();}
		
	}

	public void addLarva(Point pos, double dir)
	{
		Larva l = new Larva(this,pos,dir);
		drawObjects.add(l);
		updateObjects.add(l);
		
		// If no larva currently tracked, track this one
		if (larva == null)
			{setTrackedLarva(l);}
		
	}
	
	// Sets which larva we want to track data for
	// (Defaults to first larva added)
	public void setTrackedLarva(Larva l)
	{
		this.larva = l;
	}
	
	public void addOdour(OdourSource odour)
	{
		odourList.add(odour);
		// Doesn't need added to drawable, as all odours in odourlist get drawn
	}
	
	public void addWall(Wall wall)
	{
		walls.add(wall);
		drawObjects.add(wall);
		odour.setRadius(wall.radius);
	}
	

	// Set up objects (larva, graphs etc.)
	private void initObjects(Parameters parameters)
	{
		
		// Create object lists
		// NB adding objects should now be done from outside, after creating Simulator object
		// (Using addLarva, addOdour, addWall)
		
		odourList = new ArrayList<OdourSource>();
		odour = new MultiOdourSource(odourList);
		
		//SingleOdourSource odour1 = new SingleOdourSource(new Point(-200,0),100,70,40,120);
		//odour1.setIntensity(1);
		//odourList.add(odour1);
		
		//SingleOdourSource odour2 = new SingleOdourSource(new Point(400,300),50,100,50,50);
		//odour2.setIntensity(0.5);
		//odourList.add(odour2);
		
		//OneDimOdourSource linOdour = new OneDimOdourSource(0, 1.0/300);
		//odourList.add(linOdour);
		
		//LinearOdourSource linSource = new LinearOdourSource(new Point(200,200), 1.0/200);
		//odourList.add(linSource);
		
		
		walls = new ArrayList<Wall>();
		
		//Wall wall = new Wall(new Point(0,0),200);
		
		//larva = new Larva(this);
		//larva.addWall(wall);
		
		larvaData = new ArrayList<LarvaData>();
		
		// Add objects to be drawn onto simViewer
		// NOTE: Order added is order drawn
		drawObjects = new ArrayList<Drawable>();
		drawObjects.add(odour);
		//drawObjects.add(wall);
		//drawObjects.add(larva);
		
		
		// Add objects to list of items which need updated every cycle
		updateObjects = new ArrayList<Updateable>();
		// updateObjects.add(larva);
	
		
	}


	public OdourSource getOdour()
	{
		return odour;
	}
	
	public Parameters getParameters()
	{
		return parameters;
	}

	public List<Wall> getWalls()
	{
		return walls;
	}
	
}
