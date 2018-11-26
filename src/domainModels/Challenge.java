/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domainModels;

import models.enums.ChallengeStatus;

public class Challenge {
	private int id;
	private int version;
	private int challenger;
	private int challengee;
	private int challengerDeck;
	private int challengeeDeck;
	private int status;
	
	public Challenge(int id, int version, int challenger, int challengee, int challengerDeck, int challengeeDeck, int status) {
		this.id = id;
		this.version = version;
		this.challenger = challenger;
		this.challengee = challengee;
		this.challengerDeck = challengerDeck;
		this.challengeeDeck = challengeeDeck;
		this.status = status;
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
	
	public int getChallenger() {
		return challenger;
	}
	
	public void setChallenger(int challenger) {
		this.challenger = challenger;
	}
	
	public int getChallengee() {
		return challengee;
	}
	
	public void setChallengee(int challengee) {
		this.challengee = challengee;
	}
	
	public int getChallengerDeck() {
		return challengerDeck;
	}

	public void setChallengerDeck(int challengerDeck) {
		this.challengerDeck = challengerDeck;
	}

	public int getChallengeeDeck() {
		return challengeeDeck;
	}

	public void setChallengeeDeck(int challengeeDeck) {
		this.challengeeDeck = challengeeDeck;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(ChallengeStatus status) {
		this.status = status.ordinal();
	}
	
	public ChallengeStatus getChallengeStatus() {
		return ChallengeStatus.values()[status];
	}	
}
