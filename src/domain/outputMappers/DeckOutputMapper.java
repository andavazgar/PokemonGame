/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.outputMappers;

import java.util.ArrayList;
import java.util.List;

import domain.models.Card;
import domain.models.Deck;
import services.tdg.DeckTDG;

public class DeckOutputMapper {

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
}
