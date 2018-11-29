/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.JSONObject;
import rdg.ChallengeRDG;

/**
 * Servlet implementation class ListChallenges
 */
@WebServlet("/ListChallenges")
public class ListChallenges extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ListChallenges() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(true).getAttribute("userID") == null) {
			request.setAttribute("message", "You need to be logged in in order to view challenges.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		List<ChallengeTDG> challenges = ChallengeTDG.findAll();
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("challenges", challenges);
		
		request.setAttribute("jsonObj", jsonObj.getJSON());
		request.getRequestDispatcher("WEB-INF/jsp/outputJSON.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
