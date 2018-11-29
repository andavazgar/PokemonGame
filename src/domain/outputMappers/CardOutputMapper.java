/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.outputMappers;

import domain.models.Card;
import services.tdg.CardTDG;

public class CardOutputMapper {

	public static int insert(Card card) {
		int output = 0;
		output = CardTDG.insert(card.getId(), card.getVersion(), card.getDeckID(), card.getDeckPosition(), card.getCardType(), card.getCardName());
		
		return output;
	}
}
