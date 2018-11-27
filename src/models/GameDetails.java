/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import services.DatabaseManager;

// This class is a VIEW. It is read-only.
public class GameDetails {
	private static final String viewName = "GameDetails";
	private int id;
	private int version;
	private int challengeID;
	private int challenger;
	private int challengee;
	private int challengerDeck;
	private int challengeeDeck;
	private int challengerStatus;
	private int challengeeStatus;
	
	
	public GameDetails(int id, int version, int challengeID, int challenger, int challengee, int challengerDeck, int challengeeDeck,
			int challengerStatus, int challengeeStatus) {
		super();
		this.id = id;
		this.version = version;
		this.challengeID = challengeID;
		this.challenger = challenger;
		this.challengee = challengee;
		this.challengerDeck = challengerDeck;
		this.challengeeDeck = challengeeDeck;
		this.challengerStatus = challengerStatus;
		this.challengeeStatus = challengeeStatus;
	}
	
	public static void createView() {
		String query = "CREATE OR REPLACE VIEW " + viewName + " AS " +
							"SELECT Games.id, Games.version, challenge_id, challenger, challengee, challenger_deck, challengee_deck, challenger_status, challengee_status " +
							"FROM Games JOIN Challenges ON Games.challenge_id = Challenges.id;";
		
		Connection connection = DatabaseManager.getConnection();
    	
    	try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			
			System.out.println("Created the view '" + viewName + "' successfully");
		}
    	catch (SQLException e) {
			System.out.println("Failed to create the table '" + viewName + "'.\n" + e.getMessage());
		}
    	finally {
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
	}
	
	public static void dropView() {
		String query = "DROP VIEW IF EXISTS " + viewName;
    	Connection connection = DatabaseManager.getConnection();
    	
    	try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			
			System.out.println("Dropped the view '" + viewName + "' successfully");
		}
    	catch (SQLException e) {
			System.out.println("Failed to drop the view '" + viewName + "'.\n" + e.getMessage());
		}
    	finally {
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
	}

	public static GameDetails find(int id) {
		String query = "SELECT * FROM " + viewName + " WHERE id = ?;";
		Connection conn = DatabaseManager.getConnection();
		GameDetails output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output = new GameDetails(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenge_id"), rs.getInt("challenger"), rs.getInt("challengee"), rs.getInt("challenger_deck"), rs.getInt("challengee_deck"), rs.getInt("challenger_status"), rs.getInt("challengee_status"));
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
	
	public static List<GameDetails> findAll() {
		String query = "SELECT * FROM " + viewName + ";";
		Connection conn = DatabaseManager.getConnection();
		ArrayList<GameDetails> output = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output.add(new GameDetails(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenge_id"), rs.getInt("challenger"), rs.getInt("challengee"), rs.getInt("challenger_deck"), rs.getInt("challengee_deck"), rs.getInt("challenger_status"), rs.getInt("challengee_status")));
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
	
//	public GameTDG getGameRDG() {
//		GameTDG game = new GameTDG(id, version, challengeID, challengerStatus, challengeeStatus);
//		
//		return game;
//	}

	public int getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	public int getChallenger() {
		return challenger;
	}

	public int getChallengee() {
		return challengee;
	}

	public int getChallengerDeck() {
		return challengerDeck;
	}

	public int getChallengeeDeck() {
		return challengeeDeck;
	}

	public int getChallengerStatus() {
		return challengerStatus;
	}

	public int getChallengeeStatus() {
		return challengeeStatus;
	}
	
	public Integer getDeckForPlayer(int playerID) {
		if(playerID == challenger) {
			return challengerDeck;
		}
		else if(playerID == challengee) {
			return challengeeDeck;
		}
		else {
			return null;
		}
	}
}
