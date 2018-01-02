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
	private int customerId;
	private int refundId;
	private int customerServiceId;
	


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

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getRefundId() {
		return refundId;
	}

	public void setRefundId(int refundId) {
		this.refundId = refundId;
	}

	public int getCustomerServiceId() {
		return customerServiceId;
	}

	public void setCustomerServiceId(int customerServiceId) {
		this.customerServiceId = customerServiceId;
	}

	public Complain(Date creationDate, String title, String details, int customerId,
			int customerServiceId) {
		super();
		this.creationDate = creationDate;
		this.title = title;
		this.details = details;
		this.customerId = customerId;
		this.customerServiceId = customerServiceId;
	}

	public Complain(int id, Date creationDate, String title, String details, int customerId,
			int customerServiceId) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		this.title = title;
		this.details = details;
		this.customerId = customerId;
		this.customerServiceId = customerServiceId;
	}

	@Override
	public void setRefund(int refundId) {
		// TODO Auto-generated method stub
		this.refundId = refundId;
	}
	
}
