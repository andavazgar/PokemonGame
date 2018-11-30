/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.models;

import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObject;

public class Deck extends DomainObject<Long> {
	private static final int numberOfCardsNeeded = 40;
	private int userID;
	private List<Card> deckCards = new ArrayList<>();
	
	public Deck(long id, long version, int userID) {
		super(id, version);
		this.userID = userID;
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
