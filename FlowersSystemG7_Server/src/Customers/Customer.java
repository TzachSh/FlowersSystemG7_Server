package Customers;

import java.io.Serializable;
import java.util.ArrayList;

import Orders.Order;
import Users.Permission;
import Users.User;

/***
 * Entity
 * Customer with id, orders, actual account number and complains
 */
public class Customer extends User implements Serializable {

	private int id;
	private ArrayList<Order> orderList;
	private ArrayList<Complain> complainList;
	private int accountNum;
	/**
	 * 
	 * @return the customer's id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id setting the customer's id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 
	 * @return the customer's order list
	 */
	public ArrayList<Order> getOrderList() {
		return orderList;
	}
	/**
	 * 
	 * @param orderList setting the customer's order list
	 */
	public void setOrderList(ArrayList<Order> orderList) {
		this.orderList = orderList;
	}
	/**
	 * 
	 * @return the customer's complain list
	 */
	public ArrayList<Complain> getComplainList() {
		return complainList;
	}
	/**
	 * 
	 * @param complainList setting the customer's complain list
	 */
	public void setComplainList(ArrayList<Complain> complainList) {
		this.complainList = complainList;
	}
	/**
	 * 
	 * @return account's number
	 */
	public int getAccountNum() {
		return accountNum;
	}
	/**
	 * 
	 * @param accountNum setting the account's number
	 */
	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}
	/**
	 * Customer constructor
	 * @param uId customer's id
	 * @param user customer's user 
	 * @param password customer's password
	 * @param isLogged if the customer is logged or not 
	 * @param permission customer's permission 
	 * @param id customer number
	 * @param orderList customer's order list
	 * @param complainList customer's complain list
	 * @param accountNum
	 */
	public Customer(int uId, String user, String password, boolean isLogged, Permission permission, int id,
			ArrayList<Order> orderList, ArrayList<Complain> complainList, int accountNum) {
		super(uId, user, password, isLogged, permission);
		this.id = id;
		this.orderList = orderList;
		this.complainList = complainList;
		this.accountNum = accountNum;
	}
	/**
	 * 
	 * @param uId customer's id
	 * @param user customer's user
	 * @param password customer's password
	 * @param isLogged if the customer is logged or not 
	 * @param permission customer's permission 
	 */
	public Customer(int uId, String user, String password, boolean isLogged, Permission permission) {
		super(uId, user, password, isLogged, permission);
	}
	/**
	 * 
	 * @param id customer number 
	 * @param uId customer's id
	 */
	public Customer(int id,int uId ) {
		super(uId);
		this.id=id;
	}
	/**
	 * 
	 * @param user customer's user
	 * @param id customer number
	 */
	public Customer(User user, int id)
	{
		super(user);
		this.id=id;
	}
	/**
	 * 
	 * @param cId customer number
	 */
	public Customer( int cId)
	{
		super(cId);
		this.id=cId;
	}
}
