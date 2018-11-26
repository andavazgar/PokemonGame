/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package setup;

import models.GameDetails;
import tdg.CardTDG;
import tdg.ChallengeTDG;
import tdg.DeckTDG;
import tdg.GameTDG;
import tdg.PlayingCardTDG;
import tdg.UserTDG;

public class DatabaseSetup {
	
	public static void main(String[] args) {
		
		System.out.println("Dropping views ...");
		
		GameDetails.dropView();
		
		
		System.out.println("\nDropping tables ...");
		
		PlayingCardTDG.dropTable();
		GameTDG.dropTable();
		CardTDG.dropTable();
		ChallengeTDG.dropTable();
		DeckTDG.dropTable();
		UserTDG.dropTable();
		
		System.out.println("\nCreating tables ...");
		
		UserTDG.createTable();
		DeckTDG.createTable();
		ChallengeTDG.createTable();
		CardTDG.createTable();
		GameTDG.createTable();
		PlayingCardTDG.createTable();
		
		System.out.println("\nCreating Views ...");
		
		GameDetails.createView();
		
		System.out.println("\nDone!");
	}
}
