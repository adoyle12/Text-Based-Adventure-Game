package edu.ycp.cs320.teamproject.tbag.model;

import java.util.Random;

public enum AgentName {
	//for enemy names

	HERCULES,
	
	SQUALL,
	
	THESEUS,
	
	DANTE;
	
	public static AgentName getRandomEnemy() {
		//get random enemy
		Random rand = new Random();
		return values()[rand.nextInt(values().length)];
	}
	
}
