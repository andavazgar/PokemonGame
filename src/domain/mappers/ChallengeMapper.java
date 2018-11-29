/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.models.Challenge;
import services.finders.ChallengeFinder;
import services.tdg.ChallengeTDG;

public class ChallengeMapper {

	public int insert(Challenge challenge) {
		int output = 0;
		output = ChallengeTDG.insert(challenge.getId(), challenge.getVersion(), challenge.getChallenger(), challenge.getChallengee(), challenge.getChallengerDeck(), challenge.getStatus());
		
		return output;
	}
	
	public int update(Challenge challenge) {
		int output = 0;
		output = ChallengeTDG.update(challenge.getId(), challenge.getVersion(), challenge.getChallenger(), challenge.getChallengee(), challenge.getChallengerDeck(), challenge.getChallengeeDeck(), challenge.getStatus());
		
		return output;
	}
	
	public int delete(Challenge challenge) {
		int output = 0;
		output = ChallengeTDG.delete(challenge.getId());
		
		return output;
	}
	
	public static Challenge find(int challengeID) {
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

	public static List<Challenge> findOpenByChallenger(int challenger) {
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
	
	public static List<Challenge> findOpenByChallengee(int challengee) {
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
