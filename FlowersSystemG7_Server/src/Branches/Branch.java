package Branches;

import java.util.ArrayList;
//asdisahdsaoida
/**
 * 
 *	This Class Contain Branch Details 
 */
public class Branch {
	private int id;
	private String name;
	private ArrayList<Employee> employeeList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Employee> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(ArrayList<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	public int getbId() {
		return id;
	}
	public void setbId(int id) {
		this.id = id;
	}

	public Branch(int id , ArrayList<Employee> employeeList) {
		this.id = id;
		this.employeeList = employeeList;
	}
}
