/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.models;

import org.dsrg.soenea.domain.DomainObject;

import domain.enums.CardStatus;

public class PlayingCard extends DomainObject<Long> {
	private int gameID;
	private int playerID;
	private int cardID;
	private int cardStatus;
	
	public PlayingCard(long id, long version, int gameID, int playerID, int cardID, int cardStatus) {
		super(id, version);
		this.gameID = gameID;
		this.playerID = playerID;
		this.cardID = cardID;
		this.cardStatus = cardStatus;
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
