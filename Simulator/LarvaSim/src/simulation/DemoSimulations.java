package simulation;
import java.awt.Color;

import odours.SingleOdourSource;

import larvae.AlgoLarva;
import larvae.KernelLarvaParameters;
import larvae.KernelLarva;
import larvae.Larva_MinForward;
import larvae.Larva_NoBackswing;



public class DemoSimulations
{

	public Simulation odourtaxis;
	
	public Simulation multiOdour;

	public Simulation multiOdourMany;

	public Simulation celegans;

	public Simulation lowOdour;

	public Simulation regOdour;

	public Simulation pref;

	public Simulation cols;
	
	// NB - changed units from 1mm/10 to 1mm
	// May have missed some, if things look weird this is likely why
	public DemoSimulations()
	{
		
		KernelLarvaParameters defaultParams = new KernelLarvaParameters();
		KernelLarvaParameters celegansParams = new KernelLarvaParameters();
		celegansParams.castKernelDuration = 0;
		celegansParams.castProbBase = 0.2;
		
		SingleOdourSource defaultOdour = new SingleOdourSource(new Point(-10,0), 10,7,4,12);
		SingleOdourSource secondOdour = new SingleOdourSource(new Point(10,10), 23,10,9,19);
		secondOdour.setIntensity(0.7);
		SingleOdourSource lowOdourSource = new SingleOdourSource(new Point(-10,0), 10,7,4,12);
		lowOdourSource.setIntensity(0.01);
		SingleOdourSource prefOdourSource = new SingleOdourSource(new Point(-10,0), 10,7,4,12);
		
		odourtaxis = new Simulation(defaultParams, "Foo");
		odourtaxis.addOdour(defaultOdour);
		odourtaxis.addLarva(new Larva_NoBackswing(odourtaxis,defaultParams,new Point(20,15),Math.PI/2));
		
		multiOdour = new Simulation(defaultParams, "Foo");
		multiOdour.addOdour(defaultOdour);
		multiOdour.addOdour(secondOdour);
		multiOdour.addLarva(new Larva_NoBackswing(multiOdour,defaultParams,new Point(0,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI));
		
		multiOdourMany = new Simulation(defaultParams, "Foo");
		multiOdourMany.addOdour(defaultOdour);
		multiOdourMany.addOdour(secondOdour);
		for (int i = 0; i < 100; i++)
			{multiOdourMany.addLarva(new Larva_NoBackswing(multiOdourMany,defaultParams, new Point(0,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI));}
		
		
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
		pref.addWall(new CircleWall(new Point(0,0),20));
		for (int i = 0; i < 100; i++)
			{pref.addLarva(new Larva_MinForward(pref,defaultParams, new Point(0,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI));}
		
		cols = new Simulation(defaultParams, "Foo");
		cols.addOdour(defaultOdour);
		cols.addWall(new CircleWall(new Point(0,0),20));
		for (int i = 0; i < 2000; i++)
			{KernelLarva l = new AlgoLarva(cols,defaultParams, new Point(0,30*Math.random()-15),2*Math.PI*Math.random()-Math.PI);
			l.setColour(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
			cols.addLarva(l);}
	}
	
	
}
