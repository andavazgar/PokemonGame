/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.models;

import java.util.ArrayList;
import java.util.List;

public class Deck {
	private static final int numberOfCardsNeeded = 40;
	private int id;
	private int version;
	private int userID;
	private List<Card> deckCards = new ArrayList<>();
	
	public Deck(int id, int version, int userID) {
		this.id = id;
		this.version = version;
		this.userID = userID;
	}
	
	public int getId() {
		return id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public List<Card> getDeckCards() {
		return deckCards;
	}
	
	public void addCardToDeck(Card card) {
		deckCards.add(card);
	}

	public static int getNumberOfCardsNeeded() {
		return numberOfCardsNeeded;
	}
}
