/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.models;

import org.dsrg.soenea.domain.DomainObject;

import com.google.gson.annotations.SerializedName;

public class Card extends DomainObject<Long> {
	private transient int deckID;
	private transient int deckPosition;
	
	@SerializedName("t")
	private char cardType;
	
	@SerializedName("n")
	private String cardName;
	
	public Card(long id, long version, int deckID, int deckPosition, char cardType, String cardName) {
		super(id, version);
		this.deckID = deckID;
		this.deckPosition = deckPosition;
		this.cardType = cardType;
		this.cardName = cardName;
	}

	public int getDeckID() {
		return deckID;
	}

	public void setDeckID(int deckID) {
		this.deckID = deckID;
	}

	public int getDeckPosition() {
		return deckPosition;
	}

	public void setDeckPosition(int deckPosition) {
		this.deckPosition = deckPosition;
	}

	public char getCardType() {
		return cardType;
	}

	public void setCardType(char cardType) {
		this.cardType = cardType;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	public String valuesAsString() {
		String output = "(" + getId() + ", " + getVersion() + ", " + deckID + ", " + deckPosition + ", '" + cardType + "', '" + cardName + "')";
		
		return output;
	}
}
