package Branches;

import java.io.Serializable;

import Users.*;

public class Employee extends User implements Serializable {
	
	private int eId;
	private Role role;
	private int branchId;
	
	public int getBranchId() {
		return branchId;
	}
		
	public void setBranchId(int branchId) {
		this.branchId = branchId;
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

	public Employee(int uId, String user, String password, boolean isLogged,Users.Permission permission,int eId,Role role,int branchId) {
		super(uId, user, password, isLogged,permission);
		// TODO Auto-generated constructor stub
		seteId(eId);
		setRole(role);
		setBranchId(branchId);
	}
	
	public Employee(User user, int eId,Role role,int branchId)
	{
		super(user);
		seteId(eId);
		setRole(role);
		setBranchId(branchId);
	}
	
	public Employee(int uId, int eId,Role role,int branchId)
	{
		super(uId);
		seteId(eId);
		setRole(role);
		setBranchId(branchId);
	}
}
