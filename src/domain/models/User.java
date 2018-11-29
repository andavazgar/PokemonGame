/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.models;

import java.security.MessageDigest;
import java.util.Base64;

import com.google.gson.annotations.SerializedName;

public class User {
	private int id;
	private int version;
	
	@SerializedName("user")
	private String username;
	
	private transient String password;
	
	public User(int id, int version, String username, String password) {
		this.id = id;
		this.version = version;
		this.username = username;
		this.password = hashPassword(password);
	}
	
	public int getId() {
		return id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = hashPassword(password);
	}
	
	/*
	 * Source: https://stackoverflow.com/a/5531479
	 */
	private static String hashPassword(String password) {
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