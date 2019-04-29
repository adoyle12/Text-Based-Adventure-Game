package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class GameplayTest 
{
	private Gameplay model;
	
	@Before
	public void setUp() 
	{
		model = new Gameplay();
	}
	
	@Test
	public void testSetInput() 
	{
		model.setInput("input");
		assertEquals("input", model.getInput()); 
	}
	
}
