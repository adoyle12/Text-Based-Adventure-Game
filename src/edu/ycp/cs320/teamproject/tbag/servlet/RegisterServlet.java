package edu.ycp.cs320.teamproject.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import edu.ycp.cs320.teamproject.tbag.controller.RegisterController;

public class RegisterServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private RegisterController controller = null; 
	
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
		
		String errorMessage = null; 
		String successMessage = null;
		String username = null; 
		String password = null; 
		
		//Decode form parameters and dispatch to controller
		
		username = req.getParameter("username"); 
		password = req.getParameter("password"); 
		
		if (username == null || username.equals("") || password == null || password.equals(""))
		{
			errorMessage = "Please enter username and password"; 
		}
		else
		{
			controller = new RegisterController(); 
			
			//insert the user
			if (controller.insertUser(username, password))
			{
				successMessage = "User successfully added"; 
			}
			else
			{
				errorMessage = "User already exists"; 
			}
		}
		
		
		// Add parameters as request attributes
		req.setAttribute("username", username);
		req.setAttribute("password", password);
		
		// Add result objects as request attributes
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("successMessage", successMessage);

		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/register.jsp").forward(req, resp);
	}
}
