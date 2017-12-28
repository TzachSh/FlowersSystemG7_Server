package Orders;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import Commons.*;
import Customers.Customer;

public class Order implements Serializable {
	private int id;
	private Date creationDate;
	private Date requestedDate;
	private Customer customer;
	private Status status;
	private OrderPayment orderPayment;
	private ArrayList<ProductInOrder> productInOrderList;
	private Refund Refund;
	private Delivery deliery;
	
	public int getoId() {
		return id;
	}
	public void setoId(int id) {
		this.id = id;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public OrderPayment getOrderPayment() {
		return orderPayment;
	}
	public void setOrderPayment(OrderPayment orderPayment) {
		this.orderPayment = orderPayment;
	}
	public ArrayList<ProductInOrder> getProductInOrderList() {
		return productInOrderList;
	}
	public void setProductInOrderList(ArrayList<ProductInOrder> productInOrderList) {
		this.productInOrderList = productInOrderList;
	}
	public Refund getRefund() {
		return Refund;
	}
	public void setRefund(Refund refund) {
		Refund = refund;
	}
	public Delivery getDeliery() {
		return deliery;
	}
	public void setDeliery(Delivery deliery) {
		this.deliery = deliery;
	}
	public Order(int id, Date creationDate, Date requestedDate, Customer customer, Status status,
			OrderPayment orderPayment, ArrayList<ProductInOrder> productInOrderList, Commons.Refund refund,
			Delivery deliery) {

		this.id = id;
		this.creationDate = creationDate;
		this.requestedDate = requestedDate;
		this.customer = customer;
		this.status = status;
		this.orderPayment = orderPayment;
		this.productInOrderList = productInOrderList;
		Refund = refund;
		this.deliery = deliery;
	}

}
