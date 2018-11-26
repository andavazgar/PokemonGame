/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package tdg;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import services.DatabaseManager;

public class UserTDG extends AbstractTDG {
	private static final String tableName = "Users";
	private static int nextID = 0;
	
	public static void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
							"id INT," +
							"version INT NOT NULL," +
							"username VARCHAR(255) NOT NULL UNIQUE," +
							"password CHAR(44) NOT NULL," +
							"PRIMARY KEY (id)" +
						");";
		createTable(tableName, query);
	}
	
	public static void truncateTable() {
		truncateTable(tableName);
	}
	
	public static void dropTable() {
		dropTable(tableName);
	}

	public static int insert(int id, int version, String username, String password) {
		String query = "INSERT INTO " + tableName + " (id, version, username, password) VALUES (?, ?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.setInt(2, version);
			ps.setString(3, username);
			ps.setString(4, hashPassword(password));
			
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
	
	public static int update(int id, int version, String username, String password) {
		String query = "UPDATE " + tableName + " SET version = ?, username = ?, password = ? WHERE id = ? AND version = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, version +1);
			ps.setString(2, username);
			ps.setString(3, hashPassword(password));
			ps.setInt(4, id);
			ps.setInt(5, version);
			
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

	public static ResultSet find(String username) {
		String query = "SELECT * FROM " + tableName + " WHERE username = ?;";
		Connection conn = DatabaseManager.getConnection();
		ResultSet output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			
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
	
	public static ResultSet find(String username, String password) {
		String query = "SELECT * FROM " + tableName + " WHERE username = ? AND password = ?;";
		Connection conn = DatabaseManager.getConnection();
		ResultSet output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, hashPassword(password));
			
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
	
	public static int getNextUserID() {
		nextID = getNextID(tableName, nextID);
		
		return nextID;
	}
	
	/*
	 * Source: https://stackoverflow.com/a/5531479
	 */
	private static String hashPassword(String password) {
		if(password.length() == 44) {
			return password;
		}
		else {
			String hashedPassword = null;
			
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] hash = digest.digest(password.getBytes());
				hashedPassword = Base64.getEncoder().encodeToString(hash);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			return hashedPassword;
		}
	}
}