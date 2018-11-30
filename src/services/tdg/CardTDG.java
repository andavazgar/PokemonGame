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

public final class CardTDG extends AbstractTDG {
	private static final String TABLE_NAME = "Cards";
	
	public static void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
							"id INT," +
							"version INT NOT NULL," +
							"deck_id INT NOT NULL," +
							"deck_position TINYINT NOT NULL," +
							"card_type CHAR(1) NOT NULL," +
							"card_name VARCHAR(255) NOT NULL," +
							"PRIMARY KEY (id)," +
							"FOREIGN KEY (deck_id) REFERENCES Decks(id)" +
						");";
		createTable(TABLE_NAME, query);
	}
	
	public static void truncateTable() {
		truncateTable(TABLE_NAME);
	}
	
	public static void dropTable() {
		dropTable(TABLE_NAME);
	}

	public static int insert(int id, int version, int deckID, int deckPosition, int cardType, String cardName) {
		String query = "INSERT INTO " + TABLE_NAME + " (id, version, deck_id, deck_position, card_type, card_name) VALUES (?, ?, ?, ?, ?, ?);";
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
	
	public static long getMaxId() throws SQLException {
		return UniqueIdFactory.getMaxId(TABLE_NAME, "id");
	}
}
