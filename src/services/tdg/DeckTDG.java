/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package services.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dsrg.soenea.service.UniqueIdFactory;

import services.DatabaseManager;

public class DeckTDG extends AbstractTDG {
	private static final String TABLE_NAME = "Decks";
	
	public static void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
							"id INT," +
							"version INT NOT NULL," +
							"user_id INT NOT NULL," +
							"PRIMARY KEY (id)," +
							"FOREIGN KEY (user_id) REFERENCES Users(id)" +
						");";
		createTable(TABLE_NAME, query);
	}
	
	public static void truncateTable() {
		truncateTable(TABLE_NAME);
	}
	
	public static void dropTable() {
		dropTable(TABLE_NAME);
	}

	public static int insert(int id, int version, int userID) {
		String query = "INSERT INTO " + TABLE_NAME + " (id, version, user_id) VALUES (?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.setInt(2, version);
			ps.setInt(3, userID);
			
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
	
	public static int insertDeckCards(String deckCardsValues) {
		String query = "INSERT INTO Cards (id, version, deck_id, deck_position, card_type, card_name) VALUES ";
		int output = 0;
		
		query = query + deckCardsValues + ";";
		
		Connection conn = DatabaseManager.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
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
	
	public static long getMaxId() throws SQLException {
		return UniqueIdFactory.getMaxId(TABLE_NAME, "id");
	}
}
