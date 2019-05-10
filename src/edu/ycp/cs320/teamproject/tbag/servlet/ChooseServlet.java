package edu.ycp.cs320.teamproject.tbag.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.teamproject.tbag.controller.GameplayController;
import edu.ycp.cs320.teamproject.tbag.model.Gameplay;

public class ChooseServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	private GameplayController controller = null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Choose Servlet: doGet");	
		
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
		 * Must do this each time since they don't persist 
		 */
		controller = new GameplayController(username, false);
		Gameplay model = new Gameplay(); 
		controller.setModel(model);
		
		
	//	req.setAttribute("gameplay", model);
		
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/choose.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Choose Servlet: doPost");
		
		System.out.println("Button Click");
		// Get the current user from the session
		String username = req.getSession().getAttribute("username").toString();
		
		/*
		 * Initiate the controller and model
		 * Set the mode
		 * Must do this each time since they don't persist between POSTs
		 */		
		controller = new GameplayController(username, true);
		resp.sendRedirect(req.getContextPath() + "/gameplay");
		return; 
		
//		//Come back - country roads
//		Gameplay model = new Gameplay(); 
//		controller.setModel(model);
//
//		
//		req.setAttribute("gameplay", model);
//		
//
//		
//		// Forward to view to render the result HTML document
//		req.getRequestDispatcher("/_view/choose.jsp").forward(req, resp);
	}
}
