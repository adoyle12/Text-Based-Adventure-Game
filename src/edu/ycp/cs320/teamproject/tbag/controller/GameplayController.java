package edu.ycp.cs320.teamproject.tbag.controller;


import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;
import edu.ycp.cs320.teamproject.tbag.model.Gameplay;
import edu.ycp.cs320.teamproject.tbag.model.Location;

/**
 * Controller for Gameplay
 * @author adoyle 
 *
 */
public class GameplayController 
{
	private IDatabase db;
	private Gameplay model; 
	private ArrayList<String> output;
	
	public GameplayController() {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
	
	public void setModel(Gameplay model)
	{
		this.model = model; 
	}

	public void input(String input)
	{
		db.addUserInput(input);
		model.setInput(input);
	}
	
	public void output(){
		output = db.getInputs();
		model.setOutput(output);
	}
	
	public void displayMap() {
		model.displayMap();
	}
	
	public int moveTo(String username, int direction) {
		int currentLocation = db.getUserLocation(username);
		int nextLocation = model.moveTo(currentLocation, direction);
		int moveLocation = db.setUserLocation(nextLocation, username);
		
		System.out.println("Moved to room #" + moveLocation);
		return nextLocation;
	}
}
