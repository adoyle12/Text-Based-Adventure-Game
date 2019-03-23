package edu.ycp.cs320.teamproject.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.teamproject.tbag.controller.NumbersController;
import edu.ycp.cs320.teamproject.tbag.model.Numbers;

public class AddNumbersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("AddNumbers Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/addNumbers.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("AddNumbers Servlet: doPost");
		

		/*
		 * Initiate the controller and model
		 * Set the mode
		 * Must do this each time since they don't persist between POSTs
		 */
		NumbersController controller = new NumbersController();
		Numbers model = new Numbers(); 
		controller.setModel(model);

		// holds the error message text, if there is any
		String errorMessage = null;

		
		// decode POSTed form parameters and dispatch to controller
		try 
		{
			Double first = getDoubleFromParameter(req.getParameter("first"));
			Double second = getDoubleFromParameter(req.getParameter("second"));
			Double third = getDoubleFromParameter(req.getParameter("third")); 

			// check for errors in the form data before using is in a calculation
			if (first == null || second == null || third == null) 
			{
				errorMessage = "Please specify three numbers";
			}
			// otherwise, data is good, do the calculation using controller
			else 
			{
				controller.add(first, second, third);
			}
		} 
		catch (NumberFormatException e) 
		{
			errorMessage = "Invalid double - Please specify three numbers";
		}
		
		// Add parameters as request attributes
		req.setAttribute("numbers", model);
		
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);

		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/addNumbers.jsp").forward(req, resp);
	}

	// gets double from the request with attribute named s
	private Double getDoubleFromParameter(String s) 
	{
		if (s == null || s.equals("")) 
		{
			return null;
		} 
		else 
		{
			return Double.parseDouble(s);
		}
	}
}
