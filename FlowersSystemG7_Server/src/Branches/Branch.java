package Branches;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 *	This Class Contain Branch Details 
 */
public class Branch implements Serializable {
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

	public Branch(int id ,String name, ArrayList<Employee> employeeList) {
		this.id = id;
		this.name = name;
		this.employeeList = employeeList;
	}
	public Branch(int id, String name) {
		this.id = id;
		this.name = name;
	}
}
