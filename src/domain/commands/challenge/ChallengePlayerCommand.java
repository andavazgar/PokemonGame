/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.commands.challenge;

import org.dsrg.soenea.application.servlet.impl.RequestAttributes;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;

import domain.models.Deck;
import domain.models.User;
import domain.factories.ChallengeFactory;
import domain.inputMappers.DeckInputMapper;
import domain.inputMappers.UserInputMapper;

public class ChallengePlayerCommand extends ValidatorCommand {
	
	public ChallengePlayerCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		long challengerID = (long) helper.getSessionAttribute(RequestAttributes.CURRENT_USER_ID);
		long challengeeID = (long) helper.getRequestAttribute("player");
		
		if(challengerID == challengeeID) {
			throw new CommandException("You cannot challenge yourself.");
		}
		
		User challengee = UserInputMapper.find(challengeeID);
		
		if(challengee == null) {
			throw new CommandException("Invalid challengee ID.");
		}
		
		Deck deck = DeckInputMapper.findByUserID(challengerID);
		
		if(deck == null) {
			throw new CommandException("You need to upload a deck before issuing a challenge.");
		}
		
		ChallengeFactory.createNew(challengerID, challengeeID, deck.getId());
	}
}