/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.models;

import org.dsrg.soenea.domain.DomainObject;

import domain.enums.ChallengeStatus;

public class Challenge extends DomainObject<Long> {
	private long challenger;
	private long challengee;
	private long challengerDeck;
	private long challengeeDeck;
	private int status;
	
	public Challenge(long id, long version, long challenger, long challengee, long challengeeDeck, int status) {
		super(id, version);
		this.challenger = challenger;
		this.challengee = challengee;
		this.challengeeDeck = challengeeDeck;
		this.status = status;
	}
	
	public Challenge(long id, long version, long challenger, long challengee, long challengerDeck, long challengeeDeck, int status) {
		super(id, version);
		this.challenger = challenger;
		this.challengee = challengee;
		this.challengerDeck = challengerDeck;
		this.challengeeDeck = challengeeDeck;
		this.status = status;
	}
	
	public long getChallenger() {
		return challenger;
	}
	
	public void setChallenger(long challenger) {
		this.challenger = challenger;
	}
	
	public long getChallengee() {
		return challengee;
	}
	
	public void setChallengee(long challengee) {
		this.challengee = challengee;
	}
	
	public long getChallengerDeck() {
		return challengerDeck;
	}

	public void setChallengerDeck(long challengerDeck) {
		this.challengerDeck = challengerDeck;
	}

	public long getChallengeeDeck() {
		return challengeeDeck;
	}

	public void setChallengeeDeck(long challengeeDeck) {
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
