 package edu.ycp.cs320.teamproject.tbag.model;

import java.util.ArrayList;
import java.util.Arrays;

import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;


public class Gameplay {
	
	private String input; 		//input from the user
	private ArrayList<String> output;//initial story and story as a result of user input
	
	public Gameplay() 
	{
		
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
	
	public void setOutput(ArrayList<String> output2) {
		this.output = output2;
		for(int i=0; i<output2.size(); i++) {
			System.out.println(output2.get(i));
		}
	}
}
