package Users;

import java.io.Serializable;

/***
 * 
 * Entity class to define a user in the system
 *
 */
public class User implements Serializable {
	/***
	 * Instance variables
	 */
	private int uId;
	private String user;
	private String password;
	private boolean isLogged;
	private Permission permission;
	/***
	 * 
	 * @return user's permission
	 */
	public Permission getPermission() {
		return permission;
	}
	/***
	 * 
	 * @param permission to set
	 */
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	/***
	 * 
	 * @return user id
	 */
	public  int getuId() {
		return uId;
	}
	/***
	 * 
	 * @param uId to set
	 */
	public void setuId(int uId) {
		this.uId = uId;
	}
	/***
	 * 
	 * @return user name
	 */
	public String getUser() {
		return user;
	}
	/***
	 * 
	 * @param user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/***
	 * 
	 * @return user's password
	 */
	public String getPassword() {
		return password;
	}
	/***
	 * 
	 * @param password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/***
	 * 
	 * @return the logged state
	 */
	public boolean isLogged() {
		return isLogged;
	}
	/***
	 * 
	 * @param isLogged to set
	 */
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	/***
	 * Constructor for the server side use
	 * 
	 * @param uId
	 * @param user
	 * @param password
	 * @param isLogged
	 * @param permission
	 */
	public User(int uId, String user, String password, boolean isLogged , Permission permission) {
		super();
		this.uId = uId;
		this.user = user;
		this.password = password;
		this.isLogged = isLogged;
		this.permission = permission;
	}
	/***
	 * Constructor for the client side use
	 * 
	 * @param user
	 * @param password
	 * @param isLogged
	 * @param permission
	 */
	public User(String user, String password, boolean isLogged , Permission permission) {
		super();
		this.user = user;
		this.password = password;
		this.isLogged = isLogged;
		this.permission = permission;
	}
	/***
	 * Minimal constructor
	 * @param user
	 * @param password
	 */
	public User(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}
	/***
	 * Initialize only uId
	 * @param uId
	 */
	public User(int uId) {
		super();
		this.uId = uId;
	}
	/***
	 * Copy Constructor
	 * @param user
	 */
	public User(User user) {
		this(user.uId, user.user, user.password, user.isLogged, user.permission);
	}
}