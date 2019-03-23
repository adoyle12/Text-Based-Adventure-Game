package edu.ycp.cs320.teamproject.tbag.controller;

import edu.ycp.cs320.teamproject.tbag.model.Numbers;
/**
 * Controller for Numbers
 * @author adoyle (this popped up automatically and I like it so it stays)
 *
 */
public class NumbersController 
{
	private Numbers model; 
	
	/**
	 * Set the model
	 * @param model the model to be set
	 */
	
	public void setModel(Numbers model)
	{
		this.model = model; 
	}
	
	/**
	 * Adding three numbers together. Have to set all three first and use setAdd().
	 * There might be some overkill here with all the setters and getters but I did it this way to make sure the JSPs could 
	 * call directly from the model 
	 * @param first the first number to add
	 * @param second the second number to add
	 * @param third the third number to add
	 */
	public void add(double first, double second, double third)
	{
		model.setFirst(first);
		model.setSecond(second);
		model.setThird(third);
		model.setAdd();
	}
	
	/**
	 * Multiplying two numbers together. Same overkill idea as add()
	 * @param first first number to multiply
	 * @param second second number to multiply
	 */
	public void multiply(double first, double second)
	{
		model.setFirst(first);
		model.setSecond(second);
		model.setMultiply();
	}

}
