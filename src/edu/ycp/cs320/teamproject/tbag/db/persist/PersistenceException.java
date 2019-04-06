package edu.ycp.cs320.teamproject.tbag.db.persist;

//________________________________"Borrowed" From CS320_Example_______________________________________

public class PersistenceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PersistenceException(String msg) {
		super(msg);
	}
	
	public PersistenceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
