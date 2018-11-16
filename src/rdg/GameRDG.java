/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import services.DatabaseManager;

public class GameRDG extends AbstractRDG {
	private static final String tableName = "Games";
	private static int nextID = 0;
	private int challengeID;
	private int challengerStatus;
	private int challengeeStatus;
	private int gameStatus;
	
	
	public GameRDG(int id, int challengeID, int challengerStatus, int challengeeStatus, int gameStatus) {
		super(id);
		this.challengeID = challengeID;
		this.challengerStatus = challengerStatus;
		this.challengeeStatus = challengeeStatus;
		this.gameStatus = gameStatus;
	}
	
	public GameRDG(int id, int version, int challengeID, int challengerStatus, int challengeeStatus, int gameStatus) {
		super(id, version);
		this.challengeID = challengeID;
		this.challengerStatus = challengerStatus;
		this.challengeeStatus = challengeeStatus;
		this.gameStatus = gameStatus;
	}

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
	
	public int insert() {
		String query = "INSERT INTO " + tableName + " (id, version, challenge_id, challenger_status, challengee_status, game_status) VALUES (?, ?, ?, ?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, getId());
			ps.setInt(2, getVersion());
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
	
	public int update() {
		String query = "UPDATE " + tableName + " SET version = ?, challenge_id = ?, challenger_status = ?, challengee_status = ?, game_status WHERE id = ? AND version = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, getVersion() +1);
			ps.setInt(2, challengeID);
			ps.setInt(3, challengerStatus);
			ps.setInt(4, challengeeStatus);
			ps.setInt(5, gameStatus);
			ps.setInt(6, getId());
			ps.setInt(7, getVersion());
			
			output = ps.executeUpdate();
			
			ps.close();
			
			if(output == 1) {
				incrementVersion();
			}
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
	
	public int delete() {
		String query = "DELETE FROM " + tableName + " WHERE id = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, getId());
			
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
	
	public static GameRDG find(int id) {
		String query = "SELECT * FROM " + tableName + " WHERE id = ?;";
		Connection conn = DatabaseManager.getConnection();
		GameRDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output = new GameRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenge_id"), rs.getInt("challenger_status"), rs.getInt("challengee_status"), rs.getInt("game_status"));
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
	
	public static List<GameRDG> findAll() {
		String query = "SELECT * FROM " + tableName + ";";
		Connection conn = DatabaseManager.getConnection();
		List<GameRDG> output = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				output.add(new GameRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenge_id"), rs.getInt("challenger_status"), rs.getInt("challengee_status"), rs.getInt("game_status")));
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
	
	public static int getNextGameID() {
		nextID = getNextID(tableName, nextID);
		
		return nextID;
	}

	public int getChallengeID() {
		return challengeID;
	}

	public void setChallengeID(int challengeID) {
		this.challengeID = challengeID;
	}

	public int getChallengerStatus() {
		return challengerStatus;
	}

	public void setChallengerStatus(int challengerStatus) {
		this.challengerStatus = challengerStatus;
	}

	public int getChallengeeStatus() {
		return challengeeStatus;
	}

	public void setChallengeeStatus(int challengeeStatus) {
		this.challengeeStatus = challengeeStatus;
	}

	public int getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}
}
