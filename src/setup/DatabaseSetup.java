/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package setup;

import models.GameDetails;
import rdg.CardRDG;
import rdg.ChallengeRDG;
import rdg.DeckRDG;
import rdg.GameRDG;
import rdg.PlayingCardRDG;
import rdg.UserRDG;

public class DatabaseSetup {
	
	public static void main(String[] args) {
		
		System.out.println("Dropping views ...");
		
		GameDetails.dropView();
		
		
		System.out.println("\nDropping tables ...");
		
		PlayingCardRDG.dropTable();
		GameRDG.dropTable();
		CardRDG.dropTable();
		ChallengeRDG.dropTable();
		DeckRDG.dropTable();
		UserRDG.dropTable();
		
		System.out.println("\nCreating tables ...");
		
		UserRDG.createTable();
		DeckRDG.createTable();
		ChallengeRDG.createTable();
		CardRDG.createTable();
		GameRDG.createTable();
		PlayingCardRDG.createTable();
		
		System.out.println("\nCreating Views ...");
		
		GameDetails.createView();
		
		System.out.println("\nDone!");
	}
}
