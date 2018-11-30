/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.inputMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.models.User;
import services.finders.UserFinder;

public class UserInputMapper {
	
	public static User find(long id) {
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