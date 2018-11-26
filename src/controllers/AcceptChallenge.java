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

import models.enums.ChallengeStatus;
import models.enums.GameStatus;
import models.enums.PlayerStatus;
import rdg.ChallengeRDG;
import rdg.DeckRDG;
import rdg.GameRDG;

/**
 * Servlet implementation class AcceptChallenge
 */
@WebServlet("/AcceptChallenge")
public class AcceptChallenge extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
    public AcceptChallenge() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(true).getAttribute("userID") == null) {
			request.setAttribute("message", "You need to be logged in to create a challenge.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int userID = (int) request.getSession(true).getAttribute("userID");
		int challengeID = Integer.parseInt(request.getParameter("challenge"));
		ChallengeTDG challenge = ChallengeTDG.find(challengeID);
		
		if(challenge == null) {
			request.setAttribute("message", "The challenge provided was not found.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		if(challenge.getChallengee() != userID) {
			request.setAttribute("message", "You cannot accept someone else's challenge.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		if(challenge.getChallengeStatus() != ChallengeStatus.open) {
			request.setAttribute("message", "The challenge is not open. Therefore, it cannot be accepted.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		DeckTDG deck = DeckTDG.findByUserID(userID);
		
		if(deck == null) {
			request.setAttribute("message", "You need to upload a deck before accepting a challenge.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		challenge.setChallengeeDeck(deck.getId());
		challenge.setStatus(ChallengeStatus.accepted);
		challenge.update();
		
		GameTDG game = new GameTDG(GameTDG.getNextGameID(), challenge.getId(), PlayerStatus.playing.ordinal(), PlayerStatus.playing.ordinal(), GameStatus.inProgress.ordinal());
		game.insert();
		
		request.setAttribute("message", "Challenge was successfully accepted. A new game was created with id: " + game.getId());
		request.getRequestDispatcher("WEB-INF/jsp/success.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
