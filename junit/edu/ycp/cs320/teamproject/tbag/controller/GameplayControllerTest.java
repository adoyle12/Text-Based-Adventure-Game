package edu.ycp.cs320.teamproject.tbag.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


import edu.ycp.cs320.teamproject.tbag.model.*;

public class GameplayControllerTest 
{
	private Gameplay model;
	private GameplayController controller; 
	
	@Before
	public void setUp() 
	{
		model = new Gameplay();
		controller = new GameplayController();
		
		controller.setModel(model);
	}
	
	@Test
	public void testInput()
	{
		controller.input("Test");
		assertEquals("Test", model.getInput()); 
	}
	
	@Test
	public void testMove() {
		// ???????????????????????????????????????????????
	}
}
