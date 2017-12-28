package Customers;

import java.io.Serializable;
import java.util.ArrayList;

import Orders.Order;
import Users.Permission;
import Users.User;

public class Customer extends User implements Serializable {

	private int id;
	private ArrayList<Order> orderList;
	private ArrayList<Complain> complainList;
	private int membershipId;
	private int accountNum;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<Order> getOrderList() {
		return orderList;
	}
	public void setOrderList(ArrayList<Order> orderList) {
		this.orderList = orderList;
	}
	public ArrayList<Complain> getComplainList() {
		return complainList;
	}
	public void setComplainList(ArrayList<Complain> complainList) {
		this.complainList = complainList;
	}
	public int getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(int membershipId) {
		this.membershipId = membershipId;
	}
	public int getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}
	public Customer(int uId, String user, String password, boolean isLogged, Permission permission, int id,
			ArrayList<Order> orderList, ArrayList<Complain> complainList, int membershipId, int accountNum) {
		super(uId, user, password, isLogged, permission);
		this.id = id;
		this.orderList = orderList;
		this.complainList = complainList;
		this.membershipId = membershipId;
		this.accountNum = accountNum;
	}
	
	
}
