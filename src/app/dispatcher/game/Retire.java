/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package app.dispatcher.game;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.uow.UoW;

import domain.commands.game.RetireCommand;

public class Retire extends Dispatcher {

	@Override
	public void execute() throws ServletException, IOException {
		RetireCommand c = new RetireCommand(myHelper);
		try {
			c.execute();
			try {
				UoW.getCurrent().commit();
				myHelper.setRequestAttribute("message", "");
				forward("/WEB-INF/jsp/success.jsp");
			} catch (Exception e) {
				myHelper.setRequestAttribute("message", e.getMessage());
				forward("/WEB-INF/jsp/fail.jsp");
			}
			
		} catch (CommandException e) {
			forward("/WEB-INF/jsp/fail.jsp");
		}
	}
}