package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.*;

import larvae.AlgoLarvaParameters;
import larvae.Parameters;


public class ParameterPicker extends JFrame
{
	
	private static final long serialVersionUID = 1L;
	Parameters outputParameters;
	Field[] parameterFields;
	
	private JLabel[] parameterValueLabels;
	private JTextField[] parameterValueBoxes;
	
	// TODO: Make so moving away from box resets value (if not entered) 
	public ParameterPicker()
	{
		
		// Get info about parameters
		// TODO: Figure out how to specify which kind of parameters we want to create
		outputParameters = new AlgoLarvaParameters();
		
		parameterFields = outputParameters.getClass().getFields();
		int numParams = parameterFields.length;
		String[] parameterNames = new String[numParams];
		for(int i = 0; i < numParams; i++)
			{parameterNames[i] = parameterFields[i].getName();}
		
		// Setup window
		setSize(300,250);
		setLocation(20, 20);
		
		JPanel mainPanel = new JPanel();
		this.add(mainPanel);
		
	
		
		// Add boxes to display / change parameter value
		parameterValueBoxes = new JTextField[numParams];
		parameterValueLabels = new JLabel[numParams]; 
		
		for(int i = 0; i < numParams; i++)
		{
			
			parameterValueBoxes[i] = new JTextField();
			parameterValueBoxes[i].setColumns(10);
			try
				{parameterValueBoxes[i].setText(parameterFields[i].get(outputParameters).toString());}
			catch (Exception e)
				{e.printStackTrace();}
			
			mainPanel.add(parameterValueBoxes[i]);
		
			parameterValueLabels[i] = new JLabel();
			parameterValueLabels[i].setLabelFor(parameterValueBoxes[i]);
			parameterValueLabels[i].setText(parameterNames[i]);
			mainPanel.add(parameterValueLabels[i]);
			
			final int boxNum = i;
			
			ActionListener boxListener = new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
					{parameterBoxListener(event, boxNum);}
			};
			parameterValueBoxes[i].addActionListener(boxListener);
		}
		
	}
	
	
//	public void updateParameterDisplay()
//	{
//		Field selectedParameter = parameterFields[parameterList.getSelectedIndex()];
//		try
//			{parameterValueBox.setText(selectedParameter.get(outputParameters).toString());}
//		catch (Exception e)
//			{e.printStackTrace();}
//		
//	}
	
	public void parameterBoxListener(ActionEvent event, int boxNumber)
	{
		
		//  TODO: Match event to either enter or move away
		// if(event.getID() == ActionEvent.)
		
		// TODO: Validate numeric input
		double boxValue = Double.parseDouble(parameterValueBoxes[boxNumber].getText()); 
		
		Field selectedParameter = parameterFields[boxNumber];
		try
			{selectedParameter.set(outputParameters, boxValue);}
		catch (Exception e)
			{e.printStackTrace();}
		
	}
	
	
	public static Parameters getParameters()
	{
				
		// Open picker window
		Runnable runPickerWindow= new Runnable()
		{
			public void run()
			{
				ParameterPicker picker = new ParameterPicker();
				picker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				picker.setVisible(true);
			}
		};
		
		SwingUtilities.invokeLater(runPickerWindow);
		
		
		
		
		return null;
		
	}


	
	

}
