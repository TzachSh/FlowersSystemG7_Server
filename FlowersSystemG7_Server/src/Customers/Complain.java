package Customers;

import java.io.Serializable;
import java.sql.Date;

import Branches.CustomerService;
import Commons.IRefundAble;
import Commons.Refund;


public class Complain implements IRefundAble, Serializable {
	private int id;
	private Date creationDate;
	private String title;
	private String details;
	private Customer customer;
	private Refund refund;
	private CustomerService customerService;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Refund getRefund() {
		return refund;
	}
	public void setRefund(Refund refund) {
		this.refund = refund;
	}
	public CustomerService getCustomerService() {
		return customerService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	public Complain(int id, Date creationDate, String title, String details, Customer customer, Refund refund,
			CustomerService customerService) {

		this.id = id;
		this.creationDate = creationDate;
		this.title = title;
		this.details = details;
		this.customer = customer;
		this.refund = refund;
		this.customerService = customerService;
	}
	
}
