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

import models.GameDetails;
import rdg.CardRDG;
import rdg.PlayingCardRDG;

/**
 * Servlet implementation class DrawCard
 */
@WebServlet("/DrawCard")
public class DrawCard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
    public DrawCard() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(true).getAttribute("userID") == null) {
			request.setAttribute("message", "You need to be logged in to draw a card.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int userID = (int) request.getSession(true).getAttribute("userID");
		String gameIDStr = request.getParameter("game");
		
		if(gameIDStr == null) {
			request.setAttribute("message", "Please provide a game ID.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int gameID = Integer.parseInt(gameIDStr);
		GameDetails gameDetails = GameDetails.find(gameID);
		
		if(gameDetails == null) {
			request.setAttribute("message", "The game ID provided was not found.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		Integer deckID = gameDetails.getDeckForPlayer(userID);
		
		if(deckID == null) {
			request.setAttribute("message", "A deck was not found to draw a card. You must be part of the game to draw a card.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int deckPosition = PlayingCardTDG.findDrawCardPosition(gameID, userID);	
		CardTDG cardDrawn = CardTDG.findCardByDeckPosition(deckID, deckPosition);
		
		PlayingCardTDG playingCard = new PlayingCardTDG(gameID, userID, cardDrawn);
		playingCard.insert();
		
		request.setAttribute("message", "Card succefully drawn.");
		request.getRequestDispatcher("WEB-INF/jsp/success.jsp").forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
