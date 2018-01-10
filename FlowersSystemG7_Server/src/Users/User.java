package Users;

import java.io.Serializable;

public class User implements Serializable {
	private static int uId;
	private String user;
	private String password;
	private boolean isLogged;
	private Permission permission;
	
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public static int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLogged() {
		return isLogged;
	}
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	public User(int uId, String user, String password, boolean isLogged , Permission permission) {
		super();
		this.uId = uId;
		this.user = user;
		this.password = password;
		this.isLogged = isLogged;
		this.permission = permission;
	}
	public User( String user, String password, boolean isLogged , Permission permission) {
		super();
		this.user = user;
		this.password = password;
		this.isLogged = isLogged;
		this.permission = permission;
	}
	public User( int uId) {
		super();
		this.uId = uId;
	}
}