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

import models.enums.ChallengeStatus;
import rdg.ChallengeRDG;
import rdg.DeckRDG;
import rdg.UserRDG;

/**
 * Servlet implementation class ChallengePlayer
 */
@WebServlet("/ChallengePlayer")
public class ChallengePlayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ChallengePlayer() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(true).getAttribute("userID") == null) {
			request.setAttribute("message", "You need to be logged in to create a challenge.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int challengerID = (int) request.getSession(true).getAttribute("userID");
		int challengeeID = Integer.parseInt(request.getParameter("player"));
		
		if(challengerID == challengeeID) {
			request.setAttribute("message", "You cannot challenge yourself.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		UserTDG challengee = UserTDG.find(challengeeID);
		
		if(challengee == null) {
			request.setAttribute("message", "Invalid challengee ID.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		DeckTDG deck = DeckTDG.findByUserID(challengerID);
		
		if(deck == null) {
			request.setAttribute("message", "You need to upload a deck before issuing a challenge.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		ChallengeTDG challenge = new ChallengeTDG(ChallengeTDG.getNextChallengeID(), challengerID, challengeeID, deck.getId(), ChallengeStatus.open.ordinal());
		challenge.insert();
		
		request.setAttribute("message", "Challenge '" + challenge.getId() + "' has been successfully created");
		request.getRequestDispatcher("WEB-INF/jsp/success.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
