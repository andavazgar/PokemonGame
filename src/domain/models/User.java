/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.models;

import org.dsrg.soenea.domain.DomainObject;

import com.google.gson.annotations.SerializedName;

public class User extends DomainObject<Long> {	
	@SerializedName("user")
	private String username;
	private transient String password;
	
	public User(long id, long version, String username, String password) {
		super(id, version);
		this.username = username;
		this.password = password;
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
		this.password = password;
	}
}