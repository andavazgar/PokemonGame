/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import models.enums.ChallengeStatus;
import services.DatabaseManager;

public class ChallengeRDG extends AbstractRDG {
	private static final String tableName = "Challenges";
	private static int nextID = 0;
	private int challenger;
	private int challengee;
	private int challengerDeck;
	private int challengeeDeck;
	private int status;
	
	public ChallengeRDG(int id, int challenger, int challengee, int challengerDeck, int status) {
		super(id);
		this.challenger = challenger;
		this.challengee = challengee;
		this.challengerDeck = challengerDeck;
		this.status = status;
	}
	
	private ChallengeRDG(int id, int version, int challenger, int challengee, int challengerDeck, int challengeeDeck, int status) {
		super(id, version);
		this.challenger = challenger;
		this.challengee = challengee;
		this.challengerDeck = challengerDeck;
		this.challengeeDeck = challengeeDeck;
		this.status = status;
	}

	public static void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
							"id INT," +
							"version INT NOT NULL," +
							"challenger INT NOT NULL," +
							"challengee INT NOT NULL," +
							"challenger_deck INT NOT NULL," +
							"challengee_deck INT," +
							"status TINYINT NOT NULL," +
							"PRIMARY KEY (id)," +
							"FOREIGN KEY (challenger) REFERENCES Users(id)," +
							"FOREIGN KEY (challengee) REFERENCES Users(id)," +
							"FOREIGN KEY (challenger_deck) REFERENCES Decks(id)," +
							"FOREIGN KEY (challengee_deck) REFERENCES Decks(id)" +
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
		String query = "INSERT INTO " + tableName + " (id, version, challenger, challengee, challenger_deck, status) VALUES (?, ?, ?, ?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, getId());
			ps.setInt(2, getVersion());
			ps.setInt(3, challenger);
			ps.setInt(4, challengee);
			ps.setInt(5, challengerDeck);
			ps.setInt(6, status);
			
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
		String query = "UPDATE " + tableName + " SET version = ?, challenger = ?, challengee = ?, challenger_deck = ?, challengee_deck = ?, status = ? WHERE id = ? AND version = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, getVersion() +1);
			ps.setInt(2, challenger);
			ps.setInt(3, challengee);
			ps.setInt(4, challengerDeck);
			
			if(challengeeDeck != 0) {
				ps.setInt(5, challengeeDeck);
			}
			else {
				ps.setNull(5, Types.INTEGER);
			}
			ps.setInt(6, status);
			ps.setInt(7, getId());
			ps.setInt(8, getVersion());
			
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
	
	public static ChallengeRDG find(int id) {
		String query = "SELECT * FROM " + tableName + " WHERE id = ?;";
		Connection conn = DatabaseManager.getConnection();
		ChallengeRDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output = new ChallengeRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenger"), rs.getInt("challengee"), rs.getInt("challenger_deck"), rs.getInt("challengee_deck"), rs.getInt("status"));
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

	public static List<ChallengeRDG> findOpenByChallenger(int challenger) {
		String query = "SELECT * FROM " + tableName + " WHERE challenger = ?;";
		Connection conn = DatabaseManager.getConnection();
		List<ChallengeRDG> output = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, challenger);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output.add(new ChallengeRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenger"), rs.getInt("challengee"), rs.getInt("challenger_deck"), rs.getInt("challengee_deck"), rs.getInt("status")));
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
	
	public static List<ChallengeRDG> findOpenByChallengee(int challengee) {
		String query = "SELECT * FROM " + tableName + " WHERE challengee = ?;";
		Connection conn = DatabaseManager.getConnection();
		List<ChallengeRDG> output = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, challengee);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				output.add(new ChallengeRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenger"), rs.getInt("challengee"), rs.getInt("challenger_deck"), rs.getInt("challengee_deck"), rs.getInt("status")));
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
	
	public static List<ChallengeRDG> findAll() {
		String query = "SELECT * FROM " + tableName + ";";
		Connection conn = DatabaseManager.getConnection();
		List<ChallengeRDG> output = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				output.add(new ChallengeRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenger"), rs.getInt("challengee"), rs.getInt("challenger_deck"), rs.getInt("challengee_deck"), rs.getInt("status")));
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
	
	public static List<ChallengeRDG> findAllOpen() {
		int openStatus = ChallengeStatus.open.ordinal();
		String query = "SELECT * FROM " + tableName + " WHERE status = ?;";
		Connection conn = DatabaseManager.getConnection();
		List<ChallengeRDG> output = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, openStatus);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				output.add(new ChallengeRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenger"), rs.getInt("challengee"), rs.getInt("challenger_deck"), rs.getInt("challengee_deck"), rs.getInt("status")));
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
	
	public static int getNextChallengeID() {
		nextID = getNextID(tableName, nextID);
		
		return nextID;
	}
	
	public int getChallenger() {
		return challenger;
	}
	
	public void setChallenger(int challenger) {
		this.challenger = challenger;
	}
	
	public int getChallengee() {
		return challengee;
	}
	
	public void setChallengee(int challengee) {
		this.challengee = challengee;
	}
	
	public int getChallengerDeck() {
		return challengerDeck;
	}

	public void setChallengerDeck(int challengerDeck) {
		this.challengerDeck = challengerDeck;
	}

	public int getChallengeeDeck() {
		return challengeeDeck;
	}

	public void setChallengeeDeck(int challengeeDeck) {
		this.challengeeDeck = challengeeDeck;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(ChallengeStatus status) {
		this.status = status.ordinal();
	}
	
	public ChallengeStatus getChallengeStatus() {
		return ChallengeStatus.values()[status];
	}	
}
