package Branches;

import java.io.Serializable;
import java.util.ArrayList;

import Customers.Complain;

/**
 * 
 *	Entity Class Contain Branch Details 
 */
public class Branch implements Serializable {
	private int id;
	private String name;
	/** Contains all employees */
	private ArrayList<Employee> employeeList;
	/** Contains all complains */
	private ArrayList<Complain> complainList;
	
	/**
	 * Getter for get the branch name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for set the Branch name
	 * @param name The Branch name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get The Employee list of the Branch
	 * @return list of employees
	 */
	public ArrayList<Employee> getEmployeeList() {
		return employeeList;
	}
	
	/**
	 * Set The Employee list in The Branch
	 * @param employeeList The employee list
	 */
	public void setEmployeeList(ArrayList<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	
	/**
	 * Getter for get the branch id
	 * @return branch number
	 * 	 */
	public int getbId() {
		return id;
	}
	
	/**
	 * Setter for set the branch id
	 * @param id branch id
	 */
	public void setbId(int id) {
		this.id = id;
	}

	/**
	 * Constructor to initialize all instance attributes
	 * @param id branch id
	 * @param name branch name
	 */
	public Branch(int id , String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
}
