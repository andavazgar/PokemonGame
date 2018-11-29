/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.dataMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.models.Card;
import domain.models.Deck;
import services.tdg.DeckTDG;

public class DeckMapper {

	public static int insert(Deck deck) {
		int output = 0;
		output = DeckTDG.insert(deck.getId(), deck.getVersion(), deck.getUserID());
		
		return output;
	}
	
	public static int insertDeckCards(Deck deck) {
		List<String> cardsStringList = new ArrayList<>();
		String cardsString;
		int output = 0;
		
		if(deck.getDeckCards().size() != Deck.getNumberOfCardsNeeded()) {
			System.out.println("You need " + Deck.getNumberOfCardsNeeded() + " cards in a deck. Unsuccessful insert.");
			return 0;
		}
		
		for(Card card: deck.getDeckCards()) {
			cardsStringList.add(card.valuesAsString());
		}
		
		cardsString = String.join(", ", cardsStringList);
		
		output = DeckTDG.insertDeckCards(cardsString);
		
		return output;
	}
	
	public static Deck find(int id) {
		ResultSet rs = DeckTDG.find(id);
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
		ResultSet rs = DeckTDG.findByUserID(userID);
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
