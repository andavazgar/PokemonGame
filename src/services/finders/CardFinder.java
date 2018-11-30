/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package services.finders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.DatabaseManager;

public final class CardFinder {
	private static final String tableName = "Cards";
	
	public static ResultSet find(long cardID) {
		String query = "SELECT * FROM " + tableName + " WHERE id = ?;";
		Connection conn = DatabaseManager.getConnection();
		ResultSet output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, cardID);
			
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
	
	public static ResultSet findCardByDeckPosition(long deckID, int deckPosition) {
		String query = "SELECT * FROM " + tableName + " WHERE deck_id = ? AND deck_position = ?;";
		Connection conn = DatabaseManager.getConnection();
		ResultSet output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, deckID);
			ps.setInt(2, deckPosition);
			
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
	
	public static ResultSet findCardsForUserID(long userID) {
		String query = "SELECT Cards.* FROM Users JOIN Decks ON Users.id = Decks.user_id JOIN Cards ON Decks.id = Cards.deck_id WHERE Decks.user_id = ?;";
		Connection conn = DatabaseManager.getConnection();
		ResultSet output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, userID);
			
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
