/**
 * 
 */
/**
 * @author Vincent Maresca
 *
 */
package edu.ycp.cs320.teamproject.tbag.db.persist;

import java.util.List;

import edu.ycp.cs320.teamproject.tbag.db.model.ItemDb;

public interface IDatabase{
	public List<ItemDb>getItemByName(String name);
}

