/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package dataMappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domainModels.PlayingCard;
import models.enums.CardStatus;
import services.DatabaseManager;
import tdg.PlayingCardTDG;

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
	
	public static PlayingCardTDG find(int gameID, int playerID, int cardID) {
		String query = "SELECT * FROM " + tableName + " WHERE game_id = ? AND player_id = ? AND card_id = ?;";
		Connection conn = DatabaseManager.getConnection();
		PlayingCardTDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, gameID);
			ps.setInt(2, playerID);
			ps.setInt(3, cardID);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output = new PlayingCardTDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("game_id"), rs.getInt("player_id"), rs.getInt("card_id"), rs.getInt("card_status"));
            }
			
			ps.close();
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
	
	public static List<PlayingCardTDG> findCardsByStatus(int gameID, int playerID, CardStatus cardStatus) {
		String query = "SELECT * FROM " + tableName + " WHERE game_id = ? AND player_id = ? AND card_status = ?;";
		Connection conn = DatabaseManager.getConnection();
		List<PlayingCardTDG> output = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, gameID);
			ps.setInt(2, playerID);
			ps.setInt(3, cardStatus.ordinal());
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output.add(new PlayingCardTDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("game_id"), rs.getInt("player_id"), rs.getInt("card_id"), rs.getInt("card_status")));
            }
			
			ps.close();
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
	
	public static int findDrawCardPosition(int gameID, int playerID) {
		String query = "SELECT COUNT(id) as deck_position FROM " + tableName + " WHERE game_id = ? AND player_id = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 1;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, gameID);
			ps.setInt(2, playerID);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output = rs.getInt("deck_position") + 1;
            }
			
			ps.close();
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
	
	public static int getNextChallengeID() {
		nextID = getNextID(tableName, nextID);
		
		return nextID;
	}
	
	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public int getCardID() {
		return cardID;
	}

	public void setCardID(int cardID) {
		this.cardID = cardID;
	}

	public int getCardStatus() {
		return cardStatus;
	}
	
	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus.ordinal();
	}
}
