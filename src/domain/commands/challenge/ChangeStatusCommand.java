/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.commands.challenge;

import org.dsrg.soenea.application.servlet.impl.RequestAttributes;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;

import domain.enums.ChallengeStatus;
import domain.factories.GameFactory;
import domain.inputMappers.ChallengeInputMapper;
import domain.inputMappers.DeckInputMapper;
import domain.models.Challenge;
import domain.models.Deck;
import domain.outputMappers.ChallengeOutputMapper;

public class ChangeStatusCommand extends ValidatorCommand {
	
	public ChangeStatusCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		long userID = (long) helper.getSessionAttribute(RequestAttributes.CURRENT_USER_ID);
		long challengeID = (long) helper.getRequestAttribute("challenge");
		String newChallengeStatus = (String) helper.getRequestAttribute("newChallengeStatus");
		Challenge challenge = ChallengeInputMapper.find(challengeID);
		
		if(challenge == null) {
			throw new CommandException("The challenge provided was not found.");
		}
		
		if(challenge.getChallengee() != userID) {
			throw new CommandException("You cannot accept someone else's challenge.");
		}
		
		if(challenge.getChallengeStatus() != ChallengeStatus.open) {
			throw new CommandException("The challenge is not open. Therefore, it cannot be accepted.");
		}
		
		Deck deck = DeckInputMapper.findByUserID(userID);
		
		if(deck == null) {
			throw new CommandException("You need to upload a deck before accepting a challenge.");
		}
		
		challenge.setChallengeeDeck(deck.getId());
		
		switch (newChallengeStatus) {
		case "Accept":
			challenge.setStatus(ChallengeStatus.accepted);
			break;
		case "Refuse":
			challenge.setStatus(ChallengeStatus.refused);
			break;
		case "Withdraw":
			challenge.setStatus(ChallengeStatus.withdrawn);
			break;
		default:
			throw new CommandException("Invalid challenge status.");
		}
		
		ChallengeOutputMapper.update(challenge);
		
		GameFactory.createNew(challenge.getId());
	}

}