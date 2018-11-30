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

public class GameTDG extends AbstractTDG {
	private static final String TABLE_NAME = "Games";
	
	public static void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
							"id INT," +
							"version INT NOT NULL," +
							"challenge_id INT NOT NULL UNIQUE," +
							"challenger_status INT NOT NULL," +
							"challengee_status INT NOT NULL," +
							"game_status INT NOT NULL," +
							"PRIMARY KEY (id)," +
							"FOREIGN KEY (challenge_id) REFERENCES Challenges(id)" +
						");";
		createTable(TABLE_NAME, query);
	}
	
	public static void truncateTable() {
		truncateTable(TABLE_NAME);
	}
	
	public static void dropTable() {
		dropTable(TABLE_NAME);
	}
	
	public static int insert(long id, long version, int challengeID, int challengerStatus, int challengeeStatus, int gameStatus) {
		String query = "INSERT INTO " + TABLE_NAME + " (id, version, challenge_id, challenger_status, challengee_status, game_status) VALUES (?, ?, ?, ?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			ps.setLong(2, version);
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
	
	public static int update(long id, long version, int challengeID, int challengerStatus, int challengeeStatus, int gameStatus) {
		String query = "UPDATE " + TABLE_NAME + " SET version = ?, challenge_id = ?, challenger_status = ?, challengee_status = ?, game_status WHERE id = ? AND version = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, version +1);
			ps.setInt(2, challengeID);
			ps.setInt(3, challengerStatus);
			ps.setInt(4, challengeeStatus);
			ps.setInt(5, gameStatus);
			ps.setLong(6, id);
			ps.setLong(7, version);
			
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
	
	public static int delete(long id) {
		String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			
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
