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

public class DeckRDG extends AbstractRDG {
	private static final int numberOfCardsNeeded = 40;
	private static final String tableName = "Decks";
	private static int nextID = 0;
	private int userID;
	

	public DeckRDG(int id, int userID) {
		super(id);
		this.userID = userID;
	}
	
	public DeckRDG(int id, int version, int userID) {
		super(id, version);
		this.userID = userID;
	}
	
	public static void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
							"id INT," +
							"version INT NOT NULL," +
							"user_id INT NOT NULL," +
							"PRIMARY KEY (id)," +
							"FOREIGN KEY (user_id) REFERENCES Users(id)" +
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
		String query = "INSERT INTO " + tableName + " (id, version, user_id) VALUES (?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, getId());
			ps.setInt(2, getVersion());
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
	
	public int insertDeckCards(List<CardRDG> cards) {
		String query = "INSERT INTO Cards (id, version, deck_id, deck_position, card_type, card_name) VALUES ";
		List<String> cardsStringList = new ArrayList<>();
		String cardsString;
		int output = 0;
		
		for(CardRDG card: cards) {
			cardsStringList.add(card.valuesAsString());
		}
		
		cardsString = String.join(", ", cardsStringList);
		
		query = query + cardsString + ";";
		
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
	
	public static DeckRDG find(int id) {
		String query = "SELECT * FROM " + tableName + " WHERE id = ?;";
		Connection conn = DatabaseManager.getConnection();
		DeckRDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output = new DeckRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("user_id"));
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
	
	public static DeckRDG findByUserID(int userID) {
		String query = "SELECT * FROM " + tableName + " WHERE user_id = ?;";
		Connection conn = DatabaseManager.getConnection();
		DeckRDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output = new DeckRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("user_id"));
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
	
	
	public static int getNextDeckID() {
		nextID = getNextID(tableName, nextID);
		
		return nextID;
	}

	public static int getNumberOfCardsNeeded() {
		return numberOfCardsNeeded;
	}
}
