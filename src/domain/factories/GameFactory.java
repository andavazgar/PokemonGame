/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.factories;

import java.sql.SQLException;

import org.dsrg.soenea.uow.UoW;

import domain.enums.GameStatus;
import domain.enums.PlayerStatus;
import domain.models.Game;
import services.tdg.GameTDG;

public class GameFactory {
	public static Game createNew(long challengeID) {
		Game challenge = null;
		try {
			challenge = createNew(GameTDG.getMaxId(), 1, challengeID, PlayerStatus.playing, PlayerStatus.waiting, GameStatus.inProgress);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return challenge;
	}
	
	public static Game createNew(long id, long version, long challengeID, PlayerStatus challengerStatus, PlayerStatus challengeeStatus, GameStatus gameStatus) {
		Game challenge = new Game(id, version, challengeID, challengerStatus.ordinal(), challengeeStatus.ordinal(), gameStatus.ordinal());
		try {
			UoW.getCurrent().registerNew(challenge);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return challenge;
	}
	
	public static Game createClean(long id, long version, long challengeID, PlayerStatus challengerStatus, PlayerStatus challengeeStatus, GameStatus gameStatus) {
		Game challenge = new Game(id, version, challengeID, challengerStatus.ordinal(), challengeeStatus.ordinal(), gameStatus.ordinal());
		UoW.getCurrent().registerClean(challenge);
		
		return challenge;
	}
}
