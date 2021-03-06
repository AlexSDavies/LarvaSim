package simulation;

import gui.Drawable;
import gui.GraphWindow;
import gui.SimWindow;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import odours.MultiOdourSource;
import odours.OdourSource;

import larvae.Larva;
import larvae.LarvaData;
import larvae.Parameters;


// This class is responsible for running the simulation
// It keeps track of the other objects, runs the main loop, and initates data processing when everything's finished
// Note that at the moment only data for a single larva is saved (even if there are lots of larva in one simulation)
// Might update this in the future (although currently running one larva many times is exactly the same as running many larvae at once)

// Distances are in mm
// Times are in seconds

public class Simulation {

	private List <Drawable> drawObjects;
	private List <Updateable> updateObjects;
	
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

	private boolean showSimulation;
	
	double t;
	private Point boundry;
	
	public Simulation(Parameters parameters, String uniqueName)
	{
		
		this.saveName = uniqueName;
		
		initObjects();
		
		showSimulation = true;
		
		boundry = new Point(Double.MAX_VALUE,Double.MAX_VALUE);
		
	}
	
	
	// *---------------------------*
	// Main simulation loop
	public void runSimulation(double runTime, double speedup)
	{
				
		if(showSimulation)
		{
			initWindows();
		}
		
		// Check we have at least one larva
		if (larva == null)
		{
			System.out.println("No larva added to simulation! Exiting.");
			cleanup();
			return;
		}
		
		
		// -------------- Run simulation -----------------
		
		t = 0;
		
		// Setup first data point so we can get rates of change in first step 
		LarvaData prevData = new LarvaData(0,larva); 
				
		while(t < runTime){
			
			if (windowClosed())
				{break;}
			
			
			// Update all objects
			for (Updateable u : updateObjects)
			{
				u.update();
			}


			// This can be added to draw the larva's 'trail'
			// (might break on long runs, as it adds a lot of objects)
			// Point p = new Point(larva.getPos().mid.x,larva.getPos().mid.y);
			// drawObjects.add(p);

			// Store data
			LarvaData data = new LarvaData((double) t,larva,prevData);
			larvaData.add(data);
			prevData = data;
			
			// Terminate if boundry reached
			if (Math.abs(data.getHeadPos().x) > boundry.x || Math.abs(data.getHeadPos().y) > boundry.y)
				{break;}

			
			if (showSimulation)
			{
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
			}
			
			// Increment time
			t += timestep;
			
		}
		
		
		// -------------- Process data -----------------
		
		// Don't process data if window closed by user
		if (!windowClosed())
		{
			if (larva != null)
				{DataProcessing.doAll(larvaData,larva.getParameters(),simWindow,saveName);}
		}
		
		cleanup();

	}

	private void cleanup()
	{
		if(showSimulation)
		{
			simWindow.dispose();
			graphWindow.dispose();
		}
		
	}

	private void initWindows() {

		Runnable runSimWindow = new Runnable()
		{
			public void run()
			{
				simWindow = new SimWindow(drawObjects);
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

	
	public void addLarva(Larva statLarva)
	{
		drawObjects.add(statLarva);
		updateObjects.add(statLarva);
		
		// If no larva currently tracked, track this one
		if (larva == null)
			{setTrackedLarva(statLarva);}
	}
	
	// Sets which larva we want to track data for
	// (Defaults to first larva added)
	public void setTrackedLarva(Larva statLarva)
	{
		this.larva = statLarva;
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
		// odour.setDrawRadius(wall.radius);
	}
	

	// Set up objects (larva, graphs etc.)
	private void initObjects()
	{
		
		// Create object lists
		
		odourList = new ArrayList<OdourSource>();
		odour = new MultiOdourSource(odourList);
		
		walls = new ArrayList<Wall>();
		
		larvaData = new ArrayList<LarvaData>();
		
		// List of objects to be drawn onto simViewer
		// NOTE: Order added is order drawn
		drawObjects = new ArrayList<Drawable>();
		drawObjects.add(odour);
	
		// List of items which need updated every cycle
		updateObjects = new ArrayList<Updateable>();
		
	}

	public void setShowSimulation(boolean show)
	{
		this.showSimulation = show;
	}
	
	// Returns true if the simulation window has been closed by user
	// (If simulation is being displayed, always returns false  
	private boolean windowClosed()
	{
		if(showSimulation)
			{return !simWindow.isShowing();}
		else
			{return false;}
	}
	
	public OdourSource getOdour()
	{
		return odour;
	}
	

	public List<Wall> getWalls()
	{
		return walls;
	}


	public double getTime() {
		return t;
	}


	public void setBoundry(double x, double y)
	{
		boundry = new Point(x,y);		
	}
	
}
