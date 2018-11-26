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

public class GameTDG extends AbstractTDG {
	private static final String tableName = "Games";
	private static int nextID = 0;
	
	public static void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
							"id INT," +
							"version INT NOT NULL," +
							"challenge_id INT NOT NULL UNIQUE," +
							"challenger_status INT NOT NULL," +
							"challengee_status INT NOT NULL," +
							"game_status INT NOT NULL," +
							"PRIMARY KEY (id)," +
							"FOREIGN KEY (challenge_id) REFERENCES Challenges(id)" +
						");";
		createTable(tableName, query);
	}
	
	public static void truncateTable() {
		truncateTable(tableName);
	}
	
	public static void dropTable() {
		dropTable(tableName);
	}
	
	public static int insert(int id, int version, int challengeID, int challengerStatus, int challengeeStatus, int gameStatus) {
		String query = "INSERT INTO " + tableName + " (id, version, challenge_id, challenger_status, challengee_status, game_status) VALUES (?, ?, ?, ?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.setInt(2, version);
			ps.setInt(3, challengeID);
			ps.setInt(4, challengerStatus);
			ps.setInt(5, challengeeStatus);
			ps.setInt(6, gameStatus);
			
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
	
	public static int update(int id, int version, int challengeID, int challengerStatus, int challengeeStatus, int gameStatus) {
		String query = "UPDATE " + tableName + " SET version = ?, challenge_id = ?, challenger_status = ?, challengee_status = ?, game_status WHERE id = ? AND version = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, version +1);
			ps.setInt(2, challengeID);
			ps.setInt(3, challengerStatus);
			ps.setInt(4, challengeeStatus);
			ps.setInt(5, gameStatus);
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
	
	public static ResultSet find(int id) {
		String query = "SELECT * FROM " + tableName + " WHERE id = ?;";
		Connection conn = DatabaseManager.getConnection();
		ResultSet output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			
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
	
	public static ResultSet findAll() {
		String query = "SELECT * FROM " + tableName + ";";
		Connection conn = DatabaseManager.getConnection();
		ResultSet output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			
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
	
	public static int getNextGameID() {
		nextID = getNextID(tableName, nextID);
		
		return nextID;
	}
}
