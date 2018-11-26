/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rdg.UserRDG;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Logout() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(true).getAttribute("userID") == null) {
			request.setAttribute("message", "You were already logged out.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int id = (int) request.getSession(true).getAttribute("userID");
		UserTDG user = UserTDG.find(id);
		
		if(user == null) {
			request.setAttribute("message", "User ID was not found.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		request.getSession(true).invalidate();
		request.setAttribute("message", "User '" + user.getUsername() + "' has been successfully logged out.");
		request.getRequestDispatcher("WEB-INF/jsp/success.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
