/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package models;

public class TempCard {
	private char cardType;
	private String cardName;
	
	public TempCard(char cardType, String cardName) {
		super();
		this.cardType = cardType;
		this.cardName = cardName;
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
	
	
}
