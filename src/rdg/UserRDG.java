/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package rdg;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import services.DatabaseManager;

public class UserRDG extends AbstractRDG {
	private static final String tableName = "Users";
	private static int nextID = 0;
	
	@SerializedName("user")
	private String username;
	
	private transient String password;
	

	public UserRDG(int id, String username, String password) {
		super(id);
		this.username = username;
		this.password = hashPassword(password);
	}
	
	// Used for reading records from the database. Version is not necessarily 1.
	private UserRDG(int id, int version, String username, String password) {
		super(id, version);
		this.username = username;
		this.password = hashPassword(password);
	}
	
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

	public int insert() {
		String query = "INSERT INTO " + tableName + " (id, version, username, password) VALUES (?, ?, ?, ?);";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, getId());
			ps.setInt(2, getVersion());
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
	
	public int update() {
		String query = "UPDATE " + tableName + " SET version = ?, username = ?, password = ? WHERE id = ? AND version = ?;";
		Connection conn = DatabaseManager.getConnection();
		int output = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, getVersion() +1);
			ps.setString(2, username);
			ps.setString(3, hashPassword(password));
			ps.setInt(4, getId());
			ps.setInt(5, getVersion());
			
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
	
	public static UserRDG find(int id) {
		String query = "SELECT * FROM " + tableName + " WHERE id = ?;";
		Connection conn = DatabaseManager.getConnection();
		UserRDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output = new UserRDG(rs.getInt("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password"));
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

	public static UserRDG find(String username) {
		String query = "SELECT * FROM " + tableName + " WHERE username = ?;";
		Connection conn = DatabaseManager.getConnection();
		UserRDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				output = new UserRDG(rs.getInt("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password"));
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
	
	public static UserRDG find(String username, String password) {
		String query = "SELECT * FROM " + tableName + " WHERE username = ? AND password = ?;";
		Connection conn = DatabaseManager.getConnection();
		UserRDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, hashPassword(password));
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				output = new UserRDG(rs.getInt("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password"));
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
	
	public static List<UserRDG> findAll() {
		String query = "SELECT * FROM " + tableName + ";";
		Connection conn = DatabaseManager.getConnection();
		List<UserRDG> output = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output.add(new UserRDG(rs.getInt("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password")));
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
	
	public static int getNextUserID() {
		nextID = getNextID(tableName, nextID);
		
		return nextID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = hashPassword(password);
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