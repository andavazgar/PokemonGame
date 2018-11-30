/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.factories;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.role.IRole;
import org.dsrg.soenea.domain.user.User;
import org.dsrg.soenea.service.tdg.UserTDG;
import org.dsrg.soenea.uow.UoW;

public class CardFactory {
	public static  User createNew(String username, String password, List<IRole> roles) throws SQLException, MapperException{
		return createNew(UserTDG.getMaxId(), 1, username, password, roles);
	}
	
	public static  User createNew(long id, long version, String username, String password, List<IRole> roles) throws SQLException, MapperException{
		User u = new User(id, 1, username, roles);
		u.setPassword(password);
		UoW.getCurrent().registerNew(u);
		return u;
	}
	
	public static User createClean(long id, long version, String username, List<IRole> roles) {
		User u = new User(id, version, username, roles);
		UoW.getCurrent().registerClean(u);
		return u;
	}
}
