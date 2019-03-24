package edu.ycp.cs320.teamproject.tbag.model;

public class GameState {
	//stores information about game state in progress.
	//None of the game logic is implemented in this class.
	//All game logic is implemented in the (@link GameEngine) class.
	//contains objects for player, agents, player inventory, game inventory
	
	//create reference for user
	private Player user;
	
	//create reference for player's inventory
	private Inventory playerInv;
	
	//reference for game inventory
	private Inventory gameInv;
	
	//create reference for an array of enemies for enemy encounters
	private AgentEncounter enemies;
	
	//create reference for an array of npcs for npc encounters
	private AgentEncounter npcs;

	//constructor creates all required objects but does not initialize them
	public GameState() {
		//create user state
		user = new Player(100, Location.RM0, 0);
		
		//create player's inventory
		playerInv = new Inventory();
		
		//create game inventory
		gameInv = new Inventory();
		
		//create array for enemy encounters
		enemies = new AgentEncounter();
		
		//create array for npc encounters
		npcs = new AgentEncounter();
	}

	//gets user data
	public Player getUser() {
		return user;
	}

	//gets player inventory data
	public Inventory getPlayerInventory() {
		return playerInv;
	}
	
	//gets game inventory data
	public Inventory getGameInventory() {
		return gameInv;
	}

	//gets enemy data
	public AgentEncounter getEnemies() {
		return enemies;
	}
	
	//get npc data
	public AgentEncounter getNPCs() {
		return npcs;
	}
}
