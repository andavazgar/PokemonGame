/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.TempCard;
import rdg.CardRDG;
import rdg.DeckRDG;

/**
 * Servlet implementation class UploadDeck
 */
@WebServlet("/UploadDeck")
public class UploadDeck extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
    public UploadDeck() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deckString = request.getParameter("deck").trim();
		
		if(request.getSession(true).getAttribute("userID") == null) {
			request.setAttribute("message", "You need to be logged in to upload a deck");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		if(deckString == null) {
			request.setAttribute("message", "No deck was provided");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int userID = (int) request.getSession(true).getAttribute("userID");
		String[] cardEntries = deckString.split("\\r?\\n");
		
		if(cardEntries.length != 40) {
			request.setAttribute("message", "Invalid deck size. Decks must contain 40 cards");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		List<TempCard> tempCards = new ArrayList<>();
		Pattern p = Pattern.compile("(?<cardType>[e,p,t]) \"(?<cardName>[A-Z-a-z]+)\"");
		Matcher m;
		
		for(String cardEntry: cardEntries) {
			m = p.matcher(cardEntry);
			if (m.matches()) {
				tempCards.add(new TempCard(m.group("cardType").charAt(0), m.group("cardName")));
			}
		}
		
		if(tempCards.size() != DeckRDG.getNumberOfCardsNeeded()) {
			request.setAttribute("message", "The deck is not properly formatted.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		DeckRDG deck = new DeckRDG(DeckRDG.getNextDeckID(), userID);
		deck.insert();
		
		int deckID = deck.getId();
		List<CardRDG> cards = new ArrayList<>();
		int deckPosition = 1;
		
		for(TempCard tcard: tempCards) {
			cards.add(new CardRDG(CardRDG.getNextCardID(), deckID, deckPosition, tcard.getCardType(), tcard.getCardName()));
			
			deckPosition++;
		}
		
		deck.insertDeckCards(cards);
		
		request.setAttribute("message", "The deck was uploaded successfully.");
		request.getRequestDispatcher("WEB-INF/jsp/success.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
