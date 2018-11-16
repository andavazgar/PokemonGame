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

import models.enums.CardStatus;
import services.DatabaseManager;

public class PlayingCardRDG extends AbstractRDG {
	private static final String tableName = "PlayingCards";
	private static int nextID = 0;
	private int gameID;
	private int playerID;
	private int cardID;
	private int cardStatus;
	
	
	public PlayingCardRDG(int id, int gameID, int playerID, int cardID, int cardStatus) {
		super(id);
		this.gameID = gameID;
		this.playerID = playerID;
		this.cardID = cardID;
		this.cardStatus = cardStatus;
	}
	
	public PlayingCardRDG(int id, int version, int gameID, int playerID, int cardID, int cardStatus) {
		super(id, version);
		this.gameID = gameID;
		this.playerID = playerID;
		this.cardID = cardID;
		this.cardStatus = cardStatus;
	}
	
	public PlayingCardRDG(int gameID, int playerID, CardRDG card) {
		super(card.getId(), card.getVersion());
		this.gameID = gameID;
		this.playerID = playerID;
		this.cardID = card.getId();
		this.cardStatus = CardStatus.hand.ordinal();
	}
	
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
	
	public int insert() {
		String query = "INSERT INTO " + tableName + " (id, version, game_id, player_id, card_id, card_status) VALUES (?, ?, ?, ?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, getId());
			ps.setInt(2, getVersion());
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
	
	public int update() {
		String query = "UPDATE " + tableName + " SET version = ?, game_id = ?, player_id = ?, card_id = ?, card_status = ? WHERE id = ? AND version = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, getVersion() +1);
			ps.setInt(2, gameID);
			ps.setInt(3, playerID);
			ps.setInt(4, cardID);
			ps.setInt(5, cardStatus);
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
	
	public static PlayingCardRDG find(int gameID, int playerID, int cardID) {
		String query = "SELECT * FROM " + tableName + " WHERE game_id = ? AND player_id = ? AND card_id = ?;";
		Connection conn = DatabaseManager.getConnection();
		PlayingCardRDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, gameID);
			ps.setInt(2, playerID);
			ps.setInt(3, cardID);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output = new PlayingCardRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("game_id"), rs.getInt("player_id"), rs.getInt("card_id"), rs.getInt("card_status"));
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
	
	public static List<PlayingCardRDG> findCardsByStatus(int gameID, int playerID, CardStatus cardStatus) {
		String query = "SELECT * FROM " + tableName + " WHERE game_id = ? AND player_id = ? AND card_status = ?;";
		Connection conn = DatabaseManager.getConnection();
		List<PlayingCardRDG> output = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, gameID);
			ps.setInt(2, playerID);
			ps.setInt(3, cardStatus.ordinal());
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output.add(new PlayingCardRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("game_id"), rs.getInt("player_id"), rs.getInt("card_id"), rs.getInt("card_status")));
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
	
	public static int findDrawCardPosition(int gameID, int playerID) {
		String query = "SELECT COUNT(id) as deck_position FROM " + tableName + " WHERE game_id = ? AND player_id = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 1;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, gameID);
			ps.setInt(2, playerID);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output = rs.getInt("deck_position") + 1;
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
	
	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public int getCardID() {
		return cardID;
	}

	public void setCardID(int cardID) {
		this.cardID = cardID;
	}

	public int getCardStatus() {
		return cardStatus;
	}
	
	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus.ordinal();
	}
}
