package edu.ycp.cs320.teamproject.tbag.model;

import java.util.ArrayList;
import java.util.Arrays;

import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;

/**
 * Model class for gameplay
 * @author adoyle 
 * Only the controller should be allowed to call the set methods
 *
 */
public class Gameplay {
	
	private String input; 		//input from the user
	private String output;		//initial story and story as a result of user input
	
	private ArrayList<String> story = new ArrayList<String>(Arrays.asList("A subtle shift in the current of air flowing through the room stirs your consciousness awake.\r\n" + 
			"		Another day has passed, enclosed at the center of the labyrinth, which, for centuries, has been the place you call home.\r\n" + 
			"		Slightly groggy, you stretch and yawn, causing a low rumbling growl to echo through the corridors that stretch beyond this main room.\r\n" + 
			"		As you come to your senses, you glance around the familiar surroundings, assessing that everything is in its proper place.","The wall behind you is painted with a mural of your father, King Minos, as he orders the craftsman,\r\n" + 
			"		Daedalus, and the craftsman's son, Icarus, to build the labyrinth you currently reside in, an ever-present\r\n" + 
			"		reminder of the one responsible for your enclosure. Home and a prison, merely because your father found you\r\n" + 
			"		monstrous when it was your father who angered the Gods in the first place! It was because of him that the\r\n" + 
			"		Gods made his wife, your mother, desire the majestic bull that sired you. You snort angrily, hot air bellowing\r\n" + 
			"		out from your nostrils as you continue to scan the room. Along the walls there are several shelves of literature\r\n" + 
			"		and ancient knowledge. Various treasures, miscellaneous garments, and other offerings lay strewn about, a\r\n" + 
			"		rather huge collection built from thousands of years of Athenians trying to appease you. A jewel embellished sword\r\n" + 
			"		catches your eye, it has the name \"Asterion\" intricately engraved into the blade. Memory stirs, \"Ah, yes, that was\r\n" + 
			"		my name, wasn't it?\" you reminisce with a self-deprecating chuckle.","Another glance around and you notice the amount of treasures that have been plundered from your hoard as\r\n" + 
			"		would-be heroes have taken much over time with their attempts at glory and honor. Several items of power are\r\n" + 
			"		missing, items you greatly desire returned. Suddenly, as thoughts of past heroes' trespassing and thievery\r\n" + 
			"		fill your mind, the desire to leave the labyrinth, take back what is rightfully yours, and enact revenge on\r\n" + 
			"		those that tormented you becomes too strong to ignore. A load roar escapes your throat, rattling the huge\r\n" + 
			"		wooden door at the front of the room. How will you manage to escape the labyrinth? What do you do first?"));
	
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
	
	public String getOutput() {
		return output;
	}
	
	public void setOutput(String output) {
		this.output = output;
	}
	
	public void setStory (String input) {
		this.story.add(input);
	}
	
	public ArrayList<String> getStory(){
		return story;
	}
}
