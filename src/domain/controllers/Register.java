/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.commands;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rdg.UserRDG;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Register() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		
		if(user==null || user.isEmpty() || pass==null || pass.isEmpty()) {
			request.setAttribute("message", "Please enter both a username and a password.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
		}
		else {
			UserTDG u = UserTDG.find(user);
			
			if(u != null) {
				request.setAttribute("message", "That user has already registered.");
				request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
				}
			else {
				u = new UserTDG(UserTDG.getNextUserID(), user, pass);
				u.insert();
				int id = u.getId();
				request.getSession(true).setAttribute("userID", id);
				request.setAttribute("message", "User '" + user + "' has been successfully registered.");
				request.getRequestDispatcher("WEB-INF/jsp/success.jsp").forward(request, response);
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
