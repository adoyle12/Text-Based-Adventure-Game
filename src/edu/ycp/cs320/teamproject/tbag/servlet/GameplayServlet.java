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
		
		String errorMessage = null;
		String username = null;
		
		try {
		// Get the current user from the session
		username = req.getSession().getAttribute("username").toString();
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		if(username == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		/*
		 * Initiate the controller and model
		 * Set the mode
		 * Must do this each time since they don't persist between POSTs
		 */
		controller = new GameplayController(username);
		Gameplay model = new Gameplay(); 
		controller.setModel(model);
		
		
		//Just need the output list to go to the model on doGet 
		controller.output();
		
		
		req.setAttribute("gameplay", model);
		
		
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/gameplay.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Gameplay Servlet: doPost");
		
		String errorMessage = null;
		
		// Get the current user from the session
		String username = req.getSession().getAttribute("username").toString();
		
		/*
		 * Initiate the controller and model
		 * Set the mode
		 * Must do this each time since they don't persist between POSTs
		 */		
		controller = new GameplayController(username);
		Gameplay model = new Gameplay(); 
		controller.setModel(model);

		// decode POSTed form parameters and dispatch to controller
		String input = req.getParameter("input");

		if(input == null || input.equals("")) {
			errorMessage = "Command Line Blank";
		}
		else {
			controller.gameLogic(input);
		}
		
		req.setAttribute("gameplay", model);
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);

		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/gameplay.jsp").forward(req, resp);
	}
}
