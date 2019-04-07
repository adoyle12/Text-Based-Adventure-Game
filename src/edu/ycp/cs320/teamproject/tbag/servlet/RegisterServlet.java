package edu.ycp.cs320.teamproject.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import edu.ycp.cs320.teamproject.tbag.controller.RegisterController;
import edu.ycp.cs320.teamproject.tbag.model.Register;

public class RegisterServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("register Servlet: doGet");
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("register Servlet: doPost");
		
		/*
		 * Initiate the controller and model
		 * Set the mode
		 * Must do this each time since they don't persist between POSTs
		 */
		RegisterController controller = new RegisterController();
		Register model = new Register(); 
		controller.setModel(model);

		// holds the error message text, if there is any
		String errorMessage = null;
		
		// Add parameters as request attributes
		req.setAttribute("register", model);
		
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);

		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/register.jsp").forward(req, resp);
	}
}
