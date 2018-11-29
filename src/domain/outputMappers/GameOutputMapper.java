/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.outputMappers;

import domain.models.Game;
import services.tdg.GameTDG;

public class GameOutputMapper {
	
	public static int insert(Game game) {
		int output = 0;
		output = GameTDG.insert(game.getId(), game.getVersion(), game.getChallengeID(), game.getChallengerStatus(), game.getChallengeeStatus(), game.getGameStatus());
		
		return output;
	}
	
	public static int update(Game game) {
		int output = 0;
		output = GameTDG.update(game.getId(), game.getVersion(), game.getChallengeID(), game.getChallengerStatus(), game.getChallengeeStatus(), game.getGameStatus());
		
		return output;
	}
	
	public static int delete(Game game) {
		int output = 0;
		output = GameTDG.delete(game.getId());
		
		return output;
	}
}
