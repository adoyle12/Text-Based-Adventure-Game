/**
 * 
 */
/**
 * @author Vincent Maresca
 *
 */
package edu.ycp.cs320.teamproject.tbag.db.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.teamproject.tbag.db.persist.InitialData;
import edu.ycp.cs320.teamproject.tbag.db.model.ItemDb;

public class FakeDatabase implements IDatabase{
	
	private List<ItemDb> itemList;
	
	public FakeDatabase() {
		itemList = new ArrayList<ItemDb>();
		
		populateLists();
	}

	public void populateLists() {
		try {
			itemList.addAll(InitialData.getItem());
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}
	
	public List<ItemDb>getItemByName(String name){
		List<ItemDb> returnList = new ArrayList<ItemDb>();
		for(ItemDb item : itemList) {
			if(item.getName().equals(name)) {
				returnList.add(item);
			}
		}
		
		return returnList;
	}
}