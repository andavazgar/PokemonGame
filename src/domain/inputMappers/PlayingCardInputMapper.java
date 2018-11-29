/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.inputMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.models.Game;
import domain.models.PlayingCard;
import domain.enums.CardStatus;
import services.finders.PlayingCardFinder;

public class PlayingCardInputMapper {
	
	public static PlayingCard find(int gameID, int playerID, int cardID) {
		ResultSet rs = PlayingCardFinder.find(gameID, playerID, cardID);
		PlayingCard output = null;
		
		try {
			while (rs.next()) {
			    output = new PlayingCard(rs.getInt("id"), rs.getInt("version"), rs.getInt("game_id"), rs.getInt("player_id"), rs.getInt("card_id"), rs.getInt("card_status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static List<PlayingCard> findCardsByStatus(Game game, int playerID, CardStatus cardStatus) {
		ResultSet rs = PlayingCardFinder.findCardsByStatus(game.getId(), playerID, cardStatus);
		List<PlayingCard> output = new ArrayList<>();
		
		try {
			while (rs.next()) {
				output.add(new PlayingCard(rs.getInt("id"), rs.getInt("version"), rs.getInt("game_id"), rs.getInt("player_id"), rs.getInt("card_id"), rs.getInt("card_status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static int findDrawCardPosition(Game game, int playerID) {
		ResultSet rs = PlayingCardFinder.findDrawCardPosition(game.getId(), playerID);
		int output = 1;
		
		try {
			while (rs.next()) {
                output = rs.getInt("deck_position") + 1;
            }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
}
