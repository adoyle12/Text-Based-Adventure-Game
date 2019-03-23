package edu.ycp.cs320.teamproject.tbag.model;

/**
 * Model class for Numbers
 * @author adoyle (again, automatic pop up - big fan) 
 *
 *Only the controller should be allowed to call the set methods
 *JSPs should be allowed to call the getAdd() and getMultiply() methods implicitly 
 */
public class Numbers 
{
	private double first; 
	private double second; 
	private double third; 
	private double aResult; 	//addition result
	private double mResult; 	//multiplication result
	
	
	
	public Numbers() 
	{
		
	}
	
	public void setFirst (double first)
	{
		this.first = first; 
	}
	public double getFirst()
	{
		return first; 
	}
	public void setSecond (double second)
	{
		this.second = second; 
	}
	public double getSecond()
	{
		return second; 
	}
	public void setThird(double third)
	{
		this.third = third; 
	}
	public double getThird()
	{
		return third; 
	}
	
	public void setAdd() 
	{
		aResult = first + second + third;
	}
	public double getAdd()
	{
		return aResult; 
	}
	
	public void setMultiply()
	{
		mResult = first * second; 
	}
	public double getMultiply()
	{
		return mResult; 
	}
}
