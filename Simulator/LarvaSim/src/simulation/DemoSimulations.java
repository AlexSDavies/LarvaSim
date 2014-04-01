package simulation;
import java.awt.Color;

import odours.DataOdour;
import odours.SingleOdourSource;

import larvae.AlgoLarva;
import larvae.KernelLarvaParameters;
import larvae.KernelLarva;
import larvae.Larva_MinForward;
import larvae.Larva_NoBackswing;
import larvae.Larva_NoNormalisation;
import larvae.MinForwardParameters;



public class DemoSimulations
{

	public Simulation odourtaxis;

	public Simulation louisOdour;
	
	public Simulation multiOdour;

	public Simulation multiOdourMany;

	public Simulation celegans;

	public Simulation lowOdour;

	public Simulation regOdour;

	public Simulation pref;
	public Simulation prefNoNorm;

	public Simulation cols;
	
	public Simulation noCastBias;
	public Simulation noTurnBias;
	
	
	public static void main(String[] args)
	{
		DemoSimulations demo = new DemoSimulations();
	
		demo.odourtaxis.runSimulation(600000, 10);
		
		//demo.odourtaxis.runSimulation(600000, 10);

		// demo.louisOdour.runSimulation(6000000, 10);
		
		// demo.pref.runSimulation(600000, 10);
		
		// demo.prefNoNorm.runSimulation(600000, 10);
		
		// demo.multiOdourMany.runSimulation(600000, 10);
		
		// demo.cols.runSimulation(6000000, 10);
		
	}
	
	
	
	// NB - changed units from 1mm/10 to 1mm
	// May have missed some, if things look weird this is likely why
	public DemoSimulations()
	{
	
		
		MinForwardParameters defaultParams = new MinForwardParameters();
		KernelLarvaParameters celegansParams = new KernelLarvaParameters();
		celegansParams.castKernelDuration = 0;
		celegansParams.castProbBase = 0.2;
		
		SingleOdourSource defaultOdour = new SingleOdourSource(new Point(-20,0), 10,7,20,12);
		SingleOdourSource secondOdour = new SingleOdourSource(new Point(10,10), 23,10,9,19);
		secondOdour.setIntensity(0.7);
		SingleOdourSource lowOdourSource = new SingleOdourSource(new Point(-10,0), 10,7,4,12);
		lowOdourSource.setIntensity(0.01);
		SingleOdourSource prefOdourSource = new SingleOdourSource(new Point(-40,0), 30,30,30,30);
		DataOdour louisOdourSource = new DataOdour("D:/Uni/LarvaSim/Input Data/BarcelonaOdourNorm.csv");
		
		odourtaxis = new Simulation(defaultParams, "Foo");
		odourtaxis.addOdour(defaultOdour);
		odourtaxis.addLarva(new Larva_MinForward(odourtaxis,defaultParams,new Point(20,15),Math.PI/2));
		
		louisOdour = new Simulation(defaultParams, "Foo");
		louisOdour.addOdour(louisOdourSource);
		louisOdour.addLarva(new Larva_MinForward(louisOdour,defaultParams,new Point(10,-8),Math.PI));
		
		
		multiOdour = new Simulation(defaultParams, "Foo");
		multiOdour.addOdour(defaultOdour);
		multiOdour.addOdour(secondOdour);
		multiOdour.addLarva(new Larva_MinForward(multiOdour,defaultParams,new Point(0,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI));
		
		
		multiOdourMany = new Simulation(defaultParams, "Foo");
		multiOdourMany.addOdour(defaultOdour);
		multiOdourMany.addOdour(secondOdour);
		for (int i = 0; i < 100; i++)
			{multiOdourMany.addLarva(new Larva_MinForward(multiOdourMany,defaultParams, new Point(0,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI));}
		
		
		noCastBias = new Simulation(defaultParams,  "Foo");
		noCastBias.addOdour(defaultOdour);
		MinForwardParameters noCastParams = new MinForwardParameters();
		((KernelLarvaParameters) noCastParams).castKernelEndVal = 0;
		((KernelLarvaParameters) noCastParams).castProbBase = 2.3;
		for (int i = 0; i < 100; i++)
			{noCastBias.addLarva(new Larva_MinForward(noCastBias,noCastParams, new Point(30*Math.random()-15,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI));}
		
		
		noTurnBias = new Simulation(defaultParams,  "Foo");
		noTurnBias.addOdour(defaultOdour);
		MinForwardParameters noTurnParams = new MinForwardParameters();
		((KernelLarvaParameters) noTurnParams).turnKernelEndVal = 0;
		((KernelLarvaParameters) noTurnParams).turnKernelStartVal = 0;
		((KernelLarvaParameters) noTurnParams).turnProbBase = 0.2;
		for (int i = 0; i < 100; i++)
			{noTurnBias.addLarva(new Larva_MinForward(noTurnBias,noTurnParams, new Point(30*Math.random()-15,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI));}
		
		
		
		celegans = new Simulation(defaultParams,"Foo");
		celegans.addOdour(defaultOdour);
		for (int i = 0; i < 100; i++)
		{
			celegans.addLarva(new Larva_NoBackswing(celegans,defaultParams, new Point(0,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI));
			KernelLarva c = new AlgoLarva(celegans,celegansParams, new Point(0,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI);
			c.setColour(Color.WHITE);
			celegans.addLarva(c);
		}
		
		regOdour = new Simulation(defaultParams, "Foo");
		regOdour.addOdour(defaultOdour);
		for (int i = 0; i < 100; i++)
			{regOdour.addLarva(new Larva_NoBackswing(regOdour,defaultParams, new Point(0,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI));}
		
		lowOdour = new Simulation(defaultParams, "Foo");
		lowOdour.addOdour(lowOdourSource);
		for (int i = 0; i < 100; i++)
			{lowOdour.addLarva(new Larva_NoBackswing(lowOdour,defaultParams, new Point(0,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI));}
		
		
		pref = new Simulation(defaultParams, "Foo");
		pref.addOdour(prefOdourSource);
		pref.addWall(new CircleWall(new Point(0,0),50));
		for (int i = 0; i < 100; i++)
			{pref.addLarva(new Larva_MinForward(pref,defaultParams, new Point(15*Math.random()-7,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI));}
		pref.getOdour().setDrawRadius(50);

		prefNoNorm = new Simulation(defaultParams, "Foo");
		prefNoNorm.addOdour(prefOdourSource);
		prefNoNorm.addWall(new CircleWall(new Point(0,0),50));
		for (int i = 0; i < 100; i++)
			{prefNoNorm.addLarva(new Larva_NoNormalisation(prefNoNorm,defaultParams, new Point(15*Math.random()-7,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI));}
		prefNoNorm.getOdour().setDrawRadius(50);
		
		cols = new Simulation(defaultParams, "Foo");
		cols.addOdour(prefOdourSource);
		cols.addWall(new CircleWall(new Point(0,0),50));
		cols.getOdour().setDrawRadius(50);
		for (int i = 0; i < 2000; i++)
			{KernelLarva l = new Larva_NoNormalisation(cols,defaultParams, new Point(0,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI);
			l.setColour(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
			cols.addLarva(l);}
	}
	
	
}
