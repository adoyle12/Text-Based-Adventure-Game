package edu.ycp.cs320.teamproject.tbag.db.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.teamproject.tbag.db.model.ItemDb;
import edu.ycp.cs320.teamproject.tbag.model.Item;
import edu.ycp.cs320.teamproject.tbag.model.Location;

public class InitialData 
{
	
	public static List<Item> getInventory() throws IOException{
		List<Item> inventory = new ArrayList<Item>();
		ReadCSV readInventory = new ReadCSV("inventory.csv");
		try 
		{
			//auto-generated primary key for inventory table
			Integer itemID = 1; 
			while (true) 
			{
				List<String> tuple = readInventory.next();
				if (tuple == null) 
				{
					break;
				}
				Iterator<String> i = tuple.iterator();
				Item item = new Item();
				
				item.setItemID(itemID++); 
				item.setLocationID(Integer.parseInt(i.next()));
				item.setName(i.next());
				inventory.add(item);
			}
			System.out.println("Inventory loaded from CSV file");
			return inventory;
		} 
		finally 
		{
			readInventory.close();
		}
	}
	
	public static List<Location> getLocations() throws IOException{
		List<Location> locationList = new ArrayList<Location>();
		ReadCSV readLocations = new ReadCSV("locations.csv");
		try 
		{
			//auto-generated primary key for location table
			Integer locationID = 1; 
			while (true) 
			{
				List<String> tuple = readLocations.next();
				if (tuple == null) 
				{
					break;
				}
				Iterator<String> i = tuple.iterator();
				Location location = new Location(); 
				location.setLocationID(locationID++);
				location.setShortDescription(i.next());
				location.setLongDescription(i.next());
				locationList.add(location); 
				
				
			}
			System.out.println("Locations loaded from CSV file");
			return locationList;
		} 
		finally 
		{
			readLocations.close();
		}
	}

	
}