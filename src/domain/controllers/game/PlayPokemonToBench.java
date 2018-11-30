/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.commands.game;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.GameDetails;
import models.enums.CardStatus;
import models.enums.CardType;
import rdg.CardRDG;
import rdg.PlayingCardRDG;

/**
 * Servlet implementation class PlayPokemonToBench
 */
@WebServlet("/PlayPokemonToBench")
public class PlayPokemonToBench extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
    public PlayPokemonToBench() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(true).getAttribute("userID") == null) {
			request.setAttribute("message", "You need to be logged in to play a card to the bench.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int userID = (int) request.getSession(true).getAttribute("userID");
		String gameIDStr = request.getParameter("game");
		String cardIDStr = request.getParameter("card");
		
		if(gameIDStr == null) {
			request.setAttribute("message", "Please provide a game ID.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		if(cardIDStr == null) {
			request.setAttribute("message", "Please provide a card ID.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int gameID = Integer.parseInt(gameIDStr);
		int cardID = Integer.parseInt(cardIDStr);
		
		GameDetails gameDetails = GameDetails.find(gameID);
		
		if(userID != gameDetails.getChallenger() && userID != gameDetails.getChallengee()) {
			request.setAttribute("message", "You are not part of this game. You can only play in the games you are part of.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		PlayingCardTDG playingCard = PlayingCardTDG.find(gameID, userID, cardID);
		CardTDG cardDetails = CardTDG.find(cardID);
		
		if(playingCard == null) {
			request.setAttribute("message", "Card not found. You cannot play this card to the bench.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		if(cardDetails.getCardType() != CardType.pokemon.getLetter()) {
			request.setAttribute("message", "You can only play pokemon cards to the bench.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		if(playingCard.getCardStatus() != CardStatus.hand.ordinal()) {
			request.setAttribute("message", "You can only put on the bench pokemon cards from your hand.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
//		if(playingCard.getCardStatus() == CardStatus.bench.ordinal()) {
//			request.setAttribute("message", "The pokemon is already on the bench.");
//			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
//			return;
//		}
		
		playingCard.setCardStatus(CardStatus.bench);
		playingCard.update();
		
		request.setAttribute("message", "Card succefully played to bench.");
		request.getRequestDispatcher("WEB-INF/jsp/success.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
