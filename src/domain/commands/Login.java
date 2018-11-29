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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");

		UserTDG u = UserTDG.find(user, pass);
		if(u == null) {
			request.setAttribute("message", "I do not recognize that username and password combination.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
		}
		else {
			int id = u.getId();
			request.getSession(true).setAttribute("userID", id);
			request.setAttribute("message", "User '" + u.getUsername() + "' has been successfully logged in.");
			request.getRequestDispatcher("WEB-INF/jsp/success.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}
}
