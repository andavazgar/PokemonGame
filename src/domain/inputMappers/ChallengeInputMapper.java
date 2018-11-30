/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.inputMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.models.Challenge;
import services.finders.ChallengeFinder;

public class ChallengeInputMapper {
	
	public static Challenge find(long challengeID) {
		ResultSet rs = ChallengeFinder.find(challengeID);
		Challenge output = null;
		
		try {
			while (rs.next()) {
			    output = new Challenge(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenger"), rs.getInt("challengee"), rs.getInt("challenger_deck"), rs.getInt("challengee_deck"), rs.getInt("status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}

	public static List<Challenge> findOpenByChallenger(long challenger) {
		ResultSet rs = ChallengeFinder.findOpenByChallenger(challenger);
		List<Challenge> output = new ArrayList<>();
		
		try {
			while (rs.next()) {
				output.add(new Challenge(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenger"), rs.getInt("challengee"), rs.getInt("challenger_deck"), rs.getInt("challengee_deck"), rs.getInt("status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static List<Challenge> findOpenByChallengee(long challengee) {
		ResultSet rs = ChallengeFinder.findOpenByChallengee(challengee);
		List<Challenge> output = new ArrayList<>();
		
		try {
			while (rs.next()) {
				output.add(new Challenge(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenger"), rs.getInt("challengee"), rs.getInt("challenger_deck"), rs.getInt("challengee_deck"), rs.getInt("status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static List<Challenge> findAll() {
		ResultSet rs = ChallengeFinder.findAll();
		List<Challenge> output = new ArrayList<>();
		
		try {
			while (rs.next()) {
				output.add(new Challenge(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenger"), rs.getInt("challengee"), rs.getInt("challenger_deck"), rs.getInt("challengee_deck"), rs.getInt("status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static List<Challenge> findAllOpen() {
		ResultSet rs = ChallengeFinder.findAllOpen();
		List<Challenge> output = new ArrayList<>();
		
		try {
			while (rs.next()) {
				output.add(new Challenge(rs.getInt("id"), rs.getInt("version"), rs.getInt("challenger"), rs.getInt("challengee"), rs.getInt("challenger_deck"), rs.getInt("challengee_deck"), rs.getInt("status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
}
