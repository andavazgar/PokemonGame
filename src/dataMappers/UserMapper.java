/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package dataMappers;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import domainModels.User;
import services.DatabaseManager;
import tdg.UserTDG;

public class UserMapper {
	public int insert(User user) {
		int output = 0;
		output = UserTDG.insert(user.getId(), user.getVersion(), user.getUsername(), user.getPassword());
		
		return output;
	}
	
	public int update(User user) {
		int output = 0;
		output = UserTDG.update(user.getId(), user.getVersion(), user.getUsername(), user.getPassword());
		
		return output;
	}
	
	public int delete(User user) {
		int output = 0;
		output = UserTDG.delete(user.getId());
		
		return output;
	}
	
	public static UserTDG find(int id) {
		String query = "SELECT * FROM " + tableName + " WHERE id = ?;";
		Connection conn = DatabaseManager.getConnection();
		UserTDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output = new UserTDG(rs.getInt("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password"));
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

	public static UserTDG find(String username) {
		String query = "SELECT * FROM " + tableName + " WHERE username = ?;";
		Connection conn = DatabaseManager.getConnection();
		UserTDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				output = new UserTDG(rs.getInt("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password"));
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
	
	public static UserTDG find(String username, String password) {
		String query = "SELECT * FROM " + tableName + " WHERE username = ? AND password = ?;";
		Connection conn = DatabaseManager.getConnection();
		UserTDG output = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, hashPassword(password));
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				output = new UserTDG(rs.getInt("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password"));
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
	
	public static List<UserTDG> findAll() {
		String query = "SELECT * FROM " + tableName + ";";
		Connection conn = DatabaseManager.getConnection();
		List<UserTDG> output = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                output.add(new UserTDG(rs.getInt("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password")));
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