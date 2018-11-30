/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package services.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.dsrg.soenea.service.UniqueIdFactory;

import services.DatabaseManager;

public class ChallengeTDG extends AbstractTDG {
	private static final String TABLE_NAME = "Challenges";
	
	public static void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
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
		createTable(TABLE_NAME, query);
	}
	
	public static void truncateTable() {
		truncateTable(TABLE_NAME);
	}
	
	public static void dropTable() {
		dropTable(TABLE_NAME);
	}

	public static int insert(int id, int version, int challenger, int challengee, int challengerDeck, int status) {
		String query = "INSERT INTO " + TABLE_NAME + " (id, version, challenger, challengee, challenger_deck, status) VALUES (?, ?, ?, ?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.setInt(2, version);
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
	
	public static int update(int id, int version, int challenger, int challengee, int challengerDeck, int challengeeDeck, int status) {
		String query = "UPDATE " + TABLE_NAME + " SET version = ?, challenger = ?, challengee = ?, challenger_deck = ?, challengee_deck = ?, status = ? WHERE id = ? AND version = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, version +1);
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
			ps.setInt(7, id);
			ps.setInt(8, version);
			
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
