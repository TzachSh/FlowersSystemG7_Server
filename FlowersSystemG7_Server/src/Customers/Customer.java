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
	private Membership membership;
	private Account account;
	
	public Customer(int uId, String user, String password, boolean isLogged,Permission permission, int id, ArrayList<Order> orderList,
			ArrayList<Complain> complainList, Membership membership) {
		super(uId, user, password, isLogged,permission);
		this.id = id;
		this.orderList = orderList;
		this.complainList = complainList;
		this.membership = membership;
	}
}
