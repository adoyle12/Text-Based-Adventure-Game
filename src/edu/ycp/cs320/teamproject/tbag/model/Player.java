package edu.ycp.cs320.teamproject.tbag.model;

public class Player {
	
	//create reference for health
	private int health;
	
	//create reference for player's location
	private Location location;
	
	//create reference for player's score
	private int score;
	
	public Player(int hp, Location loc, int sc) {
		//initialize player state
		health = hp;
		location = loc;
		score = sc;
	}
	
	public void setHealth(int hp) {
		//set player's starting health to 100
		health = hp;
	}
	
	public int getHealth() {
		//used to get health status of player
		return health;
	}
	
	public void setLocation(Location loc) {
		//set player's initial starting point
		location = loc;
	}
	
	public Location getLocation() {
		//used to get player's current location
		return location;
	}
	
	public void setScore(int sc) {
		//initialize score to 0 at start of game
		score = sc;
	}
	
	public int getScore() {
		//used to get player's current score
		return score;
	}
	
}
