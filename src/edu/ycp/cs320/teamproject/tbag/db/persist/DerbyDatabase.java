package edu.ycp.cs320.teamproject.tbag.db.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.teamproject.tbag.db.persist.InitialData;
import edu.ycp.cs320.teamproject.tbag.db.persist.DBUtil;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.PersistenceException;
import edu.ycp.cs320.teamproject.tbag.model.Agent;
import edu.ycp.cs320.teamproject.tbag.model.Item;
import edu.ycp.cs320.teamproject.tbag.model.Location;


public class DerbyDatabase implements IDatabase{
	
	String userFilePath = null; 
	
	//sets the file path to switch to correct data base when registering, logging in, and clearing game.

	public void setUserFilePath(String userFilePath)
	{
		this.userFilePath = userFilePath; 
	}

	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;
	
	//returns user ID from user name
	@Override
	public Integer findUserIDFromUsername(final String username) 
	{
		setUserFilePath("users");
		return executeTransaction(new Transaction<Integer>() 
		{
			@Override
			public Integer execute(Connection conn) throws SQLException 
			{
				PreparedStatement stmt1 = null;
				
				ResultSet resultSet1 = null; 
				
				// for saving user ID
				Integer user_id = null;
				
				// try to retrieve user_ID (if it exists) from DB, for username passed into query
				try 
				{
					stmt1 = conn.prepareStatement(
							"select user_id from users " +
							"  where username = ? "
					);
					stmt1.setString(1, username);
					
					
					// execute the query, get the result
					resultSet1 = stmt1.executeQuery();

					
					// if user was found then update user_ID					
					if (resultSet1.next())
					{
						user_id = resultSet1.getInt(1);
						System.out.println("User found");	
						
					}
					else
					{
						System.out.println("User does not exist, try registering user");
						user_id = -1; 
					}
					
					return user_id; 
				
				}
				finally
				{
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
				}
			}
				
		});
	}
	
	//finds users password in the users table based on the user name entered by the user
	@Override
	public String findPasswordFromUsername(String username) 
	{
			setUserFilePath("users"); 
			return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				
				PreparedStatement getPassword = null;
				ResultSet resultSet = null;
				
				try {
					getPassword = conn.prepareStatement( 
							" select password from users " +
							" where users.username = ? " 
					);
					getPassword.setString(1, username);
					
					resultSet = getPassword.executeQuery();
					
					String password = null;
					
					if(resultSet.next()) {
						password = resultSet.getString(1);
						
					}
					
					return password;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(getPassword);
				}
				
			}
		});
	}
	
	//transaction that inserts new user into user's table
	//if user already exists then cancel the operation
	@Override
	public Integer insertUserIntoUsersTable(final String username, final String password) 
	{
		setUserFilePath("users"); 
		return executeTransaction(new Transaction<Integer>() 
		{
			@Override
			public Integer execute(Connection conn) throws SQLException 
			{
				
				PreparedStatement stmt1 = null; 
				PreparedStatement stmt2 = null; 	
				
				ResultSet resultSet2 = null; 
				
				// for saving user ID
				Integer user_id = -1;
				
				// try to retrieve user_ID (if it exists) from DB, for username passed into query
				try {
					
					user_id = findUserIDFromUsername(username);
					
					// if user was found then inform the user 					
					if (user_id > -1)
					{
						System.out.println("Username already taken");	
						user_id = -1;
					}
					else
					{
						System.out.println("Creating new user");
				
						// prepare SQL insert statement to add user to users table
						stmt1 = conn.prepareStatement(
								"insert into users (username, password) " +
								"  values(?, ?) "
						);
						stmt1.setString(1, username);
						stmt1.setString(2, password);
							
						// execute the update
						stmt1.executeUpdate();
							
						//Get the new user's id
						stmt2 = conn.prepareStatement(
								"select user_id from users " +
										"  where username = ? "
						);
						stmt2.setString(1, username);
						
						//execute query and get result
						resultSet2 = stmt2.executeQuery(); 
							
						//should only be one value 
						resultSet2.next(); 
						user_id = resultSet2.getInt(1); 
						System.out.println("New user added");	
							
						System.out.println("Creating new user's db"); 
							
						//Create the new users db
						DerbyDatabase db = new DerbyDatabase();
						db.setUserFilePath(username);
						db.createGameTables(username);
						db.loadInitialData();
										
					}
												
					return user_id;
				} 
				finally 
				{
					DBUtil.closeQuietly(stmt1);	
					DBUtil.closeQuietly(stmt2);	
					DBUtil.closeQuietly(resultSet2);	
				}
			}
		});
		
	}
		
	//Deletes user from users database but doesn't delete the actual databaase
	@Override
	public Integer deleteUserFromUsersTable(final int user_id) 
	{
		setUserFilePath("users");
		return executeTransaction(new Transaction<Integer>()
		{
			@Override
			public Integer execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null; 
				
				try
				{
					stmt1 = conn.prepareStatement(
							"delete from users where user_id = ?"
							); 
					
					stmt1.setInt(1, user_id);
					stmt1.executeUpdate(); 
					System.out.println("Deleted user with ID number: " + user_id);
					return user_id; 	
				}
				
				finally
				{
					DBUtil.closeQuietly(stmt1);
				}
			}
		});
	}

	// wrapper SQL transaction function that calls actual transaction function (which has retries)
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
		
	// SQL transaction function which retries the transaction MAX_ATTEMPTS times before failing
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
				
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
				
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}
	
	// DO NOT PUT THE DB IN THE SAME FOLDER AS YOUR PROJECT - that will cause conflicts later w/Git
	private Connection connect() throws SQLException 
	{
		String resourcePath = null; 
		String operatingSystem = System.getProperty("os.name");
		
		if(operatingSystem.equals("Windows 10")) {
			resourcePath = "jdbc:derby:C:/TBAG_DBs/" + userFilePath + "TBAG.db;create=true";
		} else if(operatingSystem.equals("Mac OS X")) {
			resourcePath = "jdbc:derby:/Users/adoyle/Desktop/" + userFilePath + "TBAG.db;create=true";
		} else {
			System.out.println("ACCESS DENIED: " + operatingSystem + " IS NOT A VALID OS SYSTEM");
		}
		 Connection conn = DriverManager.getConnection(resourcePath);

		
		// Set autocommit() to false to allow the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	// Creates the initial blank users table when Derby is run
	public void createUsersTable()
	{
		setUserFilePath("users"); 
		executeTransaction(new Transaction<Boolean>() 
		{
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement createUsersStmt = null;
				
				try {
					createUsersStmt = conn.prepareStatement(
						"create table users (" +
								"	user_id integer primary key " +
								"		generated always as identity (start with 1, increment by 1), "	+
								"	username varchar(20), " +
								"	password varchar(20)" +
								")"
					);
					createUsersStmt.executeUpdate();
					
					System.out.println("User table created");				
										
					return true;
				} finally {
					DBUtil.closeQuietly(createUsersStmt);
				}
			}
		});
	}
	
	//  creates the tables for each users individual game
	public void createGameTables(String username) 
	{
		
		executeTransaction(new Transaction<Boolean>() 
		{
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement createLocationsStmt = null;
				PreparedStatement createInventoryStmt = null;	
				PreparedStatement createInputsStmt = null;
				PreparedStatement createGameStateStmt = null; 
				PreparedStatement createAgentsStmt = null;
				
			
				try {
					
					createLocationsStmt = conn.prepareStatement(
							"	create table locations (" +
							"	location_id integer primary key " +
							"	generated always as identity (start with 1, increment by 1), "	+
							"	description_short varchar(4000), " +
							"	description_long varchar(8000), " +
							"	player_has_been integer," +
							" 	location_north integer, " +
							" 	location_south integer, " +
							" 	location_east integer, " +
							" 	location_west integer, " +
							" 	location_up integer, " +
							" 	location_down integer " +
							")"
	
							);
					
					createLocationsStmt.executeUpdate(); 
					
					createInventoryStmt = conn.prepareStatement(
							"	create table inventory ( " +
							"	item_id integer primary key " +
							"	generated always as identity (start with 1, increment by 1), " +
							"	item_location_id integer, " +
							"   item_name varchar(40), " +
							"	item_description varchar(8000) " +
							")"
					);	
					createInventoryStmt.executeUpdate();
					
					createInputsStmt = conn.prepareStatement(
							"   create table commands (" +
							"	command_id integer primary key " +
							" 	generated always as identity (start with 1, increment by 1),  " +
							" 	command varchar(10000) " +
							")"
					);
					createInputsStmt.executeUpdate();	
					
					createGameStateStmt = conn.prepareStatement(
							"	create table gameState (" +
							"	game_id integer primary key " +
							"	generated always as identity (start with 1, increment by 1), " +
							"	location_id integer, " +
							"	health integer, " +
							" 	score integer " +
							")"	
							);
					createGameStateStmt.executeUpdate();

					createAgentsStmt = conn.prepareStatement(
							"	create table agents (" +
							"	agent_id integer primary key " +
							"	generated always as identity (start with 1, increment by 1), " +
							"	agent_location_id integer, " +
							"	agent_description varchar(8000) " +
							")"
							);
					
					createAgentsStmt.executeUpdate();
					
					
					System.out.println("Game tables: locations, inventory, commands, gamestate, and agents created");				
										
					return true;
				} finally {
					DBUtil.closeQuietly(createLocationsStmt);
					DBUtil.closeQuietly(createInventoryStmt);
					DBUtil.closeQuietly(createInputsStmt);
					DBUtil.closeQuietly(createGameStateStmt);
					DBUtil.closeQuietly(createAgentsStmt);
				}
			}
		});
	}
	
	// loads data retrieved from CSV files into DB tables in batch mode
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Item> inventory;
				List<Location> locationList;
				List<Agent> agentList;
				
				try {
					inventory = InitialData.getInventory();
					locationList = InitialData.getLocations(); 
					agentList = InitialData.getAgents();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertItem = null;
				PreparedStatement insertLocation = null; 
				PreparedStatement insertAgents = null;
				PreparedStatement insertGameState = null; //This is the hard coded initial game state - don't need to read from CSV
				PreparedStatement insertOpeningMessage = null; // This is the hard coded opening message.

				try {
					// AD: populate locations first since location_id is foreign key in inventory table
					insertLocation = conn.prepareStatement("insert into locations (description_short, description_long, player_has_been, location_north, location_south, location_east, location_west, location_up, location_down) values (?, ?, ?, ?, ?, ?, ?, ?, ?)" );
					for (Location location : locationList)
					{
						insertLocation.setString(1, location.getShortDescription());
						insertLocation.setString(2, location.getLongDescription());
						insertLocation.setInt(3, location.getPlayerHasBeen());
						insertLocation.setInt(4, location.getLocationNorth());
						insertLocation.setInt(5, location.getLocationSouth());
						insertLocation.setInt(6, location.getLocationEast());
						insertLocation.setInt(7, location.getLocationWest());
						insertLocation.setInt(8, location.getLocationUp());
						insertLocation.setInt(9, location.getLocationDown());
						
						insertLocation.addBatch();
					}
					insertLocation.executeBatch(); 
					
					
					insertItem = conn.prepareStatement("insert into inventory (item_location_id, item_name, item_description) values (?, ?, ?)");
					for (Item item : inventory) 
					{
						// Auto generate itemID
						insertItem.setInt(1, item.getLocationID());
						insertItem.setString(2, item.getName());
						insertItem.setString(3, item.getItemDescription());
						insertItem.addBatch();
					}
					insertItem.executeBatch();
					
					//inserts agents
					insertAgents = conn.prepareStatement("insert into agents (agent_location_id, agent_description) values (?, ?)");
					for(Agent agent: agentList) {
						insertAgents.setInt(1, agent.getLocationID());
						insertAgents.setString(2, agent.getAgentDescription());
						insertAgents.addBatch();
					}
					insertAgents.executeBatch();
					
					
					insertGameState = conn.prepareStatement("insert into gameState (location_id, health, score) values (1, 100, 0)"); 
					insertGameState.executeUpdate(); 
					
					insertOpeningMessage = conn.prepareStatement("insert into commands (command) values (?)");
					insertOpeningMessage.setString(1, "You are the minotaur isolated at the end of the labyrinth. A goddess has taken pity on you and granted you the opportunity to escape. As you traverse the labyrinth, trying to find your way out, you will encounter heroes that you must defeat and puzzles that you must solve in order to gain your freedom.");
					insertOpeningMessage.executeUpdate();
					
					System.out.println("Tables populated");
					
					
					
				} finally {
					DBUtil.closeQuietly(insertLocation);	
					DBUtil.closeQuietly(insertItem);
					DBUtil.closeQuietly(insertAgents);
					DBUtil.closeQuietly(insertGameState);
					DBUtil.closeQuietly(insertOpeningMessage);
				}
				return true;
			}
		});
	}
	
	public void resetGame()
	{
		executeTransaction(new Transaction<Boolean>() 
		{
			@Override
			public Boolean execute(Connection conn) throws SQLException 
			{
				List<Item> inventory;
				
				try 
				{
					inventory = InitialData.getInventory();
				} 
				catch (IOException e) 
				{
					throw new SQLException("Couldn't read initial data", e);
				}
				PreparedStatement resetCommands = null; 		//Gotta delete them	
				PreparedStatement resetOpeningMessage = null; 
				PreparedStatement resetItemLocations = null;	//Set back to original location
				PreparedStatement resetGameState = null; 		//Update to original
				PreparedStatement resetPlayerHasBeen = null; 	//Set back to 0
				
				try
				{
					resetCommands = conn.prepareStatement("delete from commands");
					resetCommands.executeUpdate(); 
					
					resetOpeningMessage = conn.prepareStatement("insert into commands (command) values (?)"); 
					resetOpeningMessage.setString(1, "You are the minotaur isolated at the end of the labyrinth. A goddess has taken pity on you and granted you the opportunity to escape. As you traverse the labyrinth, trying to find your way out, you will encounter heroes that you must defeat and puzzles that you must solve in order to gain your freedom.");
					resetOpeningMessage.executeUpdate();
					
					resetItemLocations = conn.prepareStatement("update inventory set item_location_id = ? where item_id = ?");
					for (Item item : inventory)
					{
						resetItemLocations.setInt(1, item.getLocationID());
						resetItemLocations.setInt(2, item.getItemID());
						resetItemLocations.addBatch();
						
					}
					resetItemLocations.executeBatch(); 
							
					resetGameState = conn.prepareStatement("update gameState set location_id = 1, health = 100, score = 0");
					resetGameState.executeUpdate(); 
					
					resetPlayerHasBeen = conn.prepareStatement("update locations set player_has_been = 0 where player_has_been = 1"); 
					resetPlayerHasBeen.executeUpdate(); 
					
				}
				finally
				{
					DBUtil.closeQuietly(resetCommands); 
					DBUtil.closeQuietly(resetOpeningMessage);
					DBUtil.closeQuietly(resetItemLocations); 
					DBUtil.closeQuietly(resetGameState); 
					DBUtil.closeQuietly(resetPlayerHasBeen); 
				}
				return true; 
			}
			
		}); 
	}
	
	// The main method creates the users table
	public static void main(String[] args) throws IOException {
		System.out.println("Creating Users table...");
		DerbyDatabase db = new DerbyDatabase();
		db.createUsersTable();
		
		System.out.println("UsersDB successfully initialized!");
	}

	//Adds both user input and game responses to the commands table
	@Override
	public Boolean addToCommands(final String input) 
	{
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement addInput = null;
				
				try {
					addInput = conn.prepareStatement( 
							"insert into commands (command) " +
							"  values(?) " 
					);
					addInput.setString(1, input);
					
					addInput.executeUpdate();
				}
				finally {
					DBUtil.closeQuietly(addInput);
				}
				return null;
				
			}
		});
		
	}

	//Returns an array list with all of the items in the commands table to be displayed
	@Override
	public ArrayList<String> getCommands() {
		return executeTransaction(new Transaction<ArrayList<String>>() {
			@Override
			public ArrayList<String> execute(Connection conn) throws SQLException {
				PreparedStatement getCommands = null;
				ResultSet resultSet = null;
				
				try {
					getCommands = conn.prepareStatement( 
							" select * from commands "
					);
					
					ArrayList<String> results = new ArrayList<String>();
					
					resultSet = getCommands.executeQuery();
					
					
					Boolean found = false;
					String command = new String();
					
					while(resultSet.next()) {
						found = true;
						
						
						command = resultSet.getString(2);
						
						results.add(command);
					}
					
					if(!found) {
						System.out.println("No commands found!");
					}
					
					return results;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(getCommands);
				}
				
			}
		});
	}

	//Returns the users location from the gamestate table
	@Override
	public Integer getUserLocation() {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement getLocationID = null;
				ResultSet resultSet = null;
				
				try {
					getLocationID = conn.prepareStatement( 
							" select location_id " +
							" 	from gameState "
						
					);
					
					resultSet = getLocationID.executeQuery();
					
					Integer currentLocation = -1;
					
					if(resultSet.next()) {
						currentLocation = resultSet.getInt("location_id");
					}
					
					if(currentLocation == -1) {
						System.out.println("No Location Found? Where are you?");
					}
					
					return currentLocation;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(getLocationID);
				}
				
			}
		});
	}

	//Updates the users location in the gamestate table
	@Override
	public void setUserLocation(final int location) {
			executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement stmt1 = null;
					
					try {
						stmt1 = conn.prepareStatement( 
								" update gameState " +
								" 	set location_id = ? " 
							
						);
						stmt1.setInt(1, location);
						
						stmt1.executeUpdate();
						
					}
					finally {
						DBUtil.closeQuietly(stmt1);
					}
					return true;
					
				}
			});
		}
		
	//Get the user's current health 
	@Override
	public Integer getUserHealth() {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement getHealth = null;
				ResultSet resultSet = null;
				
				try {
					getHealth = conn.prepareStatement( 
							" select health " +
							" 	from gameState "
						
					);
					
					resultSet = getHealth.executeQuery();
					
					Integer currentHealth = -1;
					
					if(resultSet.next()) {
						currentHealth = resultSet.getInt("health");
					}
					else
					{
						System.out.println("No row found");
					}
					
					return currentHealth;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(getHealth);
				}
				
			}
		});
	}
	
	//Changes the users health by the number specified 
	@Override
	public void setUserHealth(final int healthPoints) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				
				try {
					stmt1 = conn.prepareStatement( 
							" update gameState " +
							" 	set health = health + ? " 
						
					);
					stmt1.setInt(1, healthPoints);
					
					stmt1.executeUpdate();
					
				}
				finally {
					DBUtil.closeQuietly(stmt1);
				}
				return true;
				
			}
		});
	}
	
	//Get the user's current score
	@Override
	public Integer getUserScore() {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement getScore = null;
				ResultSet resultSet = null;
				
				try {
					getScore = conn.prepareStatement( 
							" select score " +
							" 	from gameState "
						
					);
					
					resultSet = getScore.executeQuery();
					
					Integer currentScore = -1;
					
					if(resultSet.next()) {
						currentScore = resultSet.getInt("score");
					}
					else
					{
						System.out.println("No row found");
					}
					
					return currentScore;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(getScore);
				}
				
			}
		});
	}
	
	//Changes the users current score by the number specified 
	@Override
	public void setUserScore(final int scorePoints) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				
				try {
					stmt1 = conn.prepareStatement( 
							" update gameState " +
							" 	set score = score + ? " 
						
					);
					stmt1.setInt(1, scorePoints);
					
					stmt1.executeUpdate();
					
				}
				finally {
					DBUtil.closeQuietly(stmt1);
				}
				return true;
				
			}
		});
	}
	
	
	//Gets an agents location to compare with the users location during agent encounters
	@Override
	public Integer getAgentLocation(final int agent_id) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement getLocationID = null;
				ResultSet resultSet = null;
				
				try {
					getLocationID = conn.prepareStatement( 
							" select agents.agent_location_id " +
							" 	from agents " +
							"	where agent_id = ? "
						
					);
					getLocationID.setInt(1, agent_id);
					
					resultSet = getLocationID.executeQuery();
					
					Integer agentLocation = null;
					
					if(resultSet.next()) {
						agentLocation = resultSet.getInt("agent_location_id");
					}
					
					return agentLocation;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(getLocationID);
				}
				
			}
		});
	}
	
	//Returns a string that describes the agent
	@Override
	public String getAgentDescription(final int agent_location_id) {
		return executeTransaction(new Transaction<String>(){
			public String execute(Connection conn) throws SQLException {
				PreparedStatement agentDescription = null;
				ResultSet resultSet = null;
				try {
					agentDescription = conn.prepareStatement(
							"select agents.agent_description " +
							" from agents " +
							" where agent_location_id = ? "
					);
					agentDescription.setInt(1, agent_location_id);
				
					resultSet = agentDescription.executeQuery();
				
					String result = new String();
					
					if(resultSet.next()) {
						result = resultSet.getString("agent_description");
					}
				
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(agentDescription);
				}
			}
		});
	}
	
	//Returns the item description from inventory table
	@Override
	public ArrayList<String> getItemDescription(final int item_location) {
		return executeTransaction(new Transaction<ArrayList<String>>(){
			public ArrayList<String> execute(Connection conn) throws SQLException {
				PreparedStatement itemDescription = null;
				ResultSet resultSet = null;
				ArrayList<String> itemsInRoom = new ArrayList<String>();
				
				try {
					itemDescription = conn.prepareStatement(
							"select item_description " +
							" from inventory " +
							" where item_location_id = ? "
					);
					itemDescription.setInt(1, item_location);
				
					resultSet = itemDescription.executeQuery();
				
					String item;
					
					while(resultSet.next()) {
						item = resultSet.getString("item_description");
						itemsInRoom.add(item);
					}
				
					return itemsInRoom;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(itemDescription);
				}
			}
		});
	}
	
	//Used to set item location when picking up and dropping items
	@Override
	public void setItemLocation(final String itemName, final int location) {

		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement setItemLocation = null;
				
				try {
					setItemLocation = conn.prepareStatement( 
							" update inventory " +
							" 	set item_location_id = ? " +
							" 	where item_name = ? "
						
					);
					setItemLocation.setInt(1, location);
					setItemLocation.setString(2, itemName);
					
					setItemLocation.executeUpdate();
					
					return true;
				}
				finally {
					DBUtil.closeQuietly(setItemLocation);
				}
				
			}
		});
	}

	//Gets item location used when examining a room and solving puzzles
	@Override
	public Integer getItemLocationID(final String itemName) {
		return executeTransaction(new Transaction<Integer>() {
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement locationID = null;
				ResultSet resultSet = null;
			try {
				locationID = conn.prepareStatement(
						" select item_location_id " +
						"  from inventory " +
						"  where item_name = ? "
				);
				locationID.setString(1, itemName);
				
				resultSet = locationID.executeQuery();
				
				Integer itemLocationID = -1;
				
				if(resultSet.next()) {
					itemLocationID = resultSet.getInt("item_location_id");
				}
				if(itemLocationID == -1) {
					System.out.println("Location of item does not exist. Error; see getItemLocation method.");
				}
				
				return itemLocationID;
			}
			finally {
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(locationID);
			}
			}
		});
	}

	//Returns a list of all items in a given location used in the inventory query
	public List<Item> getItemsInLocation(final int locationID)
	{
		return executeTransaction(new Transaction<List<Item>>() 
		{
			public List<Item> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null; 
				ResultSet resultSet = null; 
				List<Item> items = new ArrayList<Item>(); 
				
				try 
				{
					stmt1 = conn.prepareStatement(
							"select * from inventory " +
							"where item_location_id = ? " 
							); 
					
					stmt1.setInt(1, locationID);
					
					resultSet = stmt1.executeQuery(); 
					
					while (resultSet.next())
					{
						Item item = new Item(); 
						item.setItemID(resultSet.getInt(1));
						item.setLocationID(resultSet.getInt(2));
						item.setName(resultSet.getString(3));
						item.setItemDescription(resultSet.getString(4)); 
						items.add(item); 
					}
					
					return items; 
				}
				
				finally 
				{
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt1);
				}
				
			}
		}); 
	}
	
	//Returns long location description from locations table
	@Override
	public String getLocationDescriptionLong(final int location_id) {
		return executeTransaction(new Transaction<String>(){
			public String execute(Connection conn) throws SQLException {
				PreparedStatement longDescription = null;
				ResultSet resultSet = null;
				try {
					longDescription = conn.prepareStatement(
							"select description_long " +
							" from locations " +
							" where location_id = ? "
					);
					longDescription.setInt(1, location_id);
				
					resultSet = longDescription.executeQuery();
				
					String result = new String();
					
					if(resultSet.next()) {
						result = resultSet.getString("description_long");
					}
				
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(longDescription);
				}
			}
		});
	}
	
	//Returns short location description from locations table
	@Override
	public String getLocationDescriptionShort(final int location_id) {
		return executeTransaction(new Transaction<String>(){
			public String execute(Connection conn) throws SQLException {
				PreparedStatement shortDescription = null;
				ResultSet resultSet = null;
				try {
					shortDescription = conn.prepareStatement(
							"select description_short " +
							" from locations " +
							" where location_id = ? "
					);
					shortDescription.setInt(1, location_id);
				
					resultSet = shortDescription.executeQuery();
				
					String result = new String();
					
					if(resultSet.next()) {
						result = resultSet.getString("description_short");
					}
				
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(shortDescription);
				}
			}
		});
	}

	//Returns the room location to the north
	@Override
	public Integer getLocationNorth(final int currentLocation) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement getLocationNorth = null;
				ResultSet resultSet = null;
				
				try {

					getLocationNorth = conn.prepareStatement( 
							" select location_north " +
							" 	from locations " +
							"	where location_id = ? "
						
					);
					
					
					getLocationNorth.setInt(1, currentLocation);
					
					resultSet = getLocationNorth.executeQuery();
					
					Integer nextLocation = -1;
					
					if(resultSet.next()) {
						nextLocation = resultSet.getInt("location_north");
					}
					
					if(nextLocation == -1) {
						System.out.println("Next room not found");
					}
					
					return nextLocation;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(getLocationNorth);
				}	
			}
		});
	}

	//Returns the room location to the south
	@Override
	public Integer getLocationSouth(final int currentLocation) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement getLocationSouth = null;
				ResultSet resultSet = null;
				
				try {
					getLocationSouth = conn.prepareStatement( 
							" select location_south " +
							" 	from locations " +
							"	where location_id = ? "
						
					);
					
					
					getLocationSouth.setInt(1, currentLocation);
					
					resultSet = getLocationSouth.executeQuery();
					
					Integer nextLocation = -1;
					
					if(resultSet.next()) {
						nextLocation = resultSet.getInt("location_south");
					}
					
					if(nextLocation == -1) {
						System.out.println("Next room not found");
					}
					
					return nextLocation;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(getLocationSouth);
				}	
			}
		});
	}

	//Returns the room location to the east
	@Override
	public Integer getLocationEast(final int currentLocation) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement getLocationEast = null;
				ResultSet resultSet = null;
				
				try {
					getLocationEast = conn.prepareStatement( 
							" select location_east " +
							" 	from locations " +
							"	where location_id = ? "
						
					);
					
					
					getLocationEast.setInt(1, currentLocation);
					
					resultSet = getLocationEast.executeQuery();
					
					Integer nextLocation = -1;
					
					if(resultSet.next()) {
						nextLocation = resultSet.getInt("location_east");
					}
					
					if(nextLocation == -1) {
						System.out.println("Next room not found");
					}
					
					return nextLocation;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(getLocationEast);
				}	
			}
		});
	}

	//Returns the room location to the west
	@Override
	public Integer getLocationWest(final int currentLocation) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement getLocationWest = null;
				ResultSet resultSet = null;
				
				try {
					getLocationWest = conn.prepareStatement( 
							" select location_west " +
							" 	from locations " +
							"	where location_id = ? "
						
					);
					
					
					getLocationWest.setInt(1, currentLocation);
					
					resultSet = getLocationWest.executeQuery();
					
					Integer nextLocation = -1;
					
					if(resultSet.next()) {
						nextLocation = resultSet.getInt("location_west");
					}
					
					if(nextLocation == -1) {
						System.out.println("Next room not found");
					}
					
					return nextLocation;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(getLocationWest);
				}	
			}
		});
	}
	
	//Returns the room location above
	@Override
	public Integer getLocationUp(final int currentLocation) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement getLocationUp = null;
				ResultSet resultSet = null;
				
				try {
					getLocationUp = conn.prepareStatement( 
							" select location_up " +
							" 	from locations " +
							"	where location_id = ? "
						
					);
					
					
					getLocationUp.setInt(1, currentLocation);
					
					resultSet = getLocationUp.executeQuery();
					
					Integer nextLocation = -1;
					
					if(resultSet.next()) {
						nextLocation = resultSet.getInt("location_up");
					}
					
					if(nextLocation == -1) {
						System.out.println("Next room not found");
					}
					
					return nextLocation;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(getLocationUp);
				}	
			}
		});
	}
	
	//Returns the room location below
	@Override
	public Integer getLocationDown(final int currentLocation) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement getLocationDown = null;
				ResultSet resultSet = null;
				
				try {
					getLocationDown = conn.prepareStatement( 
							" select location_down " +
							" 	from locations " +
							"	where location_id = ? "
						
					);
					
					
					getLocationDown.setInt(1, currentLocation);
					
					resultSet = getLocationDown.executeQuery();
					
					Integer nextLocation = -1;
					
					if(resultSet.next()) {
						nextLocation = resultSet.getInt("location_down");
					}
					
					if(nextLocation == -1) {
						System.out.println("Next room not found");
					}
					
					return nextLocation;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(getLocationDown);
				}	
			}
		});
	}

	//Checks locations table to see if the user has visited a room before. Used to decide what room description to show
	@Override
	public Integer getPlayerHasBeen(final int location) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement getPlayerHasBeen = null;
				ResultSet resultSet = null;
				
				try {
					getPlayerHasBeen = conn.prepareStatement( 
							" select player_has_been " +
							" 	from locations " +
							"	where location_id = ? "
						
					);
					
					
					getPlayerHasBeen.setInt(1, location);
					
					
					resultSet = getPlayerHasBeen.executeQuery();
					
					int hasBeen = -1;
					
					if(resultSet.next()) {
						hasBeen = resultSet.getInt(1);
					}
					
					if(hasBeen == -1) {
						System.out.println("The player never existed... Error in getPlayerHasBeen method");
					}
					
					return hasBeen;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(getPlayerHasBeen);
				}	
			}
		});
	}
 
	//Updates a rooms has been location when a use visits the room
	@Override
	public void setPlayerHasBeen(final int location) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement setPlayerHasBeen = null;
				ResultSet resultSet = null;
				
				try {
					setPlayerHasBeen = conn.prepareStatement( 
							" update locations " +
							" 	set player_has_been = 1 " +
							"	where location_id = ? "
					);
					
					setPlayerHasBeen.setInt(1, location);
					
					setPlayerHasBeen.executeUpdate();
					
					return true;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(setPlayerHasBeen);
				}	
			}
		});
	}
}
