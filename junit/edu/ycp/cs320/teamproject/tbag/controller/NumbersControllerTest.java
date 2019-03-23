package edu.ycp.cs320.teamproject.tbag.controller;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.teamproject.tbag.controller.NumbersController;
import edu.ycp.cs320.teamproject.tbag.model.Numbers;

public class NumbersControllerTest 
{ 
	private Numbers model;
	private NumbersController controller;
	
	@Before
	public void setUp() 
	{
		model = new Numbers();
		controller = new NumbersController();
		
		controller.setModel(model);
	}
	
	@Test
	public void testAdd() 
	{
		controller.add(1, 1, 1);
		assertEquals(3.0, model.getAdd(), 0.0001);
	}
	
	@Test
	public void testMultiply()
	{
		controller.multiply(5, 5);
		assertEquals(25, model.getMultiply(), 0.0001); 
	}
	
}
