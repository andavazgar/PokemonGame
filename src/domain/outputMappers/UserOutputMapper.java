/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.outputMappers;

import domain.models.User;
import services.tdg.UserTDG;

public class UserOutputMapper {
	
	public static int insert(User user) {
		int output = 0;
		output = UserTDG.insert(user.getId(), user.getVersion(), user.getUsername(), user.getPassword());
		
		return output;
	}
	
	public static int update(User user) {
		int output = 0;
		output = UserTDG.update(user.getId(), user.getVersion(), user.getUsername(), user.getPassword());
		
		return output;
	}
	
	public static int delete(User user) {
		int output = 0;
		output = UserTDG.delete(user.getId());
		
		return output;
	}
}