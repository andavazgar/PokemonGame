/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.inputMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.models.Card;
import services.finders.CardFinder;

public class CardInputMapper {
	
	public static Card find(int cardID) {
		ResultSet rs = CardFinder.find(cardID);
		Card output = null;
		
		try {
			while (rs.next()) {
			    output = new Card(rs.getInt("id"), rs.getInt("version"), rs.getInt("deck_id"), rs.getInt("deck_position"), rs.getString("card_type").charAt(0), rs.getString("card_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static Card findCardByDeckPosition(int deckID, int deckPosition) {
		ResultSet rs = CardFinder.findCardByDeckPosition(deckID, deckPosition);
		Card output = null;
		
		try {
			while (rs.next()) {
			    output = new Card(rs.getInt("id"), rs.getInt("version"), rs.getInt("deck_id"), rs.getInt("deck_position"), rs.getString("card_type").charAt(0), rs.getString("card_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static List<Card> findCardsForUserID(int userID) {
		ResultSet rs = CardFinder.findCardsForUserID(userID);
		List<Card> output = new ArrayList<>();
		
		try {
			while (rs.next()) {
				output.add(new Card(rs.getInt("id"), rs.getInt("version"), rs.getInt("deck_id"), rs.getInt("deck_position"), rs.getString("card_type").charAt(0), rs.getString("card_name")));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
}
