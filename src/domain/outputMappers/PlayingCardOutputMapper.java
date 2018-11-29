/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.outputMappers;

import domain.models.PlayingCard;
import services.tdg.PlayingCardTDG;

public class PlayingCardOutputMapper {
	
	public static int insert(PlayingCard playingCard) {
		int output = 0;
		output = PlayingCardTDG.insert(playingCard.getId(), playingCard.getVersion(), playingCard.getGameID(), playingCard.getPlayerID(), playingCard.getCardID(), playingCard.getCardStatus());
		
		return output;
	}
	
	public static int update(PlayingCard playingCard) {
		int output = 0;
		output = PlayingCardTDG.update(playingCard.getId(), playingCard.getVersion(), playingCard.getGameID(), playingCard.getPlayerID(), playingCard.getCardID(), playingCard.getCardStatus());
		
		return output;
	}
	
	public static int delete(PlayingCard playingCard) {
		int output = 0;
		output = PlayingCardTDG.delete(playingCard.getId());
		
		return output;
	}
}
