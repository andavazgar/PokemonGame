/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.models;

public class Game {
	private int id;
	private int version;
	private int challengeID;
	private int challengerStatus;
	private int challengeeStatus;
	private int gameStatus;
	
	public Game(int id, int version, int challengeID, int challengerStatus, int challengeeStatus, int gameStatus) {
		this.id = id;
		this.version = version;
		this.challengeID = challengeID;
		this.challengerStatus = challengerStatus;
		this.challengeeStatus = challengeeStatus;
		this.gameStatus = gameStatus;
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

	public int getChallengeID() {
		return challengeID;
	}

	public void setChallengeID(int challengeID) {
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
