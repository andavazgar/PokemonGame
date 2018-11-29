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
import rdg.CardRDG;
import rdg.DeckRDG;

/**
 * Servlet implementation class ViewDeck
 */
@WebServlet("/ViewDeck")
public class ViewDeck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ViewDeck() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(true).getAttribute("userID") == null) {
			request.setAttribute("message", "You need to be logged in to view your deck.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int userID = (int) request.getSession(true).getAttribute("userID");
		DeckTDG deck = DeckTDG.findByUserID(userID);
		
		if(deck == null) {
			request.setAttribute("message", "You need to upload a deck in order to view it.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		else {
			JSONObject jsonObj = new JSONObject();
			JSONObject innerJSONObj = new JSONObject();
			List<CardTDG> deckCards = CardTDG.findCardsForUserID(userID);
			int deckID = deckCards.get(0).getDeckID();
			
			innerJSONObj.put("id", deckID);
			innerJSONObj.put("cards", deckCards);
			
			jsonObj.put("deck", innerJSONObj.getContents());
			
			request.setAttribute("jsonObj", jsonObj.getJSON());
			request.getRequestDispatcher("WEB-INF/jsp/outputJSON.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
