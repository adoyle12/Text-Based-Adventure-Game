package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class InventoryTest {

	private Inventory inv;
	
	@Before
	public void setUp() {
		inv = new Inventory();
		inv.populate();
	}
	
	@Test
	public void testGetInventoryCap() {
		//test initial capacity of inventory
		assertEquals(2, inv.getInventoryCap());
	}

}
