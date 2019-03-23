package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.teamproject.tbag.model.GuessingGame;

public class GuessingGameTest 
{
	private GuessingGame model;
	
	@Before
	public void setUp() 
	{
		model = new GuessingGame();
	}
	
	@Test
	public void testSetMin() 
	{
		model.setMin(1);
		assertEquals(1, model.getMin());
	}
	
	@Test
	public void testSetMax() 
	{
		model.setMax(100);
		assertEquals(100, model.getMax());
	}
	
	@Test
	public void testGetGuess()
	{
		model.setMin(1);
		model.setMax(100);
		assertEquals(50, model.getGuess());
	}
	
	@Test
	public void testIsDone()
	{
		//False
		model.setMin(12);
		model.setMax(13);
		assertFalse(model.isDone());
		
		//True
		model.setMax(12);
		assertTrue(model.isDone());
	}
}
