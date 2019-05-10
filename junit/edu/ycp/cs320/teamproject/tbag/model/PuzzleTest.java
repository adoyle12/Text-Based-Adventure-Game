package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PuzzleTest {
	
	private Puzzle model;
	
	@Before
	public void setUp() {
		model = new Puzzle();
		model.setLocationID(1);
		model.setName("puzzle");
	}
	
	@Test
	public void testGetSetName() {
		//test that name was set correctly
		assertEquals("puzzle", model.getName());
		
		//test that name was changed correctly
		model.setName("enigma");
		assertEquals("enigma", model.getName());
	}
	
	@Test
	public void testGetSetLocationID() {
		//test that location was set correctly
		assertEquals(1, model.getLocationID());
		
		//test that location was changed correctly
		model.setLocationID(5);
		assertEquals(5, model.getLocationID());
	}
}
