package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GameplayTest 
{
	private Gameplay model;
	private ArrayList<String> output;
	private ArrayList<String> output2;
	private ArrayList<String> output3;
	private String input = "0";
	private String input2 = "1";
	
	@Before
	public void setUp() 
	{
		model = new Gameplay();
		
		output = new ArrayList<String>();
		output2 = new ArrayList<String>();
		output3 = new ArrayList<String>();
	
	}
	
	@Test
	public void testGetSetInput() 
	{
		model.setInput(input);
		assertEquals(model.getInput(), input);
	}
	
	@Test
	public void testGetSetAddOutput() {
		
		model.setOutput(output);
		assertEquals(output, model.getOutput());
		
		output2.add(input);
		
		model.setOutput(output2);
		assertEquals(output2, model.getOutput());
		
		output3.add(input2);
		
		model.addOutput(output3);
		assertTrue(model.getOutput().contains(input));
		assertTrue(model.getOutput().contains(input2));
		
	}
	
}
