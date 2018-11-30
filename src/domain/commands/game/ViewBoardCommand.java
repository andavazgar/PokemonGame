/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.commands.game;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.validator.source.Source;
import org.dsrg.soenea.domain.helper.Helper;

public class ViewBoardCommand extends ValidatorCommand {

	@Source
	public Object object;
	
	public ViewBoardCommand(Helper helper) {
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