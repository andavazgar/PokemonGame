/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.models.User;
import services.finders.UserFinder;
import services.tdg.UserTDG;

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
	
	public static User find(int id) {
		ResultSet rs = UserFinder.find(id);
		User output = null;
		
		try {
			while (rs.next()) {
			    output = new User(rs.getInt("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}

	public static User find(String username) {
		ResultSet rs = UserFinder.find(username);
		User output = null;
		
		try {
			while (rs.next()) {
				output = new User(rs.getInt("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password"));
            }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static User find(String username, String password) {
		ResultSet rs = UserFinder.find(username, password);
		User output = null;
		
		try {
			while (rs.next()) {
				output = new User(rs.getInt("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password"));
            }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static List<User> findAll() {
		ResultSet rs = UserFinder.findAll();
		List<User> output = new ArrayList<>();
		
		try {
			while (rs.next()) {
                output.add(new User(rs.getInt("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password")));
            }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
}