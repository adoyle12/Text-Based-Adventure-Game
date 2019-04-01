package edu.ycp.cs320.teamproject.tbag.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.teamproject.tbag.controller.GuessingGameController;
import edu.ycp.cs320.teamproject.tbag.model.Gameplay;
import edu.ycp.cs320.teamproject.tbag.model.GuessingGame;

public class GameplayControllerTest 
{
	private Gameplay gameplayModel;
	private GameplayController controller;
	
	@Before
	public void setUp() 
	{
		gameplayModel = new Gameplay();
		controller = new GameplayController();
		
		controller.setModel(gameplayModel);
	}
	
	@Test
	public void testNumberIsGreater() 
	{
		gameplayModel.setInput("Testing Input is Fun");
		controller.setInput(gameplayModel.getInput());
		assertEquals("Testing Input is Fun", controller.getInput());
	}
}
