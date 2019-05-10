 package edu.ycp.cs320.teamproject.tbag.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;


public class Gameplay 
{
	
	private String input; 		//input from the user
	private ArrayList<String> output; //game log
	private List<String> inventory; 
	
	public Gameplay() 
	{
		output = new ArrayList<String>();
		
	}
	
	public void setInput (String input)
	{
		this.input = input;
	}
	
	public String getInput()
	{
		return input; 
	}
	
	public ArrayList<String> getOutput() {
		return output;
	}
	
	public void setOutput(ArrayList<String> output) {
		this.output = output;
	}
	
	public void addOutput(ArrayList<String> output) {
		this.output = output;
	}

	public List<String> getInventory() 
	{
		return inventory;
	}

	public void addInventory(List<String> inventory) 
	{
		this.inventory = inventory;
	}

	
	
	
}
