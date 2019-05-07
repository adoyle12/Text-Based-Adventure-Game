package edu.ycp.cs320.teamproject.tbag.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


import edu.ycp.cs320.teamproject.tbag.model.*;

public class GameplayControllerTest 
{
	private Gameplay model;
	private GameplayController controller; 
	private User user;
	
	@Before
	public void setUp() 
	{
		model = new Gameplay();
		user = new User();
		user.setUsername("john");
		
		controller = new GameplayController("john");
		
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
