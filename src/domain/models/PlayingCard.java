/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.models;

import domain.enums.CardStatus;

public class PlayingCard {
	private int id;
	private int version;
	private int gameID;
	private int playerID;
	private int cardID;
	private int cardStatus;
	
	public PlayingCard(int id, int version, int gameID, int playerID, int cardID, int cardStatus) {
		this.id = id;
		this.version = version;
		this.gameID = gameID;
		this.playerID = playerID;
		this.cardID = cardID;
		this.cardStatus = cardStatus;
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
	
	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public int getCardID() {
		return cardID;
	}

	public void setCardID(int cardID) {
		this.cardID = cardID;
	}

	public int getCardStatus() {
		return cardStatus;
	}
	
	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus.ordinal();
	}
}
