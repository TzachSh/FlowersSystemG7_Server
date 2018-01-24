package Orders;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import Commons.*;
import Customers.Customer;

public class Order implements Serializable {
	private int id;
	private Date creationDate;
	private Timestamp requestedDate;
	private int customerId;
	private Status status;
	private int orderPaymentId;
	private ArrayList<ProductInOrder> productInOrderList;
	private ArrayList<OrderPayment> orderPaymentList;
	private Refund Refund;
	private Delivery deliery;
	private int brId;
	private int stId;
	private double total;
	
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
	public int getBrId() {
		return brId;
	}
	public void setBrId(int brId) {
		this.brId = brId;
	}

	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public int getOrderPaymentId() {
		return orderPaymentId;
	}
	public void setOrderPaymentId(int orderPaymentId) {
		this.orderPaymentId = orderPaymentId;
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
	public Order(int id, Date creationDate, Timestamp requestedDate, Customer customer, Status status,
			OrderPayment orderPayment, ArrayList<ProductInOrder> productInOrderList, Commons.Refund refund,
			Delivery deliery) {

		this.id = id;
		this.creationDate = creationDate;
		this.setRequestedDate(requestedDate);
		this.customerId = customerId;
		this.status = status;
		this.orderPaymentId = orderPaymentId;
		this.productInOrderList = productInOrderList;
		Refund = refund;
		this.deliery = deliery;
	}
	public Order(int id,Date creationDate, Timestamp requestedDate,int cId,int stId,int brId,double total)
	{
		this.id=id;
		this.creationDate=creationDate;
		this.setRequestedDate(requestedDate);
		this.customerId=cId;
		this.stId=stId;
		this.brId=brId;
		this.setTotal(total);
	}
	public Order()
	{

	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Timestamp getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(Timestamp requestedDate) {
		this.requestedDate = requestedDate;
	}
	public ArrayList<OrderPayment> getOrderPaymentList() {
		return orderPaymentList;
	}
	public void setOrderPaymentList(ArrayList<OrderPayment> orderPaymentList) {
		this.orderPaymentList = orderPaymentList;
	}
}
