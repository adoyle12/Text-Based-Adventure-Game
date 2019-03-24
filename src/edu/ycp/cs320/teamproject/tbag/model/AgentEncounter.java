package edu.ycp.cs320.teamproject.tbag.model;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class AgentEncounter {
	//class to represent the enemy encounters
	
	//reference for array list of agents
	private ArrayList<Agent> agents;
	
	//reference for specific enemy
	private Agent enemy;
	
	//reference for specific npc
	private Agent npc;
	
	//creates agent encounters, but list is empty initially
	public AgentEncounter() {
		agents = new ArrayList<Agent>();
	}
	
	public Agent getEnemy(int index) {
		//gets a specific enemy
		if(index < 0 || index > 3) { //enemy needs to be in enemy array
			throw new NoSuchElementException();//if outside those bounds, there is no enemy
		}
		return agents.get(index);
	}
	
	public int getNumAgents() {
		//gets number of agents
		return agents.size();
	}

	public void populateEnemies() {
		//populates random enemy encounters
		AgentName[] allNames = AgentName.values();
		
		for(int i = 0; i < allNames.length; i++) {
			agents.add(new Agent(AgentName.getRandomEnemy(), 100, Item.getRandomItemDrop(), Location.getRandomLocation()));
		}
	}
	
	public void removeDeadEnemy() {
		//removes enemy from encounter list when enemy dies
		for(int i = 0; i < agents.size(); i++) {
			enemy = agents.get(i);
			if(enemy.getHealth() == 0) {
				agents.remove(i);
			}
		}
	}
}
