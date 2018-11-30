/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.factories;

import java.sql.SQLException;

import org.dsrg.soenea.uow.UoW;

import domain.enums.ChallengeStatus;
import domain.models.Challenge;
import services.tdg.ChallengeTDG;

public class ChallengeFactory {
	public static Challenge createNew(long challengerID, long challengeeID, long challengerDeckID) {
		Challenge challenge = null;
		try {
			challenge = createNew(ChallengeTDG.getMaxId(), 1, challengerID, challengeeID, challengerDeckID, ChallengeStatus.open);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return challenge;
	}
	
	public static Challenge createNew(long id, long version, long challengerID, long challengeeID, long challengerDeckID, ChallengeStatus challengeStatus) {
		Challenge challenge = new Challenge(id, version, challengerID, challengeeID, challengerDeckID, challengeStatus.ordinal());
		try {
			UoW.getCurrent().registerNew(challenge);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return challenge;
	}
	
	public static Challenge createClean(long id, long version, long challengerID, long challengeeID, long challengerDeckID, ChallengeStatus challengeStatus) {
		Challenge challenge = new Challenge(id, version, challengerID, challengeeID, challengerDeckID, challengeStatus.ordinal());
		UoW.getCurrent().registerClean(challenge);
		
		return challenge;
	}
}
