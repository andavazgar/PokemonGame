/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domainModels;

import com.google.gson.annotations.SerializedName;

public class Card {
	private int id;
	private int version;
	private transient int deckID;
	private transient int deckPosition;
	
	@SerializedName("t")
	private char cardType;
	
	@SerializedName("n")
	private String cardName;
	
	public Card(int id, int version, int deckID, int deckPosition, char cardType, String cardName) {
		this.id = id;
		this.version = version;
		this.deckID = deckID;
		this.deckPosition = deckPosition;
		this.cardType = cardType;
		this.cardName = cardName;
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
		String output = "(" + id + ", " + version + ", " + deckID + ", " + deckPosition + ", '" + cardType + "', '" + cardName + "')";
		
		return output;
	}
}
