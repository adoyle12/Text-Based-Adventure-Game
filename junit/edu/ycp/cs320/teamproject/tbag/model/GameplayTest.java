package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GameplayTest {

	private Gameplay gameplay;
	private String input;
	
	@Before
	public void setUp() {
		input = "Generic command";
		gameplay = new Gameplay();
	}
	
	@Test
	public void testGetAndSetInput() throws Exception {
		//test getting and setting input
		gameplay.setInput(input);
		assertEquals(input, gameplay.getInput());
	}
}
