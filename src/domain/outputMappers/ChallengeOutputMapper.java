/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.outputMappers;

import domain.models.Challenge;
import services.tdg.ChallengeTDG;

public class ChallengeOutputMapper {

	public static int insert(Challenge challenge) {
		int output = 0;
		output = ChallengeTDG.insert(challenge.getId(), challenge.getVersion(), challenge.getChallenger(), challenge.getChallengee(), challenge.getChallengerDeck(), challenge.getStatus());
		
		return output;
	}
	
	public static int update(Challenge challenge) {
		int output = 0;
		output = ChallengeTDG.update(challenge.getId(), challenge.getVersion(), challenge.getChallenger(), challenge.getChallengee(), challenge.getChallengerDeck(), challenge.getChallengeeDeck(), challenge.getStatus());
		
		return output;
	}
	
	public static int delete(Challenge challenge) {
		int output = 0;
		output = ChallengeTDG.delete(challenge.getId());
		
		return output;
	}
}
