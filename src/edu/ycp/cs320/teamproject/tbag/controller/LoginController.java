package edu.ycp.cs320.teamproject.tbag.controller;

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;
import edu.ycp.cs320.teamproject.tbag.model.Login;

public class LoginController {
	private Login model; 
	private IDatabase db = null;
	
	public LoginController() {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
		
	public void credentials(String username, String password) {
		String dbPassword = db.findPasswordFromUsername(username);
		String jspPassword = password;
		model.setJSPPassword(jspPassword);
		model.setDBPassword(dbPassword);
		model.setCredentials();
	}
		
		public void setModel(Login model)
		{
			this.model = model; 
		}
		
}
