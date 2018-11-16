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

import com.google.gson.annotations.SerializedName;

import services.DatabaseManager;

public class CardRDG extends AbstractRDG {
	private static final String tableName = "Cards";
	private static int nextID = 0;
	private transient int deckID;
	private transient int deckPosition;
	
	@SerializedName("t")
	private char cardType;
	
	@SerializedName("n")
	private String cardName;
	
	
	public CardRDG(int id, int deckID, int deckPosition, char cardType, String cardName) {
		super(id);
		this.deckID = deckID;
		this.deckPosition = deckPosition;
		this.cardType = cardType;
		this.cardName = cardName;
	}
	
	public CardRDG(int id, int version, int deckID, int deckPosition, char cardType, String cardName) {
		super(id, version);
		this.deckID = deckID;
		this.deckPosition = deckPosition;
		this.cardType = cardType;
		this.cardName = cardName;
	}
	
	public static void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
							"id INT," +
							"version INT NOT NULL," +
							"deck_id INT NOT NULL," +
							"deck_position TINYINT NOT NULL," +
							"card_type CHAR(1) NOT NULL," +
							"card_name VARCHAR(255) NOT NULL," +
							"PRIMARY KEY (id)," +
							"FOREIGN KEY (deck_id) REFERENCES Decks(id)" +
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
		String query = "INSERT INTO " + tableName + " (id, version, deck_id, deck_position, card_type, card_name) VALUES (?, ?, ?, ?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, getId());
			ps.setInt(2, getVersion());
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
	
	public static CardRDG find(int cardID) {
		String query = "SELECT * FROM " + tableName + " WHERE id = ?;";
		Connection conn = DatabaseManager.getConnection();
		CardRDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, cardID);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output = new CardRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("deck_id"), rs.getInt("deck_position"), rs.getString("card_type").charAt(0), rs.getString("card_name"));
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
	
	public static CardRDG findCardByDeckPosition(int deckID, int deckPosition) {
		String query = "SELECT * FROM " + tableName + " WHERE deck_id = ? AND deck_position = ?;";
		Connection conn = DatabaseManager.getConnection();
		CardRDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, deckID);
			ps.setInt(2, deckPosition);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output = new CardRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("deck_id"), rs.getInt("deck_position"), rs.getString("card_type").charAt(0), rs.getString("card_name"));
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
	
	public static List<CardRDG> findCardsForUserID(int userID) {
		String query = "SELECT Cards.* FROM Users JOIN Decks ON Users.id = Decks.user_id JOIN Cards ON Decks.id = Cards.deck_id WHERE Decks.user_id = ?;";
		Connection conn = DatabaseManager.getConnection();
		List<CardRDG> output = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				output.add(new CardRDG(rs.getInt("id"), rs.getInt("version"), rs.getInt("deck_id"), rs.getInt("deck_position"), rs.getString("card_type").charAt(0), rs.getString("card_name")));
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
	
	public static int getNextCardID() {
		nextID = getNextID(tableName, nextID);
		
		return nextID;
	}
	
	public String valuesAsString() {
		String output = "(" + getId() + ", " + getVersion() + ", " + deckID + ", " + deckPosition + ", '" + cardType + "', '" + cardName + "')";
		
		return output;
	}

	public int getDeckID() {
		return deckID;
	}

	public void setDeckID(int deckID) {
		this.deckID = deckID;
	}

	public int getDeckPosition() {
		return deckPosition;
	}

	public void setDeckPosition(int deckPosition) {
		this.deckPosition = deckPosition;
	}

	public char getCardType() {
		return cardType;
	}

	public void setCardType(char cardType) {
		this.cardType = cardType;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
}
