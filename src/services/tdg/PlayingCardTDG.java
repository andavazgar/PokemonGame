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

public class PlayingCardTDG extends AbstractTDG {
	private static final String TABLE_NAME = "PlayingCards";
	
	public static void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
							"id INT," +
							"version INT NOT NULL," +
							"game_id INT NOT NULL," +
							"player_id INT NOT NULL," +
							"card_id INT NOT NULL," +
							"card_status INT NOT NULL," +
							"PRIMARY KEY (id)," +
							"FOREIGN KEY (game_id) REFERENCES Games(id)," +
							"FOREIGN KEY (player_id) REFERENCES Users(id)," +
							"FOREIGN KEY (card_id) REFERENCES Cards(id)" +
						");";
		createTable(TABLE_NAME, query);
	}
	
	public static void truncateTable() {
		truncateTable(TABLE_NAME);
	}
	
	public static void dropTable() {
		dropTable(TABLE_NAME);
	}
	
	public static int insert(int id, int version, int gameID, int playerID, int cardID, int cardStatus) {
		String query = "INSERT INTO " + TABLE_NAME + " (id, version, game_id, player_id, card_id, card_status) VALUES (?, ?, ?, ?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.setInt(2, version);
			ps.setInt(3, gameID);
			ps.setInt(4, playerID);
			ps.setInt(5, cardID);
			ps.setInt(6, cardStatus);
			
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
	
	public static int update(int id, int version, int gameID, int playerID, int cardID, int cardStatus) {
		String query = "UPDATE " + TABLE_NAME + " SET version = ?, game_id = ?, player_id = ?, card_id = ?, card_status = ? WHERE id = ? AND version = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, version +1);
			ps.setInt(2, gameID);
			ps.setInt(3, playerID);
			ps.setInt(4, cardID);
			ps.setInt(5, cardStatus);
			ps.setInt(6, id);
			ps.setInt(7, version);
			
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
	
	public static int delete(int id) {
		String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			
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
