package edu.ycp.cs320.teamproject.tbag.controller;

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;
import edu.ycp.cs320.teamproject.tbag.model.User;

public class UserController {
	private User model; 
	private IDatabase db = null;
	
	public UserController() {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
		
	public void credentials(String username, String password) {
		String jspPassword = password;
		model.setUsername(username);
		model.setJSPPassword(jspPassword);
		String dbPassword = db.findPasswordFromUsername(username);
		model.setDBPassword(dbPassword);
		model.setCredentials();
	}
		
		public void setModel(User model)
		{
			this.model = model; 
		}
		
}
