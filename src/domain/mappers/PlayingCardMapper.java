/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.mappers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.models.Game;
import domain.models.PlayingCard;
import domain.enums.CardStatus;
import services.DatabaseManager;
import services.finders.PlayingCardFinder;
import services.tdg.PlayingCardTDG;

public class PlayingCardMapper {
	
	public int insert(PlayingCard playingCard) {
		int output = 0;
		output = PlayingCardTDG.insert(playingCard.getId(), playingCard.getVersion(), playingCard.getGameID(), playingCard.getPlayerID(), playingCard.getCardID(), playingCard.getCardStatus());
		
		return output;
	}
	
	public int update(PlayingCard playingCard) {
		int output = 0;
		output = PlayingCardTDG.update(playingCard.getId(), playingCard.getVersion(), playingCard.getGameID(), playingCard.getPlayerID(), playingCard.getCardID(), playingCard.getCardStatus());
		
		return output;
	}
	
	public int delete(PlayingCard playingCard) {
		int output = 0;
		output = PlayingCardTDG.delete(playingCard.getId());
		
		return output;
	}
	
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
		Connection conn = DatabaseManager.getConnection();
		int output = 1;
		
		try {
			while (rs.next()) {
                output = rs.getInt("deck_position") + 1;
            }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return output;
	}
}
