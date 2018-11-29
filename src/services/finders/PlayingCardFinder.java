/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package services.finders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.enums.CardStatus;
import services.DatabaseManager;

public class PlayingCardFinder {
	private static final String tableName = "PlayingCards";
	
	public static ResultSet find(int gameID, int playerID, int cardID) {
		String query = "SELECT * FROM " + tableName + " WHERE game_id = ? AND player_id = ? AND card_id = ?;";
		Connection conn = DatabaseManager.getConnection();
		ResultSet output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, gameID);
			ps.setInt(2, playerID);
			ps.setInt(3, cardID);
			
			output = ps.executeQuery();
			
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
	
	public static ResultSet findCardsByStatus(int gameID, int playerID, CardStatus cardStatus) {
		String query = "SELECT * FROM " + tableName + " WHERE game_id = ? AND player_id = ? AND card_status = ?;";
		Connection conn = DatabaseManager.getConnection();
		ResultSet output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, gameID);
			ps.setInt(2, playerID);
			ps.setInt(3, cardStatus.ordinal());
			
			output = ps.executeQuery();
			
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
	
	public static ResultSet findDrawCardPosition(int gameID, int playerID) {
		String query = "SELECT COUNT(id) as deck_position FROM " + tableName + " WHERE game_id = ? AND player_id = ?;";
		Connection conn = DatabaseManager.getConnection();
		ResultSet output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, gameID);
			ps.setInt(2, playerID);
			
			output = ps.executeQuery();
			
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
}
