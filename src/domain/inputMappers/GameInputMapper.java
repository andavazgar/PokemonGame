/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.inputMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.models.Game;
import services.finders.GameFinder;

public class GameInputMapper {
	
	public static Game find(int gameID) {
		ResultSet rs = GameFinder.find(gameID);
		Game output = null;
		
		try {
			while (rs.next()) {
			    output = new Game(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenge_id"), rs.getInt("challenger_status"), rs.getInt("challengee_status"), rs.getInt("game_status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static List<Game> findAll() {
		ResultSet rs = GameFinder.findAll();
		List<Game> output = new ArrayList<>();
		
		try {
			while (rs.next()) {
				output.add(new Game(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenge_id"), rs.getInt("challenger_status"), rs.getInt("challengee_status"), rs.getInt("game_status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
}
