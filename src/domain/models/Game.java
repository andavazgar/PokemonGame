/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.models;

import org.dsrg.soenea.domain.DomainObject;

public class Game extends DomainObject<Long> {
	private long challengeID;
	private int challengerStatus;
	private int challengeeStatus;
	private int gameStatus;
	
	public Game(long id, long version, long challengeID, int challengerStatus, int challengeeStatus, int gameStatus) {
		super(id, version);
		this.challengeID = challengeID;
		this.challengerStatus = challengerStatus;
		this.challengeeStatus = challengeeStatus;
		this.gameStatus = gameStatus;
	}

	public long getChallengeID() {
		return challengeID;
	}

	public void setChallengeID(long challengeID) {
		this.challengeID = challengeID;
	}

	public int getChallengerStatus() {
		return challengerStatus;
	}

	public void setChallengerStatus(int challengerStatus) {
		this.challengerStatus = challengerStatus;
	}

	public int getChallengeeStatus() {
		return challengeeStatus;
	}

	public void setChallengeeStatus(int challengeeStatus) {
		this.challengeeStatus = challengeeStatus;
	}

	public int getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}
}
