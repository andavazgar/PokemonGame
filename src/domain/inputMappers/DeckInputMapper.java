/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.inputMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.models.Deck;
import services.finders.DeckFinder;

public class DeckInputMapper {
	
	public static Deck find(int id) {
		ResultSet rs = DeckFinder.find(id);
		Deck output = null;
		
		try {
			while (rs.next()) {
			    output = new Deck(rs.getInt("id"), rs.getInt("version"), rs.getInt("user_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static Deck findByUserID(int userID) {
		ResultSet rs = DeckFinder.findByUserID(userID);
		Deck output = null;
		
		try {
			while (rs.next()) {
			    output = new Deck(rs.getInt("id"), rs.getInt("version"), rs.getInt("user_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
}
