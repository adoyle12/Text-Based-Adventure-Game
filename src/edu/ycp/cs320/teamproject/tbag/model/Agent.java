package edu.ycp.cs320.teamproject.tbag.model;

public class Agent {
	
	//reference for enemy name
	private AgentName name;
	
	//reference for enemy hp stat
	private int hp;
	
	//reference for experience given by defeating enemy
	//which is added to player's score
	private int exp;
	
	//reference for item dropped by enemy
	private Item itemDrop;
	
	//reference for enemy location
	private Location location;
	
	public Agent(AgentName name, int hp, Item itemDrop, Location location) {
		//initializes enemy state
		this.name = name;
		this.hp = hp;
		this.exp = 10;
		this.itemDrop = itemDrop;
		this.location = location;
	}
	
	public AgentName getName() {
		//get enemy's name
		return name;
	}
	
	public int getHealth() {
		//get enemy's hp stat
		return hp;
	}
	
	public void setHealth(int health) {
		hp = health;
	}
	
	public int getExp() {
		//get experience for defeating enemy
		return exp;
	}
	
	public Item getItemDrop() {
		//get item dropped by enemy
		return itemDrop;
	}
	
	public Location getLocation() {
		//get enemy's current location
		return location;
	}
	
	public void setLocation(Location loc) {
		//change enemy's current location
		location = loc;
	}
	
}
