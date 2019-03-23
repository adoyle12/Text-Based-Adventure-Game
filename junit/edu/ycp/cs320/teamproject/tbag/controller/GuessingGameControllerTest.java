package edu.ycp.cs320.teamproject.tbag.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.teamproject.tbag.controller.GuessingGameController;
import edu.ycp.cs320.teamproject.tbag.model.GuessingGame;

public class GuessingGameControllerTest 
{
	private GuessingGame model;
	private GuessingGameController controller;
	
	@Before
	public void setUp() 
	{
		model = new GuessingGame();
		controller = new GuessingGameController();
		
		model.setMin(1);
		model.setMax(100);
		
		controller.setModel(model);
	}
	
	@Test
	public void testNumberIsGreater() 
	{
		int currentGuess = model.getGuess();
		controller.setNumberIsGreaterThanGuess();
		assertTrue(model.getGuess() > currentGuess);
	}
	
	@Test
	public void testNumberIsLess()
	{
		int currentGuess = model.getGuess(); 
		controller.setNumberIsLessThanGuess();
		assertTrue(model.getGuess() < currentGuess); 
	}
	
	@Test
	public void testStartGame()
	{
		controller.startGame();
		assertEquals(1, model.getMin());
		assertEquals(100, model.getMax()); 
	}
	
	@Test
	public void testNumberFound()
	{
		int currentGuess = model.getGuess(); 
		controller.setNumberFound();
		assertTrue(currentGuess == model.getMax());
		assertTrue(currentGuess == model.getMin()); 
	}
}
