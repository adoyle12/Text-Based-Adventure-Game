package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class AgentTest {

	private Agent model;
	private String agentDescription;
	
	@Before
	public void setUp() {
		model = new Agent();
		model.setAgentID(1);
		model.setAgentDescription(agentDescription);
		model.setLocationID(2);
	}
	
	@Test
	public void testGetSetAgentID() {
		//test acquiring agent's ID
		assertEquals(1, model.getAgentID());
		
		model.setAgentID(2);
		assertEquals(2, model.getAgentID());
	}
	
	@Test
	public void testGetSetAgentDescription() {
		//test acquiring agent encounter description
		assertEquals(agentDescription, model.getAgentDescription());
	}
	
	@Test
	public void testGetSetLocationID() {
		//test acquiring agent's location
		assertEquals(2, model.getLocationID());
		
		model.setLocationID(3);
		assertEquals(3, model.getLocationID());
	}
}
