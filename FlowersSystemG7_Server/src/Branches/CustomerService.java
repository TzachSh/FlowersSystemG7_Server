package Branches;

import java.io.Serializable;
import java.util.ArrayList;

import Customers.Complain;
import Users.Permission;

/**
 * Entity class contains customer service details
 *
 */
public class CustomerService extends Employee implements Serializable {
	
	private ArrayList<Complain> complainList;

	/**
	 * Getter for get the complain list
	 */
	public ArrayList<Complain> getComplainList() {
		return complainList;
	}

	/**
	 * Setter for set the complain list
	 * @param complainList complain list
	 */
	public void setComplainList(ArrayList<Complain> complainList) {
		this.complainList = complainList;
	}

	/**
	 * Constructor to initialize instance attributes of customer service
	 * @param uId user Id
	 * @param user user name
	 * @param password password
	 * @param isLogged state if the user is logged in
	 * @param permission user permission
	 * @param eId employee id
	 * @param role the role of employee
	 * @param branchId branch id
	 * @param complainList the complain list
	 */
	public CustomerService(int uId, String user, String password, boolean isLogged,Permission permission, int eId, Role role, int branchId,
			ArrayList<Complain> complainList) {
		super(uId, user, password, isLogged,permission, eId, role, branchId);
		this.complainList = complainList;
	}
	
}
