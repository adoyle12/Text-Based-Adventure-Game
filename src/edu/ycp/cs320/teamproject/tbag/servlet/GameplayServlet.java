package edu.ycp.cs320.teamproject.tbag.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.teamproject.tbag.controller.GameplayController;
import edu.ycp.cs320.teamproject.tbag.model.Gameplay;

public class GameplayServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	private GameplayController controller = null;
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
		
		String errorMessage = null;
		
		/*
		 * Initiate the controller and model
		 * Set the mode
		 * Must do this each time since they don't persist between POSTs
		 */
		controller = new GameplayController();
		Gameplay model = new Gameplay(); 
		controller.setModel(model);
		

		// decode POSTed form parameters and dispatch to controller
		String input = getStringFromParameter(req.getParameter("input"));

		// Get the current user from the session
		String username = req.getSession().getAttribute("username").toString();
		
		// Do all the game logic
		controller.gameLogic(input, username);
		
		req.setAttribute("gameplay", model);
		
		// Add parameters as request attributes
		req.setAttribute("input", model.getInput());
		req.setAttribute("output", model.getOutput());
		req.setAttribute("size", model.getOutput().size());
		
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
