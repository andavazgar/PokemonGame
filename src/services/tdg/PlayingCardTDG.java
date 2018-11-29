/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package services.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import services.DatabaseManager;

public class PlayingCardTDG extends AbstractTDG {
	private static final String tableName = "PlayingCards";
	private static int nextID = 0;
	
	public static void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
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
		createTable(tableName, query);
	}
	
	public static void truncateTable() {
		truncateTable(tableName);
	}
	
	public static void dropTable() {
		dropTable(tableName);
	}
	
	public static int insert(int id, int version, int gameID, int playerID, int cardID, int cardStatus) {
		String query = "INSERT INTO " + tableName + " (id, version, game_id, player_id, card_id, card_status) VALUES (?, ?, ?, ?, ?, ?);";
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
		String query = "UPDATE " + tableName + " SET version = ?, game_id = ?, player_id = ?, card_id = ?, card_status = ? WHERE id = ? AND version = ?;";
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
		String query = "DELETE FROM " + tableName + " WHERE id = ?;";
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
	
	public static int getNextChallengeID() {
		nextID = getNextID(tableName, nextID);
		
		return nextID;
	}
}
