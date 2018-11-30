/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.commands.challenge;

import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;
import org.dsrg.soenea.domain.command.validator.source.Source;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.role.IRole;
import org.dsrg.soenea.domain.role.impl.GuestRole;
import org.dsrg.soenea.domain.user.IUser;
import org.dsrg.soenea.domain.user.UserFactory;
import org.dsrg.soenea.domain.user.mapper.UserInputMapper;

import domain.roles.RegisteredRole;

public class ChallengePlayerCommand extends ValidatorCommand {

	@Source
	public Object object;
	
	public ChallengePlayerCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try{
			object = InputMapper.find(fields);
			throw new CommandException("Message");
		} catch (MapperException e) {}
		
		try {
			object = Factory.createNew(field1, field2);			
		} catch (Exception e) {
			e.printStackTrace();
			addNotification(e.getMessage());
			throw new CommandException(e);
		}
	}

}