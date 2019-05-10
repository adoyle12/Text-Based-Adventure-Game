package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DescriptionTest {
	
	private Description model;
	private int descriptionID;
	private String shortDescription;
	private String longDescription;
	
	@Before
	public void setUp() {
		model = new Description();
		model.setDescriptionID(descriptionID);
		model.setShort(shortDescription);
		model.setLong(longDescription);
	}
	
	@Test
	public void testGetSetDescriptionID() {
		//test that descriptionID was set correctly
		assertEquals(descriptionID, model.getDescriptionID());
	}
	
	@Test
	public void testGetSetShortLongDescription() {
		//test that short description was set correctly
		assertEquals(shortDescription, model.getShort());
		//test that long description was set correctly
		assertEquals(longDescription, model.getLong());
	}
}
