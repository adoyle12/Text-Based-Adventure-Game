package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
	private List<String> inventory;
	
	@Before
	public void setUp() 
	{
		model = new Gameplay();
		
		output = new ArrayList<String>();
		output2 = new ArrayList<String>();
		output3 = new ArrayList<String>();
		
		model.addInventory(inventory);
	
	}
	
	@Test
	public void testGetSetInput() 
	{
		model.setInput(input);
		assertEquals(model.getInput(), input);
	}
	
	@Test
	public void testGetSetAddOutput() {
		
		//test getting and setting output
		model.setOutput(output);
		assertEquals(output, model.getOutput());
		
		//test changing output to output2
		output2.add(input);
		
		model.setOutput(output2);
		assertEquals(output2, model.getOutput());
		assertTrue(output2.contains(input));
		
		//test adding output3 to output2
		output3.add(input2);
		
		model.addOutput(output3);
		assertEquals(output3, model.getOutput());
		assertTrue(output3.contains(input2));
	}
	
	@Test
	public void testGetAddInventory() {
		//test that inventory was added correctly
		assertEquals(inventory, model.getInventory());
	}
	
}
