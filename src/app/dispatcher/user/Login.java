/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package app.dispatcher.user;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.application.servlet.impl.RequestAttributes;
import org.dsrg.soenea.domain.command.CommandException;
import domain.commands.user.LoginCommand;

public class Login extends Dispatcher {

	@Override
	public void execute() throws ServletException, IOException {
		LoginCommand c = new LoginCommand(myHelper);
		try {
			myRequest.getSession(true).invalidate();
			c.execute();
			myRequest.getSession(true).setAttribute(RequestAttributes.CURRENT_USER_ID, c.currentUser.getId());
			forward("/WEB-INF/jsp/success.jsp");
		} catch (CommandException e) {
			forward("/WEB-INF/jsp/fail.jsp");
		}
	}

}
