package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.teamproject.tbag.model.Numbers;

public class NumbersTest 
{
	private Numbers model;
	
	@Before
	public void setUp() 
	{
		model = new Numbers();
	}
	
	@Test
	public void testSetFirst() 
	{
		model.setFirst(1);
		assertEquals(1, model.getFirst(), 0.0001);
	}
	
	@Test
	public void testSetSecond()
	{
		model.setSecond(2);
		assertEquals(2, model.getSecond(), 0.0001);
	}
	
	@Test
	public void testSetThird()
	{
		model.setThird(3);
		assertEquals(3, model.getThird(), 0.0001); 
	}
	
	@Test
	public void testSetAdd()
	{	
		model.setFirst(1); 
		model.setSecond(2);
		model.setThird(3);
		model.setAdd();
		assertEquals(6, model.getAdd(), 0.0001);
	}
	
	@Test
	public void testSetMultiply()
	{
		model.setFirst(5);
		model.setSecond(5);
		model.setMultiply();
		assertEquals(25, model.getMultiply(), 0.0001); 
	}
	
}
