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

	public static int insert(long id, long version, long challenger, long challengee, long challengerDeck, int status) {
		String query = "INSERT INTO " + TABLE_NAME + " (id, version, challenger, challengee, challenger_deck, status) VALUES (?, ?, ?, ?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			ps.setLong(2, version);
			ps.setLong(3, challenger);
			ps.setLong(4, challengee);
			ps.setLong(5, challengerDeck);
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
	
	public static int update(long id, long version, long challenger, long challengee, long challengerDeck, long challengeeDeck, int status) {
		String query = "UPDATE " + TABLE_NAME + " SET version = ?, challenger = ?, challengee = ?, challenger_deck = ?, challengee_deck = ?, status = ? WHERE id = ? AND version = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, version +1);
			ps.setLong(2, challenger);
			ps.setLong(3, challengee);
			ps.setLong(4, challengerDeck);
			
			if(challengeeDeck != 0) {
				ps.setLong(5, challengeeDeck);
			}
			else {
				ps.setNull(5, Types.INTEGER);
			}
			ps.setInt(6, status);
			ps.setLong(7, id);
			ps.setLong(8, version);
			
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
