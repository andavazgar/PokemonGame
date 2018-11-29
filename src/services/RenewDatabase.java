/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package services;

import app.PokemonFC;
import domain.outputModels.GameDetails;
import services.tdg.UserTDG;
import services.tdg.CardTDG;
import services.tdg.ChallengeTDG;
import services.tdg.DeckTDG;
import services.tdg.GameTDG;
import services.tdg.PlayingCardTDG;

public class RenewDatabase {

	public static void main(String[] args) {
		PokemonFC.prepareDbRegistry("");
		
		System.out.println("Dropping views ...");
		GameDetails.dropView();
		
		
		System.out.println("\nDropping tables ...");
		PlayingCardTDG.dropTable();
		GameTDG.dropTable();
		CardTDG.dropTable();
		ChallengeTDG.dropTable();
		DeckTDG.dropTable();
		services.tdg.UserTDG.dropTable();
			
			//TODO: Delete this
			try {
				org.dsrg.soenea.service.tdg.UserTDG.dropTable();
			} catch (Exception e) {}
		
		try {
			org.dsrg.soenea.service.tdg.UserTDG.dropUserRoleTable();
		} catch(Exception e){}
		
		
			System.out.println("\nCreating tables ...");
			UserTDG.createTable();
			DeckTDG.createTable();
			ChallengeTDG.createTable();
			CardTDG.createTable();
			GameTDG.createTable();
			PlayingCardTDG.createTable();
			
			System.out.println("\nCreating Views ...");
			GameDetails.createView();
			
		try {
			//TODO: Delete this
			org.dsrg.soenea.service.tdg.UserTDG.createTable();
			
			org.dsrg.soenea.service.tdg.UserTDG.createUserRoleTable();
		} catch(Exception e){}
		
		
		System.out.println("\nDone!");
	}
}
