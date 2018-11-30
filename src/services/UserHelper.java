/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package services;

import java.security.MessageDigest;
import java.util.Base64;

public class UserHelper {
	/*
	 * Source: https://stackoverflow.com/a/5531479
	 */
	public static String hashPassword(String password) {
		if(password.length() == 44) {
			return password;
		}
		else {
			String hashedPassword = null;
			
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] hash = digest.digest(password.getBytes());
				hashedPassword = Base64.getEncoder().encodeToString(hash);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			return hashedPassword;
		}
	}
}
