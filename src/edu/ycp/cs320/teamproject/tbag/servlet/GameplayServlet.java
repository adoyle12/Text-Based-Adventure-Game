package edu.ycp.cs320.teamproject.tbag.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.teamproject.tbag.controller.GameplayController;
import edu.ycp.cs320.teamproject.tbag.model.Gameplay;

public class GameplayServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	ArrayList<String> story = new ArrayList<String>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Gameplay Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/gameplay.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Gameplay Servlet: doPost");
		

		/*
		 * Initiate the controller and model
		 * Set the mode
		 * Must do this each time since they don't persist between POSTs
		 */
		GameplayController controller = new GameplayController();
		Gameplay model = new Gameplay(); 
		controller.setModel(model);
		
		
		
		// holds the error message text, if there is any
		String errorMessage = null;

		
		// decode POSTed form parameters and dispatch to controller
		try 
		{
			String input = getStringFromParameter(req.getParameter("input"));

			// check for errors in the form data before using is in a calculation
			if (input == null) 
			{
				errorMessage = "Please select a command";
			}
			// otherwise, data is good, do the calculation using controller
			else 
			{
				controller.input(input);
			}
		} 
		catch (Exception e) 
		{
			errorMessage = "No good";		//AD: come back to this
		}
		
		// Add parameters as request attributes
		req.setAttribute("input", model);
		
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);

		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/gameplay.jsp").forward(req, resp);
	}

	// gets double from the request with attribute named s
	private String getStringFromParameter(String s) 
	{
		if (s == null || s.equals("")) 
		{
			return null;
		} 
		else 
		{
			return s; 
		}
	}
}
