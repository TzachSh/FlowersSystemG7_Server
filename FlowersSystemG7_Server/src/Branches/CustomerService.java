package Branches;

import java.io.Serializable;
import java.util.ArrayList;

import Customers.Complain;
import Users.Permission;

public class CustomerService extends Employee implements Serializable {
	
	private ArrayList<Complain> complainList;

	public ArrayList<Complain> getComplainList() {
		return complainList;
	}

	public void setComplainList(ArrayList<Complain> complainList) {
		this.complainList = complainList;
	}

	
	public CustomerService(int uId, String user, String password, boolean isLogged,Permission permission, int eId, Role role, int branchId,
			ArrayList<Complain> complainList) {
		super(uId, user, password, isLogged,permission, eId, role, branchId);
		this.complainList = complainList;
	}
	
}
