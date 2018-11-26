/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.DatabaseManager;

public final class CardTDG extends AbstractTDG {
	private static final String tableName = "Cards";
	private static int nextID = 0;
	
	public static void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
							"id INT," +
							"version INT NOT NULL," +
							"deck_id INT NOT NULL," +
							"deck_position TINYINT NOT NULL," +
							"card_type CHAR(1) NOT NULL," +
							"card_name VARCHAR(255) NOT NULL," +
							"PRIMARY KEY (id)," +
							"FOREIGN KEY (deck_id) REFERENCES Decks(id)" +
						");";
		createTable(tableName, query);
	}
	
	public static void truncateTable() {
		truncateTable(tableName);
	}
	
	public static void dropTable() {
		dropTable(tableName);
	}

	public static int insert(int id, int version, int deckID, int deckPosition, int cardType, String cardName) {
		String query = "INSERT INTO " + tableName + " (id, version, deck_id, deck_position, card_type, card_name) VALUES (?, ?, ?, ?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.setInt(2, version);
			ps.setInt(3, deckID);
			ps.setInt(4, deckPosition);
			ps.setInt(5, cardType);
			ps.setString(6, cardName);
			
			output = ps.executeUpdate();
			
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
	
	public static ResultSet find(int cardID) {
		String query = "SELECT * FROM " + tableName + " WHERE id = ?;";
		Connection conn = DatabaseManager.getConnection();
		ResultSet output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, cardID);
			
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
	
	public static ResultSet findCardByDeckPosition(int deckID, int deckPosition) {
		String query = "SELECT * FROM " + tableName + " WHERE deck_id = ? AND deck_position = ?;";
		Connection conn = DatabaseManager.getConnection();
		ResultSet output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, deckID);
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
	
	public static ResultSet findCardsForUserID(int userID) {
		String query = "SELECT Cards.* FROM Users JOIN Decks ON Users.id = Decks.user_id JOIN Cards ON Decks.id = Cards.deck_id WHERE Decks.user_id = ?;";
		Connection conn = DatabaseManager.getConnection();
		ResultSet output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userID);
			
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
	
	public static int getNextCardID() {
		nextID = getNextID(tableName, nextID);
		
		return nextID;
	}
}
