package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {
	
	private Item model;
	private String name;
	private String item_description;
	
	@Before
	public void setUp() {
		model = new Item();
		model.setItemDescription(item_description);
		model.setItemID(1);
		model.setLocationID(5);
		model.setName(name);
	}
	
	@Test
	public void testGetSetItemID() {
		//test that item ID was set correctly
		assertEquals(1, model.getItemID());
		
		//test that item ID was changed to 2
		model.setItemID(2);
		assertEquals(2, model.getItemID());
	}
	
	@Test
	public void testGetSetName() {
		//test that name was set correctly
		assertEquals(name, model.getName());
	}
	
	@Test
	public void testGetSetLocationID() {
		//test that location was set correctly
		assertEquals(5, model.getLocationID());
		
		//test that item location ID was changed to 7
		model.setLocationID(7);
		assertEquals(7, model.getLocationID());
	}
	
	@Test
	public void testGetSetItemDescription() {
		//test that item description was set correctly
		assertEquals(item_description, model.getItemDescription());
	}
}
