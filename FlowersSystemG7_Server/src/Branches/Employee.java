package Branches;

import java.io.Serializable;

import Users.*;

/**
 * Entity class that contains the employee details
 *
 */
public class Employee extends User implements Serializable {
	/** Employee id */
	private int eId;
	/** The role of employee */
	private Role role;
	/** The branch of employee */
	private int branchId;
	
	/**
	 * Getter for get the branch id
	 */
	public int getBranchId() {
		return branchId;
	}
		
	/**
	 * Setter for set the branch id
	 * @param branchId branch id
	 */
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	/**
	 * Getter for get the role of employee
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Setter for set the role of employee
	 * @param role the role of employee
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Getter of get the employee id
	 */
	public int geteId() {
		return eId;
	}

	/**
	 * Setter for set the employee id
	 * @param eId
	 */
	public void seteId(int eId) {
		this.eId = eId;
	}

	/**
	 * Constructor that initialize instance attributes of employee
     * @param uId user Id
	 * @param user user name
	 * @param password password
	 * @param isLogged state if the user is logged in
	 * @param permission user permission
	 * @param eId employee id
	 * @param role the role of employee
	 * @param branchId branch id
	 */
	public Employee(int uId, String user, String password, boolean isLogged,Users.Permission permission,int eId,Role role,int branchId) {
		super(uId, user, password, isLogged,permission);
		// TODO Auto-generated constructor stub
		seteId(eId);
		setRole(role);
		setBranchId(branchId);
	}
	
	/**
	 * Constructor that initialize instance attributes of employee
	 * @param user user name
	 * @param eId employee id
	 * @param role the role of employee
	 * @param branchId branch id
	 */
	public Employee(User user, int eId,Role role,int branchId)
	{
		super(user);
		seteId(eId);
		setRole(role);
		setBranchId(branchId);
	}
	
	
	/**
	 * Constructor that initialize instance attributes of employee
	 * @param uId user id
	 * @param eId employee id
	 * @param role the role of employee
	 * @param branchId branch id
	 */
	public Employee(int uId, int eId,Role role,int branchId)
	{
		super(uId);
		seteId(eId);
		setRole(role);
		setBranchId(branchId);
	}
}
