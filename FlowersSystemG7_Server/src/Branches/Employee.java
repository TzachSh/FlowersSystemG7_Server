package Branches;

import java.io.Serializable;

import Users.*;

public class Employee extends User implements Serializable {
	
	private int eId;
	private Role role;
	private Branch branch;
	
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int geteId() {
		return eId;
	}

	public void seteId(int eId) {
		this.eId = eId;
	}

	public Employee(int uId, String user, String password, boolean isLogged,Users.Permission permission,int eId,Role role,Branch branch) {
		super(uId, user, password, isLogged,permission);
		// TODO Auto-generated constructor stub
		seteId(eId);
		setRole(role);
		setBranch(branch);
	}
}
